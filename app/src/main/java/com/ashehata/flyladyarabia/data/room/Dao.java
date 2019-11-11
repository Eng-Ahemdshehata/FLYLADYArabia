package com.ashehata.flyladyarabia.data.room;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ashehata.flyladyarabia.data.room.notificationTable.NotificationEntity;
import com.ashehata.flyladyarabia.data.room.roomTable.RoomEntity;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM room")
    LiveData<List<RoomEntity>> getAllRooms();

    @Query("SELECT * FROM notification")
    LiveData<List<NotificationEntity>> getAllNotification();



    @Query("DELETE FROM notification WHERE id = :userId  ")
    void delete(int userId);

    @Query("DELETE FROM notification ")
    void deleteAllNotification();

    @Insert
    void insertAll(List<RoomEntity> list);

    @Query("DELETE FROM room ")
    void deleteAllRooms();

    @Insert
    void insertAllNotification(List<NotificationEntity> list);

    @Insert
    void insert(NotificationEntity notificationEntity);



}
