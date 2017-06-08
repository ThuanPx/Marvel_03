package com.hyperion.ths.marvel_03.data.source.local.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hyperion.ths.marvel_03.data.model.Hero;
import com.hyperion.ths.marvel_03.data.model.ImageHero;
import com.hyperion.ths.marvel_03.data.source.HeroDataSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.operators.observable.ObservableJust;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ths on 02/06/2017.
 */

public class HeroLocalDataSource implements HeroDataSource.LocalDataSource {
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    private final String[] projection = {
            DatabaseHelper.ContactEntry.COLUMN_ID, DatabaseHelper.ContactEntry.COLUMN_NAME,
            DatabaseHelper.ContactEntry.COLUMN_DES, DatabaseHelper.ContactEntry.COLUMN_IMAGE
    };

    public HeroLocalDataSource(Context context) {
        mDbHelper = new DatabaseHelper(context);
    }

    @Override
    public Observable<Void> insertHero(@android.support.annotation.NonNull final Hero hero) {
        mDatabase = mDbHelper.getWritableDatabase();

        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
                mDatabase.insert(DatabaseHelper.ContactEntry.TABLE_NAME, null,
                        hero.getContentValues());
                mDatabase.close();
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<Hero>> getAllHero() {
        mDatabase = mDbHelper.getReadableDatabase();
        return Observable.create(new ObservableOnSubscribe<List<Hero>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Hero>> e) throws Exception {
                List<Hero> heroList = new ArrayList<>();
                Cursor cursor =
                        mDatabase.query(DatabaseHelper.ContactEntry.TABLE_NAME, projection, null,
                                null, null, null, null);
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
                e.onNext(heroList);
                mDatabase.close();
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Hero> getHeroByName(@android.support.annotation.NonNull final String name) {
        mDatabase = mDbHelper.getReadableDatabase();
        return Observable.defer(
                new ObservableJust<ObservableSource<? extends Hero>>(new Observable<Hero>() {
                    @Override
                    protected void subscribeActual(Observer<? super Hero> observer) {
                        String whereClause = DatabaseHelper.ContactEntry.COLUMN_NAME + " LIKE ?";
                        String[] whereArgs = { name };
                        Cursor cursor =
                                mDatabase.query(DatabaseHelper.ContactEntry.TABLE_NAME, projection,
                                        whereClause, whereArgs, null, null, null);
                        Hero hero = new Hero();
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                ImageHero imageHero = new ImageHero();
                                hero.setId(cursor.getInt(cursor.getColumnIndex(
                                        DatabaseHelper.ContactEntry.COLUMN_ID)));
                                hero.setName(cursor.getString(cursor.getColumnIndex(
                                        DatabaseHelper.ContactEntry.COLUMN_NAME)));
                                hero.setDescription(cursor.getString(cursor.getColumnIndex(
                                        DatabaseHelper.ContactEntry.COLUMN_DES)));
                                imageHero.setImageUrl(cursor.getString(cursor.getColumnIndex(
                                        DatabaseHelper.ContactEntry.COLUMN_IMAGE)));
                                hero.setImageHero(imageHero);
                            } while (cursor.moveToNext());
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        observer.onNext(hero);
                        mDatabase.close();
                        observer.onComplete();
                    }
                }));
    }

    @Override
    public Observable<Void> deleteHero(@android.support.annotation.NonNull final Hero hero) {
        mDatabase = mDbHelper.getWritableDatabase();
        return Observable.defer(
                new ObservableJust<ObservableSource<? extends Void>>(new Observable<Void>() {

                    @Override
                    protected void subscribeActual(Observer<? super Void> observer) {
                        mDatabase.delete(DatabaseHelper.ContactEntry.TABLE_NAME,
                                DatabaseHelper.ContactEntry.COLUMN_ID + " = ?",
                                new String[] { String.valueOf(hero.getId()) });
                        mDatabase.close();
                        observer.onComplete();
                    }
                }));
    }
}
