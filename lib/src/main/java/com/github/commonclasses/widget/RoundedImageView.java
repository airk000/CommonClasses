/*
 * This source is part of the CommonClasses repository.
 *
 * Copyright 2014 Kevin Liu (airk908@gmail.com)
 *
 * CommonClasses is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CommonClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CommonClasses.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.commonclasses.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.github.commonclasses.R;

/**
 * 4-corners custom rounded ImageView
 * <p/>
 * xml attribute:
 * radiusLeftTop
 * radiusLeftBottom
 * radiusRightTop
 * radiusRightBottom
 * <p/>
 * Can not be init by new in code. Must declare in xml.
 */
public class RoundedImageView extends ImageView {
    private final float DEFAULT_RADIUS = 0f;

    private float mRadiusLT;
    private float mRadiusLB;
    private float mRadiusRT;
    private float mRadiusRB;
    private Path mPath;
    private RectF mLeftTop;
    private RectF mLeftBottom;
    private RectF mRightTop;
    private RectF mRightBottom;

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs, 0);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, 0);
        mRadiusLT = a.getDimensionPixelSize(R.styleable.RoundedImageView_radiusLeftTop, -1);
        if (mRadiusLT < 0) {
            mRadiusLT = DEFAULT_RADIUS;
        }
        mRadiusLB = a.getDimensionPixelSize(R.styleable.RoundedImageView_radiusLeftBottom, -1);
        if (mRadiusLB < 0) {
            mRadiusLB = DEFAULT_RADIUS;
        }
        mRadiusRT = a.getDimensionPixelSize(R.styleable.RoundedImageView_radiusRightTop, -1);
        if (mRadiusRT < 0) {
            mRadiusRT = DEFAULT_RADIUS;
        }
        mRadiusRB = a.getDimensionPixelSize(R.styleable.RoundedImageView_radiusRightBottom, -1);
        if (mRadiusRB < 0) {
            mRadiusRB = DEFAULT_RADIUS;
        }
        a.recycle();
        getViewTreeObserver().addOnPreDrawListener(preDrawListener);
    }

    private ViewTreeObserver.OnPreDrawListener preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            initElements();
            RoundedImageView.this.getViewTreeObserver().removeOnPreDrawListener(this);
            return true;
        }
    };

    private void initElements() {
        mPath = new Path();
        mLeftTop = new RectF(0, 0, mRadiusLT * 2, mRadiusLT * 2);
        mLeftBottom = new RectF(0, getHeight() - mRadiusLB * 2, mRadiusLB * 2, getHeight());
        mRightTop = new RectF(getWidth() - mRadiusRT * 2, 0, getWidth(), mRadiusRT * 2);
        mRightBottom = new RectF(getWidth() - mRadiusRB * 2, getHeight() - mRadiusRB * 2,
                getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.moveTo(0, mRadiusLT);
        mPath.arcTo(mLeftTop, 180, 90);
        mPath.lineTo(getWidth() - mRadiusRT, 0);
        mPath.arcTo(mRightTop, 270, 90);
        mPath.lineTo(getWidth(), getHeight() - mRadiusRB);
        mPath.arcTo(mRightBottom, 0, 90);
        mPath.lineTo(mRadiusLB, getHeight());
        mPath.arcTo(mLeftBottom, 90, 90);
        mPath.lineTo(0, mRadiusLT);

        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }
}
