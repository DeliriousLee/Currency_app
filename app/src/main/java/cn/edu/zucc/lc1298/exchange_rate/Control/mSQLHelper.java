package cn.edu.zucc.lc1298.exchange_rate.Control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lee on 2017/7/11.
 */

public class mSQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Currency.db";
    private static final int DATABASE_VERSION=1;//写死版本号后，将只对一个数据库进行操作，不会进行更新创建等操作，如要进行更新则对版本号进行更改
    public static final String CREATE_HISTORY="create table searchRecords (\n" +
            "countryId            INTEGER                        not null,\n" +
            "moneyCode            text                    not null,\n" +
            "countryName          text,\n"+
            "time                 text,\n" +
            "primary key (countryId,time)\n" +
            ")";
    public static final String CREATE_CURRENCY=
            "create table currencyRecords (\n" +
            "recordId           INTEGER  not null,\n"+
            "currency             text,\n" +
            "currencyTime           text  not null,\n" +
            "primary key (recordId)\n" +
            ")";
    public mSQLHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CURRENCY);
        db.execSQL(CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
