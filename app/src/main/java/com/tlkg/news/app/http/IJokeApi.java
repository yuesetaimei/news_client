package com.tlkg.news.app.http;

import com.tlkg.news.app.bean.JokeCommentBean;
import com.tlkg.news.app.bean.JokeContentBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IJokeApi {

    /**
     * 获取段子正文内容
     * http://www.toutiao.com/api/article/feed/?category=essay_joke&as=A115C8457F69B85&cp=585F294B8845EE1
     */
    @GET("api/article/feed/?category=essay_joke")
    Observable<JokeContentBean> getJokeContent(
            @Query("max_behot_time") String maxBehotTime,
            @Query("as") String as,
            @Query("cp") String cp);

    /**
     * 获取段子评论
     * http://m.neihanshequ.com/api/get_essay_comments/?group_id=编号&count=数量&offset=偏移量
     */
    @GET("http://m.neihanshequ.com/api/get_essay_comments/?count=20")
    @Headers({"User-Agent:" + UrlConstant.USER_AGENT_MOBILE})
    Observable<JokeCommentBean> getJokeComment(
            @Query("group_id") String groupId,
            @Query("offset") int offset);
}
