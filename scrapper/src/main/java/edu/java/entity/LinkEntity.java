package edu.java.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "links")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private long id;

    private String url;

    private OffsetDateTime deletedAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @CreatedDate
    private OffsetDateTime addedAt;

    private OffsetDateTime lastActivity;

    private int siteId;

    private Integer answerCount;

    private Integer commentCount;

    @ManyToMany(mappedBy = "links")
    private List<UserEntity> users = new ArrayList<>();

    public void addUser(UserEntity user) {
        user.getLinks().add(this);
        this.getUsers().add(user);
    }

    public void removeUser(UserEntity user) {
        user.getLinks().remove(this);
        this.getUsers().remove(user);
    }
}
