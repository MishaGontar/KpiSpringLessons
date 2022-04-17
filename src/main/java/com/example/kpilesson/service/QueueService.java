package com.example.kpilesson.service;


import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueueService  {
    List<User> findByQueueEntityId(long id);

    public void deleteQueueByUserIdAndQueueEntityId(long idQueue , long idUser);

    public User findFirstByUserIdAndQueueEntityId(long idUser, long idQueue);

    public void save(Queue queue);
}
