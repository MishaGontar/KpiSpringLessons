package com.example.kpilesson.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "queues")
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean isActive = true;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public boolean isOwnerById(long id) {
        return id == owner.getId();
    }

    public boolean isOwnerByUser(User user) {
        return user.equals(owner);
    }
}
