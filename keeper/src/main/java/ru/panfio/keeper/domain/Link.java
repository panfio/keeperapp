package ru.panfio.keeper.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@Entity
@Table(name = "links", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "cut"
        })}
)
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long visitCount;
    private String name;
    @NotBlank
    @Column(name = "link", length = 1000)
    private String link;
    @NotBlank
    @Column(name = "cut", length = 32, unique = true)
    private String cut;
    private Instant date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
