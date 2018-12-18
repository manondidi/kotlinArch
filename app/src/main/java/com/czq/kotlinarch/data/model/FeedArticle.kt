package com.czq.kotlinarch.data.model

class FeedArticle {

    /**
     * id : 170950
     * createTime : 1526367486
     * payload : {"article":{"id":106572,"title":"《电竞俱乐部》一款经营类的电竞游戏","cover":{"url":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x640.jpg","thumb":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-3-240x.jpg","medium":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x480.jpg"},"summary":"首先感谢萤火虫提供的激活码。 游戏是一款以电竞为题材的模拟类游戏，包含了PFS和MOBA两大主流电竞比赛项目。 游戏中需要创建俱乐部，签约选手，组建战...","replyCount":4,"likeCount":5,"shareCount":0,"retweetCount":0,"likeStatus":"normal","createTime":1526367486,"game":{"id":10070273,"name":"电竞俱乐部","icon":"http://i2.17173cdn.com/9axtlo/YWxqaGBf/gamelib/20180112/vuBdnkbmduwqCkA.jpg!a-3-240x.png","iconType":"normal","recordedUserCount":31,"hasRecord":false},"isFireflyArticle":true,"userId":127480428,"nickname":"西决","isFireflyUser":true,"avatar":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180417/mktifubmljjagvh.jpg!a-1-120x120.jpg"}}
     * type : create-article
     * user : {"avatar":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180417/mktifubmljjagvh.jpg!a-1-120x120.jpg","nickname":"西决","id":127480428,"followStatus":"unfollowed","isFireflyUser":true}
     */

    var id: String ?= null
    var createTime: Int = 0
    var payload: Payload? = null
    var type: String? = null
    var user: User? = null

    class Payload {
        /**
         * article : {"id":106572,"title":"《电竞俱乐部》一款经营类的电竞游戏","cover":{"url":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x640.jpg","thumb":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-3-240x.jpg","medium":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x480.jpg"},"summary":"首先感谢萤火虫提供的激活码。 游戏是一款以电竞为题材的模拟类游戏，包含了PFS和MOBA两大主流电竞比赛项目。 游戏中需要创建俱乐部，签约选手，组建战...","replyCount":4,"likeCount":5,"shareCount":0,"retweetCount":0,"likeStatus":"normal","createTime":1526367486,"game":{"id":10070273,"name":"电竞俱乐部","icon":"http://i2.17173cdn.com/9axtlo/YWxqaGBf/gamelib/20180112/vuBdnkbmduwqCkA.jpg!a-3-240x.png","iconType":"normal","recordedUserCount":31,"hasRecord":false},"isFireflyArticle":true,"userId":127480428,"nickname":"西决","isFireflyUser":true,"avatar":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180417/mktifubmljjagvh.jpg!a-1-120x120.jpg"}
         */

        var article: Article? = null

        class Article {
            /**
             * id : 106572
             * title : 《电竞俱乐部》一款经营类的电竞游戏
             * cover : {"url":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x640.jpg","thumb":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-3-240x.jpg","medium":"https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x480.jpg"}
             * summary : 首先感谢萤火虫提供的激活码。 游戏是一款以电竞为题材的模拟类游戏，包含了PFS和MOBA两大主流电竞比赛项目。 游戏中需要创建俱乐部，签约选手，组建战...
             * replyCount : 4
             * likeCount : 5
             * shareCount : 0
             * retweetCount : 0
             * likeStatus : normal
             * createTime : 1526367486
             * game : {"id":10070273,"name":"电竞俱乐部","icon":"http://i2.17173cdn.com/9axtlo/YWxqaGBf/gamelib/20180112/vuBdnkbmduwqCkA.jpg!a-3-240x.png","iconType":"normal","recordedUserCount":31,"hasRecord":false}
             * isFireflyArticle : true
             * userId : 127480428
             * nickname : 西决
             * isFireflyUser : true
             * avatar : https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180417/mktifubmljjagvh.jpg!a-1-120x120.jpg
             */

            var id: Int = 0
            var title: String? = null
            var cover: Cover? = null
            var summary: String? = null
            var replyCount: Int = 0
            var likeCount: Int = 0
            var shareCount: Int = 0
            var retweetCount: Int = 0
            var likeStatus: String? = null
            var createTime: Int = 0
            var game: Game? = null
            var isIsFireflyArticle: Boolean = false
            var userId: Int = 0
            var nickname: String? = null
            var isIsFireflyUser: Boolean = false
            var avatar: String? = null

            class Cover {
                /**
                 * url : https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x640.jpg
                 * thumb : https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-3-240x.jpg
                 * medium : https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180515/yyDclQbmnrjpadn.jpg!a-4-x480.jpg
                 */

                var url: String? = null
                var thumb: String? = null
                var medium: String? = null
            }

            class Game {
                /**
                 * id : 10070273
                 * name : 电竞俱乐部
                 * icon : http://i2.17173cdn.com/9axtlo/YWxqaGBf/gamelib/20180112/vuBdnkbmduwqCkA.jpg!a-3-240x.png
                 * iconType : normal
                 * recordedUserCount : 31
                 * hasRecord : false
                 */

                var id: Int = 0
                var name: String? = null
                var icon: String? = null
                var iconType: String? = null
                var recordedUserCount: Int = 0
                var isHasRecord: Boolean = false
            }
        }
    }

    class User {
        /**
         * avatar : https://i.17173cdn.com/gdthue/YWxqaGBf/snsapp/20180417/mktifubmljjagvh.jpg!a-1-120x120.jpg
         * nickname : 西决
         * id : 127480428
         * followStatus : unfollowed
         * isFireflyUser : true
         */

        var avatar: String? = null
        var nickname: String? = null
        var id: Int = 0
        var followStatus: String? = null
        var isIsFireflyUser: Boolean = false
    }
}
