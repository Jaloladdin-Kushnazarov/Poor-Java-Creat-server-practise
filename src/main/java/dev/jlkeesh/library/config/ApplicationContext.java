package dev.jlkeesh.library.config;

import dev.jlkeesh.library.repository.BookRepository;
import dev.jlkeesh.library.repository.FileRepository;
import dev.jlkeesh.library.service.BookService;
import dev.jlkeesh.library.service.FileService;

public final class ApplicationContext {

    private ApplicationContext() {
        throw new AssertionError("can not be instantiated");
    }

    private static final FileRepository fileRepository = new FileRepository();
    private static final FileService fileService = new FileService(fileRepository);

    private static final BookRepository bookRepository = new BookRepository();
    private static final BookService bookService = new BookService(bookRepository, fileService);

    @SuppressWarnings("unchecked")
    public static <B> B getBean(Class<B> clazz) {
        return switch (clazz.getSimpleName()) {
            case "FileRepository" ->  (B)fileRepository;
            case "FileService" -> (B) fileService;
            case "BookRepository" -> (B) bookRepository;
            case "BookService" -> (B) bookService;
            default -> throw new IllegalArgumentException("Bean not found:" + clazz.getSimpleName());
        };
    }
}
