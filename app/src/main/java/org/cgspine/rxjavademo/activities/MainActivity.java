package org.cgspine.rxjavademo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.cgspine.rxjavademo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.btn_handler)
    void onHandlerClick(Button button){
        Intent intent = new Intent(this,HandlerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_async_task)
    void onClickAsyncTask(Button button){
        Intent intent = new Intent(this,AsyncTaskActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_rxjava_download)
    void onClickRxjavaHelloworldTask(Button button){
        Intent intent = new Intent(this,RxjavaDownloadActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_gank)
    void onClickGank(Button button){
        Intent intent = new Intent(this,RxJavaActivity.class);
        startActivity(intent);
    }
}
