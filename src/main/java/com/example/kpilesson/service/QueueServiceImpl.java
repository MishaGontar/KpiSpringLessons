package com.example.kpilesson.service;

import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.UserEntity;
import com.example.kpilesson.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QueueServiceImpl implements QueueService {
    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<UserEntity> findByQueueEntityId(long id) {
        return jdbcTemplate.query(
                "SELECT users.id, first_name, last_name, password, username " +
                        "from queue " +
                        "JOIN users  on users.id = queue.user_id " +
                        "JOIN queues ON queue.queue_id = queues.id and queue_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public void deleteUserByUserIdAndQueueEntityId(long idQueue, long idUser) {
        System.out.println("ID Q :" + idQueue + " ID U : " + idUser);
        jdbcTemplate.update("DELETE from queue where queue_id = ? and user_id = ? ",
                idQueue, idUser);
    }

    @Override
    public UserEntity findByUserIdAndQueueEntityId(long idUser, long idQueue) {
        return jdbcTemplate.query(
                        "SELECT users.id, first_name, last_name, password, username\n" +
                                "from queue\n" +
                                "         JOIN users on users.id = queue.user_id\n" +
                                "         JOIN queues on queue.queue_id = queues.id\n" +
                                "where queue_id = ?  and user_id = ?",
                        new Object[]{idQueue, idUser}, new BeanPropertyRowMapper<>(UserEntity.class)).stream()
                .findFirst().orElse(null);
    }

    @Override
    public void updateActive(long idQueue, boolean status) {
        jdbcTemplate.update("update queues set is_active = ? where id = ?;", !status, idQueue);
    }


    @Override
    public void save(Queue queue) {
        queueRepository.save(queue);
    }
}
