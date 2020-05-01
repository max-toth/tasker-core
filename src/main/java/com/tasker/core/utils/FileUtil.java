package com.tasker.core.utils;

import static com.tasker.core.cli.ICmdLine.data_file;
import static com.tasker.core.cli.ICmdLine.homepath;
import static com.tasker.core.cli.ICmdLine.tasker_dir;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasker.core.LocalStorage;
import com.tasker.core.Task;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mtolstyh
 * @since 19.02.2016.
 */
public class FileUtil {

    private final File dir = new File(homepath + tasker_dir);
    private final File file = new File(homepath + tasker_dir + data_file);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public File getDir() {
        return dir;
    }

    public File getFile() {
        return file;
    }

    public void init() {
        if (!getDir().exists()) {
            boolean mkdir = getDir().mkdir();
            if (mkdir) {
                if (!getFile().exists()) {
                    saveFile();
                }
            }
        } else {
            if (getFile().exists()) {
                readFile();
            } else {
                saveFile();
            }
        }
    }

    public void sync() {
        if (!getDir().exists()) {
            boolean mkdir = getDir().mkdir();
            if (mkdir) {
                saveFile();
            }
        } else {
            saveFile();
        }
    }

    public void saveFile(File file) {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(objectMapper.writeValueAsString(LocalStorage.getTaskList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(File fileInput) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileInput))) {
            String contentBuilder = reader.lines().collect(Collectors.joining());
            LocalStorage.setTaskList(objectMapper.readValue(contentBuilder, new TypeReference<List<Task>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(File Input) {
        createFile(Input, "");
    }

    public static void createFile(File fileInput, String content) {

        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileInput), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        saveFile(file);
    }

    public void readFile() {
        readFile(file);
    }
}
