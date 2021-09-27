package com.ike.util.task;

import com.ike.util.util.ModHandler;

/**
 * @author Ike
 * @version 1.0A
 **/
public abstract class Task {

    protected ModHandler handler;

    public Task(ModHandler handler) {
        this.handler = handler;
    }

    public abstract int execute();


    public ModHandler getHandler() {
        return handler;
    }

    public void setHandler(ModHandler handler) {
        this.handler = handler;
    }

}
