package com.game.smvc.entity.result;


public class SingleDataResult extends Result {
    protected String data;

    public SingleDataResult(Errcode errcode, String data) {
        super(errcode, errcode.getMsg());
        this.data = data;
    }

    public SingleDataResult(Errcode errcode, String msg, String data) {
        super(errcode, msg);
        this.data = data;
    }
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
