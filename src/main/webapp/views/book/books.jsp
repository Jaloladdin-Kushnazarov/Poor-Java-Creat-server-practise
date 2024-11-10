<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<html>
<head>
    <title>Books</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        img.book_cover {
            width: 30px;
            height: 30px;
            border-radius: 50%;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-danger">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Dropdown
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another action</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="btn btn-danger dropdown-item" href="/auth/logout">Something else here</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                </li>
            </ul>
            <form class="d-flex" role="search">
                <a class="btn btn-danger" href="/auth/logout">Logout</a>
            </form>
        </div>
    </div>
</nav>
<fmt:setLocale value="${param.lang == null ? 'uz' : param.lang}"/>
<fmt:setBundle basename="messages"/>

<h1 class="text-center mt-5 mb-5">
    <fmt:message key="home.page.title"/>
</h1>
<p>Username : ${sessionScope.get("username")}</p>
<p>Id : ${sessionScope.get("id")}</p>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-5 p-3">
            <form method="post" action="/books" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" name="title" class="form-control" id="title">
                </div>
                <div class="mb-3">
                    <label for="author" class="form-label">Author</label>
                    <input type="text" name="author" class="form-control" id="author">
                </div>
                <div class="form-floating">
                    <textarea class="form-control" name="description" placeholder="Leave a description here"
                              id="description"></textarea>
                    <label for="description">Description</label>
                </div>
                <div class="mb-3">
                    <label for="published_at" class="form-label">Published Date</label>
                    <input type="date" name="published_at" class="form-control" id="published_at">
                </div>
                <div class="mb-3">
                    <label for="cover" class="form-label">Cover Picture</label>
                    <input type="file" name="cover" class="form-control" id="cover">
                </div>
                <div class="mb-3">
                    <label for="book" class="form-label">Book</label>
                    <input type="file" name="book" class="form-control" id="book" placeholder="dive deep to java.pdf">
                </div>
                <button type="submit" class="btn btn-primary">Save Book</button>
            </form>
        </div>
        <div class="col-md-7 p-3">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Title</th>
                    <th scope="col">Author</th>
                    <th scope="col">Description</th>
                    <th scope="col">Published At</th>
                    <th scope="col">Size</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="counter" value="1"/>
                <c:forEach items="${page.content}" var="book">
                    <a href="/books/${book.id}">
                        <tr>
                            <th>${counter}</th>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.description}</td>
                            <td>${book.publishedAt}</td>
                            <td><fmt:formatNumber value="${book.size / 1024.0 / 1024}" maxFractionDigits="2"/> MB</td>
                        </tr>
                    </a>
                    <c:set var="counter" value="${counter + 1}"/>
                </c:forEach>
                </tbody>
            </table>
            <select id="size" class="mb-3">
                <option ${page.elementsPerPage == 5 ? "selected" : ""} value="5">5</option>
                <option ${page.elementsPerPage == 10 ? "selected" : ""} value="10">10</option>
                <option ${page.elementsPerPage == 15 ? "selected" : ""} value="15">15</option>
            </select>
            <nav aria-label="...">
                <ul class="pagination">
                    <li class="page-item ${page.first ? "disabled" : ""}">
                        <a class="page-link" onclick="addPageFilter(${page.previous})" tabindex="-1"
                           aria-disabled="true">Previous</a>
                    </li>
                    <c:forEach begin="1" end="${page.totalPages}" var="count">
                        <li class="page-item ${page.currentPage == count ? "active" : ""}" aria-current="page">
                            <a class="page-link" onclick="addPageFilter(${count})">${count}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${page.last ? "disabled" : ""}">
                        <a class="page-link" onclick="addPageFilter(${page.next})">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>
<script>
    const selectBox = document.getElementById("size");
    selectBox.addEventListener("change", function () {
        const selectedValue = selectBox.value;
        var url = new URL(window.location);
        url.searchParams.set("size", selectedValue);
        history.pushState({}, '', url);
        window.location.reload()
    });

    function addPageFilter(pageNumber) {
        var url = new URL(window.location);
        url.searchParams.set("page", pageNumber);
        history.pushState({}, '', url);
        window.location.reload()
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>
</html>
