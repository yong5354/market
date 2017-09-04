package xprinter.xpos.market.myapplication.CoolpadMarket.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import xprinter.xpos.market.myapplication.Base.model.BaseApk;

/**
 * Created by Administrator on 2017-08-30.
 */

public class CApk {

    /**
     * result : 0
     * msg :
     * content : {"apps":{"meta":{"page":1,"count":5,"total_page":16},"list":[{"editorIntro":"头条新闻资讯抢先看！","icon":"http://appstorecos.coolyun.com/group4/local_upload/02/02c322831c777ae0e7eea6c16dd32cca.png","appName":"搜狐新闻","versionCode":126,"size":17255830,"package":"com.sohu.newsclient","gift":"","packageId":4053,"downloadNum":21238023,"apkUrl":"http://appstorecos.coolyun.com/group5/dev_upload/25/25bd76f05c1dc0868f04ecf7d3c931ca.apk","corner":"","source":"adv","warningTips":"","compatibleMsg":"","isVerified":0,"sourceId":"1e8f6a0091d7f0ee91f27da5d3cf90d1"},{"editorIntro":"光荣正版授权三国志手游","icon":"http://appstorecos.coolyun.com/group3/local_upload/60/60c5c797bd0b6a44def77d6a486b77e4.png","appName":"三国志2017","versionCode":200,"size":278572131,"package":"com.game.sgz.coolpad","gift":"","packageId":299993,"downloadNum":45689,"apkUrl":"http://appstorecos.coolyun.com/group7/dev_upload/0f/0f0f18c74419be9c7426569562dfdc03.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"千万正版曲库，告别缺歌烦恼","icon":"http://appstorecos.coolyun.com/group3/local_upload/a1/a11e3bd4c7cca8f16bb37a8bc17417a0.png","appName":"QQ音乐","versionCode":679,"size":31373418,"package":"com.tencent.qqmusic","gift":"","packageId":7896,"downloadNum":56121687,"apkUrl":"http://appstorecos.coolyun.com/group5/dev_upload/30/3069d29d9ad9e56c4e84f33bd04d6e4a.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"新版捕鱼达人","icon":"http://appstorecos.coolyun.com/group6/local_upload/9d/9d122639485eedc81bc423f31488939a.png","appName":"波克捕鱼-七夕有奖","versionCode":402,"size":39191950,"package":"com.pokercity.bydrqp.kupai","gift":"","packageId":1015,"downloadNum":7658800,"apkUrl":"http://appstorecos.coolyun.com/group4/share_upload/85/851e8f619fabfadaf31a767564657e97.apk","corner":"2","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"全球精选  正品特卖","icon":"http://appstorecos.coolyun.com/group2/local_upload/e0/e00c5f70d42237871eff2826080359ed.png","appName":"唯品会","versionCode":739,"size":42632348,"package":"com.achievo.vipshop","gift":"","packageId":1034,"downloadNum":46799892,"apkUrl":"http://appstorecos.coolyun.com/group4/dev_upload/53/53d1201656cadca95aad4aa60065826e.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0}]},"rec":[]}
     */

