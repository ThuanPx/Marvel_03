package com.hyperion.ths.marvel_03.data.source.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by ths on 02/06/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int D_VERSION = 1;
    public static final String DATABASE_NAME = "hero.db";
    private static final String SQL_CREATE_CONTACTS = "CREATE TABLE "
            + DatabaseHelper.ContactEntry.TABLE_NAME
            + "("
            + DatabaseHelper.ContactEntry.COLUMN_AUTOID
            + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + DatabaseHelper.ContactEntry.COLUMN_ID
            + " INTEGER ,"
            + DatabaseHelper.ContactEntry.COLUMN_NAME
            + " TEXT ,"
            + DatabaseHelper.ContactEntry.COLUMN_DES
            + " TEXT ,"
            + DatabaseHelper.ContactEntry.COLUMN_IMAGE
            + " TEXT )";
    private static final String SQL_DELETE_CONTACTS =
            "DROP TABLE IF EXISTS " + DatabaseHelper.ContactEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, D_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CONTACTS);
        db.execSQL(SQL_CREATE_CONTACTS);
    }

    /**
     * Inner class that defines the table user contents
     */
    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_hero";
        public static final String COLUMN_AUTOID = "autoid";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DES = "description";
        public static final String COLUMN_IMAGE = "imageUrl";
    }
}

