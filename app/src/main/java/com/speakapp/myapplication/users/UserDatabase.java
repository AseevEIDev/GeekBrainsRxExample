package com.speakapp.myapplication.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "database.db";
  private static final int VERSION = 2;

  public static final String USERS_TABLE_NAME = "Users";
  //regionUserColumnNames
  public static final String ID = "_id";
  public static final String NAME = "name";
  public static final String AVATAR = "avatar";
  public static final String USER_ID = "user_id";
  //endregion

  public static final String[] USER_COLUMNS = new String[]{ID, NAME, AVATAR, USER_ID};

  private static String CREATE_USERS_TABLE = "create table " + USERS_TABLE_NAME
      + " ( " + ID + " integer primary key autoincrement, "
      + NAME + " TEXT NOT NULL unique, "
      + AVATAR + " TEXT NOT NULL, "
      + USER_ID + " integer NOT NULL DEFAULT 0)";

  public UserDatabase(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion < 2) {
      db.execSQL("ALTER TABLE " + USERS_TABLE_NAME + " ADD COLUMN " + USER_ID + " INTEGER NOT NULL DEFAULT 0;");
    }
  }

  //regionUsers
  public static void addUsers(List<User> users, SQLiteDatabase sqLiteDatabase) {
    for (User user : users) {
      ContentValues values = new ContentValues();
      values.put(NAME, user.getName());
      values.put(AVATAR, user.getAvatar());
      values.put(USER_ID, user.getUserId());
      sqLiteDatabase.insert(USERS_TABLE_NAME, null, values);
    }
  }

  public static void deleteUsers(SQLiteDatabase db) {
    db.delete(USERS_TABLE_NAME, null, null);
  }

  public static Single<ArrayList<User>> getAllUsers(SQLiteDatabase db) {
    return Single.fromCallable(() -> {
      ArrayList<User> users = new ArrayList<>();
      Cursor listCursor;
      try {
        listCursor = db.query(USERS_TABLE_NAME, USER_COLUMNS, null, null, null, null, null, null);
        listCursor.moveToFirst();
        if (!listCursor.isAfterLast()) {
          do {
            User user = new User(listCursor.getString(1), listCursor.getString(2), listCursor.getInt(3));
            users.add(user);
          }
          while (listCursor.moveToNext());
        }
        listCursor.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return users;
    });
  }
  //endregion
}
