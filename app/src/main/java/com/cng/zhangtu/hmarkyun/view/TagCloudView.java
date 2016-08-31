package com.cng.zhangtu.hmarkyun.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cng.zhangtu.hmarkyun.MyApplication;
import com.cng.zhangtu.hmarkyun.R;
import com.cng.zhangtu.hmarkyun.utils.DimensionPixelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dongdz on 2016/8/29.
 */

public class TagCloudView extends LinearLayout {

    private List<Integer> colors;
    private List<Integer> randomXCanche;
    private List<Integer> viewWidthCanche;
    private List<Integer> randomYCanche;
    private List<Integer> viewHeightCanche;
    private List<String> items;

    private int textMinSize = 14;
    private int colorCount = 0;
    private int textMaxSize = 19;
    private int offset = 15;

    private boolean isLayout = false;
    private boolean isAddSigleItem = false;

    public TagCloudView(Context context) {
        this(context, null);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
        colors.add(R.drawable.shape_mark12);
        colorCount = colors.size();
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TagCloudView, defStyleAttr, 0);
        textMinSize = array.getInt(R.styleable.TagCloudView_txt_min_size, 14);
        textMaxSize = array.getInt(R.styleable.TagCloudView_txt_max_size, 19) + 1;
        array.recycle();
        randomYCanche = new ArrayList<>();
        randomXCanche = new ArrayList<>();
        viewHeightCanche = new ArrayList<>();
        viewWidthCanche = new ArrayList<>();
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.WHITE);
        colors = new ArrayList<>();
        colors.add(R.drawable.shape_mark1);
        colors.add(R.drawable.shape_mark2);
        colors.add(R.drawable.shape_mark3);
        colors.add(R.drawable.shape_mark4);
        colors.add(R.drawable.shape_mark5);
        colors.add(R.drawable.shape_mark6);
        colors.add(R.drawable.shape_mark7);
        colors.add(R.drawable.shape_mark8);
        colors.add(R.drawable.shape_mark9);
        colors.add(R.drawable.shape_mark10);
        colors.add(R.drawable.shape_mark11);
        colors.add(R.drawable.shape_mark12);
        colors.add(R.drawable.shape_mark12);
        colorCount = colors.size();
    }

    public void bindData(List<String> list) {
        items = list;
        addAllItemViews();
    }

    public void addItem(String item) {
        isAddSigleItem = true;
        items.add(item);
        if (!TextUtils.isEmpty(item)) {
            TextView textview = createTextView(item);
            addView(textview);
        }
    }

    private void addAllItemViews() {
        clearAllData();
        removeAllViews();
        if (items != null && items.size() > 0) {
            for (String text : items) {
                if (!TextUtils.isEmpty(text)) {
                    TextView textview = createTextView(text);
                    addView(textview);
                }
            }
        }
    }

    private void clearAllData() {
        randomXCanche.clear();
        randomYCanche.clear();
        viewHeightCanche.clear();
        viewWidthCanche.clear();
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textMinSize + getRandom(textMaxSize - textMinSize, 0));
        textView.setBackgroundResource(colors.get(getRandom(colorCount, 0)));
        textView.setSingleLine();
        int paddingleft = (int) DimensionPixelUtil.dip2px(getContext(), 9);
        int paddingtop = (int) DimensionPixelUtil.dip2px(getContext(), 6);
        textView.setPadding(paddingleft, paddingtop, paddingleft, paddingtop);
        textView.setText(text);
        return textView;
    }

    private boolean isValid(int x, int y) {
        for (int i = 0; i < randomXCanche.size(); i++) {
            int checkX = randomXCanche.get(i);
            int checkwidth = viewWidthCanche.get(i);
            int checkY = randomYCanche.get(i);
            int checkHeight = viewHeightCanche.get(i);
            if ((x >= checkX - offset && x <= checkX + checkwidth + offset) && (y >= checkY - offset && y <= checkY + checkHeight + offset)) {
                return false;
            }
        }
        return true;
    }

    private int getRandom(int max, int min) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 得到随机坐标
     *
     * @param view
     * @param maxX
     * @param minX
     * @param maxY
     * @param minY
     * @return
     */
    private int[] getRandomXY(View view, int maxX, int minX, int maxY, int minY) {
        int[] randomXY = new int[2];
        randomXY[0] = getRandom(maxX - view.getMeasuredWidth(), minX);
        randomXY[1] = getRandom(maxY - view.getMeasuredHeight(), minY);
        while (!isValid(randomXY[0], randomXY[1]) || !isValid(randomXY[0] + view.getMeasuredWidth(), randomXY[1])
                || !isValid(randomXY[0], randomXY[1] + view.getMeasuredHeight())
                || !isValid(randomXY[0] + view.getMeasuredWidth(), randomXY[1] + view.getMeasuredHeight())
                || !isValid(randomXY[0] + view.getMeasuredWidth() / 2, randomXY[1] + view.getMeasuredHeight() / 2)) {
            randomXY[0] = getRandom(maxX - view.getMeasuredWidth(), minX);
            randomXY[1] = getRandom(maxY - view.getMeasuredHeight(), minY);
        }
        randomXCanche.add(randomXY[0]);
        randomYCanche.add(randomXY[1]);
        viewWidthCanche.add(view.getMeasuredWidth());
        viewHeightCanche.add(view.getMeasuredHeight());
        return randomXY;
    }

    @Override
    public void requestLayout() {
        isLayout = false;
        super.requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        if (count != 0) {
            int measureWidth = 0;
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                measureChild(view, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
                measureWidth = measureWidth + view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            if (measureWidth <= MyApplication.screenWidth) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(MyApplication.screenWidth, MeasureSpec.EXACTLY);
            } else {
                if (measureWidth * 2 / 3 >= MyApplication.screenWidth) {
                    measureWidth = measureWidth * 2 / 3;
                } else {
                    measureWidth = MyApplication.screenWidth;
                }
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.AT_MOST);
            }
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isLayout) {
            isLayout = true;
            int minX = getPaddingLeft();
            int minY = getPaddingTop();
            int maxX = getMeasuredWidth() - getPaddingRight();
            int maxY = getMeasuredHeight() - getPaddingBottom();
            int count = getChildCount();
            if (isAddSigleItem) {
                isAddSigleItem = false;
                for (int i = 0; i < count; i++) {
                    View view = getChildAt(i);
                    if (i < randomXCanche.size()) {
                        view.layout(randomXCanche.get(i), randomYCanche.get(i), randomXCanche.get(i) + view.getMeasuredWidth(), randomYCanche.get(i) + view.getMeasuredHeight());
                    } else {
                        int[] randomXY = getRandomXY(view, maxX, minX, maxY, minY);
                        view.layout(randomXY[0], randomXY[1], randomXY[0] + view.getMeasuredWidth(), randomXY[1] + view.getMeasuredHeight());
                    }
                }
            } else {
                for (int i = 0; i < count; i++) {
                    View view = getChildAt(i);
//                    String text = ((TextView) view).getText().toString();
//                    Log.e("dongdianzhou", "onLayout:text:" + text + "randomX:" + randomX + " randomY:" + randomY
//                            + " view.getMeasuredHeight():" + view.getMeasuredHeight() + " view.getMeasuredWidth():" + view.getMeasuredWidth());
                    int[] randomXY = getRandomXY(view, maxX, minX, maxY, minY);
                    view.layout(randomXY[0], randomXY[1], randomXY[0] + view.getMeasuredWidth(), randomXY[1] + view.getMeasuredHeight());
                }
            }
        }
    }
}
