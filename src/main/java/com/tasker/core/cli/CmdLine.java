package com.tasker.core.cli;

import com.tasker.core.CmdFactory;
import com.tasker.core.cli.cmd.HelpCmd;
import com.tasker.core.utils.FileUtil;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

/**
 * @author mtolstyh
 * @since 19.02.2016.
 */
public class CmdLine {

    public CmdLine() {
        FileUtil fileUtil = new FileUtil();
        fileUtil.init();
    }

    public static void handle(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(CmdConfig.options, args);
            Arrays.stream(line.getOptions()).forEach(op -> handleOption(line, op));
        } catch (ParseException exp) {
            System.out.println(exp.getLocalizedMessage());
            new HelpCmd().handle(null);
            System.exit(-1);
        }
        System.exit(0);
    }

    private static void handleOption(CommandLine line, Option op) {
        CmdFactory cmd = CmdFactory.getCmd(op.getLongOpt());
        if (cmd != null) {
            cmd.instance.handle(line);
            cmd.instance.print();
        }
    }
}
