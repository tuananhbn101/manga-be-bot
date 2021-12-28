package com.example.manga_be_bot;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manga_be_bot.bot.BaseBot;
import com.example.manga_be_bot.storage.AppStorage;
import com.example.manga_be_bot.utils.AppLogger;
import com.google.gson.Gson;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run(getApplicationContext());
    }

    public static void run(Context context) {
        AppLogger appLogger = AppLogger.getInstance();
        appLogger.info("start");

        appLogger.info("loading data");
        Gson gson = new Gson();
        AppStorage appStorage = AppStorage.getInstance();
        appStorage.loadConfig(gson,context);

        appLogger.info("configure application");
        AppLogger.Setting setting = new AppLogger.Setting(
                appStorage.config.logger.isLog,
                appStorage.config.logger.isFile,
                appStorage.config.logger.level,
                appStorage.config.logger.directory);
        appLogger.setting(setting);

        appLogger.info("*****************************************************************************");
        appLogger.info("start schedules");
        BotControl botControl = new BotControl();
        botControl.run();
    }

    public static class BotControl {
        long delay, period;

        AppStorage appStorage;
        AppLogger appLogger;
        Timer timer;

        List<BaseBot> bots = new ArrayList<>();

        BotControl() {
            appLogger = AppLogger.getInstance();
            appLogger.info("creating main schedule");
            appStorage = AppStorage.getInstance();
            timer = new Timer();
            delay = 0;
            period = appStorage.config.setting.timeCheck;
            appLogger.debug(String.format("delay[ %s ] - period[ %s ]", delay, period));

            appLogger.info("creating bots");
            Reflections reflections = new Reflections("bot");
            Set<Class<? extends BaseBot>> classes = reflections.getSubTypesOf(BaseBot.class);
            for (Class<? extends BaseBot> clazz : classes) {
                if (clazz == null) continue;
                try {
                    Constructor constructor = clazz.getConstructor(int.class, long.class);
                    Object bot = constructor.newInstance(5, 15 * 60 * 1000);
                    bots.add((BaseBot) bot);
                    appLogger.info(String.format("create bot[ %s ] success", clazz.getName()));
                } catch (Exception e) {
                    appLogger.warning(String.format("can't create bot[ %s ] fail : %s", clazz.getName(), e.getCause()));
                }
            }
        }

        void run() {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    appLogger.info("schedule checking");
                    long now = System.currentTimeMillis();
                    for (BaseBot bot : bots) {
                        if (bot.isNeedRun(now)) bot.run();
                    }
                }
            }, delay, period);
        }
    }
}