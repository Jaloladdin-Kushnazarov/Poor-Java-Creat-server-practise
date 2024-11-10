package dev.jlkeesh.library.dto.file;

import java.io.InputStream;

public record FileDto(
        String originalName,
        String generatedName,
        String contentType,
        Long size,
        byte[] content
) {
}
