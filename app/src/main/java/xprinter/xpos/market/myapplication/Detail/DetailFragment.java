package xprinter.xpos.market.myapplication.Detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public class DetailFragment extends Fragment {

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    public static DetailFragment newInstance(int arg) {
        DetailFragment fragment = new DetailFragment();
        Bundle b = new Bundle();
        b.putInt("id", arg);
        fragment.setArguments(b);
        return fragment;
    }
}
