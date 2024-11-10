package dev.jlkeesh.library.servlet;

import dev.jlkeesh.library.config.ApplicationContext;
import dev.jlkeesh.library.dto.book.BookDetailDto;
import dev.jlkeesh.library.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "BookViewServlet",
        urlPatterns = "/books/*")
public class BookViewServlet extends HttpServlet {
    private final BookService bookService;

    public BookViewServlet() {
        this.bookService = ApplicationContext.getBean(BookService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String bookId =pathInfo.substring(1);
        BookDetailDto dto = bookService.getById(bookId);
        req.setAttribute("book", dto);
        req.getRequestDispatcher("/views/book/bookview.jsp").forward(req, resp);
    }
}
