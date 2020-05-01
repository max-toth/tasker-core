package com.tasker.core.cli.cmd;

import com.tasker.core.cli.Cmd;
import com.tasker.core.cli.CmdConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public class HelpCmd implements Cmd<Object> {

    @Override
    public void handle(CommandLine line) {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Tasker", CmdConfig.options, true);
    }

    @Override
    public Object result() {
        return null;
    }

    @Override
    public void print() {
        System.out.println("help cmd object printed");
    }
}
