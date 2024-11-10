package dev.jlkeesh.library.repository.projection;

import java.time.LocalDate;
import java.util.UUID;

public interface BookShortProject {
    UUID getId();

    String getTitle();

    String getAuthor();

    String getDescription();

    LocalDate getPublishedAt();

    int getSize();
}
