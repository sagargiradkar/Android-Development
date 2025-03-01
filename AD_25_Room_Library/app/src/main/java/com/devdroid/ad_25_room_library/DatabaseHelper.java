package com.devdroid.ad_25_room_library;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Expense.class,exportSchema = false,version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "expensedb";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDb(Context context){
        if(instance == null){
            instance = Room.databaseBuilder( context,DatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract ExpenseDAO expenseDAO();
}
