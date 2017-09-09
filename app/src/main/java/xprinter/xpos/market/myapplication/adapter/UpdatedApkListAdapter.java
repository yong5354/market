package xprinter.xpos.market.myapplication.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-09-05.
 */

public class UpdatedApkListAdapter extends RecyclerView.Adapter<UpdatedApkListAdapter.ViewHolder>{

    private Context mContext;
    private List<BaseApkField> mList = new ArrayList<>();
    private OnItemClickListener mListener;

    public UpdatedApkListAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<BaseApkField> list) {
        mList = list;
    }

    public void setListener(OnItemClickListener l) {
        mListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_update,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BaseApkField apkfield = mList.get(position);
        final BaseApk apk = apkfield.getApk();
        holder.title.setText(apk.getTitle());
        holder.version.setText(apk.getVersionName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.changelog.setText(Html.fromHtml(apkfield.getChangelog(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.changelog.setText(Html.fromHtml(apkfield.getChangelog()));
        }

        Glide.with(mContext)
                .load(apk.getLogo())
                .placeholder(R.drawable.ic_default_thumbnail)
                .into(holder.icon);
        holder.icon.setTag(R.id.icon_tag,apk.getTitle());

        holder.changelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.changelog.getMaxLines() == 1)
                    holder.changelog.setMaxLines(10);
                else
                    holder.changelog.setMaxLines(1);
            }
        });
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.showDetail(apk.getId());
                }
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.download(apk);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private ImageView icon;
        private TextView title;
        private TextView version;
        private TextView changelog;
        private Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            icon = (ImageView) itemView.findViewById(R.id.list_item_icon);
            title = (TextView) itemView.findViewById(R.id.list_item_title);
            version = (TextView) itemView.findViewById(R.id.list_item_version);
            changelog = (TextView) itemView.findViewById(R.id.list_item_changelog);
            button = (Button) itemView.findViewById(R.id.list_item_download);
        }
    }

    public interface OnItemClickListener {
        void showDetail(int packageid);
        void download(BaseApk apk);
    }
}
