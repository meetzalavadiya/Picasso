package com.mynewacc.dreamteam11.cricket.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.mynewacc.dreamteam11.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PicassoImageGetter implements Html.ImageGetter {
    private Context mContext;
    private int sWidth;
    private TextView textView = null;

    private class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {
        protected Drawable drawable;

        @Override // com.squareup.picasso.Target
        public void onBitmapFailed(Exception exc, Drawable drawable2) {
        }

        @Override // com.squareup.picasso.Target
        public void onPrepareLoad(Drawable drawable2) {
        }

        private BitmapDrawablePlaceHolder() {
        }

        public void draw(Canvas canvas) {
            Drawable drawable2 = this.drawable;
            if (drawable2 != null) {
                drawable2.draw(canvas);
            }
        }

        public void setDrawable(Drawable drawable2) {
            this.drawable = drawable2;
            int i = PicassoImageGetter.this.sWidth - 80;
            int round = Math.round((((float) drawable2.getIntrinsicHeight()) / ((float) drawable2.getIntrinsicWidth())) * ((float) i));
            drawable2.setBounds((PicassoImageGetter.this.sWidth - i) / 4, 0, ((PicassoImageGetter.this.sWidth - i) / 4) + i, round);
            setBounds((PicassoImageGetter.this.sWidth - i) / 2, 0, i + ((PicassoImageGetter.this.sWidth - i) / 2), round);
            if (PicassoImageGetter.this.textView != null) {
                PicassoImageGetter.this.textView.setText(PicassoImageGetter.this.textView.getText());
            }
        }

        @Override // com.squareup.picasso.Target
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            setDrawable(new BitmapDrawable(PicassoImageGetter.this.mContext.getResources(), bitmap));
        }
    }

    public PicassoImageGetter(TextView textView2, Context context) {
        this.textView = textView2;
        this.mContext = context;
        this.sWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    public Drawable getDrawable(String str) {
        BitmapDrawablePlaceHolder bitmapDrawablePlaceHolder = new BitmapDrawablePlaceHolder();
        if (str.contains("glteam")) {
            Picasso.get().load(R.drawable.gl_team_info).into(bitmapDrawablePlaceHolder);
        } else {
            Picasso.get().load(str).into(bitmapDrawablePlaceHolder);
        }
        return bitmapDrawablePlaceHolder;
    }
}
