package model;

public class Product {
	private int id;
    private int sellerId;
    private String name;
    private String description;
    private int categoryId;
    private double price;
    private int stock;
    
    public Product() {}
    
	public Product(int id, int sellerId, String name, String description, int categoryId, double price, int stock) {
		this.id = id;
		this.sellerId = sellerId;
		this.name = name;
		this.description = description;
		this.categoryId = categoryId;
		this.price = price;
		this.stock = stock;
	}

	// Getters
    public int getId() {
        return id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
