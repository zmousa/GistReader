package com.zenus.gistreader.task;

import android.os.AsyncTask;

import com.android.volley.NoConnectionError;
import com.zenus.gistreader.R;
import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.communication.Callback;
import com.zenus.gistreader.controller.CommandController;
import com.zenus.gistreader.controller.GistController;
import com.zenus.gistreader.util.Commands;
import com.zenus.gistreader.util.Logger;

public class StarGistTask extends AsyncTask<String, String, Void> {
    private StarGistListener mStateListener;
    private boolean callbackReceived;
    private static final String t = StarGistTask.class.getName();
    private String[] msg = null;
    private String gistId;

    public StarGistTask(){

    }

    @Override
    protected Void doInBackground(String... values) {
        gistId = values[0];
        GistApplication.getInstance().getApiController().starGist(gistId, new Callback() {
            @Override
            public void before(Object... params) {
                Logger.log(t, "Before Callback");
            }

            @Override
            public Object after(Object... params) {
                Logger.log(t, "After Callback");
                new GistController().editGistStarringStatus(gistId, true);
                callbackReceived = true;
                return null;
            }

            @Override
            public void error(Object... params) {
                try {
                    if (params[0] instanceof NoConnectionError) {
                        NoConnectionError noConnectionError = (NoConnectionError) params[0];
                        StarGistTask.this.msg = new String[]{GistApplication.getContext().getString(R.string.no_connection_to_host)};
                    } else {
                        StarGistTask.this.msg = new String[]{GistApplication.getContext().getString(R.string.server_error)};
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    StarGistTask.this.msg = new String[]{e.getMessage()};
                }
                new CommandController().addCommand(gistId, Commands.STAR_GIST);
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

        return null;
    }


    @Override
    protected void onPostExecute(Void v) {
        synchronized (this) {
            if (mStateListener != null) {
                mStateListener.gistStarComplete();
            }
        }
    }

    public void setTaskListener(StarGistListener sl) {
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
