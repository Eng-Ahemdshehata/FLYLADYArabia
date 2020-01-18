package com.ashehata.flyladyarabia.data.room.notificationTable;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ashehata.flyladyarabia.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "notification")
public class NotificationEntity implements Serializable {

    // To get context
    @Ignore
    private  Context context ;

    //*********************************
    @Ignore
    public NotificationEntity(Context context){
        this.context = context ;
    }
    @Ignore
    public NotificationEntity(String title , String description , int image , String routine){
        this.title = title ;
        this.description = description ;
        this.image = image ;
        this.routine = routine ;
    }

    public NotificationEntity(int id, String title , String description , int image){
        this.id = id ;
        this.title = title ;
        this.description = description ;
        this.image = image ;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title ;

    @ColumnInfo(name = "image")
    private int image ;

    @ColumnInfo(name = "description")
    private String description ;

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    @ColumnInfo(name = "routine")
    private String routine ;


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

}
