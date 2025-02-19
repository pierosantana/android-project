package es.upgrade.dao;

import java.util.ArrayList;
import java.util.List;

import es.upgrade.entidad.Product;

/**
 * The ProductDao class is a Singleton that manages a global list of products in a Java application.
 */
public class ProductDao {
    
    // The line `private List<Product> productos;` in the ProductDao class is declaring a private
    // instance variable named `productos` of type List<Product>. This variable is used to store a list
    // of Product objects within the ProductDao class.
    private List<Product> productos;

    // The `private static ProductDao instance;` line in the ProductDao class declares a private static
    private static ProductDao instance;

    // The `private ProductDao()` constructor is a private constructor of the ProductDao class in Java. It
    private ProductDao() {
        productos = new ArrayList<>();
    }

    
    /**
     * The getInstance method returns an instance of the ProductDao class, creating a new instance if
     * one does not already exist.
     * 
     * @return An instance of the ProductDao class is being returned.
     */
    public static ProductDao getInstance() {
        if (instance == null) {
            instance = new ProductDao();
        }
        return instance;
    }

   
    /**
     * The function returns a list of products, initializing it if it's null.
     * 
     * @return A List of Product objects is being returned.
     */
    public List<Product> getProductos() {
        if (productos == null) {
            productos = new ArrayList<>();
        }
        return productos;
    }

    
   /**
    * The function sets a list of new products to replace the existing list of products.
    * 
    * @param nuevosProductos The parameter `nuevosProductos` in the `setProductos` method is a List of
    * Product objects. This method is used to set a new list of products for the current object.
    */
    public void setProductos(List<Product> nuevosProductos) {
        this.productos = nuevosProductos;
    }

    
   /**
    * The clearProductos function clears all elements from the productos list.
    */
    public void clearProductos() {
        this.productos.clear();
    }
}
