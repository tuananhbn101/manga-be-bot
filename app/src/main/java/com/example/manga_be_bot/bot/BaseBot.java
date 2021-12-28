package com.example.manga_be_bot.bot;



import com.example.manga_be_bot.utils.AppData;
import com.example.manga_be_bot.utils.AppRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseBot {
    protected boolean isRunning;
    protected long lastRun;
    protected long restTime;
    protected Toolkit toolkit;
    protected AppRequest appRequest;
    protected ExecutorService executor;

    public BaseBot(int maxThread, long restTime) {
        this.isRunning = false;
        this.restTime = restTime;
        this.lastRun = 0;
        this.toolkit = new Toolkit();
        this.appRequest = new AppRequest();
        this.executor = Executors.newFixedThreadPool(Math.max(maxThread, AppData.threadDefault));
    }

    public void run() {
        toolkit.appLogger.info("running");
        isRunning = true;
    }

    public void complete() {
        toolkit.appLogger.info("complete");
        isRunning = false;
        lastRun = System.currentTimeMillis();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isNeedRun(long now) {
        toolkit.appLogger.debug(String.format("isRunning[%s] - last[%s] - rest[%s] - now[%s]", isRunning, lastRun, restTime, now));
        return (!isRunning && (lastRun + restTime < now));
    }
}
