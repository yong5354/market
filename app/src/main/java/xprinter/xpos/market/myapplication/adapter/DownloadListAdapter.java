package xprinter.xpos.market.myapplication.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseApkImpl;
import xprinter.xpos.market.myapplication.Data.DownloadApk;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Util.DownLoadTask;

/**
 * Created by Administrator on 2017-09-04.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.downloadViewHolder>{

    private Context mContext;
    private List<DownloadApk> mList = new ArrayList<>();
    private ItemClickListener mListener;

    public DownloadListAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<DownloadApk> list) {
        mList = list;
    }

    public void setListener(ItemClickListener l) {
        mListener = l;
    }

    @Override
    public downloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_download,parent,false);
        return new downloadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(downloadViewHolder holder, int position) {
        final DownloadApk dapk = mList.get(position);
        holder.title.setText(dapk.title);
        holder.version.setText(dapk.versionname);
        holder.progress.setVisibility(View.VISIBLE);
        holder.progress.setProgress(dapk.percent);
        Glide.with(mContext)
                .load(dapk.iconUrl)
                .placeholder(R.drawable.ic_default_thumbnail)
                .into(holder.icon);
        holder.icon.setTag(R.id.icon_tag,dapk.apkname);
        if(!dapk.filepath.equals("")) {
            holder.install.setEnabled(true);
            holder.progress.setProgress(100);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                    mListener.delete(dapk);
            }
        });
        holder.install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                    mListener.Install(dapk.filepath);
            }
        });
    }

    @Override
    public void onBindViewHolder(downloadViewHolder holder, int position, List<Object> payloads) {
        Log.e("FANGUOYONG","onBindViewHolder payloads : " + payloads.size());
        if(!payloads.isEmpty()) {
            int percent = (int) payloads.get(0);
            holder.progress.setProgress(percent);
            Log.e("FANGUOYONG","onBindViewHolder progress : " + percent);
        } else {
            super.onBindViewHolder(holder,position,payloads);
        }
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
        private TextView delete;
        private Button install;

        public downloadViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
            title = (TextView) itemView.findViewById(R.id.list_item_title);
            version = (TextView) itemView.findViewById(R.id.list_item_version);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);
            delete = (TextView) itemView.findViewById(R.id.delete);
            install = (Button) itemView.findViewById(R.id.install);
        }
    }

    public interface ItemClickListener {
        void delete(DownloadApk apk);
        void Install(String fileUrl);
    }
}
