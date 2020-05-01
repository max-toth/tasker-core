package com.tasker.core.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasker.core.LocalStorage;
import org.springframework.web.client.RestTemplate;

/**
 * @author mtolstyh
 * @since 05.06.2017.
 */
public class BackendNetworkAdapter {

    String api = "http://54.244.110.170:8080/tasker/rest/api/0.1";
//    String api = "http://127.0.0.1:8086/tasker/rest/api/0.1";

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    public void sync() {

        TaskerRequest request = new TaskerRequest();
        request.data = LocalStorage.getTaskList();

        Object reponse = restTemplate.postForObject(api + "/task/bulk", request, Object.class);
        try {
            System.out.println("Networking sync for " + objectMapper.writeValueAsString(reponse));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
