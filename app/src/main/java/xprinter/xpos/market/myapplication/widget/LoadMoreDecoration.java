package xprinter.xpos.market.myapplication.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xprinter.xpos.market.myapplication.R;

/**
 * Created by Administrator on 2017-08-24.
 */

public class LoadMoreDecoration extends RecyclerView.ItemDecoration{

    private View mLoadView;
    private boolean mLoadComplete = false;
    private LoadDataListener mListener;

    public LoadMoreDecoration(View v) {
        mLoadView = v;
    }

    public void setListener(LoadDataListener l) {
        mListener = l;
    }

    public void setLoadComplete(boolean complete) {
        mLoadComplete = complete;
    }

    private View getDefaultLoadView(RecyclerView parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_load_more,null);
        ViewGroup.LayoutParams parmas = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(widthMeasureSpec, heightMeasureSpec);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setLayoutParams(parmas);
        return v;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c,parent,state);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager.findFirstVisibleItemPosition() == 0 && layoutManager.findLastVisibleItemPosition() + 1 == parent.getAdapter().getItemCount()) {
            return;
        }
        if(mLoadView == null)
            mLoadView = getDefaultLoadView(parent);

        if(mLoadComplete)
            return;

        if (parent.getChildCount() < 1)
            return;
        View childView = parent.getChildAt(parent.getChildCount() - 1);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
        int dx = parent.getPaddingLeft();
        int dy = Math.max(parent.getPaddingTop(), childView.getBottom() + params.bottomMargin);
        c.save();
        c.translate(dx, dy);
        mLoadView.draw(c);
        c.restore();
        ViewCompat.postInvalidateOnAnimation(parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager.findFirstVisibleItemPosition() == 0 && layoutManager.findLastVisibleItemPosition() + 1 == parent.getAdapter().getItemCount()) {
            if(mListener != null)
                mListener.LoadData();
            return;
        }

        if (!mLoadComplete && parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, mLoadView == null ? 0 : mLoadView.getMeasuredHeight());
            if(mListener != null)
                mListener.LoadData();
        }
    }

    public interface LoadDataListener {
        void LoadData();
    }
}
