package io.gitple.android.gitpleandroidexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.andremion.counterfab.CounterFab;
//import com.onesignal.OneSignal;

import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;

import org.json.JSONException;
import org.json.JSONObject;

import io.gitple.android.sdk.Gitple;
import io.gitple.android.sdk.GitpleCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Gitple:MainActivity";

    private CounterFab counterFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.counterFab = (CounterFab) findViewById(R.id.counter_fab);
        this.counterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch gitple Activity
                Gitple.launch(MainActivity.this);
            }
        });

        Button unreadCountButton = (Button) findViewById(R.id.unreadCountButton);
        unreadCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "onClick: unreadCount button ");
                MainActivity.this.getGitpleUnreadCount(true);
            }
        });

        int cameraPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int writeExternalStoragePermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED ||
            writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            Activity activity = (Activity)this;

            ActivityCompat.requestPermissions(activity, new String[]{
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 1);
        }
    }

    private void getGitpleUnreadCount(final Boolean showAlert) {
        // Get unread count at gitple
        Gitple.unreadCount(new GitpleCallback<Number>() {
            @Override
            public void onSuccess(Number data) {
                Log.v(TAG, "unreadCount Callback onSuccess. data=" + data);
                CounterFab counterFab = (CounterFab) findViewById(R.id.counter_fab);
                counterFab.setCount(data.intValue());
                if (showAlert) {
                    MainActivity.this.displayAlert("Unread message count: " + data.intValue());
                }
            }

            @Override
            public void onError(Gitple.Error errorCode) {
                Log.v(TAG, "unreadCount Callback onError. code=" + errorCode);
            }
        });
    }

    private void displayAlert(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //set the title to be appear in the dialog
        alertDialogBuilder.setTitle("Gitple")
                          .setMessage(message)
                          .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                          })
                          .show();

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

        this.getGitpleUnreadCount(false);
    }
}
