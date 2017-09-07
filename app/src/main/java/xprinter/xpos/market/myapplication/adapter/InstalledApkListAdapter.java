package xprinter.xpos.market.myapplication.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-09-05.
 */

public class InstalledApkListAdapter extends RecyclerView.Adapter<InstalledApkListAdapter.ViewHolder>{

    private Context mContext;
    private List<PackageInfo> mList = new ArrayList<>();
    private OnButtonClickListener mListener;

    public InstalledApkListAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<PackageInfo> list) {
        mList = list;
    }

    public void setListener(OnButtonClickListener l) {
        mListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_app_simple,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PackageInfo info = mList.get(holder.getAdapterPosition());
        holder.icon.setImageDrawable(info.applicationInfo.loadIcon(mContext.getPackageManager()));
        holder.title.setText(info.applicationInfo.loadLabel(mContext.getPackageManager()));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.ButtonClick(info.packageName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView title;
        private Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
            title = (TextView) itemView.findViewById(R.id.list_item_title);
            button = (Button) itemView.findViewById(R.id.Uninstall);
        }
    }

    public interface OnButtonClickListener {
        void ButtonClick(String packagename);
    }
}
