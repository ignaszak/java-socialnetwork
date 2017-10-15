package net.ignaszak.socialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "relation")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "key", nullable = false)
    @Formula("sender_id + receiver_id")
    private Integer key;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    public Relation() {
        super();
    }

    public Relation(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @JsonIgnore
    public User getSender() {
        return sender;
    }

    @JsonIgnore
    public User getReceiver() {
        return receiver;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void accept() {
        isAccepted = true;
    }
}
