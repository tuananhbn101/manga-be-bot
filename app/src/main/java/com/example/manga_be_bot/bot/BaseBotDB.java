package com.example.manga_be_bot.bot;

import com.example.manga_be_bot.utils.AppSql;

import org.jsoup.internal.StringUtil;


import java.sql.SQLException;
import java.sql.Statement;

public class BaseBotDB {
    protected Toolkit toolkit;
    protected AppSql appSql;

    public BaseBotDB() {
        this.toolkit = new Toolkit();
        this.appSql = new AppSql();
    }

    void clear(String table) {
        if (StringUtil.isBlank(table)) {
            toolkit.appLogger.info("null table name");
        }
        toolkit.appLogger.info(String.format("clear all data table[%s]", table));
        appSql.connect(connection -> {
            Statement statement = null;
            try {
                String sql = String.format("DELETE FROM %s", table);
                statement = connection.createStatement();
                statement.execute(sql);
            } catch (Exception e) {
                toolkit.appLogger.warning(String.format("clear all data table[%s] has error[%s]", table, e.getMessage()));
            } finally {
                try {
                    if (statement != null)
                        statement.close();
                } catch (SQLException se) {
                    toolkit.appLogger.warning(String.format("clear all data table[%s] has error[%s]", table, se.getMessage()));
                }
            }
        });
        ;
    }
}
