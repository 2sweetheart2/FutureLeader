package me.solo_team.futureleader.stuff.media_player_stuff.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.media.session.MediaButtonReceiver;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.upstream.DataSource;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import me.solo_team.futureleader.Constants;
import me.solo_team.futureleader.Objects.Audio;
import me.solo_team.futureleader.R;
import me.solo_team.futureleader.ui.menu.statical.Media.MusicPlayer;

public class PlayerService extends Service {
    private final int NOTIFICATION_ID = 404;
    private final String NOTIFICATION_DEFAULT_CHANNEL_ID = "медиа контроллер";

    private final MediaMetadataCompat.Builder metadataBuilder = new MediaMetadataCompat.Builder();

    private final PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder().setActions(
            PlaybackStateCompat.ACTION_PLAY
                    | PlaybackStateCompat.ACTION_STOP
                    | PlaybackStateCompat.ACTION_PAUSE
                    | PlaybackStateCompat.ACTION_PLAY_PAUSE
                    | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
    );

    private MediaSessionCompat mediaSession;

    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    private boolean audioFocusRequested = false;

    private SimpleExoPlayer exoPlayer;
    private ExtractorsFactory extractorsFactory;
    private DataSource.Factory dataSourceFactory;


    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_DEFAULT_CHANNEL_ID, getString(R.string.notification_channel_name), NotificationManagerCompat.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setOnAudioFocusChangeListener(audioFocusChangeListener)
                    .setAcceptsDelayedFocusGain(false)
                    .setWillPauseWhenDucked(true)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mediaSession = new MediaSessionCompat(this, "PlayerService");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setCallback(mediaSessionCallback);

        Context appContext = getApplicationContext();

