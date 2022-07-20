package me.solo_team.futureleader.Objects;

import java.util.List;

public class FieldsStuff {
    public List<Field> fields;
    public List<Field> fieldsCanEdit;
    public int maxSize;

    public FieldsStuff(List<Field> fields, List<Field> fieldsCanEdit, int maxSize){
        this.fields = fields;
        this.fieldsCanEdit = fieldsCanEdit;
        this.maxSize = maxSize;
    }
}
