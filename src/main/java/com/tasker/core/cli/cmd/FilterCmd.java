package com.tasker.core.cli.cmd;

import static com.tasker.core.cli.ICmdLine.filter_opt;

import com.tasker.core.LocalStorage;
import com.tasker.core.Task;
import com.tasker.core.cli.Cmd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public class FilterCmd implements Cmd<List<Task>> {

    private int limit = 10;
    private String filterKeyword;
    private Boolean status;
    private final List<Task> result = new ArrayList<>();

    @Override
    public void handle(CommandLine line) {
        String[] optionValues = line.getOptionValues(filter_opt);
        filterKeyword = optionValues[0];
        limit = Integer.parseInt(optionValues[1]);
        status = StringUtils.equalsIgnoreCase("N", optionValues[2]);

        boolean keywordFilterActive = !StringUtils.containsIgnoreCase(filterKeyword, "@");
        boolean statusFilterActive = !StringUtils.equalsIgnoreCase("A", optionValues[2]);

        Predicate<Task> statusFilter = task -> task.status.equals(status);
        Predicate<Task> keywordFilterTitle = task -> StringUtils.containsIgnoreCase(task.title, filterKeyword);
        Predicate<Task> keywordFilterTask = task -> StringUtils.containsIgnoreCase(task.task, filterKeyword);
        Predicate<Task> emptyFilter = task -> task.number != null;

        List<Task> taskList = LocalStorage.getTaskList();

        result.clear();
        result.addAll(taskList
                .stream()
                .filter(
                        (keywordFilterActive ? keywordFilterTask.or(keywordFilterTitle) : emptyFilter)
                                .and(statusFilterActive ? statusFilter : (statusFilter.or(statusFilter.negate())))
                )
                .limit(limit)
                .collect(Collectors.toList()));
    }

    @Override
    public List<Task> result() {
        return Collections.unmodifiableList(result);
    }

    @Override
    public void print() {
        System.out.println("Filtered tasks " + result.size() + " of " + LocalStorage.getTaskList().size());
        result.forEach(Task::print);
    }
}
