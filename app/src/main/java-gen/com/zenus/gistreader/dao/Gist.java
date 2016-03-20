package com.zenus.gistreader.dao;

import java.util.List;
import com.zenus.gistreader.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table GIST.
 */
public class Gist implements java.io.Serializable {

    private String id;
    private String description;
    private Boolean isStared;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient GistDao myDao;

    private List<GistFile> files;

    public Gist() {
    }

    public Gist(String id) {
        this.id = id;
    }

    public Gist(String id, String description, Boolean isStared) {
        this.id = id;
        this.description = description;
        this.isStared = isStared;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGistDao() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsStared() {
        return isStared;
    }

    public void setIsStared(Boolean isStared) {
        this.isStared = isStared;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<GistFile> getFiles() {
        if (files == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GistFileDao targetDao = daoSession.getGistFileDao();
            List<GistFile> filesNew = targetDao._queryGist_Files(id);
            synchronized (this) {
                if(files == null) {
                    files = filesNew;
                }
            }
        }
        return files;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFiles() {
        files = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}