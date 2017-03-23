package com.nascenia.biyeta.utils;

/**
 * Created by saiful on 3/13/17.
 */

public interface MyCallback<T> {
    public void onComplete(T result, Integer id);
}