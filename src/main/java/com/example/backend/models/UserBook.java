package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserInfo user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private ReadingStatus status;

    private Integer currentPage;
    private LocalDate startedAt;
    private LocalDate finishedAt;
    private String notes;
    private Boolean isFavorite = false;

    @Version
    private Long version;

    public UserBook() {}

    @Override
    public String toString() {
        return "UserBook{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", status=" + status +
                ", currentPage=" + currentPage +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                ", version=" + version +
                '}';
    }
}
