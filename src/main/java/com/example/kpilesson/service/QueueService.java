package com.example.kpilesson.service;


import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.UserEntity;

import java.util.List;

public interface QueueService  {
    List<UserEntity> findByQueueEntityId(long id);

    public void deleteUserByUserIdAndQueueEntityId(long idQueue , long idUser);

    public UserEntity findByUserIdAndQueueEntityId(long idUser, long idQueue);

    public void updateActive(long idQueue,boolean status);
    public void save(Queue queue);
}
