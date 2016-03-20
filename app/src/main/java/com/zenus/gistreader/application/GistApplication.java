package com.zenus.gistreader.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zenus.gistreader.controller.ApiController;
import com.zenus.gistreader.dao.DaoMaster;
import com.zenus.gistreader.dao.DaoSession;
import com.zenus.gistreader.util.Utilities;


public class GistApplication extends Application {
    public static final String APP_TAG = "Gist_Android";
    private static GistApplication instance;
    private RequestQueue requestQueue;
    public DaoSession daoSession;
    public static final String ACCESS_TOKEN = "";
    public static final String GIST_USER = "";
    private ApiController apiController;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setApiController(new ApiController());
        //recreateSchema();
        setupDatabaseManager();
    }

    public static synchronized GistApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return GistApplication.getInstance().getApplicationContext();
    }

    public synchronized RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

    public void addToRequestQueue(Request req) {
        req.setTag(APP_TAG);
        getRequestQueue().add(req);
    }


    public void addRequest(String tag, Request req) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public void cancelRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }

    public synchronized SharedPreferences getPreferences(String name) {
        return getSharedPreferences(name != null ? name : APP_TAG, Context.MODE_PRIVATE);
    }


    public synchronized SharedPreferences getPreferences() {
        return getPreferences(null);
    }


    public synchronized void putInPreferences(String key, Object val) throws Exception {
        putInPreferences(key, val, null);
    }

    public synchronized void putInPreferences(String key, Object val, String name) throws Exception {
        SharedPreferences sharedPref = getPreferences(name);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (val instanceof Integer)
            editor.putInt(key, Integer.valueOf(val.toString()));
        else if (val instanceof String)
            editor.putString(key, val.toString());
        editor.commit();
    }

    public ApiController getApiController() {
        return apiController;
    }

    public void setApiController(ApiController apiController) {
        this.apiController = apiController;
    }

    private void setupDatabaseManager() {
        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "GistDB.sqlite", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        } catch (Exception e){
            Utilities.toast("DB Problem" + ": " + e.getMessage());
        }
    }

    private void recreateSchema(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "GistDB.sqlite", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, true);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoSession getDBSession() {
        return getInstance().getDaoSession();
    }
}
