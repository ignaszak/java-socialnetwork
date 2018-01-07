package net.ignaszak.socialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
@Entity
@Table(name = "media")
public class Media {

    @Transient
    private static Path uploadsLocation;

    @Value("${app.uploads.path}")
    public void setUploadsLocation(String uploadsLocation) {
        Media.uploadsLocation = Paths.get(uploadsLocation);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "filename", nullable = false)
    @NotBlank(message = "Add filename")
    private String filename;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "Add author")
    private User author;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Could not set created date")
    private Date createdDate;

    @Column(name = "key")
    private String key;

    public Media() {}

    public Media(String filename) {
        this.filename = filename;
    }

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    @PreRemove
    protected void onRemove() throws IOException {
        String thumbnail = "thumbnail-" + filename;
        Files.deleteIfExists(uploadsLocation.resolve(filename));
        Files.deleteIfExists(uploadsLocation.resolve(thumbnail));
    }

    public Integer getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @JsonIgnore
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
        this.key = null;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @JsonIgnore
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
