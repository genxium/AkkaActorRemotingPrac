package com.sample.remoting.akka.model;

/**
 * This is a POJO class.
 */
public class PlusReq {
    private int op1;
    private int op2;

    public PlusReq() {
        op1 = 0;
        op2 = 0;
    }

    public PlusReq(final int aOp1, final int aOp2) {
        op1 = aOp1;
        op2 = aOp2;
    }

    public int getOp1() {
        return op1;
    }

    public int getOp2() {
        return op2;
    }

    public void setOp1(final int op1) {
        this.op1 = op1;
    }

    public void setOp2(final int op2) {
        this.op2 = op2;
    }

    @Override
    public String toString() {
        return String.format("%d + %d", op1, op2);
    }
}
