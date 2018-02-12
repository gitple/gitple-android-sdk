package io.gitple.android.gitpleandroidexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.andremion.counterfab.CounterFab;

import io.gitple.android.sdk.Gitple;
import io.gitple.android.sdk.GitpleCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Gitple:MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CounterFab counterFab = (CounterFab) findViewById(R.id.counter_fab);
        counterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch gitple Activity
                Gitple.launch(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get unread count at gitple
        Gitple.unreadCount(new GitpleCallback<Number>() {
            @Override
            public void onSuccess(Number data) {
                Log.v(TAG, "unreadCount Callback onSuccess. data=" + data);
                CounterFab counterFab = (CounterFab) findViewById(R.id.counter_fab);
                counterFab.setCount(data.intValue());
            }

            @Override
            public void onError(Gitple.Error errorCode) {
                Log.v(TAG, "unreadCount Callback onError. code=" + errorCode);
            }
        });
    }
}
