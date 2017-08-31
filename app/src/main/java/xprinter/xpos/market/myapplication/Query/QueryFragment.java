package xprinter.xpos.market.myapplication.Query;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.Base.model.BaseMarketApi;
import xprinter.xpos.market.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {

    @Inject
    BaseMarketApi mMarketApi;
    @Bind(R.id.tagList)
    RecyclerView tagList;

    public static QueryFragment newInstance() {
        return new QueryFragment();
    }

    public static QueryFragment newInstance(String arg) {
        QueryFragment fragment = new QueryFragment();
        Bundle b = new Bundle();
        b.putString("key", arg);
        fragment.setArguments(b);
        return fragment;
    }

    public QueryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_query, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInject();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void initInject() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            DaggerQueryComponent.builder()
                    .activityComponent(((BaseActivity) activity).getActivityComponent())
                    .build()
                    .inject(this);
        }
    }
}
