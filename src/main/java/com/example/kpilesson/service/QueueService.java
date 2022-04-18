package com.example.kpilesson.service;


import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.User;

import java.util.List;

public interface QueueService  {
    List<User> findByQueueEntityId(long id);

    public void deleteUserByUserIdAndQueueEntityId(long idQueue , long idUser);

    public User findByUserIdAndQueueEntityId(long idUser, long idQueue);

    public void save(Queue queue);
}
