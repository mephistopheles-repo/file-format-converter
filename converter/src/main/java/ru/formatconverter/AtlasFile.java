package ru.formatconverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 29.12.13
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class AtlasFile {
    private String imageName;
    List<Record> records = new ArrayList<>();

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public int getCount(){
       return records.size();
    }
}
