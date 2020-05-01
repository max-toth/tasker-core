package com.tasker.core.cli.cmd;

import com.tasker.core.backend.BackendNetworkAdapter;
import com.tasker.core.cli.Cmd;
import com.tasker.core.cli.SyncResult;
import com.tasker.core.db.DatabaseManager;
import org.apache.commons.cli.CommandLine;

/**
 * @author mtolstyh
 * @since 02.06.2017.
 */
public class SyncCmd implements Cmd<SyncResult> {

    private SyncResult syncResult = new SyncResult();

    @Override
    public void handle(CommandLine line) {
        if (line.hasOption("--local")) {
            DatabaseManager databaseManager = new DatabaseManager();
            syncResult = databaseManager.sync();
        } else {
            BackendNetworkAdapter backend = new BackendNetworkAdapter();
            backend.sync();
        }
    }

    @Override
    public SyncResult result() {
        return syncResult;
    }

    @Override
    public void print() {
        System.out.println("<---- " + syncResult.inserted + " tasks were inserted into DB");
        System.out.println("<---- " + syncResult.updated + " tasks were updated during merge");
    }
}
