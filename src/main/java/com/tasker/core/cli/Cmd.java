package com.tasker.core.cli;

import org.apache.commons.cli.CommandLine;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public interface Cmd<T> {
    void handle(CommandLine line);
    T result();
    void print();
}
