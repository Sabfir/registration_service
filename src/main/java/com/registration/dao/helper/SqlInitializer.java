package com.registration.dao.helper;

import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.File;
import java.sql.Connection;

/**
 * The SqlInitializer class implements DB initializing algorithm.
 *
 * @author  Alex Pinta, Oleh Pinta
 */
public class SqlInitializer {
    final static String CREATE_USER_TABLE = "src/main/resources/scripts/createTable.sql";
    /**
     * This method is used to initialize DB.
     * It uses given connection and create tables
     */
    public static void initializeDatabase(Connection connection) {
        ScriptUtils.executeSqlScript(connection, new FileSystemResource(new File(CREATE_USER_TABLE).getAbsolutePath()));
    }

}
