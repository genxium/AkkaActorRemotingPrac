package com.sample.remoting.akka.model;

/**
 * This is a POJO class.
 */
public class PlusResp {
    private int val = 0;

    public int getVal() {
        return val;
    }

    public void setVal(final int val) {
        this.val = val;
    }

    public PlusResp() {

    }

    public PlusResp(final int aVal) {
        val = aVal;
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }
}
