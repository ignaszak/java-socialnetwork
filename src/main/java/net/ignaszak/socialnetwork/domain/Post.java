package net.ignaszak.socialnetwork.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "Add author")
    private User author;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "receiver_id", nullable = false)
    @NotNull(message = "Add receiver")
    private User receiver;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL)
    @OrderBy("id")
    private Set<Media> medias;

    @Column(name = "text")
    private String text;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Could not set created date")
    private Date createdDate;

    /**
     * Unique key is used to attach medias to post
     * Medias are upload before post submit
     */
    @Transient
    private String key;

    public Post() {
        createdDate = new Date();
    }

    public Post(User author, User receiver, String text) {
        this();
        this.author = author;
        this.receiver = receiver;
        this.text = text;
    }

    public Post(User author, User receiver, Set<Media> medias) {
        this();
        this.author = author;
        this.receiver = receiver;
        this.medias = medias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isAuthor(User user) {
        return this.author.equals(user);
    }

    public String getKey() {
        return key;
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }
}
