package com.zenus.gistreader.task;

import android.os.AsyncTask;

import com.android.volley.NoConnectionError;
import com.zenus.gistreader.R;
import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.communication.Callback;
import com.zenus.gistreader.util.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public class GistViewTask extends AsyncTask<String, String, String> {
    private GistViewListener mStateListener;
    private boolean callbackReceived;
    private static final String t = GistViewTask.class.getName();
    private String[] msg = null;

    public GistViewTask(){

    }

    @Override
    protected String doInBackground(String... values) {
        String gistId = values[0];
        final StringBuilder resultBuilder = new StringBuilder();
        GistApplication.getInstance().getApiController().gistById(gistId, new Callback() {
            @Override
            public void before(Object... params) {
                Logger.log(t, "Before Callback");
            }

            @Override
            public Object after(Object... params) {
                Logger.log(t, "After Callback");
                JSONObject response = (JSONObject) params[0];
                resultBuilder.append(response.toString());
                callbackReceived = true;
                return null;
            }

            @Override
            public void error(Object... params) {
                try {
                    if (params[0] instanceof NoConnectionError) {
                        NoConnectionError noConnectionError = (NoConnectionError) params[0];
                        GistViewTask.this.msg = new String[]{GistApplication.getContext().getString(R.string.no_connection_to_host)};
                    } else {
                        GistViewTask.this.msg = new String[]{GistApplication.getContext().getString(R.string.server_error)};
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    GistViewTask.this.msg = new String[]{e.getMessage()};
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

        return resultBuilder.toString();
    }


    @Override
    protected void onPostExecute(String value) {
        synchronized (this) {
            if (mStateListener != null) {
                mStateListener.gistDetailsComplete(value);
            }
        }
    }

    public void setTaskListener(GistViewListener sl) {
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
