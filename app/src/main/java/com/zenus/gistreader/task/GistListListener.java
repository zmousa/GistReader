package com.zenus.gistreader.task;

import com.zenus.gistreader.dao.Gist;

import java.util.HashMap;

public interface GistListListener {
    void gistListComplete(HashMap<String, Gist> value);
}
