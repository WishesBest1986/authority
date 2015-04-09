package com.neusoft.framework.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.access.jdbc.JdbcHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQL脚本执行类
 *
 * Created by admin on 2015/4/8.
 */
public class ScriptsExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ScriptsExecutor.class);

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run() {
        logger.info("scripts running ......");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (JdbcHelper.isExec(connection)) {
                logger.info("script has completed execution. skip this step");
                return;
            }

//            String databaseType = JdbcHelper.getDatabaseType(connection);
//            String[] schemas = new String[]{"db/schema-" + databaseType + ".sql", "db/init-data.sql"};
//            ScriptRunner runner = new ScriptRunner(connection, true);
//            for (String schema : schemas) {
//                runner.runScript(schema);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcHelper.close(connection);
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
