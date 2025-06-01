package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.UserDAO;
import dao.BookDAO;
import dto.UserDTO;
import dto.Book;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private BookDAO bookDAO; 

    public void init() {
        userDAO = new UserDAO();
        bookDAO = new BookDAO(); 
    }

    // ★ GET 요청 처리 추가 (로그인 폼 보여주기)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            int price = Integer.parseInt(request.getParameter("price"));

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
                return;
            }

            // 세션에서 userId 가져오기
            UserDTO user = (UserDTO) session.getAttribute("user");
            String userId = user.getUserId();

            // 유효성 검사
            if (title == null || title.isEmpty() ||
                author == null || author.isEmpty() ||
                publisher == null || publisher.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "모든 필드를 입력하세요.");
                return;
            }

            Book book = new Book(bookId, title, author, publisher, price, userId);
            bookDAO.insertBook(book);

            response.sendRedirect("books");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "숫자를 입력하세요.");
        }
    }
}
