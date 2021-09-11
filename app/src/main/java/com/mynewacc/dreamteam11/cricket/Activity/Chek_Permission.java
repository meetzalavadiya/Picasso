package com.mynewacc.dreamteam11.cricket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mynewacc.dreamteam11.R;
import com.ornach.bitpermission.BitPermission;
import com.ornach.bitpermission.PermissionListener;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.thankyou;

import java.util.ArrayList;

public class Chek_Permission extends AppCompatActivity {

    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chek__permission);

        next = findViewById(R.id.next);



        ProgressDialog dialog = new ProgressDialog(Chek_Permission.this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Checking storage permission..");
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (checkWriteExternalPermission()) {
                    Toast.makeText(Chek_Permission.this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                    next.setText("Start App");
                } else {
                    next.setText("Grant Permission");
                }

            }
        }, 2000);


    }


    @Override
    public void onBackPressed() {
        AppManage.getInstance(Chek_Permission.this).show_INTERSTIAL(Chek_Permission.this,true, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                startActivity(new Intent(Chek_Permission.this, thankyou.class));
            }
        });
    }

    public void next(View view) {

        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                AppManage.getInstance(Chek_Permission.this).show_INTERSTIAL(Chek_Permission.this, new AppManage.MyCallback() {
                    @Override
                    public void callbackCall() {
                        Intent intent1 = new Intent(Chek_Permission.this, A4_Start1.class);
                        startActivity(intent1);
                    }
                });

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };
        BitPermission bitPermission = BitPermission.with(Chek_Permission.this)
                .setPermissionListener(listener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .build();
        bitPermission.request();
    }

    private boolean checkWriteExternalPermission() {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}