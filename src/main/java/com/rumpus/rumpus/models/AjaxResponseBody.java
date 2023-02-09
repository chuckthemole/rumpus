package com.rumpus.rumpus.models;

import java.util.List;

import com.rumpus.common.Model;

public class AjaxResponseBody<MODEL extends RumpusModel<MODEL>> extends RumpusModel<MODEL> {

    private static final String MODEL_NAME = "AjaxResponseBodyModel";

    public AjaxResponseBody() {
        super(MODEL_NAME);
    }

    String message;
    List<MODEL> result;
 
    public String getMsg() {
        return message;
    }
 
    public void setMsg(String message) {
        this.message = message;
    }
 
    public List<MODEL> getResult() {
        return result;
    }
 
    public void setResult(List<MODEL> result) {
        this.result = result;
    }
 
}