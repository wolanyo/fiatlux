package com.wolasoft.fiatlux.interfaces;

import java.util.List;

/**
 * Created by osiris on 14/05/16.
 */
public interface QueryCallback<T> {
    public void onDataReceived(List<T> dataList, boolean hasMore);
    public void onDataLoaded(List<T> dataList);
}
