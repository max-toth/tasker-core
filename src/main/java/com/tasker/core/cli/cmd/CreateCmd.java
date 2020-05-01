package com.tasker.core.cli.cmd;

import static com.tasker.core.cli.ICmdLine.create_opt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasker.core.LocalStorage;
import com.tasker.core.Task;
import com.tasker.core.cli.Cmd;
import com.tasker.core.utils.FileUtil;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.cli.CommandLine;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public class CreateCmd implements Cmd<Task> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileUtil fileUtil = new FileUtil();

    private Task result;

    @Override
    public void handle(CommandLine line) {
        String[] optionValues = line.getOptionValues(create_opt);
        String title = optionValues[0];
        String task = optionValues[1];

        result = new Task();
        result.status = true;
        result.number = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        result.date = new Date(System.currentTimeMillis());
        result.title = title;
        result.task = task;

        LocalStorage.put(result);

        fileUtil.sync();
    }

    @Override
    public Task result() {
        return result;
    }

    @Override
    public void print() {
        System.out.println("--------------------------------------------->");
        System.out.println(result);
        System.out.println("--------------------------------------------->");
    }
}
