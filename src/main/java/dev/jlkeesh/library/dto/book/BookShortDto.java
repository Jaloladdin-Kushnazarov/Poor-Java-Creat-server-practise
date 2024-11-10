package dev.jlkeesh.library.dto.book;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link dev.jlkeesh.library.entity.Book}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookShortDto {
    private UUID id;
    private String title;
    private String author;
    private String description;
    private LocalDate publishedAt;
    private Long size;
}