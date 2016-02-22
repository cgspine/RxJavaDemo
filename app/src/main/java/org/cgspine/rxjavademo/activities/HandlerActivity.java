package org.cgspine.rxjavademo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.cgspine.rxjavademo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by cgspine on 16/1/12.
 */
public class HandlerActivity extends AppCompatActivity {
    private static final int IO_TASK = 0;
    private static final int HTTP_TASK = 1;
    @Bind(R.id.content) TextView mContentTv;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case IO_TASK:
                    mContentTv.setText("IO结果:"+msg.obj.toString());
                    break;
                case HTTP_TASK:
                    mContentTv.setText("Http结果:"+msg.obj.toString());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.io)
    void onIoBtnClick(Button button){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Io阻塞任务
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = IO_TASK;
                message.obj = "IO任务结果";
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @OnClick(R.id.http)
    void onHttpBtnClick(Button button){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Io阻塞任务
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = HTTP_TASK;
                message.obj = "Http任务结果";
                mHandler.sendMessage(message);
            }
        }).start();
    }
}
