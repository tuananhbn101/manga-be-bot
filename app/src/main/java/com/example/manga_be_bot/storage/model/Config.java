package com.example.manga_be_bot.storage.model;

import com.example.manga_be_bot.storage.model.database.Database;
import com.example.manga_be_bot.storage.model.manga.Manga;
import com.example.manga_be_bot.storage.model.setting.Setting;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Config {
    @SerializedName("database")
    @Expose
    public Database database;
    @SerializedName("setting")
    @Expose
    public Setting setting;
    @SerializedName("logger")
    @Expose
    public Logger logger;

    @SerializedName("manga")
    @Expose
    public Manga manga;
}