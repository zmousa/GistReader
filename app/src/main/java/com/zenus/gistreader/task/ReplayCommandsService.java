package com.zenus.gistreader.task;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zenus.gistreader.controller.CommandController;
import com.zenus.gistreader.dao.OfflineCommand;
import com.zenus.gistreader.util.Commands;
import com.zenus.gistreader.util.InternetChecker;

import java.util.List;


public class ReplayCommandsService extends IntentService {
    private String gistId;

    public ReplayCommandsService() {
        super("ReplayCommandsService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        gistId = intent.getStringExtra("gistId");
        boolean isOnline = true;//InternetChecker.isOnline();
        if (isOnline) {
            List<OfflineCommand> gistCommands = new CommandController().getGistCommands(gistId);
            for (final OfflineCommand command : gistCommands) {
                switch (command.getCommand()) {
                    case Commands.DELETE_GIST: {
                        DeleteGistTask deleteGistTask = new DeleteGistTask();
                        deleteGistTask.setTaskListener(new DeleteGistListener() {
                            @Override
                            public void gistDeleteComplete() {
                                new CommandController().deleteCommand(command.getId());
                            }
                        });
                        deleteGistTask.execute(gistId);
                        break;
                    }
                    case Commands.STAR_GIST: {
                        StarGistTask starGistTask = new StarGistTask();
                        starGistTask.setTaskListener(new StarGistListener() {
                             @Override
                             public void gistStarComplete() {
                                 new CommandController().deleteCommand(command.getId());
                             }
                         });
                        starGistTask.execute(gistId);
                        break;
                    }
                    case Commands.UNSTAR_GIST: {
                        UnStarGistTask unStarGistTask = new UnStarGistTask();
                        unStarGistTask.setTaskListener(new StarGistListener() {
                            @Override
                            public void gistStarComplete() {
                                new CommandController().deleteCommand(command.getId());
                            }
                        });
                        unStarGistTask.execute(gistId);
                        break;
                    }
                }
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Background Service Destroyed!!!", Toast.LENGTH_LONG).show();
    }
}