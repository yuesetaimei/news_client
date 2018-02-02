package com.tlkg.news.app.http;

/**
 * Created by wuxiaoqi on 2017/9/22.
 * 请求URL
 */

public interface UrlConstant {

    public static final String USER_AGENT_MOBILE = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Mobile Safari/537.36";

    public static final String USER_AGENT_PC = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";


    /**
     * Banner图片
     */
    String API_TING = "https://tingapi.ting.baidu.com/v1/restserver/";


    /**
     * 广告页图片
     */
    String API_ADPIC = "http://route.showapi.com/1377-1/";

    /**
     * 干货集中营Api
     */
    String API_GANK = "http://gank.io/api/data/";

    /**
     * 豆瓣Api
     */
    String API_DAOUBAN = "https://api.douban.com/";

    /**
     * 今日头条
     */
    String API_TOUTIAO = "http://toutiao.com/";
}
