package dev.jlkeesh.library.service;

import dev.jlkeesh.library.dto.file.FileDto;
import dev.jlkeesh.library.entity.File;
import dev.jlkeesh.library.repository.FileRepository;
import dev.jlkeesh.library.utils.StringUtil;
import jakarta.servlet.http.Part;
import lombok.SneakyThrows;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public class FileService {
    private final Path rootPath = Path.of("C:\\Users\\jlkeesh\\IdeaProjects\\pdp\\jakarta\\library\\files");
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File upload(Part part) throws IOException {
        String originalName = part.getSubmittedFileName();
        String generatedName = StringUtil.generateNameFromFileName(originalName);

        Path upladingPath = rootPath.resolve(generatedName);
        Files.copy(part.getInputStream(), upladingPath, StandardCopyOption.REPLACE_EXISTING);
        File file = new File();
        file.setOriginalName(originalName);
        file.setGeneratedName(generatedName);
        file.setContentType(part.getContentType());
        file.setSize(part.getSize());
        fileRepository.save(file);
        return file;
    }

    @SneakyThrows
    public FileDto getFileDtoByGeneratedName(String fileName) {
        File file = fileRepository.findByGeneratedName(fileName);
        Path filePath = rootPath.resolve(file.getGeneratedName());
        byte[] content = Files.readAllBytes(filePath);
        return new FileDto(
                file.getOriginalName(),
                file.getGeneratedName(),
                file.getContentType(),
                file.getSize(),
                content);
    }
}
