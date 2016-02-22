package org.cgspine.rxjavademo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.cgspine.rxjavademo.R;
import org.cgspine.rxjavademo.adapter.GankAdapter;
import org.cgspine.rxjavademo.model.Gank;
import org.cgspine.rxjavademo.model.Response;
import org.cgspine.rxjavademo.serices.ServersManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GankActivity extends AppCompatActivity {

    private List<Gank> mGankList;
    private GankAdapter mGankAdapter;

    @Bind(R.id.listview) ListView mListView;
    @Bind(R.id.state) TextView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mStateView.setText("Loading中....");

        ServersManager.getInstance().getGankService().getGanks("Android",10,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mStateView.setVisibility(View.VISIBLE);
                        mStateView.setText("怎么搞的,粗线错误啦");
                    }

                    @Override
                    public void onNext(Response response) {
                        if(response.isError()){
                            mStateView.setVisibility(View.VISIBLE);
                            mStateView.setText("怎么搞的,粗线错误啦");
                            return;
                        }
                        mGankList = response.getGanks();
                        if(mGankList==null || mGankList.size()==0){
                            mStateView.setVisibility(View.VISIBLE);
                            mStateView.setText("没有数据");
                            return;
                        }
                        mStateView.setVisibility(View.GONE);
                        updateAdapter();
                    }
                });
    }

    private void updateAdapter(){
        if(mGankAdapter == null){
            mGankAdapter = new GankAdapter(this,mGankList);
            mListView.setAdapter(mGankAdapter);
        }else{
            mGankAdapter.refresh(mGankList);
        }
    }

}
