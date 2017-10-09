package com.tlkg.news.app.bean;

import java.util.List;

/**
 * Created by wuxiaoqi on 2017/9/29.
 */

public class AndroidBean {

    /**
     * error : false
     * results : [{"_id":"59c3d43a421aa9727ddd19c0","createdAt":"2017-09-21T23:01:14.453Z","desc":"灵活的ShadowView，可替代CardView使用","images":["http://img.gank.io/5ca55569-7b78-4691-aa1a-a1165eb8a164"],"publishedAt":"2017-09-29T11:21:16.116Z","source":"web","type":"Android","url":"https://github.com/loopeer/shadow","used":true,"who":null},{"_id":"59c3db8d421aa9727ddd19c1","createdAt":"2017-09-21T23:32:29.351Z","desc":"Android Transition学习笔记","images":["http://img.gank.io/a553aa37-9a54-4bb6-bdc2-9bfc2737e065"],"publishedAt":"2017-09-29T11:21:16.116Z","source":"web","type":"Android","url":"http://rkhcy.github.io/2017/09/21/TransitionNote/","used":true,"who":"HuYounger"},{"_id":"59cc8cac421aa972879d121a","createdAt":"2017-09-28T13:46:20.656Z","desc":"Android三种姿势带你玩转360度全景图功能","publishedAt":"2017-09-29T11:21:16.116Z","source":"web","type":"Android","url":"https://github.com/CN-ZPH/Three360panorama","used":true,"who":"张鹏辉"},{"_id":"59cc9df6421aa9727ddd19db","createdAt":"2017-09-28T15:00:06.42Z","desc":"比官方更像官方的NationalGeographic国家地理-每日精选客户端，重要的是用了最新的Android Architecture Components(Lifecycle) & Kotlin，给大家探路填坑","publishedAt":"2017-09-29T11:21:16.116Z","source":"web","type":"Android","url":"https://www.github.com/wheat7/NationalGeographic","used":true,"who":"麦田哥"},{"_id":"59b74f4a421aa911847a0390","createdAt":"2017-09-12T11:06:50.144Z","desc":"基于Tesseract-OCR实现自动扫描识别手机号","images":["http://img.gank.io/d2c557fe-7d3b-4330-9c51-9c427178f633"],"publishedAt":"2017-09-26T12:12:07.813Z","source":"web","type":"Android","url":"https://github.com/simplezhli/Tesseract-OCR-Scanner","used":true,"who":"唯鹿"},{"_id":"59b9da64421aa911847a039a","createdAt":"2017-09-14T09:24:52.83Z","desc":"详细讲解Launcher整个开发流程的系列教程。","publishedAt":"2017-09-26T12:12:07.813Z","source":"web","type":"Android","url":"http://www.codemx.cn/tags/Launcher/","used":true,"who":"YUCHUAN"},{"_id":"59bbe41f421aa9118887ac26","createdAt":"2017-09-15T22:30:55.891Z","desc":"Android7.1 的 HashMap工作原理","publishedAt":"2017-09-26T12:12:07.813Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/1f0b8175ce7c","used":true,"who":"Niekon"},{"_id":"59c89a0d421aa9727fdb25dc","createdAt":"2017-09-25T13:54:21.702Z","desc":"Drakeet 的 telegram 广播频道，专用于分享一些技术上的看法、体验心得、一些库 / 项目的更新状况及解读（比如 AS、support lib），有时也会分享一些好玩的事物和文章链接","publishedAt":"2017-09-26T12:12:07.813Z","source":"web","type":"Android","url":"https://t.me/drakeets","used":true,"who":"drakeet"},{"_id":"597e016c421aa90ca209c523","createdAt":"2017-07-30T23:55:24.154Z","desc":"Android终端","publishedAt":"2017-09-21T13:27:15.675Z","source":"chrome","type":"Android","url":"https://github.com/termux/termux-app","used":true,"who":"Jason"},{"_id":"597f2861421aa90ca209c527","createdAt":"2017-07-31T20:53:53.217Z","desc":"Google从 API 21 新增了接口 android.app.usage , 通过这个api我们可以统计到每个app的使用情况，启动次数，启动时间等，也可以判断是否前后台，比较方便。","images":["http://img.gank.io/c778f7da-b580-490b-961d-34706a57d326"],"publishedAt":"2017-09-21T13:27:15.675Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/bdf47afe110d","used":true,"who":"Tamic (码小白)"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 59c3d43a421aa9727ddd19c0
         * createdAt : 2017-09-21T23:01:14.453Z
         * desc : 灵活的ShadowView，可替代CardView使用
         * images : ["http://img.gank.io/5ca55569-7b78-4691-aa1a-a1165eb8a164"]
         * publishedAt : 2017-09-29T11:21:16.116Z
         * source : web
         * type : Android
         * url : https://github.com/loopeer/shadow
         * used : true
         * who : null
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private Object who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public Object getWho() {
            return who;
        }

        public void setWho(Object who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
