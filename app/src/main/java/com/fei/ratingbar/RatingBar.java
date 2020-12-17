package com.fei.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @ClassName: RatingBar
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2020-12-14 21:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-14 21:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RatingBar extends View {

    private Bitmap mNormalBitmap;
    private Bitmap mSelectBitmap;
    private int mNum = 5;//星星总数
    private float mSpace = 10f;//间距
    private int mSingleWidth;//一个星星的宽高
    private int mProgress = 0;//当前分数

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int normalId = typedArray.getResourceId(R.styleable.RatingBar_normal, R.drawable.star_normal);
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), normalId);
        int selectId = typedArray.getResourceId(R.styleable.RatingBar_select, R.drawable.star_selected);
        mSelectBitmap = BitmapFactory.decodeResource(getResources(), selectId);
        mNum = typedArray.getInt(R.styleable.RatingBar_nums, mNum);
        mSpace = typedArray.getDimension(R.styleable.RatingBar_space, dp2px(mSpace));
        typedArray.recycle();

    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mNormalBitmap == null && mSelectBitmap == null) return;
        mSingleWidth = mNormalBitmap.getWidth();
        setMeasuredDimension((int) (mSingleWidth * mNum + (mNum - 1) * mSpace), mSingleWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mNormalBitmap == null && mSelectBitmap == null) return;
        for (int i = 0; i < mNum; i++) {
            canvas.drawBitmap(mNormalBitmap, i * mSingleWidth + i * mSpace, 0, null);
        }

        for (int i = 0; i < mProgress; i++) {
            canvas.drawBitmap(mSelectBitmap, i * mSingleWidth + i * mSpace, 0, null);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();

                if (x <= 0) return true;

                if (x > getWidth()) return true;
                int progress = (int) (x / (mSingleWidth + mSpace) + 1);
                if (progress != mProgress) {
                    mProgress = progress;
                    invalidate();
                }
                break;
        }

        return true;
    }
}
