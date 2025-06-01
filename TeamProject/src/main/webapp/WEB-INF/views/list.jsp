<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>도서 목록</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            background: #fff;
            font-family: 'Pretendard', '맑은 고딕', sans-serif;
        }
        h1 {
            margin-top: 48px;
            font-size: 2.1rem;
            font-weight: bold;
        }
        .form-container {
            margin-top: 40px;
            padding: 24px 32px;
            border: 1px solid #ddd;
            border-radius: 12px;
            background: #fafaff;
            box-shadow: 0 2px 8px #eaeaea;
        }
        table {
            margin: 24px auto 0;
            border-collapse: collapse;
            min-width: 480px;
            background: #fff;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 7px 13px;
            text-align: center;
        }
        th {
            background: #f6f6f6;
            font-weight: 600;
        }
        input[type="text"], input[type="number"] {
            width: 180px;
            padding: 5px 7px;
            margin-bottom: 8px;
            border: 1px solid #bbb;
            border-radius: 6px;
        }
        input[type="submit"], button {
            padding: 6px 16px;
            border-radius: 6px;
            border: none;
            background: #223c90;
            color: #fff;
            font-weight: 500;
            cursor: pointer;
            margin-top: 7px;
            transition: background 0.2s;
        }
        input[type="submit"]:hover, button:hover {
            background: #415ad7;
        }
        .search-bar {
            margin-bottom: 20px;
        }
        .search-bar input[type="text"] {
            width: 180px;
            margin-right: 6px;
        }
    </style>
</head>
<body>
    <h1>도서 목록</h1>
    <form class="search-bar" method="get" action="books">
        <input type="hidden" name="action" value="search">
        <input type="text" name="search" placeholder="검색어 입력">
        <input type="submit" value="검색">
    </form>
    <table>
        <tr>
            <th>Book ID</th>
            <th>제목</th>
            <th>저자</th>
            <th>출판사</th>
            <th>가격</th>
            <th>삭제</th>
        </tr>
        <c:forEach var="book" items="${bookList}">
            <tr>
                <td>${book.bookId}</td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.publisher}</td>
                <td>${book.price}</td>
                <td>
                    <form method="post" action="books" accept-charset="UTF-8">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${book.bookId}">
                        <button type="submit">삭제</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="form-container">
        <h2>도서 추가</h2>
        <form method="post" action="books">
            <input type="hidden" name="action" value="add">
            <div>
                <label>Book ID: </label>
                <input type="text" name="id">
            </div>
            <div>
                <label>제목: </label>
                <input type="text" name="title">
            </div>
            <div>
                <label>저자: </label>
                <input type="text" name="author">
            </div>
            <div>
                <label>출판사: </label>
                <input type="text" name="publisher">
            </div>
            <div>
                <label>가격: </label>
                <input type="text" name="price">
            </div>
            <input type="submit" value="추가">
        </form>
    </div>
</body>
</html>