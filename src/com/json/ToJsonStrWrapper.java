package com.json;

import com.google.gson.Gson;


public class ToJsonStrWrapper<T> implements ToJsonStr {
    private T obj;

    public ToJsonStrWrapper(T obj) {
        this.obj = obj;
    }

    public T unwrap() {
        return this.obj;
    }

    @Override
    public String toJsonStr() {
        return new Gson().toJson(this.obj);
    }
}
