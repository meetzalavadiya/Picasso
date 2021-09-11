package com.mynewacc.dreamteam11.cricket.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.ads.NativeBannerAd;
import com.mynewacc.dreamteam11.R;
import com.google.android.gms.ads.nativead.NativeAd;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.AppOpen;
import com.pesonal.adsdk.banner_listner;
import com.pesonal.adsdk.thankyou;
import com.pesonal.adsdk.view_Ad_sdk;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;

import java.util.ArrayList;

public class Privacy_Policy extends AppOpen {
    WebView pp;


    String html = "<html>\n" +
            "<body>\n" +
            "<div>\n" +
            "            <h3 class=\"section-title text-center wow fadeInDown\">Privacy Policy</h3>\n" +
            "        </div>\n" +
            "        <div class=\"col-md-1 col-sm-1\"></div>\n" +
            "        <div class=\"col-md-10 col-sm-10\">\n" +
            "            <div class=\"text-inter\">\n" +
            "                <p>When you use Our applications on Android,\n" +
            "                    This Privacy Policy describes the information collected by us and how we use that information.\n" +
            "                    Your privacy is important to us. Sometimes we need information to provide services that you request.\n" +
            "                </p>\n" +
            "\n" +
            "                <h4>Personal Information:</h4>\n" +
            "                <p><b>Take Photos and Videos:</b> This permission allows us to use your device's camera to take photos / videos and turn ON/OFF Camera Flash.<br><br>\n" +
            "                <b>Full network access:</b> This permission is used to access the device's network for certain functions including receiving update notifications or accessing app classification labels.<br><br>\n" +
            "                <b>Connect and disconnect from Wi-Fi:</b> This permission is used in settings and notification toolbar in order to connect and disconnect from Wi-Fi.<br><br>\n" +
            "                <b>Read Google service configuration:</b> This information is used to acquire advertising ID. Provide users with better advertising service by using such anonymous ID.<br><br>\n" +
            "                <b>Expand/collapse status bar:</b> This permission is used for the gesture feature of User System to expand and collapse the status bar.<br><br>\n" +
            "                <b>Measure app storage space:</b> This permission is used to acquire the amount of storage space used by an application.<br><br>\n" +
            "                <b>Modify system settings:</b> This permission is used in settings, in order to switch or adjust ringtone, vibration and brightness level of the screen.<br><br>\n" +
            "                <b>Photos / Media Files:</b> Modify or delete the contents of your Storage.</p><br>\n" +
            "\n" +
            "                <h4>How We Use Your Information :</h4>\n" +
            "                We DO NOT collect, store or use any personal information while you visit, download or upgrade our products.<br>\n" +
            "                We may use personal information submitted by you only for the following purposes:<br>\n" +
            "                Help us develop, deliver, and improve our products and services and supply higher quality service; manage online surveys and other activities you've participated in.<br>\n" +
            "                In the following circumstances, we may disclose your personal information according to your wish or regulations by law:<br>\n" +
            "                *  Your prior permission,<br>\n" +
            "                *  By the applicable law within or outside your country of residence, legal process, litigation requests,<br>\n" +
            "                *  By requests from public and governmental authorities,<br>\n" +
            "                *  To protect our legal rights and interests,<br>\n" +
            "                * comply with applicable law (e.g., in response to a valid court order or subpoena);\n" +
            "\n" +
            "                <h4>Non- Personal Information :</h4>\n" +
            "                We may collect and use non-personal information in the following circumstances.\n" +
            "                To have a better understanding in user's behaviour, solve problems in products and services, improve our products, services and advertising, we may collect non-personal information such as installed Other Applications name and package name, the data of instal.<br>\n" +
            "                We also collect unique device GCM token for Notification purpose.\n" +
            "                If non-personal information is combined with personal information, We treat the combined information as personal information for the purposes of this Privacy Policy.<br>\n" +
            "\n" +
            "                <b>Information we get from your use of our services  :</b>\n" +
            "                We may collect information about the services that you use and how you use them, such as when you view and interact with our content. We may collect device-specific information.We will not share that information with third parties. <br>\n" +
            "\n" +
            "                <b>Location information :</b>\n" +
            "                When you use a location-enabled service, we may collect and process information about your actual location, like GPS signals sent by a mobile device. We may also use various technologies to determine location, such as sensor data from your device that may, for example, provide information on nearby Wi-Fi access points and cell towers. <br>\n" +
            "\n" +
            "                <b>Unique Application numbers :</b>\n" +
            "                Certain services include a unique application number. This number and information about your installation (for example, the operating system type and application version number) may be sent to us when you install or uninstall that service or when that service periodically contacts our servers, such as for automatic updates. \n" +
            "\n" +
            "                <h4>Advertisement in App:</h4>\n" +
            "                <p>We use Google Admob, Facebook Audience Network, AppNext, StartApp and Unity SDK for advertisements in our Applications. There could be errors in the programming and sometime programming errors may cause unwanted side effects. </br> \n" +
            "\t\t\t\t</br>\n" +
            "\t\t\t\t<b>Ad Network Privacy Policy :</b>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t</br>\n" +
            "\t\t\t\t<ul>\n" +
            "\t\t\t\t<li><a href=\"https://support.google.com/admob/answer/6128543?hl=en\" target=\"_blank\">ADMOB Privacy</a></br></li>\n" +
            "\t\t\t\t<li><a href=\"https://www.facebook.com/about/privacy/\" target=\"_blank\">FACEBOOK Audience Network</a></br></li>\n" +
            "\t\t\t\t<li><a href=\"https://www.appnext.com/privacy-policy-oem-operators/\" target=\"_blank\">AppNext</a></br></li>\n" +
            "\t\t\t\t<li><a href=\"https://unity3d.com/legal/privacy-policy\" target=\"_blank\">Unity</a></br></li>\n" +
            "\t\t\t\t<li><a href=\"https://startapp.com/policy/privacy-policy/\" target=\"_blank\">StartApp</a></br></li>\n" +
            "\t\t\t\t</ul>\n" +
            "\t\t\t\n" +
            "\t\t\t\t</br>\n" +
            "                    We are very concerned about safeguarding the confidentiality of your information Please be aware that no security measures that we take to protect your information is absolutely guaranteed to avoid unauthorized access or use of your Non-Personal Information which is impenetrable. We haven't any Intention to Copy or use Others Product use and Access in company's Application.<br>\n" +
            "                  We are occasionally update this privacy statement. When we do so, we will also revise the \"last modified\" date of the privacy statement.<br>\n" +
            "                   \n" +
            "                    Thank You...</p>\n" +
            "\t\t\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\n" +
            "            </div>\n" +
            "          \n" +
            "        </div>\n" +
            "        <div class=\"col-md-1 col-sm-1\"/>\n" +
            "\n" +
            "\n" +
            "\n" +
            "</div>\n" +
            "</html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy__policy);

