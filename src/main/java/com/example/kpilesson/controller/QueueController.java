package com.example.kpilesson.controller;

import com.example.kpilesson.model.Queue;
import com.example.kpilesson.model.QueueEntity;
import com.example.kpilesson.model.User;
import com.example.kpilesson.repository.QueueEntityRepository;
import com.example.kpilesson.repository.QueueRepository;
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
        QueueEntity queue = queueEntityRepository.findById(idQueue).get();
        if (!queue.isActive()) return "redirect:/queue/list";
        System.out.println("BEFORE");
        List<User> usersList = queueRepository.findByQueueEntityId(idQueue);
        User currentUser = userRepository.findByUsername(principal.getName());
        System.out.println("LIST size : " + usersList.size());

        model.addAttribute("user", currentUser);
        model.addAttribute("queue", queue);
        model.addAttribute("isOwner", queue.isOwnerByUser(currentUser));
        model.addAttribute("userList", usersList);
        return "Queue";
    }

    @GetMapping(value = "/add/{idQueue}")
    public String getQueueById(@PathVariable long idQueue, Principal principal) {
        QueueEntity currentQueue = queueEntityRepository.findById(idQueue).get();
        User currentUser = userRepository.findByUsername(principal.getName());
        User userInQueue = queueRepository.findFirstByUserIdAndQueueEntityId(getUserIdByPrincipal(principal), idQueue);
        if (!currentQueue.isActive() || userInQueue != null) return "redirect:/queue/list";

        Queue newQueue = new Queue();
        newQueue.setQueueEntity(currentQueue);
        newQueue.setUser(currentUser);
        queueRepository.save(newQueue);
        return "redirect:/queue/" + idQueue;
    }

    @GetMapping(value = "/delete/{idQueue}&{idUser}")
    public String deleteUserFromQueueById(@PathVariable long idQueue, @PathVariable long idUser) {
        QueueEntity queue = queueEntityRepository.findById(idQueue).get();
        queueRepository.deleteQueueByUserIdAndQueueEntityId(idQueue, idUser);
        return "redirect:/queue/" + idQueue;
    }

    @GetMapping(value = "/delete/{idQueue}")
    public String deleteUserFromQueueById(@PathVariable long idQueue, Principal principal) {
        QueueEntity currentQueue = queueEntityRepository.findById(idQueue).get();
        User userInQueue = queueRepository.findFirstByUserIdAndQueueEntityId(getUserIdByPrincipal(principal), idQueue);
        if (!currentQueue.isActive() || userInQueue == null) return "redirect:/queue/list";
        queueRepository.deleteQueueByUserIdAndQueueEntityId(idQueue, getUserIdByPrincipal(principal));
        return "redirect:/queue/" + idQueue;
    }

    private long getUserIdByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName()).getId();
    }
}

