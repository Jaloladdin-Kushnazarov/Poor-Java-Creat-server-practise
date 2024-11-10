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
<h1 class="text-center mt-5 mb-5">Welcome to e-lib</h1>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-5 p-3">

            <h1>${book.title}</h1>
            <div style="width: 200px;">
                <img src="/upload/${book.cover}" alt="cover">
            </div>
            <h4><i>Author : ${book.author} </i> | <b>${book.publishedAt}</b> | <b><fmt:formatNumber
                    value="${book.size / 1024.0 / 1024}" maxFractionDigits="2"/> MB</b></h4>
            <p>${book.description}</p>
        </div>
        <div class="col-md-7 p-3">
            <iframe style="height: 100%; width: 100%; min-height: 500px"
                    src="/upload/${book.file}"
                    title="bu yerda kitob chiqishi kerak edi"></iframe>
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
