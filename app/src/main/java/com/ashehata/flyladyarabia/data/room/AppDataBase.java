package com.ashehata.flyladyarabia.data.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ashehata.flyladyarabia.data.room.notificationTable.NotificationEntity;
import com.ashehata.flyladyarabia.data.room.roomTable.RoomEntity;

import java.util.concurrent.Executors;
// Adding 2 tables in DB
@Database(entities = {RoomEntity.class, NotificationEntity.class } , version = 1 , exportSchema = false)
public abstract  class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;

    // First Dao
    public abstract Dao dataDao();

    // Second Dao
   // public abstract Dao notificationDao();

    public static void clearDataBase(Context context){
        //INSTANCE = null ;

        // Clear data base and load it again
        clearDataBaseTable(context);
    }

    private static void clearDataBaseTable(Context context) {

        final Context mContext = context ;
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                getInstance(mContext).dataDao().deleteAllRooms();
                RoomEntity roomEntity = new RoomEntity(mContext);
                getInstance(mContext).dataDao().insertAll(roomEntity.populateData());
                // Log.v("app",""+getInstance(context).dataDao().getAllRooms().size());

            }
        });
    }

    public synchronized static AppDataBase getInstance(Context context) {
        Log.v("app data"," before if");
        if (INSTANCE == null) {
            Log.v("app data","inside if ");
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }


    private static AppDataBase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDataBase.class,"my-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                       loadDataBase(context);
                    }
                })
                .build();

    }

    private static void loadDataBase(Context context) {
        final Context mContext = context ;
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                RoomEntity roomEntity = new RoomEntity(mContext);
                getInstance(mContext).dataDao().insertAll(roomEntity.populateData());

                // Log.v("app",""+getInstance(context).dataDao().getAllRooms().size());

            }
        });
    }


}
