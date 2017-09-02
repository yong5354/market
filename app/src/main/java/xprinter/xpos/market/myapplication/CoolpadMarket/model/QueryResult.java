package xprinter.xpos.market.myapplication.CoolpadMarket.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import xprinter.xpos.market.myapplication.Base.model.BaseApk;

/**
 * Created by Administrator on 2017-09-01.
 */

public class QueryResult {

    /**
     * result : 0
     * msg :
     * content : {"meta":{"page":1,"count":5,"total_page":73},"list":[{"editorIntro":"专业地图 出行利器","icon":"https://appstorecostc.coolyun.com/group1/local_upload/7f/7f2871ad8c1d216afd9af447263839c3.png","appName":"高德地图","versionCode":6121,"size":62453246,"package":"com.autonavi.minimap","gift":"","packageId":1765,"downloadNum":77965883,"apkUrl":"https://appstorecostc.coolyun.com/group3/dev_upload/c6/c61c02cb6a4c06dc8e2ecf2c17311691.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"查路线看路况找地点省流量","icon":"https://appstorecostc.coolyun.com/group6/share_upload/7b/7b0a3f82c90f025f3ac4c93c5003e97e.png","appName":"百度地图","versionCode":800,"size":42727261,"package":"com.baidu.BaiduMap","gift":"","packageId":2687,"downloadNum":1175110705,"apkUrl":"https://appstorecostc.coolyun.com/group8/local_upload/47/47d49aef2b8a92f3fc2589934921846a.apk","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"清新的操作界面，新鲜的实用功能，给您非同凡响的导航新体验！","icon":"https://appstorecostc.coolyun.com/group5/share_upload/f5/f5e81425f59e13c31e471331e12e2c22.png","appName":"和地图","versionCode":30055,"size":58541273,"package":"com.autonavi.cmccmap","gift":"","packageId":6752,"downloadNum":88542199,"apkUrl":"http://m.baidu.com/api?action=redirect&token=kpyysd&from=1014090y&type=app&dltype=new&refid=2521226788&tj=soft_11715711_1661159093_%E5%92%8C%E5%9C%B0%E5%9B%BE&refp=action_search&blink=d9cb687474703a2f2f612e67646f776e2e62616964752e636f6d2f646174612f7769736567616d652f343161613437386663623330613463362f6865646974755f33303035352e61706b3f66726f6d3d6131313031a759&crversion=1","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"创造智领3G导航~~享受实时导航、探路先锋~诸多新颖实用的服务，软件内含丰富的兴趣点信息供您查询，帮助您快速的查找各种信息，让您尽情享受驾车出行的无限乐趣。","icon":"https://appstorecostc.coolyun.com/group2/share_upload/f0/f0c0f61f7fd078abda15e7f3758a6579.png","appName":"天翼导航","versionCode":144,"size":14000908,"package":"com.pdager","gift":"","packageId":8490,"downloadNum":31270966,"apkUrl":"http://m.baidu.com/api?action=redirect&token=kpyysd&from=1014090y&type=app&dltype=new&refid=3172551088&tj=soft_22155532_3671694433_%E5%A4%A9%E7%BF%BC%E5%AF%BC%E8%88%AA&refp=action_search&blink=04d7687474703a2f2f612e67646f776e2e62616964752e636f6d2f646174612f7769736567616d652f376262386332353730346463653234372f7469616e796964616f68616e675f3134342e61706b3f66726f6d3d6131313031a459&crversion=1","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"小黄车，引领时尚环保骑行","icon":"https://appstorecostc.coolyun.com/group6/local_upload/7a/7a8d8e2af7d9b1e5bdd9f81d4d35d3bc.png","appName":"ofo共享单车","versionCode":14110,"size":18129494,"package":"so.ofo.labofo","gift":"","packageId":133824,"downloadNum":7815008,"apkUrl":"http://m.baidu.com/api?action=redirect&token=kpyysd&from=1014090y&type=app&dltype=new&refid=0468191567&tj=soft_22119869_1838814_ofo%E5%85%B1%E4%BA%AB%E5%8D%95%E8%BD%A6&refp=action_search&blink=5489687474703a2f2f612e67646f776e2e62616964752e636f6d2f646174612f7769736567616d652f373331323432313636373731613062362f6f666f676f6e677869616e6764616e6368655f31343131302e61706b3f66726f6d3d61313130319e59&crversion=1","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0}]}
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
         * meta : {"page":1,"count":5,"total_page":73}
         * list : [{"editorIntro":"专业地图 出行利器","icon":"https://appstorecostc.coolyun.com/group1/local_upload/7f/7f2871ad8c1d216afd9af447263839c3.png","appName":"高德地图","versionCode":6121,"size":62453246,"package":"com.autonavi.minimap","gift":"","packageId":1765,"downloadNum":77965883,"apkUrl":"https://appstorecostc.coolyun.com/group3/dev_upload/c6/c61c02cb6a4c06dc8e2ecf2c17311691.apk","corner":"","source":"coolpad","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"查路线看路况找地点省流量","icon":"https://appstorecostc.coolyun.com/group6/share_upload/7b/7b0a3f82c90f025f3ac4c93c5003e97e.png","appName":"百度地图","versionCode":800,"size":42727261,"package":"com.baidu.BaiduMap","gift":"","packageId":2687,"downloadNum":1175110705,"apkUrl":"https://appstorecostc.coolyun.com/group8/local_upload/47/47d49aef2b8a92f3fc2589934921846a.apk","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"清新的操作界面，新鲜的实用功能，给您非同凡响的导航新体验！","icon":"https://appstorecostc.coolyun.com/group5/share_upload/f5/f5e81425f59e13c31e471331e12e2c22.png","appName":"和地图","versionCode":30055,"size":58541273,"package":"com.autonavi.cmccmap","gift":"","packageId":6752,"downloadNum":88542199,"apkUrl":"http://m.baidu.com/api?action=redirect&token=kpyysd&from=1014090y&type=app&dltype=new&refid=2521226788&tj=soft_11715711_1661159093_%E5%92%8C%E5%9C%B0%E5%9B%BE&refp=action_search&blink=d9cb687474703a2f2f612e67646f776e2e62616964752e636f6d2f646174612f7769736567616d652f343161613437386663623330613463362f6865646974755f33303035352e61706b3f66726f6d3d6131313031a759&crversion=1","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"创造智领3G导航~~享受实时导航、探路先锋~诸多新颖实用的服务，软件内含丰富的兴趣点信息供您查询，帮助您快速的查找各种信息，让您尽情享受驾车出行的无限乐趣。","icon":"https://appstorecostc.coolyun.com/group2/share_upload/f0/f0c0f61f7fd078abda15e7f3758a6579.png","appName":"天翼导航","versionCode":144,"size":14000908,"package":"com.pdager","gift":"","packageId":8490,"downloadNum":31270966,"apkUrl":"http://m.baidu.com/api?action=redirect&token=kpyysd&from=1014090y&type=app&dltype=new&refid=3172551088&tj=soft_22155532_3671694433_%E5%A4%A9%E7%BF%BC%E5%AF%BC%E8%88%AA&refp=action_search&blink=04d7687474703a2f2f612e67646f776e2e62616964752e636f6d2f646174612f7769736567616d652f376262386332353730346463653234372f7469616e796964616f68616e675f3134342e61706b3f66726f6d3d6131313031a459&crversion=1","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0},{"editorIntro":"小黄车，引领时尚环保骑行","icon":"https://appstorecostc.coolyun.com/group6/local_upload/7a/7a8d8e2af7d9b1e5bdd9f81d4d35d3bc.png","appName":"ofo共享单车","versionCode":14110,"size":18129494,"package":"so.ofo.labofo","gift":"","packageId":133824,"downloadNum":7815008,"apkUrl":"http://m.baidu.com/api?action=redirect&token=kpyysd&from=1014090y&type=app&dltype=new&refid=0468191567&tj=soft_22119869_1838814_ofo%E5%85%B1%E4%BA%AB%E5%8D%95%E8%BD%A6&refp=action_search&blink=5489687474703a2f2f612e67646f776e2e62616964752e636f6d2f646174612f7769736567616d652f373331323432313636373731613062362f6f666f676f6e677869616e6764616e6368655f31343131302e61706b3f66726f6d3d61313130319e59&crversion=1","corner":"","source":"tencent","warningTips":"","compatibleMsg":"","isVerified":0}]
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
             * total_page : 73
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
             * editorIntro : 专业地图 出行利器
             * icon : https://appstorecostc.coolyun.com/group1/local_upload/7f/7f2871ad8c1d216afd9af447263839c3.png
             * appName : 高德地图
             * versionCode : 6121
             * size : 62453246
             * package : com.autonavi.minimap
             * gift :
             * packageId : 1765
             * downloadNum : 77965883
             * apkUrl : https://appstorecostc.coolyun.com/group3/dev_upload/c6/c61c02cb6a4c06dc8e2ecf2c17311691.apk
             * corner :
             * source : coolpad
             * warningTips :
             * compatibleMsg :
             * isVerified : 0
             */

            private String editorIntro;
            private String icon;
            private String appName;
            private int versionCode;
            private int size;
            @SerializedName("package")
            private String packageX;
            private String gift;
            private int packageId;
            private int downloadNum;
            private String apkUrl;
            private String corner;
            private String source;
            private String warningTips;
            private String compatibleMsg;
            private int isVerified;

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
                return versionCode + "";
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
        }
    }
}
