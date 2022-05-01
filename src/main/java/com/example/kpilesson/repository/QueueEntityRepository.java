package com.example.kpilesson.repository;

import com.example.kpilesson.model.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueEntityRepository extends JpaRepository<QueueEntity ,Long> {
}
