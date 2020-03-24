package org.prep.example;

public class Pair<T,V> {

    protected T tVal;
    protected V vVal;

    public Pair(T t, V v) {
        tVal = t;
        vVal = v;
    }

    public T gettVal() {
        return tVal;
    }

    public V getvVal() {
        return vVal;
    }
}
