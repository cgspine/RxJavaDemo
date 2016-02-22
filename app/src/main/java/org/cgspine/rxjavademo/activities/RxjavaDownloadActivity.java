package org.cgspine.rxjavademo.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.cgspine.rxjavademo.R;
import org.cgspine.rxjavademo.serices.ServersManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by cgine on 16/2/22.
 */
public class RxjavaDownloadActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tips)
    TextView mTipsTv;
    @Bind(R.id.image)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick(R.id.http)
    void onHttpBtnClick(Button button){
        Log.d("cgine", "click");
        ServersManager.getInstance().downLoadImageObservable("http://img.bz1111.com/d7/2012-8/20120812103719.jpg")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mTipsTv.setText("图片开始下载");
                    }
                })
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        Log.d("cgine", "complete");
                        mTipsTv.setText("图片下载中完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("cgine", "error");
                        mTipsTv.setText("出现错误");
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Log.d("cgine", "next");
                        if (bitmap != null) {
                            mImageView.setImageBitmap(bitmap);
                        } else {
                            mTipsTv.setText("结果为null");
                        }
                    }
                });
    }
}
