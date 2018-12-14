package com.czq.kotlinarch.data.model;


import java.util.List;

public class ChallengeRecomand {

    /**
     * id : 2
     * title : 主机单机
     * subscribedCount : 0
     * challengeCount : 7
     * icon : http://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180907/IvuyzvbmwzwCgbg.jpg!a-6-240x240.jpg
     * banner : http://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180907/dSminxbmwzxpuak.png!a-3-480x.jpg
     * hasSubscribed : true
     * challenges : [{"id":1239,"title":"这不是我想要的结局，那些令人失望的游戏结局","candidateCount":0,"takeOnCount":0,"candidatePoint":0,"cover":"http://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20181017/NQRCljbmAabbkij.jpg!a-4-x640.jpg"},{"id":1237,"title":"人见人爱花见花开的游戏角色","candidateCount":0,"takeOnCount":0,"candidatePoint":0,"cover":"http://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20181017/YuYuFBbmAaauzBz.jpg!a-4-x640.jpg"},{"id":1236,"title":"出淤泥而不染，这些游戏就是这么的奇葩","candidateCount":0,"takeOnCount":0,"candidatePoint":0,"cover":"http://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20181017/ntlYTQbmAaafylu.jpg!a-4-x640.jpg"},{"id":1235,"title":"那些令你摔键盘的BOSS们","candidateCount":0,"takeOnCount":0,"candidatePoint":0,"cover":"http://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20181017/tQxJoUbmAaabnFF.jpg!a-4-x640.jpg"}]
     */

    public String id;
    public String title;
    public int subscribedCount = 0;
    public int challengeCount = 0;
    public String icon;
    public String banner;
    public boolean hasSubscribed = false;
    public List<ChallengesBean> challenges;

    public static class ChallengesBean {
        /**
         * id : 1239
         * title : 这不是我想要的结局，那些令人失望的游戏结局
         * candidateCount : 0
         * takeOnCount : 0
         * candidatePoint : 0
         * cover : http://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20181017/NQRCljbmAabbkij.jpg!a-4-x640.jpg
         */

        public String id;
        public String title;
        public int candidateCount = 0;
        public int takeOnCount = 0;
        public int candidatePoint = 0;
        public String cover;
    }
}
