package com.zenus.gistreader.controller;

import com.zenus.gistreader.dao.Gist;
import com.zenus.gistreader.dao.GistFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonDataController {
    private static JsonDataController instance;

    public static JsonDataController getInstance() {
        return instance != null? instance : new JsonDataController();
    }

    public Gist makeGist(JSONObject json) {
        try {
            Gist gist = new Gist();
            gist.setId(json.getString("id"));
            gist.setDescription(json.getString("description"));
            return gist;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<GistFile> makeGistFiles(JSONObject json) {
        List<GistFile> fielsResult = new ArrayList<>();
        try {
            JSONObject filesObj = json.getJSONObject("files");
            Iterator<String> iter = filesObj.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    GistFile file = makeGistFile((JSONObject)filesObj.get(key));
                    if (file != null) {
                        fielsResult.add(file);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fielsResult;
    }

    public GistFile makeGistFile(JSONObject json) {
        try {
            GistFile file = new GistFile();
            file.setFilename(json.getString("filename"));
            return file;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
