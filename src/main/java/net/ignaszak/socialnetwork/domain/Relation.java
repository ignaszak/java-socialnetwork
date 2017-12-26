package net.ignaszak.socialnetwork.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "relation")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "key", nullable = false, unique = true, updatable = false)
    @NotBlank
    private String key;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "sender_id")
    @NotNull
    private User sender;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "receiver_id")
    @NotNull
    private User receiver;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "invitation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
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

    public String getKey() {
        return key;
    }

    public User getSender() {
        return sender;
    }

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