        fetchAd(new splshADlistner() {
            @Override
            public void onsuccess() {
                AppManage.getInstance((Activity) Privacy_Policy.this).banner(findViewById(R.id.ads), new banner_listner() {
                    @Override
                    public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                        new view_Ad_sdk(Privacy_Policy.this).inflate_NATIV_BANNER_ADMOB(ad, findViewById(R.id.ads),false);
                    }

                    @Override
                    public void on_facebook_loded(NativeBannerAd ad, ViewGroup containor) {
                        new view_Ad_sdk(Privacy_Policy.this).inflate_NB_FB(ad, findViewById(R.id.ads),false);
                    }

                    @Override
                    public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                        new view_Ad_sdk(Privacy_Policy.this).inflate_NB_STARTAPP(ad, findViewById(R.id.ads),false);
                    }
                });
            }
        });

        pp = findViewById(R.id.pp);
        pp.setWebViewClient(new MyWebViewClient());
        pp.getSettings().setJavaScriptEnabled(true);
        pp.getSettings().setDisplayZoomControls(true);
        pp.loadData(html, "text/html", "UTF-8");


    }
    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void next(View view) {
        AppManage.getInstance(Privacy_Policy.this).show_INTERSTIAL(Privacy_Policy.this, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                startActivity(new Intent(Privacy_Policy.this, Chek_Permission.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(Privacy_Policy.this).show_INTERSTIAL(Privacy_Policy.this,true, new AppManage.MyCallback() {
            @Override
            public void callbackCall() {
                startActivity(new Intent(Privacy_Policy.this, thankyou.class));
            }
        });
    }

}