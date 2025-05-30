<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>도서 목록</title>
</head>
<body>
    <h1>도서 목록</h1>
    <form method="get" action="books">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" placeholder="검색어 입력">
        <input type="submit" value="검색">
    </form>
    <br>
    <table border="1">
        <tr>
            <th>Book ID</th>
            <th>제목</th>
            <th>저자</th>
            <th>출판사</th>
            <th>가격</th>
            <th>삭제</th>
        </tr>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>${book.bookId}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.publisher}</td>
                <td>${book.price}</td>
                <td>
                    <a href="books?action=delete&bookId=${book.bookId}">삭제</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h2>도서 추가</h2>
    <form method="post" action="books">
        Book ID: <input type="number" name="bookId" required><br>
        제목: <input type="text" name="title" required><br>
        저자: <input type="text" name="author" required><br>
        출판사: <input type="text" name="publisher" required><br>
        가격: <input type="number" name="price" required><br>
        <input type="submit" value="추가">
    </form>
</body>
</html>
