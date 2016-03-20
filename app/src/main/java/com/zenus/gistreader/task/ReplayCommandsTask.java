package com.zenus.gistreader.task;

import android.os.AsyncTask;

import com.android.volley.NoConnectionError;
import com.zenus.gistreader.R;
import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.communication.Callback;
import com.zenus.gistreader.controller.CommandController;
import com.zenus.gistreader.dao.OfflineCommand;
import com.zenus.gistreader.util.Commands;
import com.zenus.gistreader.util.InternetChecker;
import com.zenus.gistreader.util.Logger;

import java.util.List;

public class ReplayCommandsTask extends AsyncTask<String, String, Void> {
    private ReplayCommandsListener mStateListener;
    private boolean finished, stepFisish;
    private static final String t = ReplayCommandsTask.class.getName();
    private String[] msg = null;

    public ReplayCommandsTask(){

    }

    @Override
    protected Void doInBackground(String... values) {
        final String gistId = values[0];

        boolean isOnline = InternetChecker.isOnline();
        if (isOnline) {
            List<OfflineCommand> gistCommands = new CommandController().getGistCommands(gistId);
            for (OfflineCommand command : gistCommands) {
                switch (command.getCommand()){
                    case Commands.DELETE_GIST: {
                        DeleteGistTask deleteGistTask = new DeleteGistTask();
                        deleteGistTask.setTaskListener(new DeleteGistListener() {
                            @Override
                            public void gistDeleteComplete() {

                            }
                        });
                        deleteGistTask.execute(gistId);

                        while (!stepFisish) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                    }
                }

            }
        } else {
            finished = true;
        }

        while (!finished) {
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
                mStateListener.replayingComplete();
            }
        }
    }

    public void setTaskListener(ReplayCommandsListener sl) {
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
