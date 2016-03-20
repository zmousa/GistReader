package com.zenus.gistreader.controller;

import com.zenus.gistreader.application.GistApplication;
import com.zenus.gistreader.dao.OfflineCommand;
import com.zenus.gistreader.util.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandController {
    public Long addCommand(OfflineCommand command){
        if (command != null) {
            return GistApplication.getInstance().getDBSession().getOfflineCommandDao().insertOrReplace(command);
        }
        return new Long(-1);
    }

    public void deleteCommand(Long id){
        if (id != null) {
            GistApplication.getInstance().getDBSession().getOfflineCommandDao().deleteByKey(id);
        }
    }

    public void addCommand(String gistId, int commandCode){
        OfflineCommand command = new OfflineCommand();
        command.setGistId(gistId);
        command.setCommand(commandCode);
        addCommand(command);
    }

    public List<OfflineCommand> getGistCommands(String gistId){
        List<OfflineCommand> all = GistApplication.getInstance().getDaoSession().getOfflineCommandDao().loadAll();
        List<OfflineCommand> result = new ArrayList<>();
        for (OfflineCommand command : all){
            if (command.getGistId().equals(gistId))
                result.add(command);
        }
        return result;
    }
}
