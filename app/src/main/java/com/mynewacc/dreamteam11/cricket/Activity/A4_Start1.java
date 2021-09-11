package com.mynewacc.dreamteam11.cricket.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.mynewacc.dreamteam11.R;
import com.mynewacc.dreamteam11.ads.Globs;
import com.ornach.bitpermission.BitPermission;
import com.ornach.bitpermission.PermissionListener;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.AppOpen;

import java.util.ArrayList;

public class A4_Start1 extends AppOpen {
    LinearLayout start;
    private SharedPreferences preferences;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.a4_start1);

        fetchAd(new splshADlistner() {
            @Override
            public void onsuccess() {

            }
        });
        start = findViewById(R.id.start);


        Globs.btn_zooming(start, A4_Start1.this);


        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionRequest(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        AppManage.getInstance(A4_Start1.this).show_INTERSTIAL(A4_Start1.this, new AppManage.MyCallback() {
                            @Override
                            public void callbackCall() {
                                Intent intent = new Intent(A4_Start1.this, A5_Start2.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(A4_Start1.this, "Without Storage Permission We Can't Run App....", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void permissionRequest(PermissionListener listener) {
        BitPermission bitPermission = BitPermission.with(A4_Start1.this)
                .setPermissionListener(listener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .build();
        bitPermission.request();
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(A4_Start1.this).show_INTERSTIAL(A4_Start1.this, true, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                A4_Start1.super.onBackPressed();
            }
        });

    }

}
