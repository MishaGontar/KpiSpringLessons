package com.example.kpilesson.service;

import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.User;
import com.example.kpilesson.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Override
    public List<User> findByQueueEntityId(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Entity " + entityManager);
        TypedQuery<User> query = entityManager.createQuery(
                "select User" +
                        " from Queue " +
                        "         join QueueEntity q on Queue.queueEntity.id = q.id " +
                        "         join User u on Queue.user.id = u.id   " +
                        " where Queue.queueEntity.id = :idQuery" , User.class);
        query.setParameter("idQuery",id);

        return query.getResultList();
    }

    @Override
    public void deleteQueueByUserIdAndQueueEntityId(long idQueue, long idUser) {
        queueRepository.deleteQueueByUserIdAndQueueEntityId(idQueue,idUser);
    }

    @Override
    public User findFirstByUserIdAndQueueEntityId(long idUser, long idQueue) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(
                "select User " +
                        " from Queue " +
                        "         join QueueEntity q on Queue.queueEntity.id = q.id " +
                        "         join User u on Queue.user.id = :idUser" +
                        " where Queue.queueEntity.id = :idQuery");
        query.setParameter("idQuery",idQueue);
        query.setParameter("idUser",idUser);
        return (User) query.getResultList().stream().findFirst().get();
    }

    @Override
    public void save(Queue queue) {
        queueRepository.save(queue);
    }
}
