package com.mynewacc.dreamteam11.cricket.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.nativead.NativeAd;
import com.mynewacc.dreamteam11.cricket.Model.PostBean;
import com.mynewacc.dreamteam11.cricket.Utils.AppUtils;
import com.mynewacc.dreamteam11.R;
import com.pesonal.adsdk.AppManage;

import com.pesonal.adsdk.nativ_listner;
import com.pesonal.adsdk.view_Ad_sdk;
import com.squareup.picasso.Picasso;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static List<PostBean> customList;
    public static int drawableId;
    public static String[] split;
    public static String[] split2;
    private Activity mContext;
    OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i, boolean z);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        if (customList.get(i).getContent().equalsIgnoreCase("ad")) {
            return 2;
        }
        return 0;
    }


    public ListViewAdapter(Activity context, List<PostBean> list) {
        this.mContext = context;
        customList = list;
//        int ad = -1;
//        for (int i = 0; i < list.size(); i++) {
//            ad++;
//            if (ad == 4) {
//                ad = 0;
//                PostBean postBean = new PostBean();
//                postBean.setContent("ad");
//                postBean.setTitle("ad");
//                postBean.setUrl("ad");
//                customList.add(postBean);
//            }
//            customList.add(list.get(i));
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 2) {
            return new AdViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nativeadadapter, viewGroup, false));
        }
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view, viewGroup, false));
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0) {
            final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            final PostBean postBean = customList.get(i);
            int size = postBean.getImgUrl().size();
            if (size == 0) {
                myViewHolder.tvTeam.setText("Team can't Added");
                myViewHolder.tvProTeam.setText("Team can't Added");
            } else if (size == 1) {
                myViewHolder.tvTeam.setVisibility(0);
                myViewHolder.tvProTeam.setText("Team can't Added");
            } else if (size == 2) {
                myViewHolder.tvTeam.setVisibility(0);
                myViewHolder.tvProTeam.setVisibility(0);
            }
            try {
                String[] split3 = postBean.getTitle().split("\\|");
                split = split3;
                split2 = split3[0].split("(?i)vs");
                String str = split[1];
                try {
                    drawableId = getDrawableId(customList.get(i).getLevel());
                    Picasso.get().load(drawableId).into(myViewHolder.iv_team1);
                    Picasso.get().load(drawableId).into(myViewHolder.iv_team2);
                } catch (Exception e) {
                    Log.e("Exception456", "Exception456: " + e.toString());
                    e.printStackTrace();
                }
                getColorId(customList.get(i).getLevel());
                myViewHolder.tv_title.setText(str);
                myViewHolder.tv_team1.setText(split2[0]);
                myViewHolder.tv_team2.setText(split2[1]);
            } catch (Exception e2) {
                Log.e("Exception789", "Exception789: " + e2.toString());
                myViewHolder.tv_title.setText(postBean.getTitle());
            }
            try {
                if (postBean.getDeadLine() == null) {
                    TextView textView = myViewHolder.time;
                    textView.setText("🕒 " + AppUtils.getDate(postBean.getPostTime().split("T")[0]));
                    myViewHolder.time.setTextColor(this.mContext.getResources().getColor(R.color.white));
                } else if (Long.valueOf(postBean.getDeadLine()).longValue() > System.currentTimeMillis()) {
                    myViewHolder.time.setTextColor(this.mContext.getResources().getColor(R.color.white));
                    long longValue = Long.valueOf(postBean.getDeadLine()).longValue() - System.currentTimeMillis();
                    if (myViewHolder.timer != null) {
                        myViewHolder.timer.cancel();
                    }
                    myViewHolder.timer = new CountDownTimer(longValue, 1000) {

                        public void onFinish() {
                            TextView textView = myViewHolder.time;
                            textView.setText("🕒 " + AppUtils.getMilliesToStr2(Long.valueOf(postBean.getDeadLine()).longValue()));
                            myViewHolder.time.setTextColor(mContext.getResources().getColor(R.color.white));
                        }

                        public void onTick(long j) {
                            int i = (int) (j / 3600000);
                            String str = String.format("%02d", Integer.valueOf(i)) + "h " + String.format("%02d", Integer.valueOf((int) ((j / 60000) % 60))) + "m " + String.format("%02d", Integer.valueOf(((int) (j / 1000)) % 60)) + "s";
                            myViewHolder.time.setText("🕒 " + str);
                        }
                    }.start();
                } else {
                    TextView textView2 = myViewHolder.time;
                    textView2.setText("🕒 " + AppUtils.getMilliesToStr2(Long.valueOf(postBean.getDeadLine()).longValue()));
                }
            } catch (Exception e3) {
                Log.e("Exception123", "Exception123: " + e3.toString());
                e3.printStackTrace();
            }

        } else {
            AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
//            AppManage.getInstance(mContext).nativeAd((ViewGroup) adViewHolder.ads1);

            AppManage.getInstance(mContext).nativeAd((ViewGroup) adViewHolder.ads1, new nativ_listner() {
                @Override
                public void on_admob_loded(NativeAd ad, ViewGroup containor) {
                    new view_Ad_sdk(mContext).inflate_NATIV_ADMOB(ad, containor,false);
                }

                @Override
                public void on_facebook_loded(com.facebook.ads.NativeAd ad, ViewGroup containor) {
                    new view_Ad_sdk(mContext).inflate_NATIV_FB(ad, containor,false);

                }

                @Override
                public void on_startapp_loded(ArrayList<NativeAdDetails> ad, ViewGroup containor) {
                    new view_Ad_sdk(mContext).inflate_NB_STARTAPP(ad, containor,false);
                }
            });
//            AppManage.getInstance(mContext).nativeAd((ViewGroup) adViewHolder.ads1, new nativ_Listner() {
//                @Override
//                public void inflate_admob(NativeAd nativeAd) {
//                    new Inflate_ADS(mContext).inflate_NATIV_ADMOB(nativeAd, (ViewGroup) adViewHolder.ads1);
//                }
//            });
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView iv_team1;
        ImageView iv_team2;
        ConstraintLayout ll_main;
        TextView time;
        CountDownTimer timer;
        TextView tvProTeam;
        TextView tvTeam;
        TextView tv_team1;
        TextView tv_team2;
        TextView tv_title;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            time = (TextView) view.findViewById(R.id.time);
            tv_team1 = (TextView) view.findViewById(R.id.tv_team1);
            tv_team2 = (TextView) view.findViewById(R.id.tv_team2);
            ll_main = (ConstraintLayout) view.findViewById(R.id.ll_main);
            iv_team1 = (ImageView) view.findViewById(R.id.iv_team1);
            iv_team2 = (ImageView) view.findViewById(R.id.iv_team2);
            tvTeam = (TextView) view.findViewById(R.id.tvTeam);
            tvProTeam = (TextView) view.findViewById(R.id.tvProTeam);
            ll_main.setOnClickListener(this);
            ll_main.setOnLongClickListener(this);
            ll_main.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent motionEvent) {
                    try {
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            v.clearAnimation();
                            v.animate().scaleX(0.92f).setDuration(50).start();
                            v.animate().scaleY(0.92f).setDuration(50).start();
                        } else if (action == 1) {
                            v.clearAnimation();
                            v.animate().scaleX(1.0f).setDuration(50).start();
                            v.animate().scaleY(1.0f).setDuration(50).start();
                        } else if (2 != motionEvent.getAction()) {
                            v.clearAnimation();
                            v.animate().scaleX(1.0f).setDuration(50).start();
                            v.animate().scaleY(1.0f).setDuration(50).start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }

            });
        }

        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition(), false);
        }

        public boolean onLongClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition(), true);
            return false;
        }
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        CardView ads1;

        public AdViewHolder(View view) {
            super(view);
            ads1 = view.findViewById(R.id.ads1);
        }

    }

    @Override
    public int getItemCount() {
        Log.e("====", "====: " + customList.size());
        return customList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    private int getDrawableId(String str) {
        char c;
        switch (str.hashCode()) {
            case -2128102205:
                if (str.equals(AppUtils.HOCKEY_L)) {
                    c = 3;
                    break;
                }
            case -1598014511:
                if (str.equals(AppUtils.CRICKET_L)) {
                    c = 0;
                    break;
                }
            case 77069:
                if (str.equals(AppUtils.NBA_L)) {
                    c = 4;
                    break;
                }
            case 459313037:
                if (str.equals(AppUtils.FOOTBALL_L)) {
                    c = 1;
                    break;
                }
            case 713821076:
                if (str.equals(AppUtils.KABADDI_L)) {
                    c = 2;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return R.drawable.cricket;
        }
        if (c == 1) {
            return R.drawable.football;
        }
        if (c == 2) {
            return R.drawable.kabaddi;
        }
        if (c != 3) {
            return c != 4 ? R.drawable.cricket : R.drawable.nba;
        }
        return R.drawable.hockey;
    }

    private int getColorId(String str) {
        char c;
        switch (str.hashCode()) {
            case -2128102205:
                if (str.equals(AppUtils.HOCKEY_L)) {
                    c = 3;
                    break;
                }
            case -1598014511:
                if (str.equals(AppUtils.CRICKET_L)) {
                    c = 0;
                    break;
                }
            case 77069:
                if (str.equals(AppUtils.NBA_L)) {
                    c = 4;
                    break;
                }
            case 459313037:
                if (str.equals(AppUtils.FOOTBALL_L)) {
                    c = 1;
                    break;
                }
            case 713821076:
                if (str.equals(AppUtils.KABADDI_L)) {
                    c = 2;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return R.color.colorAccentAlfa1;
        }
        if (c == 1) {
            return R.color.colorAccentAlfa2;
        }
        if (c != 2) {
            return c != 3 ? R.color.colorAccentAlfa1 : R.color.colorAccentAlfa4;
        }
        return R.color.colorAccentAlfa3;
    }

}
