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
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        // 유효성 검사
        if (userId == null || userId.isEmpty() ||
            password == null || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "아이디와 비밀번호를 입력하세요.");
            return;
        }

        // 로그인 시도
        UserDTO user = userDAO.login(userId, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("books"); // 로그인 성공 시 책 목록 페이지로 이동
        } else {
            response.sendRedirect("login?error=1"); // 로그인 실패
        }
    }
}
