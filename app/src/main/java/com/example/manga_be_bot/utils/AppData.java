package com.example.manga_be_bot.utils;

public interface AppData {
    String charset = "utf-8";
    int threadDefault = 5;

    interface Config {

        interface Input {
            String config =  "/config.json";
        }
    }

    interface Database {
        interface MySQL {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://%s/%s?user=%s&password=%s";
        }
    }
}
