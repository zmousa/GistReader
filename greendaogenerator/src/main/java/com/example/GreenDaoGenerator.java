package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    private static Property fileGistId;
    private static Property commandGistId;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.zenus.gistreader.dao");
        Entity gist = createGistModel(schema);
        Entity file = createGistFileModel(schema);
        Entity command = createOfflineCommandModel(schema);

        gist.addToMany(file, fileGistId).setName("files");
        file.addToOne(gist, fileGistId);

        command.addToOne(gist, commandGistId);

        new DaoGenerator().generateAll(schema, "../app/src/main/java-gen");
    }
    private static Entity createGistModel(Schema schema) {
        Entity gist = schema.addEntity("Gist");
        gist.implementsInterface("java.io.Serializable");

        gist.addStringProperty("id").primaryKey().getProperty();
        gist.addStringProperty("description");
        gist.addBooleanProperty("isStared");

        return gist;
    }

    private static Entity createGistFileModel(Schema schema) {
        Entity file = schema.addEntity("GistFile");
        file.implementsInterface("java.io.Serializable");

        file.addStringProperty("filename").primaryKey();
        fileGistId = file.addStringProperty("gistId").index().getProperty();

        return file;
    }

    private static Entity createOfflineCommandModel(Schema schema) {
        Entity command = schema.addEntity("OfflineCommand");
        command.implementsInterface("java.io.Serializable");

        command.addIdProperty().autoincrement();
        command.addIntProperty("command");
        commandGistId = command.addStringProperty("gistId").index().getProperty();

        return command;
    }
}
