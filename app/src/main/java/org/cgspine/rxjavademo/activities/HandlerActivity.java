package org.cgspine.rxjavademo.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.cgspine.rxjavademo.R;
import org.cgspine.rxjavademo.serices.ServersManager;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by cgspine on 16/1/12.
 */
public class HandlerActivity extends AppCompatActivity {
    private static final int HTTP_TASK = 1;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tips) TextView mTipsTv;
    @Bind(R.id.image) ImageView mImageView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==HTTP_TASK){
                Result result = (Result) msg.obj;
                if(result.error==1){
                    mTipsTv.setText("下载出错！！！");
                }else{
                    if(result.bitmap!=null){
                        mImageView.setImageBitmap(result.bitmap);
                        mTipsTv.setText("图片下载完成");
                    }else{
                        mTipsTv.setText("解析出错！！！");
                    }
                }
            }
        }
    };
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
        mTipsTv.setText("图片下载中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = new Result();
                Message message = Message.obtain();
                message.what = HTTP_TASK;
                try{
                    Bitmap bitmap = ServersManager.getInstance().downLoadImageFromHttp("http://img.bz1111.com/d7/2012-8/20120812103719.jpg");
                    result.error=0;
                    result.bitmap = bitmap;
                    message.obj=result;
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    result.error = 1;
                    message.obj=result;
                    mHandler.sendMessage(message);
                }

            }
        }).start();
    }

    class Result{
        int error;
        Bitmap bitmap;
    }
}
