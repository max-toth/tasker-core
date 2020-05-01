package com.tasker.core.cli.cmd;

import static com.tasker.core.cli.ICmdLine.export_opt;
import static com.tasker.core.cli.ICmdLine.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasker.core.LocalStorage;
import com.tasker.core.cli.Cmd;
import com.tasker.core.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public class ExportCmd implements Cmd<Object> {

    private final FileUtil fileUtil = new FileUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(CommandLine line) {
        String optionValue = line.getOptionValue(export_opt);
        if (StringUtils.isEmpty(optionValue) || optionValue.equals("JSON")) {
            try {
                File homePath = new File(home);
                if (!homePath.exists()) {
                    homePath.mkdirs();
                }
                String content = objectMapper.writeValueAsString(LocalStorage.getTaskList());
                File exportFile = new File(home + "/tasker_export_" + System.currentTimeMillis() + ".json");
                FileUtil.createFile(exportFile, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object result() {
        return null;//// TODO: 02.06.2017
    }

    @Override
    public void print() {
// // TODO: 02.06.2017  
    }
}
