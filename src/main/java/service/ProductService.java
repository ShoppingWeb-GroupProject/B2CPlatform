package service;

import dao.ProductDAO;
import model.Product;

public class ProductService {

    private ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product product) {
        productDAO.insert(product);
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }
}