    private int result;
    private String msg;
    private ContentBean content;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * apps : {"meta":{"page":1,"count":5,"total_page":16},"list":[{"editorIntro":"头条新闻资讯抢先看！","icon":"http://appstorecos.coolyun.com/group4/local_upload/02/02c322831c777ae0e7eea6c16dd32cca.png","appName":"搜狐新闻","versionCode":126,"size":17255830,"package":"com.sohu.newsclient","gift":"","packageId":4053,"downloadNum":21238023,"apkUrl":"http://appstorecos.coolyun.com/group5/dev_upload/25/25bd76f05c1dc0868f04ecf7d3c931ca.apk","corner":"","source":"adv","warningTips":"","compatibleMsg":"","isVerified":0,"sourceId":"1e8f6a0091d7f0ee91f27da5d3cf90d1"},{"editorIntro":"光荣正版授权三国志手游","icon":"http://appstorecos.coolyun.com/group3/local_upload/60/60c5c797bd0b6a44def77d6a486b77e4.png","appName":"三国志2017","versionCode":200,"size":278572131,"package":"com.game.sgz.coolpad","gift":"","packageId":299993,"downloadNum":45689,"apkUrl":"http://appstorecos.coolyun.com/group7/dev_upload/0f/0f0f18c74419be9c7426569562dfdc03.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"千万正版曲库，告别缺歌烦恼","icon":"http://appstorecos.coolyun.com/group3/local_upload/a1/a11e3bd4c7cca8f16bb37a8bc17417a0.png","appName":"QQ音乐","versionCode":679,"size":31373418,"package":"com.tencent.qqmusic","gift":"","packageId":7896,"downloadNum":56121687,"apkUrl":"http://appstorecos.coolyun.com/group5/dev_upload/30/3069d29d9ad9e56c4e84f33bd04d6e4a.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"新版捕鱼达人","icon":"http://appstorecos.coolyun.com/group6/local_upload/9d/9d122639485eedc81bc423f31488939a.png","appName":"波克捕鱼-七夕有奖","versionCode":402,"size":39191950,"package":"com.pokercity.bydrqp.kupai","gift":"","packageId":1015,"downloadNum":7658800,"apkUrl":"http://appstorecos.coolyun.com/group4/share_upload/85/851e8f619fabfadaf31a767564657e97.apk","corner":"2","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"全球精选  正品特卖","icon":"http://appstorecos.coolyun.com/group2/local_upload/e0/e00c5f70d42237871eff2826080359ed.png","appName":"唯品会","versionCode":739,"size":42632348,"package":"com.achievo.vipshop","gift":"","packageId":1034,"downloadNum":46799892,"apkUrl":"http://appstorecos.coolyun.com/group4/dev_upload/53/53d1201656cadca95aad4aa60065826e.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0}]}
         * rec : []
         */

        private AppsBean apps;
        private List<?> rec;

        public AppsBean getApps() {
            return apps;
        }

        public void setApps(AppsBean apps) {
            this.apps = apps;
        }

        public List<?> getRec() {
            return rec;
        }

        public void setRec(List<?> rec) {
            this.rec = rec;
        }

        public static class AppsBean {
            /**
             * meta : {"page":1,"count":5,"total_page":16}
             * list : [{"editorIntro":"头条新闻资讯抢先看！","icon":"http://appstorecos.coolyun.com/group4/local_upload/02/02c322831c777ae0e7eea6c16dd32cca.png","appName":"搜狐新闻","versionCode":126,"size":17255830,"package":"com.sohu.newsclient","gift":"","packageId":4053,"downloadNum":21238023,"apkUrl":"http://appstorecos.coolyun.com/group5/dev_upload/25/25bd76f05c1dc0868f04ecf7d3c931ca.apk","corner":"","source":"adv","warningTips":"","compatibleMsg":"","isVerified":0,"sourceId":"1e8f6a0091d7f0ee91f27da5d3cf90d1"},{"editorIntro":"光荣正版授权三国志手游","icon":"http://appstorecos.coolyun.com/group3/local_upload/60/60c5c797bd0b6a44def77d6a486b77e4.png","appName":"三国志2017","versionCode":200,"size":278572131,"package":"com.game.sgz.coolpad","gift":"","packageId":299993,"downloadNum":45689,"apkUrl":"http://appstorecos.coolyun.com/group7/dev_upload/0f/0f0f18c74419be9c7426569562dfdc03.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"千万正版曲库，告别缺歌烦恼","icon":"http://appstorecos.coolyun.com/group3/local_upload/a1/a11e3bd4c7cca8f16bb37a8bc17417a0.png","appName":"QQ音乐","versionCode":679,"size":31373418,"package":"com.tencent.qqmusic","gift":"","packageId":7896,"downloadNum":56121687,"apkUrl":"http://appstorecos.coolyun.com/group5/dev_upload/30/3069d29d9ad9e56c4e84f33bd04d6e4a.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"新版捕鱼达人","icon":"http://appstorecos.coolyun.com/group6/local_upload/9d/9d122639485eedc81bc423f31488939a.png","appName":"波克捕鱼-七夕有奖","versionCode":402,"size":39191950,"package":"com.pokercity.bydrqp.kupai","gift":"","packageId":1015,"downloadNum":7658800,"apkUrl":"http://appstorecos.coolyun.com/group4/share_upload/85/851e8f619fabfadaf31a767564657e97.apk","corner":"2","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"全球精选  正品特卖","icon":"http://appstorecos.coolyun.com/group2/local_upload/e0/e00c5f70d42237871eff2826080359ed.png","appName":"唯品会","versionCode":739,"size":42632348,"package":"com.achievo.vipshop","gift":"","packageId":1034,"downloadNum":46799892,"apkUrl":"http://appstorecos.coolyun.com/group4/dev_upload/53/53d1201656cadca95aad4aa60065826e.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0}]
             */

            private MetaBean meta;
            private List<ListBean> list;

            public MetaBean getMeta() {
                return meta;
            }

            public void setMeta(MetaBean meta) {
                this.meta = meta;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class MetaBean {
                /**
                 * page : 1
                 * count : 5
                 * total_page : 16
                 */

                private int page;
                private int count;
                private int total_page;

                public int getPage() {
                    return page;
                }

                public void setPage(int page) {
                    this.page = page;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public int getTotal_page() {
                    return total_page;
                }

                public void setTotal_page(int total_page) {
                    this.total_page = total_page;
                }
            }

            public static class ListBean extends BaseApk{
                /**
                 * editorIntro : 头条新闻资讯抢先看！
                 * icon : http://appstorecos.coolyun.com/group4/local_upload/02/02c322831c777ae0e7eea6c16dd32cca.png
                 * appName : 搜狐新闻
                 * versionCode : 126
                 * size : 17255830
                 * package : com.sohu.newsclient
                 * gift :
                 * packageId : 4053
                 * downloadNum : 21238023
                 * apkUrl : http://appstorecos.coolyun.com/group5/dev_upload/25/25bd76f05c1dc0868f04ecf7d3c931ca.apk
                 * corner :
                 * source : adv
                 * warningTips :
                 * compatibleMsg :
                 * isVerified : 0
                 * sourceId : 1e8f6a0091d7f0ee91f27da5d3cf90d1
                 */

                private String editorIntro;
                private String icon;
                private String appName;
                private int versionCode;
                private int size;
                @SerializedName("package")
                private String packageX;
                private String gift; //复用成versionName
                private int packageId;
                private int downloadNum;
                private String apkUrl;
                private String corner;
                private String source;
                private String warningTips;
                private String compatibleMsg;
                private int isVerified;
                private String sourceId;

                public String getEditorIntro() {
                    return editorIntro;
                }

                public void setEditorIntro(String editorIntro) {
                    this.editorIntro = editorIntro;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getAppName() {
                    return appName;
                }

                public void setAppName(String appName) {
                    this.appName = appName;
                }

                @Override
                public int getId() {
                    return packageId;
                }

                @Override
                public String getTitle() {
                    return appName;
                }

                @Override
                public String getDescription() {
                    return editorIntro;
                }

                @Override
                public String getLogo() {
                    return icon;
                }

                @Override
                public String getApkName() {
                    return packageX;
                }

                @Override
                public String getApkSize() {
                    float apksize = ((float) size)/1024f/1024f;
                    return (float)(Math.round(apksize*100))/100 + "M";
                }

                @Override
                public String getVersionName() {
                    return gift;
                }

                public int getVersionCode() {
                    return versionCode;
                }

                @Override
                public String getUpdateFlag() {
                    return null;
                }

                @Override
                public long getDownloadCount() {
                    return downloadNum;
                }

                @Override
                public float getScoreStar() {
                    return 0;
                }

                @Override
                public String getDownloadUrl() {
                    return apkUrl;
                }

                public void setVersionCode(int versionCode) {
                    this.versionCode = versionCode;
                }

                public int getSize() {
                    return size;
                }

                public void setSize(int size) {
                    this.size = size;
                }

                public String getPackageX() {
                    return packageX;
                }

                public void setPackageX(String packageX) {
                    this.packageX = packageX;
                }

                public String getGift() {
                    return gift;
                }

                public void setGift(String gift) {
                    this.gift = gift;
                }

                public int getPackageId() {
                    return packageId;
                }

                public void setPackageId(int packageId) {
                    this.packageId = packageId;
                }

                public int getDownloadNum() {
                    return downloadNum;
                }

                public void setDownloadNum(int downloadNum) {
                    this.downloadNum = downloadNum;
                }

                public String getApkUrl() {
                    return apkUrl;
                }

                public void setApkUrl(String apkUrl) {
                    this.apkUrl = apkUrl;
                }

                public String getCorner() {
                    return corner;
                }

                public void setCorner(String corner) {
                    this.corner = corner;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getWarningTips() {
                    return warningTips;
                }

                public void setWarningTips(String warningTips) {
                    this.warningTips = warningTips;
                }

                public String getCompatibleMsg() {
                    return compatibleMsg;
                }

                public void setCompatibleMsg(String compatibleMsg) {
                    this.compatibleMsg = compatibleMsg;
                }

                public int getIsVerified() {
                    return isVerified;
                }

                public void setIsVerified(int isVerified) {
                    this.isVerified = isVerified;
                }

                public String getSourceId() {
                    return sourceId;
                }

                public void setSourceId(String sourceId) {
                    this.sourceId = sourceId;
                }
            }
        }
    }
}
