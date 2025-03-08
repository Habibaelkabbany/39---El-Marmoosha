package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {
    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/products.json";
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }

    public ProductRepository() {

    }
    public Product addProduct(Product product){
        save(product);
        return product;
    }
    public ArrayList<Product> getProducts(){
        return findAll();
    }
    public Product getProductById(UUID productId){
        ArrayList<Product> products = getProducts();
        for(Product product : products){
            if(product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
    public Product updateProduct(UUID productId, String newName, double newPrice){
        ArrayList<Product> products = getProducts();
        for(Product product : products){
            if(product.getId().equals(productId)) {
                product.setName(newName);
                product.setPrice(newPrice);
                overrideData(products);
                return product;
            }
        }
        return null;
    }

        public void applyDiscount(double discount, ArrayList<UUID> productIds) {
            ArrayList<Product> products = getProducts();
            for (Product product : products) {
                if (productIds.contains(product.getId())) {
                    double newPrice = product.getPrice() * (discount / 100.0);
                    product.setPrice(newPrice);
                }
            }
            overrideData(products);
        }
    public void deleteProduct(UUID productId){
        ArrayList<Product> products = getProducts();
        for(Product product : products){
            if(product.getId().equals(productId)) {
                products.remove(product);
                overrideData(products);
                return;
            }
        }
    }




}
