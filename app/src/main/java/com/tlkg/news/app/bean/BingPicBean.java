package com.tlkg.news.app.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuxiaoqi on 2017/9/26.
 * 广告页图片
 */

public class BingPicBean implements Serializable {

    @Override
    public String toString() {
        return "BingPicBean{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"ret_code":0,"list":[{"1920*1080":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_240x320.jpg","country":"美国","city":"美洲杉国家公园","content":"在加利福尼亚的美洲杉国家公园，悬崖湖冰冷的湖水，映衬着一堵花岗岩的墙，安塞尔·亚当斯，这位环保主义者和艺术家在1932年拍摄了这样一张黑白照片。除了原始的高山湖泊，这个公园还因其古老而高大的树木而闻名，其中包括世界上最大的单梗树\u2014\u2014红杉树。","title":"吐鲁番盆地鸟瞰图，中国 (© NASA)","320x240":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_320x240.jpg","subtitle":"美国，美洲杉国家公园","1024x768":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_400x240.jpg","day":"2017年09月25日","1280x768":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_240x320.jpg","country":"德国","city":"埃尔福特","content":"如果你此时在德国，那么很有可能遇到慕尼黑啤酒节集市，这个持续16到18天的民间节日在巴伐利亚文化中根深蒂固。自从1810年以来，每年秋天，它都会给慕尼黑带来超过600万的客流量。此时埃尔福特大教堂和圣西弗勒斯教堂也比平时更辉煌更热闹，喝着啤酒逛教堂何不也是人生一大趣事？","title":"美洲杉国家公园内的悬崖湖，美国加利福尼亚州 (© Caleb Weston/Getty Images)","320x240":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_320x240.jpg","subtitle":"德国，埃尔福特","1024x768":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_400x240.jpg","day":"2017年09月24日","1280x768":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_240x320.jpg","country":"中国","city":"上海","content":"秋风瑟瑟，在这个景色最美的时节，清晨时分，上海外滩早早就有人沿着黄浦江晨跑，人们已经忘却夏天炎热的味道，渐渐的寒冷也使人们更加需要锻炼来增加自身的热度，也为开启新的一天准备一个更有精神的面貌。看，那空中飞舞的风筝仿佛在向我们诉说着秋分时节的来临！","title":"慕尼黑啤酒节期间的摩天轮以及埃尔福特大教堂和圣西弗勒斯教堂的景象 (© Hans P. Szyszka/age fotostock)","320x240":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_320x240.jpg","subtitle":"中国，上海","1024x768":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_400x240.jpg","day":"2017年09月23日","1280x768":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_240x320.jpg","country":"美国","city":"多利草地荒野","content":"熊岩保护区是西弗吉尼亚州最常被拍摄的地方之一，而且今天是每年秋天到此游览的第一天。这个保护区是多利草地荒野的一部分，也是一座位于阿勒格尼山脉的多岩石、高海拔高原。有句话说：\u201c如果秋天的落叶不是你的东西，那就转向夜空吧。\u201d在北半球，秋天是观赏北极光的高峰期。","title":"【今日秋分】清晨以风筝为伴晨跑中的人，中国上海 (© Kiszon Pascal/Getty Images)","320x240":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_320x240.jpg","subtitle":"美国，多利草地荒野","1024x768":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_400x240.jpg","day":"2017年09月22日","1280x768":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_240x320.jpg","country":"意大利","city":"普罗奇达岛","content":"向西航行到那不勒斯湾，到达普罗奇达岛，一个大约1.6平方英里的小岛，它向我们展示着旧世界的意大利。这里感觉就像是回到了这个渔村的狭窄街道上，这里没有海滩或大城市的便利，也就意味着一种更古老、更轻松的生活方式在这里得以保留。远离城市的喧嚣浮躁，一起来散散心吧！","title":"多利草地荒野内熊岩保护区的秋景，美国西弗吉尼亚州 (© Anthony Heflin/500px)","320x240":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_320x240.jpg","subtitle":"意大利，普罗奇达岛","1024x768":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_400x240.jpg","day":"2017年09月21日","1280x768":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1366x768.jpg"}],"msg":"提建议加微信：geo507"}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        @Override
        public String toString() {
            return "ShowapiResBodyBean{" +
                    "ret_code=" + ret_code +
                    ", msg='" + msg + '\'' +
                    ", list=" + list +
                    '}';
        }

        /**
         * ret_code : 0
         * list : [{"1920*1080":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_240x320.jpg","country":"美国","city":"美洲杉国家公园","content":"在加利福尼亚的美洲杉国家公园，悬崖湖冰冷的湖水，映衬着一堵花岗岩的墙，安塞尔·亚当斯，这位环保主义者和艺术家在1932年拍摄了这样一张黑白照片。除了原始的高山湖泊，这个公园还因其古老而高大的树木而闻名，其中包括世界上最大的单梗树\u2014\u2014红杉树。","title":"吐鲁番盆地鸟瞰图，中国 (© NASA)","320x240":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_320x240.jpg","subtitle":"美国，美洲杉国家公园","1024x768":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_400x240.jpg","day":"2017年09月25日","1280x768":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/TurpanDepression_ZH-CN12295576336_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_240x320.jpg","country":"德国","city":"埃尔福特","content":"如果你此时在德国，那么很有可能遇到慕尼黑啤酒节集市，这个持续16到18天的民间节日在巴伐利亚文化中根深蒂固。自从1810年以来，每年秋天，它都会给慕尼黑带来超过600万的客流量。此时埃尔福特大教堂和圣西弗勒斯教堂也比平时更辉煌更热闹，喝着啤酒逛教堂何不也是人生一大趣事？","title":"美洲杉国家公园内的悬崖湖，美国加利福尼亚州 (© Caleb Weston/Getty Images)","320x240":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_320x240.jpg","subtitle":"德国，埃尔福特","1024x768":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_400x240.jpg","day":"2017年09月24日","1280x768":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/PrecipiceLake_ZH-CN10138285567_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_240x320.jpg","country":"中国","city":"上海","content":"秋风瑟瑟，在这个景色最美的时节，清晨时分，上海外滩早早就有人沿着黄浦江晨跑，人们已经忘却夏天炎热的味道，渐渐的寒冷也使人们更加需要锻炼来增加自身的热度，也为开启新的一天准备一个更有精神的面貌。看，那空中飞舞的风筝仿佛在向我们诉说着秋分时节的来临！","title":"慕尼黑啤酒节期间的摩天轮以及埃尔福特大教堂和圣西弗勒斯教堂的景象 (© Hans P. Szyszka/age fotostock)","320x240":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_320x240.jpg","subtitle":"中国，上海","1024x768":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_400x240.jpg","day":"2017年09月23日","1280x768":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/ErfurtOktoberfest_ZH-CN11152792740_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_240x320.jpg","country":"美国","city":"多利草地荒野","content":"熊岩保护区是西弗吉尼亚州最常被拍摄的地方之一，而且今天是每年秋天到此游览的第一天。这个保护区是多利草地荒野的一部分，也是一座位于阿勒格尼山脉的多岩石、高海拔高原。有句话说：\u201c如果秋天的落叶不是你的东西，那就转向夜空吧。\u201d在北半球，秋天是观赏北极光的高峰期。","title":"【今日秋分】清晨以风筝为伴晨跑中的人，中国上海 (© Kiszon Pascal/Getty Images)","320x240":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_320x240.jpg","subtitle":"美国，多利草地荒野","1024x768":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_400x240.jpg","day":"2017年09月22日","1280x768":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/Shanghai_ZH-CN10665657954_1366x768.jpg"},{"1920*1080":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1920*1080.jpg","720x1280":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_720x1280.jpg","800x600":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_800x600.jpg","pic":"http://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1920x1080.jpg","640x480":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_640x480.jpg","240x320":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_240x320.jpg","country":"意大利","city":"普罗奇达岛","content":"向西航行到那不勒斯湾，到达普罗奇达岛，一个大约1.6平方英里的小岛，它向我们展示着旧世界的意大利。这里感觉就像是回到了这个渔村的狭窄街道上，这里没有海滩或大城市的便利，也就意味着一种更古老、更轻松的生活方式在这里得以保留。远离城市的喧嚣浮躁，一起来散散心吧！","title":"多利草地荒野内熊岩保护区的秋景，美国西弗吉尼亚州 (© Anthony Heflin/500px)","320x240":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_320x240.jpg","subtitle":"意大利，普罗奇达岛","1024x768":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1024x768.jpg","400x240":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_400x240.jpg","day":"2017年09月21日","1280x768":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1280x768.jpg","480x800":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_480x800.jpg","800x480":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_800x480.jpg","1366x768":"https://www.bing.com/az/hprichbg/rb/DollySods_ZH-CN10617200330_1366x768.jpg"}]
         * msg : 提建议加微信：geo507
         */

        private int ret_code;
        private String msg;
        private List<ListBean> list;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            @Override
            public String toString() {
                return "ListBean{" +
                        "_$_19201080248='" + _$_19201080248 + '\'' +
                        ", _$720x1280='" + _$720x1280 + '\'' +
                        ", _$800x600='" + _$800x600 + '\'' +
                        ", pic='" + pic + '\'' +
                        ", _$640x480='" + _$640x480 + '\'' +
                        ", _$240x320='" + _$240x320 + '\'' +
                        ", country='" + country + '\'' +
                        ", city='" + city + '\'' +
                        ", content='" + content + '\'' +
                        ", title='" + title + '\'' +
                        ", _$320x240='" + _$320x240 + '\'' +
                        ", subtitle='" + subtitle + '\'' +
                        ", _$1024x768='" + _$1024x768 + '\'' +
                        ", _$400x240='" + _$400x240 + '\'' +
                        ", day='" + day + '\'' +
                        ", _$1280x768='" + _$1280x768 + '\'' +
                        ", _$480x800='" + _$480x800 + '\'' +
                        ", _$800x480='" + _$800x480 + '\'' +
                        ", _$1366x768='" + _$1366x768 + '\'' +
                        '}';
            }

            @SerializedName("1920*1080")
            private String _$_19201080248; // FIXME check this code
            @SerializedName("720x1280")
            private String _$720x1280;
            @SerializedName("800x600")
            private String _$800x600;
            private String pic;
            @SerializedName("640x480")
            private String _$640x480;
            @SerializedName("240x320")
            private String _$240x320;
            private String country;
            private String city;
            private String content;
            private String title;
            @SerializedName("320x240")
            private String _$320x240;
            private String subtitle;
            @SerializedName("1024x768")
            private String _$1024x768;
            @SerializedName("400x240")
            private String _$400x240;
            private String day;
            @SerializedName("1280x768")
            private String _$1280x768;
            @SerializedName("480x800")
            private String _$480x800;
            @SerializedName("800x480")
            private String _$800x480;
            @SerializedName("1366x768")
            private String _$1366x768;

            public String get_$_19201080248() {
                return _$_19201080248;
            }

            public void set_$_19201080248(String _$_19201080248) {
                this._$_19201080248 = _$_19201080248;
            }

            public String get_$720x1280() {
                return _$720x1280;
            }

            public void set_$720x1280(String _$720x1280) {
                this._$720x1280 = _$720x1280;
            }

            public String get_$800x600() {
                return _$800x600;
            }

            public void set_$800x600(String _$800x600) {
                this._$800x600 = _$800x600;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String get_$640x480() {
                return _$640x480;
            }

            public void set_$640x480(String _$640x480) {
                this._$640x480 = _$640x480;
            }

            public String get_$240x320() {
                return _$240x320;
            }

            public void set_$240x320(String _$240x320) {
                this._$240x320 = _$240x320;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String get_$320x240() {
                return _$320x240;
            }

            public void set_$320x240(String _$320x240) {
                this._$320x240 = _$320x240;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String get_$1024x768() {
                return _$1024x768;
            }

            public void set_$1024x768(String _$1024x768) {
                this._$1024x768 = _$1024x768;
            }

            public String get_$400x240() {
                return _$400x240;
            }

            public void set_$400x240(String _$400x240) {
                this._$400x240 = _$400x240;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String get_$1280x768() {
                return _$1280x768;
            }

            public void set_$1280x768(String _$1280x768) {
                this._$1280x768 = _$1280x768;
            }

            public String get_$480x800() {
                return _$480x800;
            }

            public void set_$480x800(String _$480x800) {
                this._$480x800 = _$480x800;
            }

            public String get_$800x480() {
                return _$800x480;
            }

            public void set_$800x480(String _$800x480) {
                this._$800x480 = _$800x480;
            }

            public String get_$1366x768() {
                return _$1366x768;
            }

            public void set_$1366x768(String _$1366x768) {
                this._$1366x768 = _$1366x768;
            }
        }
    }
}
