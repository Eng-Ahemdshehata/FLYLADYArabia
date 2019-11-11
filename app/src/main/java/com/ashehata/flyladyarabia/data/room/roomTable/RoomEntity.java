package com.ashehata.flyladyarabia.data.room.roomTable;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ashehata.flyladyarabia.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "room")
public class RoomEntity implements Serializable {

    // To get context
    @Ignore
    private  Context context ;

    //*********************************
    @Ignore
    public RoomEntity (Context context){
        this.context = context ;
    }
    @Ignore
    public RoomEntity(String title , String description , int image){
        this.title = title ;
        this.description = description ;
        this.image = image ;
    }

    public RoomEntity(int id, String title , String description , int image){
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

    public  List<RoomEntity> populateData(){
        List<RoomEntity> list = new ArrayList<>();

        list.add(new RoomEntity(context.getString(R.string.sleeping_room)
                ,context.getString(R.string.sleeping_room_des)
                ,R.drawable.sleeping_room));

        //Log.v("rooooom",context.getString(R.string.sleeping_room));
        list.add(new RoomEntity("المطبـخ","hello from here", R.drawable.slider2));
        list.add(new RoomEntity("الحمامـ","hello from here", R.drawable.slider3));
        list.add(new RoomEntity("المعيشــة","hello from here", R.drawable.slider1));
        list.add(new RoomEntity("غرفـة","hello from here", R.drawable.slider2));
        list.add(new RoomEntity("غرفـة","hello from here", R.drawable.slider3));
        list.add(new RoomEntity("غرفـة","hello from here", R.drawable.slider1));
        //list.add(new RoomEntity("First one","hello from here", R.drawable.slider1));



        return list ;
    }
}
