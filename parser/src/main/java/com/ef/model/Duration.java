/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef.model;

/**
 *
 * @author com4t
 */
public enum Duration {
    hourly(3600000l),
    daily(86400000l);
    private final long milliseconds;
    Duration(long h) {
        this.milliseconds=h;
    }

    public long getMilliseconds(){
        return milliseconds;
    }
}
