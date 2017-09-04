package xprinter.xpos.market.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xprinter.xpos.market.myapplication.Data.DownloadApk;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-09-04.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.downloadViewHolder>{

    private Context mContext;
    private List<DownloadApk> mList = new ArrayList<>();

    public DownloadListAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<DownloadApk> list) {
        mList = list;
    }

    @Override
    public downloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_download,parent,false);
        return new downloadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(downloadViewHolder holder, int position) {
        DownloadApk apkinfo = mList.get(position);
        holder.title.setText(apkinfo.title);
        holder.version.setText(apkinfo.versionname);
        holder.progress.setVisibility(View.INVISIBLE);
        Glide.with(mContext)
                .load(apkinfo.iconUrl)
                .placeholder(R.drawable.ic_default_thumbnail)
                .into(holder.icon);
        holder.icon.setTag(R.id.icon_tag,apkinfo.apkname);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class downloadViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView title;
        private TextView version;
        private ProgressBar progress;
        private ImageButton delete;
        private Button install;

        public downloadViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
            title = (TextView) itemView.findViewById(R.id.list_item_title);
            version = (TextView) itemView.findViewById(R.id.list_item_version);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
            install = (Button) itemView.findViewById(R.id.install);
        }
    }
}
