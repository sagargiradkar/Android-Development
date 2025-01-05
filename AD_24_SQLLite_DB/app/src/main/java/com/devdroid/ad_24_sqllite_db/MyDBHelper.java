package com.devdroid.ad_24_sqllite_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactsDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create the contacts table
        // Query :: CREATE TABLE contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_PHONE + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Drop the contacts table if it exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Recreate the contacts table
        onCreate(sqLiteDatabase);
    }

    public void addContact(String name,String phone_no){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_PHONE,phone_no);
        db.insert(TABLE_NAME,null,values);
        db.close();

    }

    public void deleteContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<ContactModel> getAllContacts(){
        ArrayList<ContactModel> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //Query :: SELECT * FROM contacts
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<ContactModel> arrContacts = new ArrayList<>();

        while(cursor.moveToNext()){
            ContactModel model = new ContactModel();
            model.id = cursor.getInt(0);
            model.name = cursor.getString(1);
            model.phone_no = cursor.getString(2);
            arrContacts.add(model);
        }
        return arrContacts;
    }

    //Update database
    public void updateContact(ContactModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        //update table
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,model.name);
        values.put(COLUMN_PHONE,model.phone_no);
        db.update(TABLE_NAME,values,COLUMN_ID+"=?",new String[]{String.valueOf(model.id)});


    }

    //delete all record
    public void deleteAllContacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }


}
