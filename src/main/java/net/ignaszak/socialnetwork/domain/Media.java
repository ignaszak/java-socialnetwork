package net.ignaszak.socialnetwork.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Media() {
        uuid = UUID.randomUUID().toString();
    }

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getUuid() {
        return uuid;
    }
}
