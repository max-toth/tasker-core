package com.tasker.core.db;

import static com.tasker.core.Conf.DB_HOST;
import static com.tasker.core.Conf.DB_NAME;
import static com.tasker.core.Conf.DB_PORT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.tasker.core.LocalStorage;
import com.tasker.core.Task;
import com.tasker.core.Tasker;
import com.tasker.core.cli.SyncResult;
import org.bson.Document;

/**
 * @author mtolstyh
 * @since 24.02.2016.
 */
public class DatabaseManager {

    static final String NUMBER = "data.number";
    static final String STATUS = "data.status";
    static final String END = "data.end";
    private MongoClient mongoClient;
    private MongoDatabase db;
    private ObjectMapper objectMapper;

    public DatabaseManager() {
        try {
            mongoClient = new MongoClient(
                    Tasker.properties.getProperty(DB_HOST),
                    Integer.parseInt(Tasker.properties.getProperty(DB_PORT))
            );
            db = mongoClient.getDatabase(Tasker.properties.getProperty(DB_NAME));
            objectMapper = new ObjectMapper();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public SyncResult sync() {
        SyncResult result = new SyncResult();
        try {
            MongoCollection<Document> taskCollection = db.getCollection("task");
            for (Task t : LocalStorage.getTaskList()) {

                UpdateResult updateResult = taskCollection.updateOne(
                        Filters.eq("data.date", t.date),
                        new Document("$set",
                                new Document(STATUS, !t.status).append(END, t.end)
                        )
                );
                if (updateResult.getMatchedCount() == 0) {
                    taskCollection.insertOne(new Document()
                            .append("data", Document.parse(objectMapper.writeValueAsString(t))));
                    result.inserted++;
                }
                if (updateResult.getModifiedCount() > 0) {
                    result.updated++;
                }
            }
            mongoClient.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
