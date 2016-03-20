package com.zenus.gistreader.controller;

import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.dao.Gist;
import com.zenus.gistreader.dao.GistFile;

import java.util.List;

public class GistController {
    public Gist addGist(Gist gist){
        Gist dbGist = null;
        if (gist != null) {
            dbGist = GistApplication.getInstance().getDBSession().getGistDao().load(gist.getId());
        }
        if (dbGist == null) {
            GistApplication.getInstance().getDBSession().getGistDao().insertOrReplace(gist);
            return gist;
        } else
            return dbGist;
    }

    public void editGistStarringStatus(String gistId, boolean starred){
        Gist gist = GistApplication.getInstance().getDBSession().getGistDao().load(gistId);
        if (gist != null) {
            gist.setIsStared(starred);

            GistApplication.getInstance().getInstance().getDaoSession().getGistDao().update(gist);
        }
    }

    public List<Gist> getGists(){
        List<Gist> result = GistApplication.getInstance().getDaoSession().getGistDao().loadAll();
        return result;
    }

    public void deleteGist(String id){
        if (id != null) {
            Gist gist = GistApplication.getInstance().getDBSession().getGistDao().load(id);
            if (gist != null && gist.getFiles().size() > 0) {
                for (GistFile file : gist.getFiles()){
                    GistApplication.getInstance().getDBSession().getGistFileDao().deleteByKey(file.getFilename());
                }
            }
            GistApplication.getInstance().getDBSession().getGistDao().deleteByKey(id);
        }
    }
}
