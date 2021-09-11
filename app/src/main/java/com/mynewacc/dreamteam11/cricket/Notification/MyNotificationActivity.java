package com.mynewacc.dreamteam11.cricket.Notification;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.android.volley.VolleyError;
import com.mynewacc.dreamteam11.cricket.Model.PushBean;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiHelper;
import com.mynewacc.dreamteam11.cricket.NetworkUtils.ApiListener;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.cricket.Utils.SessionUtils;
import com.mynewacc.dreamteam11.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyNotificationActivity extends AppCompatActivity implements ApiListener, View.OnClickListener {
    ApiHelper apiHelper;
    PushBean bean;
    CheckBox cb_banner;
    private DatePickerDialog datePickerDialogFrom;
    EditText et_body;
    EditText et_title;
    MyNotificationActivity mActivity;
    RadioGroup rg_type;
    SimpleDateFormat sdf;
    SimpleDateFormat sdft;
    private TimePickerDialog timePickerDialog;
    TextView tv_announcement;
    TextView tv_app_update;
    TextView tv_date;
    TextView tv_delete;
    TextView tv_send;
    TextView tv_time;
    TextView tv_title;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_notification);
        this.mActivity = this;
        this.apiHelper = new ApiHelper(this, this);
        initView();
        initListener();
        getIntentData();
    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey("bean")) {
            finish();
            return;
        }
        this.bean = (PushBean) extras.getParcelable("bean");
        fillData();
        setDate();
        setTime();
    }

    private void fillData() {
        this.tv_title.setText(this.bean.getTitle());
        if (TextUtils.isEmpty(this.bean.getBanner())) {
            this.cb_banner.setChecked(false);
        } else {
            this.cb_banner.setChecked(true);
        }
        try {
            String[] split = AppUtils.getMilliesToStr(Long.parseLong(this.bean.getDeadLine())).split("\\|");
            this.tv_date.setText(split[0].trim());
            this.tv_time.setText(split[1].trim());
        } catch (Exception e) {
            e.printStackTrace();
            String[] split2 = AppUtils.getMilliesToStr(System.currentTimeMillis()).split("\\|");
            this.tv_date.setText(split2[0].trim());
            this.tv_time.setText(split2[1].trim());
        }
    }

    private void initView() {
        this.sdf = new SimpleDateFormat("MMM dd, yyyy");
        this.sdft = new SimpleDateFormat("hh:mm a");
        this.tv_time = (TextView) findViewById(R.id.tv_time);
        this.tv_date = (TextView) findViewById(R.id.tv_date);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_send = (TextView) findViewById(R.id.tv_send);
        this.et_title = (EditText) findViewById(R.id.et_title);
        this.et_body = (EditText) findViewById(R.id.et_body);
        this.tv_announcement = (TextView) findViewById(R.id.tv_announcement);
        this.tv_app_update = (TextView) findViewById(R.id.tv_app_update);
        this.tv_delete = (TextView) findViewById(R.id.tv_delete);
        this.rg_type = (RadioGroup) findViewById(R.id.rg_type);
        this.cb_banner = (CheckBox) findViewById(R.id.cb_banner);
        this.rg_type.check(R.id.rb_noti_popup);
        this.cb_banner.setChecked(true);
        Picasso.get().load(R.drawable.screen_bg).into((ImageView) findViewById(R.id.iv_bg));
    }

    private void initListener() {
        this.tv_send.setOnClickListener(this);
        this.tv_announcement.setOnClickListener(this);
        this.tv_app_update.setOnClickListener(this);
        this.tv_delete.setOnClickListener(this);
        this.tv_time.setOnClickListener(this);
        this.tv_date.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_announcement /*{ENCODED_INT: 2131296794}*/:
                createAnnouncementDialog().show();
                return;
            case R.id.tv_app_update:
                createUpdateDialog().show();
                return;
            case R.id.tv_content /*{ENCODED_INT: 2131296796}*/:
            case R.id.tv_cricket /*{ENCODED_INT: 2131296797}*/:
            case R.id.tv_refresh /*{ENCODED_INT: 2131296800}*/:
            case R.id.tv_team1 /*{ENCODED_INT: 2131296802}*/:
            case R.id.tv_team2 /*{ENCODED_INT: 2131296803}*/:
            default:
                return;
            case R.id.tv_date /*{ENCODED_INT: 2131296798}*/:
                this.datePickerDialogFrom.show();
                return;
            case R.id.tv_delete /*{ENCODED_INT: 2131296799}*/:
                removePostData(this.bean).show();
                return;
            case R.id.tv_send /*{ENCODED_INT: 2131296801}*/:
                AppUtils.hideSoftKeyboard(this);
                if (validateData() && AppUtils.isNetConnected(this)) {
                    apiHit();
                    return;
                }
                return;
            case R.id.tv_time /*{ENCODED_INT: 2131296804}*/:
                this.timePickerDialog.show();
                return;
        }
    }

    private void apiHit() {
        try {
            String level = this.bean.getLevel();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppUtils.NOTIFY_KEY, AppUtils.POST_AND_PUSH_ADD_KEY);
            jSONObject.put(AppUtils.PUSH_ID, this.bean.getLinkId());
            jSONObject.put(AppUtils.PUSH_TITLE, this.bean.getLevel() + " : " + this.et_title.getText().toString());
            jSONObject.put(AppUtils.PUSH_BODY, this.et_body.getText().toString());
            jSONObject.put(AppUtils.PUSH_LEVEL, level);
            jSONObject.put(AppUtils.DEAD_LINE, this.bean.getDeadLine());
            jSONObject.put(AppUtils.PUSH_BANNER, this.bean.getBanner());
            jSONObject.put(AppUtils.PUSH_VALUE, getPushValue());
            jSONObject.put(AppUtils.BANNER_VALUE, getBannerValue());
            jSONObject.put(AppUtils.POST_TITLE, this.bean.getTitle());
            jSONObject.put(AppUtils.POST_URL, this.bean.getPostUrl());
            jSONObject.put(AppUtils.CURRENT_TIME, String.valueOf(System.currentTimeMillis()));
            jSONObject.put(AppUtils.POST_TIME, this.bean.getPostTime());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("to", "/topics/" + getResources().getString(R.string.subscribe_to));
            jSONObject2.put("priority", "high");
            jSONObject2.put("data", jSONObject);
            String devId = SessionUtils.getInstance(this).getDevId();
            if (devId != null && devId.length() > 20) {
                this.apiHelper.requestJsonPostData("https://fcm.googleapis.com/fcm/send", 1, jSONObject2, devId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            toast("Something went wrong please contact to admin");
        }
    }

    private int getPushValue() {
        switch (this.rg_type.getCheckedRadioButtonId()) {
            case R.id.rb_low /*{ENCODED_INT: 2131296651}*/:
                return 2;
            case R.id.rb_noti /*{ENCODED_INT: 2131296652}*/:
                return 3;
            case R.id.rb_silent /*{ENCODED_INT: 2131296656}*/:
                return 1;
            default:
                return 4;
        }
    }

    private String getBannerValue() {
        return this.cb_banner.isChecked() ? AppUtils.BANNER_YES : ExifInterface.GPS_MEASUREMENT_2D;
    }

    private boolean validateData() {
        if (this.et_title.getText().toString().trim().length() < 1) {
            toast("Please enter Notification title");
            return false;
        } else if (this.et_body.getText().toString().trim().length() >= 1) {
            return true;
        } else {
            toast("Please enter Notification body");
            return false;
        }
    }

    private AlertDialog removePostData(final PushBean pushBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.dialog_delete_notifications, (ViewGroup) null);
        final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_delete);
        ((TextView) inflate.findViewById(R.id.tv_title)).setText(pushBean.getTitle());
        builder.setView(inflate).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.rb_this) {
                    resetPushData(pushBean.getLinkId());
                } else if (checkedRadioButtonId == R.id.rb_all) {
                    resetPushData(AppUtils.KEY_ALL);
                }
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

    private void resetPushData(String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppUtils.PUSH_KEY, AppUtils.POST_RESET_KEY);
            jSONObject.put(AppUtils.PUSH_ID, str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("to", "/topics/" + getResources().getString(R.string.subscribe_to));
            jSONObject2.put("priority", "high");
            jSONObject2.put("data", jSONObject);
            String devId = SessionUtils.getInstance(this).getDevId();
            if (devId != null && devId.length() > 20) {
                this.apiHelper.requestJsonPostData("https://fcm.googleapis.com/fcm/send", 4, jSONObject2, devId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            toast("Something went wrong please contact to admin");
        }
    }

    private AlertDialog createUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View inflate = getLayoutInflater().inflate(R.layout.dialog_app_update, (ViewGroup) null);
//        final TextView textView = (TextView) inflate.findViewById(R.id.et_title);
//        final TextView textView2 = (TextView) inflate.findViewById(R.id.et_body);
//        final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_rate_update);
//        radioGroup.check(R.id.rb_rate);
//        textView.setText("Version Update Notification");
//        textView2.setText("New Version of App is available, Please update your Application from Play Store to get New and attractive look");
//        builder.setView(inflate).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//            /* class com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.Notification.MyNotificationActivity.AnonymousClass3 */
//
//            public void onClick(DialogInterface dialogInterface, int i) {
//                String[] strArr = new String[2];
//                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_rate) {
//                    strArr[0] = AppUtils.APP_RATE_LEVEL;
//                    strArr[1] = AppUtils.APP_RATE;
//                } else {
//                    strArr[0] = AppUtils.APP_UPDATE_LEVEL;
//                    strArr[1] = AppUtils.APP_UPDATE;
//                }
//                if (textView.getText().toString().trim().length() <= 0 || textView2.getText().toString().trim().length() <= 0) {
//                    Toast.makeText(MyNotificationActivity.this, "Please fill AppUpdate title and description", 0).show();
//                    return;
//                }
//                MyNotificationActivity myNotificationActivity = MyNotificationActivity.this;
//                String charSequence = textView.getText().toString();
//                String charSequence2 = textView2.getText().toString();
//                String str = strArr[0];
//                String str2 = strArr[1];
//                myNotificationActivity.sendUpdateAndAnnouncement(charSequence, charSequence2, str, str2, AppUtils.AppURL + getPackageName());
//            }
//        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            /* class com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.Notification.MyNotificationActivity.AnonymousClass4 */
//
//            public void onClick(DialogInterface dialogInterface, int i) {
//            }
//        });
        return builder.create();
    }

    private AlertDialog createAnnouncementDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.dialog_app_announcement, (ViewGroup) null);
        final TextView textView = (TextView) inflate.findViewById(R.id.et_title);
        final TextView textView2 = (TextView) inflate.findViewById(R.id.et_body);
        final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_announcement);
        textView.setText("Announcement Title");
        textView2.setText("Announcement Description i.e Privacy Policy updated, please read.");
        builder.setView(inflate).setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
                String str;
                String str2;
                String str3;
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.rb_about) {
                    str = AppUtils.ABOUT_US;
                    str2 = AppUtils.APP_ABOUT_US;
                    str3 = AppUtils.ABOUT_URL;
                } else if (checkedRadioButtonId == R.id.rb_disclaimer) {
                    str = AppUtils.DISCLAIMER;
                    str2 = AppUtils.APP_DISCLAIMER;
                    str3 = AppUtils.DISCLAIMER_URL;
                } else if (checkedRadioButtonId != R.id.rb_privacy) {
                    str3 = AppUtils.ANNOUNCEMENT_URL;
                    str2 = AppUtils.APP_ANNOUNCEMENT;
                    str = AppUtils.ANNOUNCEMENT_LEVEL;
                } else {
                    str = AppUtils.PRIVACY_POLICY;
                    str2 = AppUtils.APP_PRIVACY;
                    str3 = AppUtils.PRIVACY_URL;
                }
                if (textView.getText().toString().trim().length() <= 0 || textView2.getText().toString().trim().length() <= 0) {
                    Toast.makeText(MyNotificationActivity.this, "Please fill Announcement title and description", 0).show();
                } else {
                    sendUpdateAndAnnouncement(textView.getText().toString(), textView2.getText().toString(), str, str2, str3);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

    private void sendUpdateAndAnnouncement(String str, String str2, String str3, String str4, String str5) {
        String str6;
        try {
            if (str3.equalsIgnoreCase(AppUtils.APP_UPDATE_LEVEL)) {
                str6 = "📲 " + str;
            } else if (str3.equalsIgnoreCase(AppUtils.APP_RATE_LEVEL)) {
                str6 = "⭐ " + str;
            } else {
                str6 = "📢 " + str;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AppUtils.NOTIFY_KEY, AppUtils.PUSH_ADD_KEY);
            jSONObject.put(AppUtils.PUSH_ID, str4);
            jSONObject.put(AppUtils.PUSH_TITLE, str6);
            jSONObject.put(AppUtils.PUSH_BODY, str2);
            jSONObject.put(AppUtils.PUSH_LEVEL, str3);
            jSONObject.put(AppUtils.DEAD_LINE, this.bean.getDeadLine());
            jSONObject.put(AppUtils.PUSH_BANNER, this.bean.getBanner());
            jSONObject.put(AppUtils.PUSH_VALUE, 4);
            jSONObject.put(AppUtils.BANNER_VALUE, ExifInterface.GPS_MEASUREMENT_2D);
            jSONObject.put(AppUtils.POST_TITLE, this.bean.getTitle());
            jSONObject.put(AppUtils.POST_URL, str5);
            jSONObject.put(AppUtils.CURRENT_TIME, String.valueOf(System.currentTimeMillis()));
            jSONObject.put(AppUtils.POST_TIME, this.bean.getPostTime());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("to", "/topics/" + getResources().getString(R.string.subscribe_to));
            jSONObject2.put("priority", "high");
            jSONObject2.put("data", jSONObject);
            String devId = SessionUtils.getInstance(this).getDevId();
            if (devId != null && devId.length() > 20) {
                this.apiHelper.requestJsonPostData("https://fcm.googleapis.com/fcm/send", 2, jSONObject2, devId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong please contact to admin", 1).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void toast(String str) {
        Toast.makeText(this, str, 0).show();
    }

    @Override
    public void onResponse(String str, int i) {
        toast("Notification sent Successfully..");
        finish();
    }

    @Override
    public void onResponseError(VolleyError volleyError, int i) {
        toast("Something went wrong..");
    }

    private void setDate() {
        Calendar instance = Calendar.getInstance();
        try {
            String charSequence = this.tv_date.getText().toString();
            if (!TextUtils.isEmpty(charSequence)) {
                instance.setTime(this.sdf.parse(charSequence));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.datePickerDialogFrom = new DatePickerDialog(this.mActivity, new DatePickerDialog.OnDateSetListener() {
            /* class com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.Notification.MyNotificationActivity.AnonymousClass7 */

            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                Calendar instance = Calendar.getInstance();
                instance.set(1, i);
                instance.set(2, i2);
                instance.set(5, i3);
                tv_date.setText(sdf.format(instance.getTime()));
            }
        }, instance.get(1), instance.get(2), instance.get(5));
    }

    private void setTime() {
        Calendar instance = Calendar.getInstance();
        try {
            String charSequence = this.tv_time.getText().toString();
            if (!TextUtils.isEmpty(charSequence)) {
                instance.setTime(this.sdft.parse(charSequence));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.timePickerDialog = new TimePickerDialog(this.mActivity, new TimePickerDialog.OnTimeSetListener() {
            /* class com.yd.expert.predictionforwinningteam.fentacyteamexpert.AppData.Notification.MyNotificationActivity.AnonymousClass8 */

            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                Calendar instance = Calendar.getInstance();
                instance.set(11, i);
                instance.set(12, i2);
                tv_time.setText(sdft.format(instance.getTime()));
            }
        }, instance.get(11), instance.get(12), false);
    }
}
