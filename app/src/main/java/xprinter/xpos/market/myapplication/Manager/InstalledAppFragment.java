package xprinter.xpos.market.myapplication.Manager;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xprinter.xpos.market.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstalledAppFragment extends Fragment {


    public InstalledAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_installed_app, container, false);
    }

}
