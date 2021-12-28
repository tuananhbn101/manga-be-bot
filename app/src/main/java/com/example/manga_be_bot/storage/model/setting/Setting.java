package com.example.manga_be_bot.storage.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting {
    @SerializedName("timeCheck")
    @Expose
    public Integer timeCheck;
}