package com.kaifa.customview.widget.parallaxpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.kaifa.customview.R;


public class ParallaxFactory implements LayoutInflater.Factory {

    private final LayoutInflater.Factory factory;
    private ParallaxLayoutInflater mInflater;

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.view."
    };

    public ParallaxFactory(ParallaxLayoutInflater inflater, LayoutInflater.Factory factory) {
        mInflater = inflater;
        this.factory = factory;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;

        if (context instanceof LayoutInflater.Factory) {
            view = ((LayoutInflater.Factory) context).onCreateView(name, context, attrs);
        }

        if (factory != null && view == null) {
            view = factory.onCreateView(name, context, attrs);
        }

        if (view == null) {
            view = createViewOrFailQuietly(name, context, attrs);
        }

        if (view != null) {
            onViewCreated(view, context, attrs);
        }

        return view;
    }

    protected View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
        if (name.contains(".")) {
            return createViewOrFailQuietly(name, null, context, attrs);
        }

        for (final String prefix : sClassPrefixList) {
            final View view = createViewOrFailQuietly(name, prefix, context, attrs);

            if (view != null) {
                return view;
            }
        }

        return null;
    }

    protected View createViewOrFailQuietly(String name, String prefix, Context context,
                                           AttributeSet attrs) {
        try {
            return mInflater.createView(name, prefix, attrs);
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * 主要是在viewCreated的时候将tag和view绑定起来
     *
     * @param view
     * @param context
     * @param attrs
     */
    protected void onViewCreated(View view, Context context, AttributeSet attrs) {


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ParallaxTag);

        if (a != null) {
            if (a.length() > 0) {
                ParallaxViewTag tag = new ParallaxViewTag();
                tag.alphaIn = a.getFloat(R.styleable.ParallaxTag_a_in, 0f);
                tag.alphaOut = a.getFloat(R.styleable.ParallaxTag_a_out, 0f);
                tag.xIn = a.getFloat(R.styleable.ParallaxTag_x_in, 0f);
                tag.xOut = a.getFloat(R.styleable.ParallaxTag_x_out, 0f);
                tag.yIn = a.getFloat(R.styleable.ParallaxTag_y_in, 0f);
                tag.yOut = a.getFloat(R.styleable.ParallaxTag_y_out, 0f);
                view.setTag(R.id.parallax_view_tag, tag);
            }
            a.recycle();
        }
    }
}