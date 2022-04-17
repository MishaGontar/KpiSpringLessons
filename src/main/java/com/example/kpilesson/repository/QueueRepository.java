package com.example.kpilesson.repository;

import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, Long> {

    public void deleteQueueByUserIdAndQueueEntityId(long idQueue , long idUser);

    public User findFirstByUserIdAndQueueEntityId(long idUser, long idQueue);
}
