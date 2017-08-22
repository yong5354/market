package xprinter.xpos.market.myapplication.Base;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import xprinter.xpos.market.myapplication.MyApplication;
import xprinter.xpos.market.myapplication.R;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    private int REQUEST_PERMISSION = new Random().nextInt(65535);
    private PermissionRequestListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();
    }

    public void setFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,f)
                .commit();
    }

    public void addFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,f)
                .commit();
    }

    protected void setActionBarTitle(int resId) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(resId);
    }

    protected void showHome(Boolean show) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkPermission(PermissionRequestListener l,String[] permissions) {
        mListener = l;
        ArrayList<String> needRequests = new ArrayList<>();
        final ArrayList<String> needShowRationale = new ArrayList<String>();
        boolean granted;
        boolean shouldRationale;
        for(String permission:permissions) {
            granted = (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            shouldRationale = shouldShowRequestPermissionRationale(permission);
            if(!granted && !shouldRationale) {
                needRequests.add(permission);
            } else if(!granted) {
                needShowRationale.add(permission);
            } else {
                if(mListener != null)
                    mListener.onPermissonResult(permission, true);
            }
        }
        if(!needRequests.isEmpty())
            requestPermissions(needRequests.toArray(new String[needRequests.size()]),REQUEST_PERMISSION);
        if(!needShowRationale.isEmpty())
            Snackbar.make(getWindow().getDecorView().getRootView(),"Need permission granted to continue.",Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissions(needShowRationale.toArray(new String[needShowRationale.size()]),REQUEST_PERMISSION);
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION && mListener != null) {
            for(int i = 0; i < permissions.length; i++) {
                mListener.onPermissonResult(permissions[i],grantResults[i] == PackageManager.PERMISSION_GRANTED);
            }
        }
    }

    interface PermissionRequestListener {
        void onPermissonResult(String permission,boolean result);
    }

    private void initInject() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((MyApplication)getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }
}