        Intent activityIntent = new Intent(appContext, MusicPlayer.class);
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null, appContext, MediaButtonReceiver.class);

        PendingIntent pendingIntent = null;
        PendingIntent pendingIntent2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, activityIntent, PendingIntent.FLAG_MUTABLE);
            pendingIntent2 = PendingIntent.getActivity
                    (this, 0, mediaButtonIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, activityIntent, PendingIntent.FLAG_ONE_SHOT);
            pendingIntent2 = PendingIntent.getActivity
                    (this, 0, mediaButtonIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        mediaSession.setSessionActivity(pendingIntent);

        mediaSession.setMediaButtonReceiver(pendingIntent2);

        exoPlayer = (SimpleExoPlayer) new ExoPlayer.Builder(getBaseContext()).build();
        exoPlayer.addListener(exoPlayerListener);
        Constants.exoPlayer = exoPlayer;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mediaSession, intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSession.release();
        exoPlayer.release();
    }

    private MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {

        private String currentUrl;
        int currentState = PlaybackStateCompat.STATE_STOPPED;

         @Override
        public void onCustomAction(String action, Bundle extras) {
             super.onCustomAction(action, extras);
             switch (action) {
                 case "update":{
                     Audio audio = Constants.audioCache.getCurrentAudio();
                     exoPlayer.stop(true);
                     updateMetadataFromTrack(audio);
                     prepareToPlay(audio.url);
                     refreshState(PlaybackStateCompat.STATE_PLAYING, chekDuratation(audio));
                 }
                 case "update_logo": {
                     Audio track = Constants.audioCache.getCurrentAudio();
                     System.err.println("TRY TO UPDATE LOGO");
                     updateMetadataFromTrack(track);
                     refreshState(currentState,exoPlayer.getCurrentPosition());
                     break;
                 }
                 case "die":{
                     onStop();
                     unregisterReceiver(becomingNoisyReceiver);
                     stopSelf();
                     break;
                 }
             }
         }

        @Override
        public void onPlay() {
            if (!exoPlayer.getPlayWhenReady()) {
                startService(new Intent(getApplicationContext(), PlayerService.class));

                Audio audio = Constants.audioCache.getCurrentAudio();


                updateMetadataFromTrack(audio);
                prepareToPlay(audio.url);

                if (!audioFocusRequested) {
                    audioFocusRequested = true;

                    int audioFocusResult;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        audioFocusResult = audioManager.requestAudioFocus(audioFocusRequest);
                    } else {
                        audioFocusResult = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                    }
                    if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                        return;
                }

                mediaSession.setActive(true); // Сразу после получения фокуса

                registerReceiver(becomingNoisyReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));

                exoPlayer.setPlayWhenReady(true);
            }

            mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
            currentState = PlaybackStateCompat.STATE_PLAYING;
            lastDuratation.clear();

            refreshNotificationAndForegroundStatus(currentState);
        }

        @Override
        public void onPause() {
            exoPlayer.pause();
            if (becomingNoisyReceiver.isOrderedBroadcast())
                unregisterReceiver(becomingNoisyReceiver);
            lastDuratation.put(Constants.audioCache.getCurrentAudio(), exoPlayer.getCurrentPosition());

            mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
            currentState = PlaybackStateCompat.STATE_PAUSED;

            refreshNotificationAndForegroundStatus(currentState);
        }

        @Override
        public void onStop() {
            exoPlayer.setPlayWhenReady(false);

            // Все, больше мы не "главный" плеер, уходим со сцены
            mediaSession.setActive(false);

            // Сообщаем новое состояние
            mediaSession.setPlaybackState(
                    stateBuilder.setState(PlaybackStateCompat.STATE_STOPPED,
                            PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
            if (becomingNoisyReceiver.isOrderedBroadcast())
                unregisterReceiver(becomingNoisyReceiver);
            audioManager.abandonAudioFocus(audioFocusChangeListener);
            stopSelf();
        }

        @Override
        public void onSkipToNext() {
            Audio audio = Constants.audioCache.next();
            updateMetadataFromTrack(audio);
            prepareToPlay(audio.url);
            refreshState(PlaybackStateCompat.STATE_PLAYING, chekDuratation(audio));

        }

        Map<Audio, Long> lastDuratation = new HashMap<>();


        private long chekDuratation(Audio track) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    if (lastDuratation.containsKey(track)) {
                        return lastDuratation.get(track);
                    }
                    lastDuratation.clear();
                    return 0;
                } catch (NullPointerException ignored) {
                    return 0;
                }
            }
            return 0;
        }

        private void refreshState(int state, long position) {
            mediaSession.setPlaybackState(stateBuilder.setState(state, position, 1).build());
            currentState = state;
            refreshNotificationAndForegroundStatus(currentState);
        }

        @Override
        public void onSkipToPrevious() {
            Audio audio = Constants.audioCache.previous();
            prepareToPlay(audio.url);
            updateMetadataFromTrack(audio);
            refreshState(PlaybackStateCompat.STATE_PLAYING, chekDuratation(audio));
        }

        private void prepareToPlay(String url) {
            if (!url.equals(currentUrl)) {
                exoPlayer.stop(true);
                currentUrl = url;
                MediaItem mediaItem = MediaItem.fromUri(currentUrl);
                exoPlayer.addMediaItem(mediaItem);
                exoPlayer.prepare();
                Constants.audioCache.getCurrentAudio().duratation = exoPlayer.getDuration();
            }
        }

        private void updateMetadataFromTrack(Audio audio) {
            metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_ART, audio.imageBitmap);
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, audio.name);
            metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, audio.author);
            metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, audio.duratation);
            mediaSession.setMetadata(metadataBuilder.build());
        }
    };
    float volume;

    private final AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = focusChange -> {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // Фокус предоставлен.
                // Например, был входящий звонок и фокус у нас отняли.
                // Звонок закончился, фокус выдали опять
                // и мы продолжили воспроизведение.
                exoPlayer.setVolume(volume);
                if (!exoPlayer.isPlaying())
                    mediaSessionCallback.onPlay();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Фокус отняли, потому что какому-то приложению надо
                // коротко "крякнуть".
                // Например, проиграть звук уведомления или навигатору сказать
                // "Через 50 метров поворот направо".
                // В этой ситуации нам разрешено не останавливать вопроизведение,
                // но надо снизить громкость.
                // Приложение не обязано именно снижать громкость,
                // можно встать на паузу, что мы здесь и делаем.
                volume = exoPlayer.getVolume();
                exoPlayer.setVolume(volume / 3);
                break;
            default:
                // Фокус совсем отняли.
                mediaSessionCallback.onPause();
                break;
        }
    };

    private final BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                mediaSessionCallback.onPause();
            }
        }
    };

    private final ExoPlayer.EventListener exoPlayerListener = new ExoPlayer.EventListener() {


        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            if (playWhenReady && playbackState == ExoPlayer.STATE_ENDED) {
                mediaSessionCallback.onSkipToNext();
            }
        }


        @Override
        public void onPlaybackParametersChanged(@NotNull PlaybackParameters playbackParameters) {
        }
    };

    @NotNull
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerServiceBinder();
    }


    public class PlayerServiceBinder extends Binder {
        public MediaSessionCompat.Token getMediaSessionToken() {
            return mediaSession.getSessionToken();
        }
    }

    private void refreshNotificationAndForegroundStatus(int playbackState) {
        switch (playbackState) {
            case PlaybackStateCompat.STATE_PLAYING: {
                startForeground(NOTIFICATION_ID, getNotification(playbackState));
                break;
            }
            case PlaybackStateCompat.STATE_PAUSED: {
                NotificationManagerCompat.from(PlayerService.this).notify(NOTIFICATION_ID, getNotification(playbackState));
                stopForeground(false);
                break;
            }

            default: {
                stopForeground(true);
                break;
            }
        }
    }

    private Notification getNotification(int playbackState) {
        NotificationCompat.Builder builder = MediaStyleHelper.from(this, mediaSession);
        builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_previous, getString(R.string.previous_), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)));

        if (playbackState == PlaybackStateCompat.STATE_PLAYING)
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_pause, getString(R.string.pause), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
        else
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, getString(R.string.play), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));

        builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_next, getString(R.string.next_), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT)));
        builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                // В компактном варианте показывать Action с данным порядковым номером.
                // В нашем случае это play/pause.
                .setShowActionsInCompactView(1)
                // Отображать крестик в углу уведомления для его закрытия.
                // Это связано с тем, что для API < 21 из-за ошибки во фреймворке
                // пользователь не мог смахнуть уведомление foreground-сервиса
                // даже после вызова stopForeground(false).
                // Так что это костыль.
                // На API >= 21 крестик не отображается, там просто смахиваем уведомление.
                .setShowCancelButton(true)
                // Указываем, что делать при нажатии на крестик или смахивании
                .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                                this,
                                PlaybackStateCompat.ACTION_STOP))
                // Передаем токен. Это важно для Android Wear. Если токен не передать,
                // кнопка на Android Wear будет отображаться, но не будет ничего делать
                .setMediaSession(mediaSession.getSessionToken()));
        builder.setSmallIcon(R.drawable.resize_300x0);
        builder.setColor(ContextCompat.getColor(this, R.color.secondary));
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_DEFAULT_CHANNEL_ID);
        builder.setShowWhen(false);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setOnlyAlertOnce(true);
        return builder.build();
    }
}
