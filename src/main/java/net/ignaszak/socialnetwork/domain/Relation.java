package net.ignaszak.socialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "relation")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "key", nullable = false, unique = true, updatable = false)
    private String key;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "invitation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invitationDate;

    @Column(name = "accepted_date")
    private Date acceptedDate;

    public Relation() {
        invitationDate = new Date();
    }

    public Relation(User sender, User receiver) {
        this();
        this.sender = sender;
        this.receiver = receiver;
        if (sender.getId() < receiver.getId()) {
            key = sender.getId() + "-" + receiver.getId();
        } else {
            key = receiver.getId() + "-" + sender.getId();
        }
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
        return accepted;
    }

    public void accept() {
        accepted = true;
        acceptedDate = new Date();
    }
}
