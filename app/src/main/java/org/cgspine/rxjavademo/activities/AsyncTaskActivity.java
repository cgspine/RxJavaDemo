package org.cgspine.rxjavademo.activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
public class AsyncTaskActivity extends AppCompatActivity {
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.progress) TextView mProgressTv;
    @Bind(R.id.image) ImageView mImageView;

    private DownloadTask mDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDownloadTask = new DownloadTask();
    }

    @OnClick(R.id.async)
    void onBtnClick(Button btn){

        mDownloadTask.execute("http://img.bz1111.com/d7/2012-8/20120812103719.jpg");
    }

    private class DownloadTask extends AsyncTask<String,Integer,Bitmap[]>{

        @Override
        protected void onPreExecute() {
            //主线程
            super.onPreExecute();
            mProgressTv.setText("开始下载");
        }

        @Override
        protected Bitmap[] doInBackground(String... urls) {
            //自动工作线程,线程池
            int count = urls.length;
            Bitmap[] output = new Bitmap[count];
            try {
                for (int i = 0; i < count; i++) {
                    Bitmap bitmap = ServersManager.getInstance().downLoadImageFromHttp(urls[i]);
                    output[i] = bitmap;
                    publishProgress(i+1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //主线程
            super.onProgressUpdate(values);
            int count = values.length;
            if(count>0){
                int value = values[0];
                mProgressTv.setText(String.format("已完成%1$s张图片下载",value));
            }
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmaps) {
            //主线程
            super.onPostExecute(bitmaps);
            if(bitmaps[0]!=null){
                mImageView.setImageBitmap(bitmaps[0]);
            }
        }
    }
}
