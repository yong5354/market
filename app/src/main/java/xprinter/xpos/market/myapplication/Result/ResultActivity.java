package xprinter.xpos.market.myapplication.Result;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.R;

public class ResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setElevation(0);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        String title = getIntent().getStringExtra("title");
        if(title != null)
            setActionBarTitle(title);
        String query = getIntent().getStringExtra("query");
        setFragment(ResultFragment.newInstance(query));

    }
}
