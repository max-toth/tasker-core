package com.tasker.core;

import static com.tasker.core.cli.ICmdLine.complete_state;
import static com.tasker.core.cli.ICmdLine.date_prefix;
import static com.tasker.core.cli.ICmdLine.progress_state;
import static org.fusesource.jansi.Ansi.ansi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/**
 * @author mtolstyh
 * @since 19.02.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    private String number;
    private String task;
    private String title;
    private Date date;
    private Date end;
    private Boolean status; //0 complete | 1 progress

    public Task() {
    }

    public void resolve() {
        status = false;
        end = new Date(System.currentTimeMillis());
    }

    public void print() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String start = simpleDateFormat.format(date);
        String endDate = getEndDate();

        AnsiConsole.systemInstall();

        System.out.println(
                ansi().a(number).a("\t")
                        .fg(status ? Ansi.Color.RED : Ansi.Color.GREEN).a(status ? progress_state : complete_state)
                        .reset()
                        .a(date_prefix).a(start)
                        .a(date_prefix).a(endDate).a("\t")
                        .a(title).a('\t').a(task)
        );

        AnsiConsole.systemUninstall();
    }

    private String getEndDate() {
        String endDate = "\t";
        if (end != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            Calendar calendar = simpleDateFormat.getCalendar();
            calendar.setTime(date);
            int dayStart = calendar.get(Calendar.DAY_OF_MONTH);
            int monthStart = calendar.get(Calendar.MONTH);
            int yearStart = calendar.get(Calendar.YEAR);

            calendar.setTime(end);
            int dayEnd = calendar.get(Calendar.DAY_OF_MONTH);
            int monthEnd = calendar.get(Calendar.MONTH);
            int yearEnd = calendar.get(Calendar.YEAR);
            if (dayStart != dayEnd || monthStart != monthEnd || yearStart != yearEnd) {
                endDate = simpleDateFormat.format(end);
            } else {
                endDate = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + "\t";
            }
        } else {
            endDate += "\t";
        }
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task1 = (Task) o;

        if (!number.equals(task1.number)) {
            return false;
        }
        if (!task.equals(task1.task)) {
            return false;
        }
        if (!title.equals(task1.title)) {
            return false;
        }
        return date.equals(task1.date);

    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + task.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return
                "\tTask" +
                        "\n\t\ttitle: " + title +
                        "\n\t\ttask: " + task +
                        "\n\t\tnumber: " + number +
                        "\n\t\tdate: " + date +
                        "\n\t\tend: " + (end == null ? "--" : end) +
                        "\n\t\tstatus: " + (status ? progress_state : complete_state);
    }
}
