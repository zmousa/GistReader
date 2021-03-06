package com.zenus.gistreader.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zenus.gistreader.dao.Gist;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table GIST.
*/
public class GistDao extends AbstractDao<Gist, String> {

    public static final String TABLENAME = "GIST";

    /**
     * Properties of entity Gist.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Description = new Property(1, String.class, "description", false, "DESCRIPTION");
        public final static Property IsStared = new Property(2, Boolean.class, "isStared", false, "IS_STARED");
    };

    private DaoSession daoSession;


    public GistDao(DaoConfig config) {
        super(config);
    }
    
    public GistDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GIST' (" + //
                "'ID' TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "'DESCRIPTION' TEXT," + // 1: description
                "'IS_STARED' INTEGER);"); // 2: isStared
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GIST'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Gist entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(2, description);
        }
 
        Boolean isStared = entity.getIsStared();
        if (isStared != null) {
            stmt.bindLong(3, isStared ? 1l: 0l);
        }
    }

    @Override
    protected void attachEntity(Gist entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Gist readEntity(Cursor cursor, int offset) {
        Gist entity = new Gist( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // description
            cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0 // isStared
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Gist entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDescription(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIsStared(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Gist entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Gist entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
