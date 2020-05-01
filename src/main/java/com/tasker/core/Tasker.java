package com.tasker.core;

import com.tasker.core.cli.CmdLine;

import java.io.*;
import java.util.Properties;

import static com.tasker.core.Conf.*;

/**
 * @author mtolstyh
 * @since 19.02.2016.
 */
public class Tasker {

    protected static final String GENERATE = "generate";
    protected static final String CONFIG = "config";
    protected static final String CONFIG_XML = "config.xml";
    protected static final String TASKER_PROPERTIES = "tasker.properties";
    public static Properties properties = new Properties();

    public static void main(String[] args) {

        FileInputStream inStream;
        try {
            inStream = new FileInputStream(CONFIG_XML);
            properties.loadFromXML(inStream);
        } catch (IOException e) {
            try {
                inStream = new FileInputStream(TASKER_PROPERTIES);
                properties.load(inStream);
            } catch (IOException e1) {
                generateConfig();
            }
        }

        if (args.length == 2) {
            if (args[1].equals(GENERATE) && args[2].equals(CONFIG)) {
                generateConfig();
                return;
            }
        }

        CmdLine manager = new CmdLine();
        CmdLine.handle(args);
    }

    private static void generateConfig() {
        properties.setProperty(DB_HOST, "localhost");
        properties.setProperty(DB_PORT, String.valueOf(27017));
        properties.setProperty(DB_NAME, "tasker-db");
        File conf = new File(CONFIG_XML);
        try {
            FileOutputStream outputStream = new FileOutputStream(conf);
            properties.storeToXML(outputStream, "Basic configuration");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}