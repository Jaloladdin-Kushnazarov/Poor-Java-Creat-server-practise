package dev.jlkeesh.library.repository;

import dev.jlkeesh.library.dto.Page;
import dev.jlkeesh.library.dto.book.BookShortDto;
import dev.jlkeesh.library.entity.Book;

import java.util.List;
import java.util.UUID;

public class BookRepository extends AbstractRepository implements GenericRepository<Book, UUID> {

    @Override
    public Book save(Book book) {
        beginTransaction();
        em.persist(book);
        commitTransaction();
        return book;
    }

    @Override
    public Book findById(UUID uuid) {
        return em.find(Book.class, uuid);
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("from Book", Book.class).getResultList();
//        return List.of();
    }

    @Override
    public void delete(Book entity) {

    }

    @Override
    public void deleteById(UUID uuid) {

    }

    public Page<BookShortDto> findAll(int size, int page) {
        List<BookShortDto> content = em
                .createQuery("""
                                select new dev.jlkeesh.library.dto.book.BookShortDto(t.id,
                                    t.title,
                                    t.author,
                                    t.description,
                                    t.publishDate,
                                    f.size) from Book t join t.bookFile f""",
                        BookShortDto.class)
                .setFirstResult(size * (page - 1))
                .setMaxResults(size)
                .getResultList();
        Long totalCount = em.createQuery("select count(0) from Book", Long.class)
                .getSingleResult();
        Page<BookShortDto> bookPage = new Page<>();
        bookPage.setContent(content);
        bookPage.setCurrentPage(page);
        bookPage.setElementsPerPage(size);
        bookPage.setTotalPages(Math.ceilDiv(totalCount, size));
        bookPage.setTotalElements(totalCount);
        bookPage.setFirst(page == 1);
        bookPage.setLast(page == bookPage.getTotalPages());
        return bookPage;
    }
}
