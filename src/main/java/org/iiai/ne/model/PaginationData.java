package org.iiai.ne.model;

import java.util.List;

public class PaginationData<T> {
    private List<T> data;

    private boolean hasMore;

    public PaginationData() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
