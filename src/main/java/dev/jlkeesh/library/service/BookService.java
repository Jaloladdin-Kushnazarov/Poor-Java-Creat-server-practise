package dev.jlkeesh.library.service;

import dev.jlkeesh.library.dto.Page;
import dev.jlkeesh.library.dto.book.BookCreateDto;
import dev.jlkeesh.library.dto.book.BookDetailDto;
import dev.jlkeesh.library.dto.book.BookShortDto;
import dev.jlkeesh.library.entity.Book;
import dev.jlkeesh.library.entity.File;
import dev.jlkeesh.library.repository.BookRepository;
import dev.jlkeesh.library.repository.projection.BookShortProject;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookService {
    private final BookRepository bookRepository;
    private final FileService fileService;

    public BookService(BookRepository bookRepository, FileService fileService) {
        this.bookRepository = bookRepository;
        this.fileService = fileService;
    }

    @SneakyThrows
    public void create(BookCreateDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        System.out.println(dto.getPublishDate());
        book.setPublishDate(LocalDate.now());
        File coverFile = fileService.upload(dto.getCover());
        File bookFile = fileService.upload(dto.getBook());
        book.setCover(coverFile);
        book.setBookFile(bookFile);
        bookRepository.save(book);
    }

    public Page<BookShortDto> getBookShortDtoList(int size, int page) {
        return bookRepository.findAll(size, page);
    }

    public BookDetailDto getById(String bookId) {
        Book book = bookRepository.findById(UUID.fromString(bookId));
        BookDetailDto bookDetailDto = new BookDetailDto();
        bookDetailDto.setId(book.getId());
        bookDetailDto.setTitle(book.getTitle());
        bookDetailDto.setAuthor(book.getAuthor());
        bookDetailDto.setDescription(book.getDescription());
        bookDetailDto.setPublishedAt(book.getPublishDate());
        bookDetailDto.setCover(book.getCover().getGeneratedName());
        File bookFile = book.getBookFile();
        bookDetailDto.setFile(bookFile.getGeneratedName());
        bookDetailDto.setSize(bookFile.getSize());
        return bookDetailDto;
    }
}
