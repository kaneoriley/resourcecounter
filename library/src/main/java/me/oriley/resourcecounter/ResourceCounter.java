/*
 * Copyright (C) 2016 Kane O'Riley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.oriley.resourcecounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResourceCounter {

    private static final String TAG = ResourceCounter.class.getSimpleName();

    private static List<ResourceCounterItem> getResourceCounts(@NonNull Class rClass) {
        List<ResourceCounterItem> items = new ArrayList<>();
        for (Class tClass : rClass.getDeclaredClasses()) {
            items.add(new ResourceCounterItem(tClass.getSimpleName(), tClass.getDeclaredFields().length));
        }
        Collections.sort(items, new ResourceCounterItem.CountComparator());
        return items;
    }

    public static void showResourceCounts(@NonNull Context context, @NonNull Class rClass) {
         new AlertDialog.Builder(context)
                .setIcon(getIcon(context))
                .setTitle(R.string.resourcecounter_dialog_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .setAdapter(new Adapter(getResourceCounts(rClass)), null)
                .show();
    }

    @Nullable
    private static Drawable getIcon(@NonNull Context context) {
        // Use app context to avoid known leak in package manager if activity context is passed
        PackageManager pm = context.getApplicationContext().getPackageManager();
        try {
            return pm.getApplicationIcon(context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "error retrieving application icon", e);
            return null;
        }
    }

    private static final class Adapter extends BaseAdapter {

        @NonNull
        private final List<ResourceCounterItem> mItems;


        Adapter(@NonNull List<ResourceCounterItem> items) {
            mItems = Collections.unmodifiableList(items);
        }


        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public ResourceCounterItem getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ResourceCounterItemView itemView;
            if (convertView != null) {
                itemView = (ResourceCounterItemView) convertView;
            } else {
                itemView = (ResourceCounterItemView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.resourcecounter_dialog_item, null);
            }

            itemView.setItem(getItem(position));
            return itemView;
        }
    }
}