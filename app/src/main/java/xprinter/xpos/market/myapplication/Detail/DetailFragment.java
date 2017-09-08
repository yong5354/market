package xprinter.xpos.market.myapplication.Detail;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import xprinter.xpos.market.myapplication.Base.BaseActivity;
import xprinter.xpos.market.myapplication.Base.model.BaseApk;
import xprinter.xpos.market.myapplication.Base.model.BaseApkField;
import xprinter.xpos.market.myapplication.CoolMarket.model.Apk;
import xprinter.xpos.market.myapplication.CoolMarket.model.ApkField;
import xprinter.xpos.market.myapplication.Data.AppDatabase;
import xprinter.xpos.market.myapplication.R;
import xprinter.xpos.market.myapplication.Util.ContextType;
import xprinter.xpos.market.myapplication.Util.DownLoadTask;
import xprinter.xpos.market.myapplication.Util.PackageUtil;

public class DetailFragment extends Fragment implements DetailFragmentContract.DetailView {

    private final String TAG = DetailFragment.class.getSimpleName();

    @ContextType("application")
    @Inject
    Context mAppContext;
    @Inject
    DetailPresenter mPresenter;
    @Inject
    Activity mContext;
    @Inject
    DownLoadTask mDownloadTask;

    @Bind(R.id.app_view_meta1)
    TextView appViewMeta1;
    @Bind(R.id.app_view_meta2)
    TextView appViewMeta2;
    @Bind(R.id.app_view_meta3)
    TextView appViewMeta3;
    @Bind(R.id.app_view_meta4)
    TextView appViewMeta4;
    @Bind(R.id.app_view_remark)
    TextView appViewRemark;
    @Bind(R.id.app_view_introduce)
    TextView appViewIntroduce;
    @Bind(R.id.app_header_icon)
    ImageView appHeaderIcon;
    @Bind(R.id.app_header_title)
    TextView appHeaderTitle;
    @Bind(R.id.app_header_ratingStar)
    RatingBar appHeaderRatingStar;
    @Bind(R.id.app_header_info)
    TextView appHeaderInfo;
    @Bind(R.id.app_header_comment)
    ImageButton appHeaderComment;
    @Bind(R.id.download)
    Button download;

    private int[] screenshotIds = {R.id.app_view_screenshot0, R.id.app_view_screenshot1, R.id.app_view_screenshot2, R.id.app_view_screenshot3, R.id.app_view_screenshot4, R.id.app_view_screenshot5};

    private final int ACTION_DOWNLOAD = 0;
    private final int ACTION_OPEN = 1;

    private int action = ACTION_DOWNLOAD;

    private Disposable mDownloadStatusObserver;
    private BaseApkField mApkField;
    private BaseApk mApk;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        initInject();
        int id = getArguments().getInt("id", -1);
        mPresenter.start(id);
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView");
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        if(mDownloadStatusObserver != null && !mDownloadStatusObserver.isDisposed())
            mDownloadStatusObserver.dispose();
        super.onDestroy();
    }

    private void initInject() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            DaggerDetailComponent.builder().activityComponent(((BaseActivity) activity).getActivityComponent())
                    .detailModule(new DetailModule(this))
                    .build()
                    .inject(this);
        }
    }

    @Override
    public void updateContent(BaseApkField apkfile) {
        if (apkfile != null) {
            mApkField = apkfile;
            mApk = mApkField.getApk();
            //icon
            Glide.with(mContext)
                    .load(mApk.getLogo())
                    .placeholder(R.drawable.ic_default_avatar)
                    .into(appHeaderIcon);
            //title
            appHeaderTitle.setText(mApk.getTitle());
            //star
            appHeaderRatingStar.setRating(mApk.getScoreStar());
            //info
            appHeaderInfo.setText(mApk.getVersionName());
            //screenshot
            Log.e("FANGUOYONG","screenshots:" + apkfile.getScreenShots());
            String[] url = apkfile.getScreenShots().split(",");
            int size = Math.min(screenshotIds.length, url.length);
            for (int i = 0; i < size; i++) {
                ImageView v = (ImageView) getView().findViewById(screenshotIds[i]);
                v.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(url[i])
                        .placeholder(R.drawable.screenshot_small)
                        .into(v);
                v.setTag(R.id.icon_tag, i);
            }
            //message
            appViewMeta1.setText(appViewMeta1.getText() + apkfile.getLanguage());
            appViewMeta2.setText(appViewMeta2.getText() + mApk.getApkSize());
            appViewMeta3.setText(appViewMeta3.getText() + apkfile.getRomversion() + "+");
            appViewMeta4.setText(appViewMeta4.getText() + apkfile.getLastupdate());
            appViewRemark.setText(appViewRemark.getText() + apkfile.getRemark());

            StringBuilder sb = new StringBuilder();
            sb.append(apkfile.getIntroduce());
            sb.append("<br/><strong>").append(mApk.getVersionName()).append(" :</strong><br/>")
                    .append(apkfile.getChangelog()).append("<br/>");
            sb.append("<br/><strong>更新记录 :</strong><br/>").append(apkfile.getChangehistory())
                    .append("<br/>");
            appViewIntroduce.setText(Html.fromHtml(sb.toString().replace("\n", "<br/>"), Html.FROM_HTML_MODE_LEGACY));
            //download
            int version = PackageUtil.getInstalledVersion(mAppContext,mApk.getApkName());
            if(version == -1) {
                download.setText(R.string.download);
                action = ACTION_DOWNLOAD;
            } else if(version < mApk.getVersionCode()) {
                download.setText(R.string.update);
                action = ACTION_DOWNLOAD;
            } else {
                download.setText(R.string.open);
                action = ACTION_OPEN;
            }
        } else {
            //no apkfile
        }
    }

    @OnClick(R.id.download)
    void OnDownload(View view) {
        if(mApk == null) {
            Toast.makeText(mContext,"应用信息错误",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (action) {
            case ACTION_DOWNLOAD:
                if (mApk.getDownloadUrl() == null) {
                    Toast.makeText(mContext, "无法下载，请切换来源", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Log.e("FANGUOYONG","DownloadTask in Detail : " + mDownloadTask);
                    mDownloadTask.addDownload(mApk, mApk.getDownloadUrl());
                    if(mDownloadStatusObserver != null && !mDownloadStatusObserver.isDisposed())
                        mDownloadStatusObserver.dispose();
                    mDownloadStatusObserver = mDownloadTask.observerDownloadStatus(mApk)
                                .subscribe(new Consumer<Integer>() {
                                    @Override
                                    public void accept(@NonNull Integer integer) throws Exception {
                                        //TODO 下载中,button显示进度?
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                        //TODO 下载失败
                                    }
                                }, new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        //TODO 下载成功
                                    }
                                });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ACTION_OPEN:
                Intent i = mContext.getPackageManager().getLaunchIntentForPackage(mApk.getApkName());
                startActivity(i);
                break;
        }
    }
}
