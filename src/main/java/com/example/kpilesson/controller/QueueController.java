package com.example.kpilesson.controller;

import com.example.kpilesson.model.QueueEntity;
import com.example.kpilesson.model.User;
import com.example.kpilesson.model.UserEntity;
import com.example.kpilesson.repository.QueueEntityRepository;
import com.example.kpilesson.repository.UserRepository;
import com.example.kpilesson.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/queue")
public class QueueController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QueueEntityRepository queueEntityRepository;
    @Autowired
    private QueueService queueRepository;

    @GetMapping(value = "/create")
    public String getCreateQueue(Model model) {
        model.addAttribute("queue", new QueueEntity());
        return "createQueue";
    }

    @PostMapping(value = "/createQueue")
    public String createQueue(QueueEntity queue, Principal principal) {
        queue.setOwner(userRepository.findByUsername(principal.getName()));
        queueEntityRepository.save(queue);
        return "redirect:/main";
    }

    @GetMapping(value = "/list")
    public String getAllQueue(Model model) {
        List<QueueEntity> queueList = queueEntityRepository.findAll();
        model.addAttribute("queueList", queueList);
        return "listQueue";
    }

    @GetMapping(value = "/{idQueue}")
    public String getQueueById(@PathVariable long idQueue, Model model, Principal principal) {
        System.out.println("Start getQueueById {}");

        QueueEntity queue = queueEntityRepository.findById(idQueue).get();

        List<UserEntity> usersList = queueRepository.findByQueueEntityId(idQueue);
        List<User> users = usersList.stream().map(User::new).collect(Collectors.toList());
        users.forEach(System.out::println);
        UserEntity currentUserEntity = userRepository.findByUsername(principal.getName());

        model.addAttribute("user", new User(currentUserEntity));
        model.addAttribute("queue", queue);
        model.addAttribute("isOwner", queue.isOwnerByUser(currentUserEntity));
        model.addAttribute("userList", users);

        System.out.println("End getQueueById {}");
        return "Queue";
    }
}

