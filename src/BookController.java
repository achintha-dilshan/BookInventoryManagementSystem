import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BookController {
	// constants
	private static final String[] COLUMNS = { "#", "Title", "Author", "Category", "Price" };

	private AppWindow appWindow;
	private BookDAO bookDAO;

	public BookController(AppWindow appWindow) {
		this.appWindow = appWindow;
	}

	// add book
	public void addBook() {
		try {

			if (!this.validateInputs())
				return;

			String title = appWindow.getTitleField().getText();
			String author = appWindow.getAuthorField().getText();
			String category = appWindow.getCategoryComboBox().getSelectedItem().toString();
			double price = Double.parseDouble(appWindow.getPriceField().getText().trim());

			if (bookDAO.bookExists(title)) {
				JOptionPane.showMessageDialog(appWindow, "A book with this title already exists.", "Duplicate Error",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Book book = new Book(title, author, category, price);
			bookDAO = new BookDAO();
			bookDAO.addBook(book);

			this.resetForm();
			this.loadTableData(null);

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(appWindow, "An unexpected error occurred.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// update book
	public void updateBook(int id) {
		try {

			if (!this.validateInputs())
				return;

			String title = appWindow.getTitleField().getText();
			String author = appWindow.getAuthorField().getText();
			String category = appWindow.getCategoryComboBox().getSelectedItem().toString();
			double price = Double.parseDouble(appWindow.getPriceField().getText().trim());

			if (!(appWindow.getOldTitle().equals(title))) {
				if (bookDAO.bookExists(title)) {
					JOptionPane.showMessageDialog(appWindow, "A book with this title already exists.",
							"Duplicate Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}

			Book book = new Book(id, title, author, category, price);
			bookDAO = new BookDAO();
			bookDAO.updateBook(book);

			this.resetForm();
			this.loadTableData(null);

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(appWindow, "An unexpected error occurred.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// delete book
	public void deleteBook(int id) {
		try {

			bookDAO = new BookDAO();
			bookDAO.deleteBook(id);

			this.resetForm();
			this.loadTableData(null);

		} catch (SQLException e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(appWindow, "An unexpected error occurred.", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
	}

	// search
	public void searchBooks(String query) {
		try {

			bookDAO = new BookDAO();
			List<Book> books = bookDAO.searchBooks(query);

			if (books == null || books.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No books found matching your search criteria.", "Search Result",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			this.loadTableData(books);

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(appWindow, "Error during search: " + e.getMessage(), "Search Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// validate inputs
	public boolean validateInputs() {

		if (appWindow.getTitleField().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Title field cannot be empty.", "Input Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (appWindow.getAuthorField().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Author field cannot be empty.", "Input Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (appWindow.getCategoryComboBox().getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Please choose a category.", "Input Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (appWindow.getPriceField().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(appWindow, "Price field cannot be empty.", "Input Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		try {
			Double.parseDouble(appWindow.getPriceField().getText().trim());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(appWindow, "Please enter a valid price.", "Input Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (Double.parseDouble(appWindow.getPriceField().getText().trim()) <= 0) {
			JOptionPane.showMessageDialog(appWindow, "Price must be greater than 0.", "Input Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	// reset form
	public void resetForm() {
		appWindow.getTitleField().setText("");
		appWindow.getAuthorField().setText("");
		appWindow.getCategoryComboBox().setSelectedIndex(0);
		appWindow.getPriceField().setText("");
		appWindow.getAddButton().setVisible(true);
		appWindow.getUpdateButton().setVisible(false);
		appWindow.getTable().clearSelection();
		appWindow.setOldTitle("");
		this.disableTableActionButtons();
	}

	// load table data
	public void loadTableData(List<Book> bookList) throws SQLException {
		bookDAO = new BookDAO();
		List<Book> books;

		if (bookList == null || bookList.isEmpty()) {
			books = bookDAO.getAllBooks();
		} else {
			books = bookList;
		}

		// Define the table data and columns
		Object[][] data = new Object[books.size()][COLUMNS.length];
		for (int i = 0; i < books.size(); i++) {
			Book book = books.get(i);
			data[i] = new Object[] { book.getId(), book.getTitle(), book.getAuthor(), book.getCategory(),
					book.getPrice() };
		}

		// Set the table model
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(data, COLUMNS) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		appWindow.getTable().setModel(model);
	}

	// enable table action buttons
	public void enableTableActionButtons() {
		appWindow.getEditButton().setEnabled(true);
		appWindow.getDeleteButton().setEnabled(true);
	}

	// disable table action buttons
	public void disableTableActionButtons() {
		appWindow.getEditButton().setEnabled(false);
		appWindow.getDeleteButton().setEnabled(false);
	}
}
