package service;

import dao.ProductDAO;
import dao.UserDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

	private static ProductDAO productDAO = new ProductDAO();
	private static UserDAO userDAO = new UserDAO();
	private static List<Product> emptyList = new ArrayList<Product>();
	
	public static List<Product> getAllProducts() {
		try {
			return productDAO.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return emptyList;
		}
	}
	
	public static List<Product> getSellerProducts(String username) {
		try {
			int sellerId = userDAO.findUserIdByUsername(username);
			if (sellerId == -1) {
                return emptyList;
            }
			return productDAO.getBySellerId(sellerId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return emptyList;
		}
	}

	public void addProduct(Product product) {
		productDAO.insert(product);
	}

	public void updateProduct(Product product) {
		productDAO.update(product);
	}
	
    public Product getProductById(int id) {
        return productDAO.getById(id);
    }
    
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

}
