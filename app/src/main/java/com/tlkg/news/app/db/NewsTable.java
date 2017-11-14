package com.tlkg.news.app.db;

/**
 * Created by wuxiaoqi on 2017/11/14.
 * 新闻表
 */

public interface NewsTable {
    String TABLE_NAME = "NewsTable";

    int NEWS_TABLE_ENABLE = 1;
    int NEWS_TABLE_DISABLE = 0;

    String _ID = "_id";
    String NAME = "name";
    String IS_ENABLE = "isEnable";
    String POSITION = "position";

    int _ID_INDEX = 0;
    int NAME_INDEX = 1;
    int ISENABLE_INDEX = 2;
    int POSITION_INDEX = 3;


    String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(" +
            _ID + " text primar key, " +
            NAME + " text, " +
            IS_ENABLE + " text default '1', " +
            POSITION + " text) ";

    String DROP_TABLE = "drop table if exists " + TABLE_NAME;
}
