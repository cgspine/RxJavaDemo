package org.cgspine.rxjavademo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    private List<Gank> mGankList;
    private GankAdapter mGankAdapter;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.listview) ListView mListView;
    @Bind(R.id.state) TextView mStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
