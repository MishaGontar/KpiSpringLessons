package com.example.kpilesson.repository;

import com.example.kpilesson.model.QueueEntity;
import com.example.kpilesson.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueEntityRepository extends JpaRepository<QueueEntity ,Long> {
    public User findByOwnerId(long id);
}
