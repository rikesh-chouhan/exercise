package org.prep.example;

public class Pair<L,R> {

    protected L left;
    protected R right;

    public Pair(L t, R v) {
        left = t;
        right = v;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
