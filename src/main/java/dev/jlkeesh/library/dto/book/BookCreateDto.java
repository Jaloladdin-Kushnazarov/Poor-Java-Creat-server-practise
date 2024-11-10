package dev.jlkeesh.library.dto.book;

import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link dev.jlkeesh.library.entity.Book}
 */
@AllArgsConstructor
@Getter
@ToString
public class BookCreateDto {
    private final String title;
    private final String author;
    private final String description;
    private final String publishDate;
    private final Part cover;
    private final Part book;
}