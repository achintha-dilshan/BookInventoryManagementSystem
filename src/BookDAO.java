import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
	private final DB db;

	public BookDAO() {
		this.db = new DB();
	}

	// Add a book to the database
	public void addBook(Book book) throws SQLException {
		final String INSERT_BOOK_QUERY = "INSERT INTO books (title, author, category, price) VALUES (?, ?, ?, ?)";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_BOOK_QUERY)) {

			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getCategory());
			ps.setDouble(4, book.getPrice());
			ps.executeUpdate();
		}
	}

	// Retrieve all books
	public List<Book> getAllBooks() throws SQLException {
		List<Book> books = new ArrayList<>();
		final String GET_ALL_BOOKS_QUERY = "SELECT * FROM books";

		try (Connection connection = db.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(GET_ALL_BOOKS_QUERY)) {

			while (rs.next()) {
				Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
						rs.getString("category"), rs.getDouble("price"));
				books.add(book);
			}
		}

		return books;

	}

	// check if book is already exist
	public boolean bookExists(String title) throws SQLException {
		String query = "SELECT COUNT(*) FROM books WHERE title = ?";
		try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, title);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	// update book
	public void updateBook(Book book) throws SQLException {
		String UPDATE_BOOK_QUERY = "UPDATE books SET title = ?, author = ?, category = ?, price = ? WHERE id = ?";
		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK_QUERY)) {
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getCategory());
			ps.setDouble(4, book.getPrice());
			ps.setInt(5, book.getId());
			ps.executeUpdate();
		}
	}

	// delete book
	public void deleteBook(int id) throws SQLException {
		String DELETE_BOOK_QUERY = "DELETE FROM books WHERE id = ?";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE_BOOK_QUERY)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}

	// search books
	public List<Book> searchBooks(String query) throws SQLException {
		List<Book> books = new ArrayList<>();
		String SEARCH_QUERY = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ?";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(SEARCH_QUERY);) {
			ps.setString(1, "%" + query + "%");
			ps.setString(2, "%" + query + "%");
			ps.setString(3, "%" + query + "%");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
						rs.getString("category"), rs.getDouble("price")));
			}
		}

		return books;
	}
}
