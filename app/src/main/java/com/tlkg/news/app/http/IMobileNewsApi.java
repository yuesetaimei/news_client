package com.tlkg.news.app.http;

import com.tlkg.news.app.bean.MultiNewsArticleBean;
import com.tlkg.news.app.bean.NewsCommentBean;
import com.tlkg.news.app.bean.WendaArticleBean;
import com.tlkg.news.app.bean.WendaContentBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Meiji on 2017/5/17.
 * 参考 :　https://github.com/hrscy/TodayNews/blob/master/news.json
 */

public interface IMobileNewsApi {

    String HOST = "http://is.snssdk.com/";

    public static final String USER_AGENT_MOBILE = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Mobile Safari/537.36";

    /**
     * 获取个性化新闻
     * 深圳 http://is.snssdk.com/api/news/feed/v58/?iid=5034850950&device_id=6096495334&category=news_society
     * 深圳 http://lf.snssdk.com/api/news/feed/v58/?iid=12507202490&device_id=37487219424&category=news_society
     * 天津 http://ib.snssdk.com/api/news/feed/v58/?
     * 北京 http://iu.snssdk.com/api/news/feed/v58/?
     *
     * @param iid      用户ID
     * @param deviceId 设备ID
     * @param category 新闻/图片/视频栏目
     */
    @GET("http://is.snssdk.com/api/news/feed/v58/")
    Call<ResponseBody> getNewsArticle(
            @Query("iid") String iid,
            @Query("device_id") String deviceId,
            @Query("category") String category);

    @GET("http://is.snssdk.com/api/news/feed/v62/?iid=5034850950&device_id=6096495334&refer=1&count=20&aid=13")
    Observable<MultiNewsArticleBean> getNewsArticle(
            @Query("category") String category,
            @Query("max_behot_time") String maxBehotTime);

    @GET("http://lf.snssdk.com/api/news/feed/v62/?iid=12507202490&device_id=37487219424&refer=1&count=20&aid=13")
    Observable<MultiNewsArticleBean> getNewsArticle2(
            @Query("category") String category,
            @Query("max_behot_time") String maxBehotTime);

    /**
     * 获取新闻评论
     * 按热度排序
     * http://is.snssdk.com/article/v53/tab_comments/?group_id=6314103921648926977&offset=0&tab_index=0
     * 按时间排序
     * http://is.snssdk.com/article/v53/tab_comments/?group_id=6314103921648926977&offset=0&tab_index=1
     *
     * @param groupId 新闻ID
     * @param offset  偏移量
     */
    @GET("http://is.snssdk.com/article/v53/tab_comments/")
    Observable<NewsCommentBean> getNewsComment(
            @Query("group_id") String groupId,
            @Query("offset") int offset);


    /**
     * 获取头条问答标题等信息
     * http://is.snssdk.com/wenda/v1/native/feedbrow/?category=question_and_answer&wd_version=5&count=20&max_behot_time=1495245397?iid=10344168417&device_id=36394312781
     *
     * @param maxBehotTime 时间轴
     */
    @GET("http://is.snssdk.com/wenda/v1/native/feedbrow/?iid=10344168417&device_id=36394312781&category=question_and_answer&wd_version=5&count=20&aid=13")
    Observable<WendaArticleBean> getWendaArticle(
            @Query("max_behot_time") String maxBehotTime);

    /**
     * 获取头条问答优质回答
     * http://is.snssdk.com/wenda/v1/question/brow/?iid=10344168417&device_id=36394312781
     *
     * @param qid 问答ID
     */
    @POST("http://is.snssdk.com/wenda/v1/question/brow/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNiceContent(@Field("qid") String qid);

    /**
     * 获取头条问答优质回答(加载更多)
     * http://is.snssdk.com/wenda/v1/question/loadmore/?iid=10344168417&device_id=36394312781
     *
     * @param qid    问答ID
     * @param offset 偏移量
     */
    @POST("http://is.snssdk.com/wenda/v1/question/loadmore/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNiceContentLoadMore(
            @Field("qid") String qid,
            @Field("offset") int offset);

    /**
     * 获取头条问答普通回答
     * http://is.snssdk.com/wenda/v1/questionother/brow/?iid=10344168417&device_id=36394312781
     *
     * @param qid 问答ID
     */
    @POST("http://is.snssdk.com/wenda/v1/questionother/brow/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNormalContent(@Field("qid") String qid);

    /**
     * 获取头条问答普通回答(加载更多)
     * http://is.snssdk.com/wenda/v1/questionother/loadmore/?iid=10344168417&device_id=36394312781
     *
     * @param qid    问答ID
     * @param offset 偏移量
     */
    @POST("http://is.snssdk.com/wenda/v1/questionother/loadmore/?iid=10344168417&device_id=36394312781")
    @FormUrlEncoded
    Observable<WendaContentBean> getWendaNormalContentLoadMore(
            @Field("qid") String qid,
            @Field("offset") int offset);

    /**
     * 获取头条问答回答正文
     */
    @GET
    @Headers("User-Agent:" + USER_AGENT_MOBILE)
    Observable<ResponseBody> getWendaAnsDetail(@Url String url);
}
