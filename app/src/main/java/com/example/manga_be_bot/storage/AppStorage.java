package com.example.manga_be_bot.storage;

import android.content.Context;

import com.example.manga_be_bot.storage.model.Config;
import com.example.manga_be_bot.utils.AppData;
import com.example.manga_be_bot.utils.AppLogger;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class AppStorage {
    private static AppStorage single_instance = null;

    public static AppStorage getInstance() {
        if (single_instance == null)
            single_instance = new AppStorage();

        return single_instance;
    }

    private AppLogger appLogger;

    public String jarPath;
    public Config config;

    private AppStorage() {
        appLogger = AppLogger.getInstance();
    }

    public void loadConfig(Gson gson,Context context) {
            appLogger.info("loading current directory");
            config =getConfig(context);
            config.setting.timeCheck = config.setting.timeCheck * 60 * 1000;
            appLogger.debug(gson.toJson(config));
    }
    public static Config getConfig(Context context) {
        String json;
        Gson gson = new Gson();
        try {
            InputStream inputStream = context.getAssets().open("config.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return gson.fromJson(json,Config.class);

    }
}
