package org.cgspine.rxjavademo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.cgspine.rxjavademo.R;

import rx.Observable;
import rx.Subscriber;

public class RxjavaHelloworldActivity extends AppCompatActivity {
    private final static String TAG = "HelloworldActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_helloworld);

        Observable<String> stream = Observable.just("a","b","c","d");
        stream.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError:" + e);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext:" + s);
            }
        });
    }

}
