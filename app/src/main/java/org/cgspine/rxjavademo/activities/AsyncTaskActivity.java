package org.cgspine.rxjavademo.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.cgspine.rxjavademo.R;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cgspine on 16/1/12.
 */
public class AsyncTaskActivity extends AppCompatActivity {
    @Bind(R.id.async) Button mButton;
    @Bind(R.id.content) TextView mContentTv;
    @Bind(R.id.progress) TextView mProgressTv;

    private DownloadTask mDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_task_layout);
        ButterKnife.bind(this);

        mDownloadTask = new DownloadTask();
    }

    @OnClick(R.id.async)
    void onBtnClick(Button btn){
        mDownloadTask.execute("haha","hehe");
    }

    private class DownloadTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            //主线程
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            //自动工作线程,线程池
            int count = urls.length;
            String output = "";
            for (int i = 0; i < count; i++) {
                try {
                    Thread.sleep(500);
                    publishProgress((i+1)/count);
                    if (isCancelled())
                        Log.e("Task", "cancel");
                    output+="参数" + (i+1) + ":" + urls[i];
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return output + "结果就是这样";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //主线程
            super.onProgressUpdate(values);
            int count = values.length;
            if(count>0){
                int value = values[0];
                mProgressTv.setText(String.format("已完成%1$s下载",value));
            }

        }

        @Override
        protected void onPostExecute(String s) {
            //主线程
            super.onPostExecute(s);
            mContentTv.setText(s);
        }
    }
}
