package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class UserBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Enumerated(EnumType.STRING)
    private ReadingStatus status;

    private Integer currentPage;
    private LocalDate startedAt;
    private LocalDate finishedAt;

    @Version
    private Long version;

    public UserBooks() {}

    @Override
    public String toString() {
        return "UserBooks{" +
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
