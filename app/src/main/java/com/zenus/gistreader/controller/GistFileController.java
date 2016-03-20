package com.zenus.gistreader.controller;

import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.dao.GistFile;

public class GistFileController {
    public long addGistFile(GistFile file){
        long rowId = -1;
        if (file != null && !isContainsFile(file.getFilename()))
            rowId = GistApplication.getInstance().getDBSession().getGistFileDao().insertOrReplace(file);
        return rowId;
    }

    public boolean isContainsFile(String fileId){
        GistFile file = GistApplication.getInstance().getDBSession().getGistFileDao().load(fileId);
        return file != null;
    }
}
