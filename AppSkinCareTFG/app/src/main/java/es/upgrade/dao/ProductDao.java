package es.upgrade.dao;

import java.util.ArrayList;
import java.util.List;

import es.upgrade.entidad.Product;

public class ProductDao {
    // Lista global de productos
    private List<Product> productos;

    // Instancia única (Singleton)
    private static ProductDao instance;

    // Constructor privado para evitar que se creen instancias adicionales
    private ProductDao() {
        productos = new ArrayList<>();
    }

    // Método para obtener la instancia del Singleton
    public static ProductDao getInstance() {
        if (instance == null) {
            instance = new ProductDao();
        }
        return instance;
    }

    // Método para obtener los productos
    public List<Product> getProductos() {
        return productos;
    }

    // Método para agregar productos a la lista global
    public void setProductos(List<Product> nuevosProductos) {
        this.productos = nuevosProductos;
    }

    // Método para limpiar la lista de productos (si es necesario)
    public void clearProductos() {
        this.productos.clear();
    }
}
