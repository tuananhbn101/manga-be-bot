package com.example.manga_be_bot.bot;

import com.example.manga_be_bot.storage.AppStorage;
import com.example.manga_be_bot.utils.AppLogger;
import com.google.gson.Gson;


import java.util.Random;

public class Toolkit {
    public Random random;
    public AppLogger appLogger;
    public AppStorage appStorage;
    public Gson gson;

    public Toolkit() {
        this.random = new Random();
        this.appLogger = AppLogger.getInstance();
        this.appStorage = AppStorage.getInstance();
        this.gson = new Gson();
    }
}
