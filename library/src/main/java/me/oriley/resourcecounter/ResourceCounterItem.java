package me.oriley.resourcecounter;

import android.support.annotation.NonNull;

import java.util.Comparator;

final class ResourceCounterItem {

    @NonNull
    final String name;

    @NonNull
    final Integer count;


    ResourceCounterItem(@NonNull String name, @NonNull Integer count) {
        this.name = name;
        this.count = count;
    }

    static class CountComparator implements Comparator<ResourceCounterItem> {
        @Override
        public int compare(ResourceCounterItem o1, ResourceCounterItem o2) {
            return compareInt(o1.count, o2.count);
        }

        private static int compareInt(int lhs, int rhs) {
            return lhs < rhs ? 1 : (lhs == rhs ? 0 : -1);
        }
    }
}
