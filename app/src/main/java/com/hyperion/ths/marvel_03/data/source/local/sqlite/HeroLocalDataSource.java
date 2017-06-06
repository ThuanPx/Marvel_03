package com.hyperion.ths.marvel_03.data.source.local.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.model.ImageHero;
import com.hyperion.ths.marvel_03.data.source.HeroDataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ths on 02/06/2017.
 */

public class HeroLocalDataSource implements HeroDataSource.LocalDataSource {
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public HeroLocalDataSource(Context context) {
        mDbHelper = new DatabaseHelper(context);
    }

    @Override
    public boolean insertHero(Hero hero) {
        if (hero == null) {
            return false;
        }
        mDatabase = mDbHelper.getWritableDatabase();
        long result = mDatabase.insert(DatabaseHelper.ContactEntry.TABLE_NAME, null,
                hero.getContentValues());
        return result != -1;
    }

    @Override
    public List<Hero> getAllHero() {
        mDatabase = mDbHelper.getReadableDatabase();
        List<Hero> heroList = new ArrayList<>();
        String[] projection = {
                DatabaseHelper.ContactEntry.COLUMN_ID, DatabaseHelper.ContactEntry.COLUMN_NAME,
                DatabaseHelper.ContactEntry.COLUMN_DES, DatabaseHelper.ContactEntry.COLUMN_IMAGE
        };

        Cursor cursor =
                mDatabase.query(DatabaseHelper.ContactEntry.TABLE_NAME, projection, null, null,
                        null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            heroList = new ArrayList<>();
            do {
                Hero hero = new Hero();
                ImageHero imageHero = new ImageHero();
                hero.setId(cursor.getInt(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_ID)));
                hero.setName(cursor.getString(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_NAME)));
                hero.setDescription(cursor.getString(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_DES)));
                imageHero.setImageUrl(cursor.getString(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_IMAGE)));
                hero.setImageHero(imageHero);
                heroList.add(hero);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        mDatabase.close();
        return heroList;
    }

    @Override
    public List<Hero> getHeroByName(String name) {
        mDatabase = mDbHelper.getReadableDatabase();
        List<Hero> heroList = new ArrayList<>();
        String[] projection = {
                DatabaseHelper.ContactEntry.COLUMN_ID, DatabaseHelper.ContactEntry.COLUMN_NAME,
                DatabaseHelper.ContactEntry.COLUMN_DES, DatabaseHelper.ContactEntry.COLUMN_IMAGE
        };
        String whereClause = DatabaseHelper.ContactEntry.COLUMN_NAME + " LIKE ?";
        String[] whereArgs = { name };
        Cursor cursor =
                mDatabase.query(DatabaseHelper.ContactEntry.TABLE_NAME, projection, whereClause,
                        whereArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            heroList = new ArrayList<>();
            do {
                Hero hero = new Hero();
                ImageHero imageHero = new ImageHero();
                hero.setId(cursor.getInt(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_ID)));
                hero.setName(cursor.getString(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_NAME)));
                hero.setDescription(cursor.getString(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_DES)));
                imageHero.setImageUrl(cursor.getString(
                        cursor.getColumnIndex(DatabaseHelper.ContactEntry.COLUMN_IMAGE)));
                hero.setImageHero(imageHero);
                heroList.add(hero);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        mDatabase.close();
        return heroList;
    }

    @Override
    public boolean deleteHero(Hero hero) {
        mDatabase = mDbHelper.getWritableDatabase();
        long result = mDatabase.delete(DatabaseHelper.ContactEntry.TABLE_NAME,
                DatabaseHelper.ContactEntry.COLUMN_ID + " = ?",
                new String[] { String.valueOf(hero.getId()) });
        mDatabase.close();
        return result != -1;
    }
}
