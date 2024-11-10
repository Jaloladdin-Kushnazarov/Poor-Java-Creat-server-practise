package dev.jlkeesh.library.servlet;

import dev.jlkeesh.library.config.ApplicationContext;
import dev.jlkeesh.library.dto.Page;
import dev.jlkeesh.library.dto.book.BookCreateDto;
import dev.jlkeesh.library.dto.book.BookShortDto;
import dev.jlkeesh.library.service.BookService;
import dev.jlkeesh.library.utils.PathVariableUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(
        name = "BookServlet",
        urlPatterns = {"/", "/books"}
)
@MultipartConfig
public class BookServlet extends HttpServlet {
    private final BookService bookService;

    public BookServlet() {
        this.bookService = ApplicationContext.getBean(BookService.class);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = PathVariableUtil.parseIntOrDefault(req.getParameter("page"), 1);
        int size = PathVariableUtil.parseIntOrDefault(req.getParameter("size"), 5);
//        HttpSession session = req.getSession();
//        session.setAttribute("username", "azamjon");
//        session.setAttribute("id", "123");
//        session.setMaxInactiveInterval(60 * 60);
        Page<BookShortDto> bookPage = bookService.getBookShortDtoList(size, page);
        req.setAttribute("page", bookPage);
        req.getRequestDispatcher("/views/book/books.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String description = req.getParameter("description");
        String publishedAt = req.getParameter("published_at");
        Part cover = req.getPart("cover");
        Part book = req.getPart("book");
        BookCreateDto dto = new BookCreateDto(title, author, description, publishedAt, cover, book);
        bookService.create(dto);
        resp.sendRedirect("/books");
    }
}
