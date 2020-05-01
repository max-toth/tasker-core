package com.tasker.core.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import static com.tasker.core.cli.ICmdLine.*;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public class CmdConfig {
    public static final Options options = new Options();

    static {
        options.addOption(
                Option.builder("c").longOpt(create_opt)
                        .hasArgs()
                        .numberOfArgs(2)
                        .valueSeparator(' ')
                        .argName(task_arg_name).type(String.class).desc("Title & Sentence of task short description")
                        .build()
        );
        options.addOption(
                Option.builder("r").longOpt(resolve_opt)
                        .hasArg().argName("TASK_ID")
                        .valueSeparator(' ')
                        .type(String.class).desc("Resolve task by ID")
                        .build()
        );
        options.addOption(
                Option.builder("f").longOpt(filter_opt)
                        .hasArg()
                        .numberOfArgs(3)
                        .valueSeparator(' ')
                        .desc("search task by KEYWORD<String> LIMIT<int> STATUS<Y, N>")
                        .build()
        );
        options.addOption(
                Option.builder("e").longOpt(export_opt)
                        .hasArg().argName("TYPE")
                        .valueSeparator(' ')
                        .type(String.class).desc("Export to JSON or CSF format")
                        .build()
        );
        options.addOption("s", sync_opt, false, "Sync all tasks with storage");

        options.addOption("h", help_opt, false, "Tasker help");

        options.addOption(Option.builder().longOpt("local").build());
    }
}
