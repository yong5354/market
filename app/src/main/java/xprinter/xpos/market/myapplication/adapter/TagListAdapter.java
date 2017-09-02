package xprinter.xpos.market.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xprinter.xpos.market.myapplication.Base.model.BaseTag;
import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-08-31.
 */

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.TagHolder>{

    private Context mContext;
    private List<BaseTag> mTags = new ArrayList<>();
    private ItemClickListener mListener;

    public TagListAdapter(Context context) {
        mContext = context;
    }

    public void setTagList(List<BaseTag> list) {
        mTags = list;
    }

    public void setListener(ItemClickListener l) {
        mListener = l;
    }

    @Override
    public TagHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_tag,parent,false);
        return new TagHolder(v);
    }

    @Override
    public void onBindViewHolder(TagHolder holder, int position) {
        BaseTag bt = mTags.get(position);
        holder.boardname.setText(bt.getname());
        holder.boardname.setTag(bt.getTagId());
        SetupClickListener(holder.boardname,bt.getname());
        Glide.with(mContext)
                .load(bt.geticon())
                .placeholder(R.drawable.ic_default_thumbnail)
                .into(holder.boardicon);
        holder.boardicon.setTag(R.id.icon_tag,bt.getTagId());
        SetupClickListener(holder.boardicon,bt.getname());
        int size = bt.getSubTagCount();
        int count = Math.min(size,holder.tags.size());
        for(int i = 0 ; i < count ; i++) {
            holder.tags.get(i).setText(bt.getSubTagName(i));
            holder.tags.get(i).setTag(bt.getSubTagId(i));
            holder.tags.get(i).setVisibility(View.VISIBLE);
            SetupClickListener(holder.tags.get(i),bt.getSubTagName(i));
        }
        if(size > count) {
            int add = size - count;
            int addRow = (add)/2 + (add)%2;
            int cloumn = holder.layout.getColumnCount();
            holder.layout.setRowCount(holder.layout.getRowCount() + addRow);
            GridLayout.Spec rowSpec;
            GridLayout.Spec columnSpec;
            GridLayout.LayoutParams params;
            for(int i = 0 ; i < add ; i++) {
                rowSpec = GridLayout.spec((i+count)/cloumn,1f);
                columnSpec = GridLayout.spec(i%cloumn,1f);
                params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                int widthmargin = dip2px(mContext,10);
                int heightmargin = dip2px(mContext,5);
                params.leftMargin = widthmargin;
                params.rightMargin = widthmargin;
                params.topMargin = heightmargin;
                params.bottomMargin = heightmargin;
                params.width = 0;
                TextView t = new TextView(mContext);
                t.setText(bt.getSubTagName(i+count));
                t.setTag(bt.getSubTagId(i+count));
                holder.layout.addView(t,params);
                SetupClickListener(t,bt.getSubTagName(i+count));
                Log.e("FANGUOYONG","add view in gridlayout:" + t.getText());
            }
        }
    }

    private void SetupClickListener(final View view, final String name) {
        String tag = "";
        if(mListener != null)
            if(view instanceof ImageView)
                tag = (String) view.getTag(R.id.icon_tag);
            else
                tag = (String) view.getTag();
        final String finalTag = tag;
        view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(name, finalTag);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public class TagHolder extends RecyclerView.ViewHolder {

        private ImageView boardicon;
        private TextView boardname;
        private GridLayout layout;
        private List<TextView> tags = new ArrayList<>();

        public TagHolder(View itemView) {
            super(itemView);
            boardicon = (ImageView) itemView.findViewById(R.id.list_tag_icon);
            boardname = (TextView) itemView.findViewById(R.id.board_name);
            layout = (GridLayout) itemView.findViewById(R.id.list_tag_layoutRight);
            tags.add((TextView)itemView.findViewById(R.id.tag1));
            tags.add((TextView) itemView.findViewById(R.id.tag2));
            tags.add((TextView) itemView.findViewById(R.id.tag3));
            tags.add((TextView) itemView.findViewById(R.id.tag4));
            tags.add((TextView) itemView.findViewById(R.id.tag5));
            tags.add((TextView) itemView.findViewById(R.id.tag6));
        }
    }

    public interface ItemClickListener {
        void onItemClick(String name,String query);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
