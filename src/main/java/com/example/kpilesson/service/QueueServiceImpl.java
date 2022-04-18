package com.example.kpilesson.service;

import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.User;
import com.example.kpilesson.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueueServiceImpl implements QueueService {
    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findByQueueEntityId(long id) {
        return jdbcTemplate.query(
                "SELECT users.id, first_name, last_name, password, username " +
                        "from queue " +
                        "JOIN users  on users.id = queue.user_id " +
                        "JOIN queues ON queue.queue_id = queues.id and queue_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void deleteUserByUserIdAndQueueEntityId(long idQueue, long idUser) {
        jdbcTemplate.update("");
    }

    @Override
    public User findByUserIdAndQueueEntityId(long idUser, long idQueue) {
        return null;
    }

    @Override
    public void save(Queue queue) {
        queueRepository.save(queue);
    }
}
