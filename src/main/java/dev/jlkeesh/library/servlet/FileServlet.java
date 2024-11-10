package dev.jlkeesh.library.servlet;

import dev.jlkeesh.library.config.ApplicationContext;
import dev.jlkeesh.library.dto.file.FileDto;
import dev.jlkeesh.library.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@WebServlet(
        name = "FileServlet",
        urlPatterns = "/upload/*"
)
@MultipartConfig
public class FileServlet extends HttpServlet {
    private final FileService fileService;

    public FileServlet() {
        this.fileService = ApplicationContext.getBean(FileService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String fileName = pathInfo.substring(1);

        FileDto dto = fileService.getFileDtoByGeneratedName(fileName);

        resp.setContentType(dto.contentType());
//        resp.setHeader("Content-Disposition", "attachment; filename=\"" + upload.getOriginalName() + "\"");
        resp.getOutputStream().write(dto.content());
        resp.getOutputStream().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("file");
        fileService.upload(part);
        resp.sendRedirect("/books");
    }
}
