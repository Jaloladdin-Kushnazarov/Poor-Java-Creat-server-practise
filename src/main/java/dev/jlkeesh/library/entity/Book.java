package dev.jlkeesh.library.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
//todo
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false,length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDate publishDate;

    @OneToOne(fetch = FetchType.EAGER)
    private File cover;

    @OneToOne(fetch = FetchType.EAGER)
    private File bookFile;
}
