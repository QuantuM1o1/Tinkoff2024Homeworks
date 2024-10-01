package edu.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "links_archive")
public class LinkArchiveEntity {
    @Id
    private long id;

    private long linkId;

    @CreatedDate
    private OffsetDateTime addedAt;

    private OffsetDateTime deletedAt;
}
