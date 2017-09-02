package xprinter.xpos.market.myapplication.Base.model;

/**
 * Created by Administrator on 2017-09-01.
 */

public abstract class BaseTag {
    public abstract String geticon(); //获取icon
    public abstract String getname(); //获取分类名字
    public abstract String getTagId(); //获取分类id,用于请求
    public abstract int getSubTagCount(); //获取子分类个数
    public abstract String getSubTagName(int id); //获取子分类名字
    public abstract String getSubTagId(int id); //获取子分类的id
}
