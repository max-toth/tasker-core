package com.tasker.core;

import static com.tasker.core.cli.ICmdLine.create_opt;
import static com.tasker.core.cli.ICmdLine.export_opt;
import static com.tasker.core.cli.ICmdLine.filter_opt;
import static com.tasker.core.cli.ICmdLine.help_opt;
import static com.tasker.core.cli.ICmdLine.resolve_opt;
import static com.tasker.core.cli.ICmdLine.sync_opt;

import com.tasker.core.cli.Cmd;
import com.tasker.core.cli.cmd.CreateCmd;
import com.tasker.core.cli.cmd.ExportCmd;
import com.tasker.core.cli.cmd.FilterCmd;
import com.tasker.core.cli.cmd.HelpCmd;
import com.tasker.core.cli.cmd.ResolveCmd;
import com.tasker.core.cli.cmd.SyncCmd;
import java.util.Arrays;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public enum CmdFactory {
    help(help_opt, 0, new HelpCmd()),
    filter(filter_opt, 1, new FilterCmd()),
    create(create_opt, 2, new CreateCmd()),
    sync(sync_opt, 3, new SyncCmd()),
    export(export_opt, 4, new ExportCmd()),
    resolve(resolve_opt, 5, new ResolveCmd());

    CmdFactory(String name, int order, Cmd instance) {
        this.name = name;
        this.order = order;
        this.instance = instance;
    }

    private final Cmd<Object> instance;
    private final String name;
    private final int order;

    public static CmdFactory getCmd(String name) {
        return Arrays.stream(values())
                .filter(cmdFactory -> cmdFactory.name.equals(name))
                .findFirst()
                .orElse(null);
    }
}
