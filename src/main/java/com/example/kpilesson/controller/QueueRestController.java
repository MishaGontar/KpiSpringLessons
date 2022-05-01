package com.example.kpilesson.controller;

import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.QueueEntity;
import com.example.kpilesson.model.User;
import com.example.kpilesson.model.UserEntity;
import com.example.kpilesson.repository.QueueEntityRepository;
import com.example.kpilesson.repository.UserRepository;
import com.example.kpilesson.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/queue")
public class QueueRestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QueueEntityRepository queueEntityRepository;
    @Autowired
    private QueueService queueRepository;

    @GetMapping(value = "/add/{idQueue}")
    public ResponseEntity<String> getQueueById(@PathVariable long idQueue, Principal principal) {
        QueueEntity currentQueue = queueEntityRepository.findById(idQueue).get();
        UserEntity currentUserEntity = userRepository.findByUsername(principal.getName());
        UserEntity userEntityInQueue = queueRepository.findByUserIdAndQueueEntityId(getUserIdByPrincipal(principal), idQueue);
        if (userEntityInQueue == null && currentQueue.isActive()) {
            System.out.println("New user in queue!");
            Queue newQueue = new Queue();
            newQueue.setQueueEntity(currentQueue);
            newQueue.setUserEntity(currentUserEntity);
            queueRepository.save(newQueue);
            return ResponseEntity.ok("ok");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/delete/{idQueue}/{idUser}")
    public void deleteUserFromQueueById(@PathVariable long idQueue, @PathVariable long idUser) {
        queueRepository.deleteUserByUserIdAndQueueEntityId(idQueue, idUser);
    }

    @GetMapping(value = "/delete/{idQueue}")
    public void deleteUserFromQueueById(@PathVariable long idQueue, Principal principal) {
        QueueEntity currentQueue = queueEntityRepository.findById(idQueue).get();
        UserEntity userEntityInQueue = queueRepository.findByUserIdAndQueueEntityId(getUserIdByPrincipal(principal), idQueue);
        if (userEntityInQueue != null && currentQueue.isActive()) {
            queueRepository.deleteUserByUserIdAndQueueEntityId(idQueue, getUserIdByPrincipal(principal));
        }
    }

    @GetMapping(value = "/list/{idQueue}")
    public ResponseEntity<List<User>> getQueueById(@PathVariable long idQueue) {
        List<UserEntity> usersList = queueRepository.findByQueueEntityId(idQueue);
        List<User> users = usersList.stream().map(User::new).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/active/{idQueue}")
    public void updateActiveQueue(@PathVariable long idQueue ,Principal principal){
        QueueEntity currentQueue = queueEntityRepository.findById(idQueue).get();
        if(currentQueue.isOwnerById(userRepository.findByUsername(principal.getName()).getId())){
            queueRepository.updateActive(idQueue, currentQueue.isActive());
        }
    }
    private long getUserIdByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName()).getId();
    }
}
