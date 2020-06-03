package cn.cslg.snowymoney.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }

}
