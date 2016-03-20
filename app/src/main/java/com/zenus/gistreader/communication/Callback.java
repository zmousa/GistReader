package com.zenus.gistreader.communication;

public interface Callback {
    void before(Object... params);

    Object after(Object... params);

    void error(Object... params);
}
