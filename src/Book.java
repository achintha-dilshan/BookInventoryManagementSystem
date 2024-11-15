
public class Book {

	private int id;
	private String title;
	private String author;
	private String category;
	private double price;

	public Book(String title, String author, String category, double price) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.price = price;
	}

	// getters
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	// setters
	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
