package com.tlkg.news.app.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.bean.NewsTableBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaoqi on 2017/11/14.
 */

public class NewsTableDao {

    private SQLiteDatabase db;
    /**
     * 初始化size大小
     */
    private static final int INIT_DATA_SIZE = 8;

    public NewsTableDao() {
        db = DatabaseHelper.getDatabase();
    }

    public void initNewsData() {
        String categoryId[] = NewsClientApplication.getAppContext().getResources().getStringArray(R.array.mobile_news_id);
        String categoryName[] = NewsClientApplication.getAppContext().getResources().getStringArray(R.array.mobile_news_name);
        for (int i = 0; i < INIT_DATA_SIZE; i++) {
            add(categoryId[i], categoryName[i], NewsTable.NEWS_TABLE_ENABLE, i);
        }
        for (int i = INIT_DATA_SIZE; i < categoryId.length; i++) {
            add(categoryId[i], categoryName[i], NewsTable.NEWS_TABLE_DISABLE, i);
        }
    }

    public void add(String _id, String name, int isEnable, int position) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NewsTable._ID, _id);
        contentValues.put(NewsTable.NAME, name);
        contentValues.put(NewsTable.IS_ENABLE, isEnable);
        contentValues.put(NewsTable.POSITION, position);
        db.insert(NewsTable.TABLE_NAME, null, contentValues);
    }

    public List<NewsTableBean> query(int isEnable) {
        Cursor cursor = db.query(NewsTable.TABLE_NAME, null, NewsTable.IS_ENABLE + "=?", new String[]{isEnable + ""}, null, null, null);
        List<NewsTableBean> list = new ArrayList<>();
        NewsTableBean bean;
        while (cursor.moveToNext()) {
            bean = new NewsTableBean();
            bean._id = cursor.getString(NewsTable._ID_INDEX);
            bean.name = cursor.getString(NewsTable.NAME_INDEX);
            bean.isEnable = cursor.getInt(NewsTable.ISENABLE_INDEX);
            bean.position = cursor.getInt(NewsTable.POSITION_INDEX);
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public List<NewsTableBean> queryAll() {
        Cursor cursor = db.query(NewsTable.TABLE_NAME, null, null, null, null, null, null);
        List<NewsTableBean> list = new ArrayList<>();
        NewsTableBean bean;
        while (cursor.moveToNext()) {
            bean = new NewsTableBean();
            bean._id = cursor.getString(NewsTable._ID_INDEX);
            bean.name = cursor.getString(NewsTable.NAME_INDEX);
            bean.isEnable = cursor.getInt(NewsTable.ISENABLE_INDEX);
            bean.position = cursor.getInt(NewsTable.POSITION_INDEX);
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public void removeAll() {
        db.delete(NewsTable.TABLE_NAME, null, null);
    }


}
