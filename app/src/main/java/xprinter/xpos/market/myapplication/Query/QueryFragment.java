package xprinter.xpos.market.myapplication.Query;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xprinter.xpos.market.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {

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
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

}
