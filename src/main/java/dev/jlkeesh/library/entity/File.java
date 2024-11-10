package dev.jlkeesh.library.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String generatedName;

    @Column(nullable = false)
    private String contentType;

    private long size;

    @Column(nullable = false)
    @CurrentTimestamp(event = EventType.INSERT, source = SourceType.DB)
    private LocalDateTime createdAt;

    @CurrentTimestamp(event = EventType.UPDATE, source = SourceType.DB)
    private LocalDateTime updatedAt;

}
