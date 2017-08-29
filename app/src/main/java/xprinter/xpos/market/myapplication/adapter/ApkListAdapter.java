package xprinter.xpos.market.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-08-24.
 */

public class ApkListAdapter extends RecyclerView.Adapter<ApkListAdapter.ViewHolder>{

    private Context mContext;
    private List<BaseApk> mApkList = new ArrayList<>();
    private ItemClickListener mListener;

    public ApkListAdapter(Context context) {
        mContext = context;
    }

    public void setApkList(List<BaseApk> list) {
        mApkList = list;
    }

    public void setListener(ItemClickListener l) {
        mListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_app,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BaseApk apkinfo = mApkList.get(position);
        holder.app_title.setText(apkinfo.getTitle());

        StringBuilder apk_info = new StringBuilder();
        apk_info.append("<font color=\"#ff35a1d4\">").append(apkinfo.getVersionName()).append("</font>");
        apk_info.append("<font color=\"black\">, ").append(apkinfo.getApkSize()).append(", </font>");
        if (apkinfo.getUpdateFlag() == "new") {
            apk_info.append("<font color=\"red\">New</font>");
        } else {
            apk_info.append("<font color=\"black\">Update</font>");
        }
        holder.app_info.setText(Html.fromHtml(apk_info.toString(),Html.FROM_HTML_MODE_LEGACY));

        holder.app_desc.setText(apkinfo.getDescription());
        holder.app_downnum.setText(apkinfo.getDownloadCount()+"");
        Glide.with(mContext)
                .load(apkinfo.getLogo())
                .placeholder(R.drawable.ic_default_thumbnail)
                .into(holder.app_icon);
        holder.app_icon.setTag(R.id.icon_tag,apkinfo.getTitle());
        holder.app_ratingbar.setRating(apkinfo.getScoreStar());

        if(mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mApkList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView app_icon;
        private TextView app_title;
        private TextView app_info;
        private TextView app_desc;
        private TextView app_downnum;
        private RatingBar app_ratingbar;

        public ViewHolder(View itemView) {
            super(itemView);
            app_icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
            app_title = (TextView) itemView.findViewById(R.id.list_item_title);
            app_info = (TextView) itemView.findViewById(R.id.list_item_info);
            app_desc = (TextView) itemView.findViewById(R.id.list_item_description);
            app_downnum = (TextView) itemView.findViewById(R.id.list_item_downnum);
            app_ratingbar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view,int position);
    }
}
