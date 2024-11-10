package dev.jlkeesh.library.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDetailDto extends BookShortDto {
    private String cover;
    private String file;
}
