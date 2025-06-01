package servlet;

import dao.BookDAO;
import dto.Book;
import dto.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "search":
                searchBooks(request, response);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("bookList", books);
        request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);
    }

    private void searchBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Book> books = bookDAO.searchBooks(keyword);
        request.setAttribute("bookList", books);
        request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        bookDAO.deleteBook(bookId);
        response.sendRedirect("books");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	
    	try {
            String action = request.getParameter("action");
            if ("add".equals(action)) {
                addBook(request, response);
            } else if ("delete".equals(action)) {
                deleteBook(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "숫자를 입력하세요.");
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        int price = Integer.parseInt(request.getParameter("price"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        String userId = user.getUserId();

        if (title == null || title.isEmpty() ||
            author == null || author.isEmpty() ||
            publisher == null || publisher.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "모든 필드를 입력하세요.");
            return;
        }

        Book book = new Book(bookId, title, author, publisher, price, userId);
        bookDAO.insertBook(book);

        response.sendRedirect("books");
    }
}
