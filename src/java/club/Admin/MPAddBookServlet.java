package club.Admin;

import club.business.Book;
import club.data.BookIO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MPAddBookServlet", urlPatterns = {"/MPAddBook"})
public class MPAddBookServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException {
        String code = request.getParameter("code");
        String description = request.getParameter("description");
        String quantityStr = request.getParameter("quantity");
        int quantity = 0;
        boolean isValid = true;
        ArrayList<String> errors = new ArrayList<>();

        if (code == null || code.isEmpty()) {
            errors.add("Book code is required.");
            isValid = false;
        }
        if (description == null || description.length() < 3) {
            errors.add("Description must have at least 3 characters.");
            isValid = false;
        }

        if (quantityStr.isEmpty() || quantityStr == null) {
            quantity = 0;
        } else {
            quantity = Integer.parseInt(quantityStr);
        }

        if (quantity <= 0) {
            errors.add("Quantity must be a positive number.");
            isValid = false;
        }

        if (!isValid) {
            request.setAttribute("errors", errors);
            request.setAttribute("code", code);
            request.setAttribute("description", description);
            request.setAttribute("quantity", quantityStr);
            getServletContext().getRequestDispatcher("/MPAddBook.jsp").forward(request, response);
        } else {
            String path = getServletContext().getRealPath("/WEB-INF/books.txt");
            Book book = new Book(code, description, quantity);
            BookIO.insert(book, path);
            response.sendRedirect("MPDisplayBooks");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid quantity format", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid quantity format", e);
        }
    }
}
