package com.zenus.gistreader.task;

import android.os.AsyncTask;

import com.android.volley.NoConnectionError;
import com.zenus.gistreader.R;
import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.communication.Callback;
import com.zenus.gistreader.communication.JsonResultChecker;
import com.zenus.gistreader.controller.GistController;
import com.zenus.gistreader.controller.GistFileController;
import com.zenus.gistreader.controller.JsonDataController;
import com.zenus.gistreader.dao.Gist;
import com.zenus.gistreader.dao.GistFile;
import com.zenus.gistreader.util.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class GistListTask extends AsyncTask<Void, String, HashMap<String, Gist>> {
    private GistListListener mStateListener;
    private boolean callbackReceived;
    private static final String t = GistListTask.class.getName();
    private String[] msg = null;

    public GistListTask(){

    }

    @Override
    protected HashMap<String, Gist> doInBackground(Void... values) {
        final HashMap<String, Gist> formList = new HashMap<String, Gist>();
        GistApplication.getInstance().getApiController().gistList(new Callback() {
            @Override
            public void before(Object... params) {
                Logger.log(t, "Before Callback");
            }

            @Override
            public Object after(Object... params) {
                Logger.log(t, "After Callback");
                try {
                    JSONArray response = (JSONArray) params[0];
                    // Check error message existence
                    if (response.length() > 0) {
                        JSONObject jo = response.getJSONObject(0);
                        String errorMessage = JsonResultChecker.checkResultContainsData(jo);
                        if (!errorMessage.isEmpty()) {
                            GistListTask.this.msg = new String[]{errorMessage};
                        }
                    }
                    if (GistListTask.this.msg == null)
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jo = response.getJSONObject(i);
                            Gist gist = JsonDataController.getInstance().makeGist(jo);
                            gist.setIsStared(false);
                            if (gist != null) {
                                gist = (new GistController()).addGist(gist);
                                if (gist != null) {
                                    List<GistFile> files = JsonDataController.getInstance().makeGistFiles(jo);
                                    for (GistFile file : files) {
                                        file.setGistId(gist.getId());
                                        (new GistFileController()).addGistFile(file);
                                    }
                                }
                            }

                            if (gist != null)
                                formList.put(gist.getId(), gist);
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                    GistListTask.this.msg = new String[]{e.getMessage()};
                }
                callbackReceived = true;
                return null;
            }

            @Override
            public void error(Object... params) {
                try {
                    if (params[0] instanceof NoConnectionError) {
                        NoConnectionError noConnectionError = (NoConnectionError) params[0];
                        GistListTask.this.msg = new String[]{GistApplication.getContext().getString(R.string.no_connection_to_host)};
                    } else {
                        GistListTask.this.msg = new String[]{GistApplication.getContext().getString(R.string.server_error)};
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    GistListTask.this.msg = new String[]{e.getMessage()};
                }
                callbackReceived = true;
            }
        });


        while (!callbackReceived) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (msg != null && msg.length > 0 && !"".equals(msg[0])) {
            Logger.log(t, msg[0]);
        }

        return formList;
    }


    @Override
    protected void onPostExecute(HashMap<String, Gist> value) {
        synchronized (this) {
            if (mStateListener != null) {
                mStateListener.gistListComplete(value);
            }
        }
    }

    public void setTaskListener(GistListListener sl) {
        synchronized (this) {
            mStateListener = sl;
        }
    }

    public boolean taskHasErrorMessage(){
        return (msg != null && msg.length > 0);
    }

    public String getTaskErrorMessage(){
        if ((msg != null && msg.length > 0)){
            return msg[0];
        }
        return "";
    }
}
