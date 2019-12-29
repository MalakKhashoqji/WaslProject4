package com.company_name.wasl_project4.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class addUserClass  extends SQLiteOpenHelper {

    private static final  String TAG ="Organizer_Info";
    private static  final String TABLE_NAME = "User";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Wasl";
    //My Column
    private static final String ID_COLUMN ="ID";

    private static final String EMAIL_COLUMN ="Email";
    private static final String USERNAME_COLOMN = "Username";
    private static final String PHONE="phone_num";
    private static final  String PASSWORD="Password";
    private static final  String TYPE="Type";//orgnizer or student or staff
    private static final  String NAME="Name";


    public addUserClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);      }


    public addUserClass(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, TABLE_NAME, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // here we are going to create our table

        String createUserTable = "CREATE TABLE " + TABLE_NAME + " (ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMAIL_COLUMN +USERNAME_COLOMN+PHONE+PASSWORD+TYPE+NAME+ " TEXT)";
                db.execSQL(createUserTable);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }





    public void addorganizer(UserClass user) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_COLUMN, user.getEmail());
        contentValues.put(EMAIL_COLUMN, user.getEmail());
        contentValues.put(USERNAME_COLOMN, user.getUsername());
        contentValues.put(PHONE, user.getPhone_number());
        contentValues.put(PASSWORD, user.getPassword());
        contentValues.put(TYPE, user.getType());
        contentValues.put(NAME, user.getName());



        db.insert(TABLE_NAME, null, contentValues);

        db.close();


        long result = db.insert(TABLE_NAME, null, contentValues);

    }


    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<UserClass> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                ID_COLUMN,
                EMAIL_COLUMN,
                USERNAME_COLOMN,
                PHONE,
                PASSWORD,
                TYPE,
                NAME,
        };

        // sorting orders
        String sortOrder =
                NAME + " ASC";
        List<UserClass> userList = new ArrayList<UserClass>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserClass user = new UserClass();
                user.setID(cursor.getString(cursor.getColumnIndex(ID_COLUMN)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_COLUMN)));
                user.setName(cursor.getString(cursor.getColumnIndex(USERNAME_COLOMN)));
                user.setPhone_number(cursor.getString(cursor.getColumnIndex(PHONE)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                user.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                user.setName(cursor.getString(cursor.getColumnIndex(NAME)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }




    /**
     * This method to update user record
     *
     *
     */
    public void updateUser(UserClass user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_COLUMN, user.getEmail());
        contentValues.put(EMAIL_COLUMN, user.getEmail());
        contentValues.put(USERNAME_COLOMN, user.getUsername());
        contentValues.put(PHONE, user.getPhone_number());
        contentValues.put(PASSWORD, user.getPassword());
        contentValues.put(TYPE, user.getType());
        contentValues.put(NAME, user.getName());

        // updating row
        db.update(TABLE_NAME, contentValues, ID_COLUMN + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }


    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(UserClass user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_NAME, ID_COLUMN + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }


}
