package cn.cslg.snowymoney.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import cn.cslg.snowymoney.R;

public class CircleImageView extends AppCompatImageView {
    private final String TAG = "TAG.CircleImageView";

    /**
     * border-painter's color
     */
    private int frameColor;
    /**
     * border's width
     */
    private float frameWidth;

    private Paint mPaint = new Paint();

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        frameWidth = array.getDimension(R.styleable.CircleImageView_frame_width, 6);
        frameColor = array.getColor(R.styleable.CircleImageView_frame_color, Color.WHITE);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        //border's color
        mPaint.setColor(frameColor);
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        int w = getWidth();
        int h = getHeight();
        //view's scale
        Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(w / 2, h / 2, w / 2, mPaint);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        } else {
            sbmp = bmp;
        }
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),     Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        //draw picture
        canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2, sbmp.getHeight() / 2 -frameWidth, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        return output;
    }
}