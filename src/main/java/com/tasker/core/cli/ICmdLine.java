package com.tasker.core.cli;

/**
 * @author mtolstyh
 * @since 19.02.2016.
 */
public abstract class ICmdLine {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String title_opt = "title";
    public static final String create_opt = "create";
    public static final String resolve_opt = "resolve";
    public static final String sync_opt = "sync";
    public static final String list_opt = "list";
    public static final String help_opt = "help";
    public static final String export_opt = "export";
    public static final String filter_opt = "filter";

    public static final String homedrive = System.getenv().get("HOMEDRIVE");
    public static final String homepath = System.getProperties().getProperty("user.home");

    public static final String tasker_dir = "/.Tasker";
    public static final String data_file = "/data.tsk";

    public static final String home = homepath + tasker_dir;

    public static final String task_arg_name = "TASK";
    public static final String head_arg_name = "HEAD";

    public static final String progress_state = "progress";
    public static final String complete_state = "complete";
    public static final String date_prefix = " -- ";

}
