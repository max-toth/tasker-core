package com.tasker.core.cli.cmd;

import static com.tasker.core.cli.ICmdLine.resolve_opt;

import com.tasker.core.LocalStorage;
import com.tasker.core.Task;
import com.tasker.core.cli.Cmd;
import com.tasker.core.cli.ResolveResult;
import com.tasker.core.utils.FileUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public class ResolveCmd implements Cmd<ResolveResult> {

    private final FileUtil fileUtil = new FileUtil();

    private final ResolveResult resolveResult = new ResolveResult();

    @Override
    public void handle(CommandLine line) {
        resolveResult.taskID = line.getOptionValue(resolve_opt);
        boolean resolved = false;
        if (StringUtils.isNotEmpty(resolveResult.taskID)) {
            for (Task t : LocalStorage.getTaskList()) {
                if (t.number.equals(resolveResult.taskID)) {
                    t.resolve();

                    resolved = true;
                    break;
                }
            }
        }

        resolveResult.result = resolved;

        fileUtil.sync();
    }

    @Override
    public ResolveResult result() {
        return resolveResult;
    }

    @Override
    public void print() {
        if (resolveResult.result) {
            System.out.println("++++++ task [" + resolveResult.taskID + "] resolved");
        } else {
            System.out.println("Task with number [" + resolveResult.taskID + "] not found.");
        }
    }
}
