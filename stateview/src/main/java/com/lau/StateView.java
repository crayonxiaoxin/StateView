package com.lau;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Lau on 2019/7/17.
 */
public class StateView extends FrameLayout {

    private int showViewType;
    private int LAYOUT_LOADING = R.layout.base_loading;
    private int LAYOUT_RETRY = R.layout.base_retry;
    private int LAYOUT_EMPTY = R.layout.base_empty;
    private View contentView;
    private SparseArray<View> views = new SparseArray<>();
    private Context context;

    public static class State {
        public static final int CONTENT = 0;
        public static final int LOADING = 1;
        public static final int RETRY = 2;
        public static final int EMPTY = 3;
    }

    public StateView(@NonNull Context context) {
        this(context, null);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        showViewType = typedArray.getInt(R.styleable.StateView_show_view, 0);
        typedArray.recycle();

        setView(State.LOADING);
        setView(State.RETRY);
        setView(State.EMPTY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("ORM", "StateView: " + getChildCount());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("ORM", "StateView: " + getChildCount());
        if (getChildCount() == 1) {
            setView(State.CONTENT);
            showView(showViewType);
        } else {
            Log.e("ORM", "onFinishInflate: stateView must have exactly one child");
        }
    }

    public View getView(int state) {
        View view = views.get(state);
        if (view == null) {
            switch (state) {
                case 0:
                    view = getChildCount() == 1 ? getChildAt(0) : null;
                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(LAYOUT_LOADING, null, false);
                    break;
                case 2:
                    view = LayoutInflater.from(context).inflate(LAYOUT_RETRY, null, false);
                    break;
                case 3:
                    view = LayoutInflater.from(context).inflate(LAYOUT_EMPTY, null, false);
                    break;
            }
        }
        return view;
    }

    public void setView(int state) {
        if (state == State.CONTENT) {
            contentView = getView(state);
        }
        View view=views.get(state,null);
        if (view==null){
            views.put(state, getView(state));
        }else{
            views.remove(state);
            views.put(state,getView(state));
        }
    }

    public void showView(int state) {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) != contentView) {
                removeViewAt(i);
            }
        }
        View child = views.get(state);
        if (child != null && child != contentView) {
            addView(views.get(state));
        }
    }

    public void setLoadingResID(int resID) {
        this.LAYOUT_LOADING = resID;
        setView(State.LOADING);
    }

    public void setRetryResID(int resID) {
        this.LAYOUT_RETRY = resID;
        setView(State.RETRY);
    }

    public void setEmptyResID(int resID) {
        this.LAYOUT_EMPTY = resID;
        setView(State.EMPTY);
    }
}
