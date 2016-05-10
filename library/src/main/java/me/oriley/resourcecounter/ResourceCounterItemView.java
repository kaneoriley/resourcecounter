package me.oriley.resourcecounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class ResourceCounterItemView extends RelativeLayout {

    @NonNull
    private final TextView mNameView;

    @NonNull
    private final TextView mCountView;

    @Nullable
    private ResourceCounterItem mItem;


    public ResourceCounterItemView(@NonNull Context context) {
        this(context, null);
    }

    public ResourceCounterItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResourceCounterItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.resourcecounter_item_view, this);
        mNameView = (TextView) findViewById(R.id.resourcecounter_item_name);
        mCountView = (TextView) findViewById(R.id.resourcecounter_item_count);
    }


    void setItem(@Nullable ResourceCounterItem item) {
        if (mItem != item) {
            mItem = item;
            updateView();
        }
    }

    private void updateView() {
        mNameView.setText(mItem != null ? mItem.name : null);
        mCountView.setText(mItem != null ? String.valueOf(mItem.count) : null);
    }
}
