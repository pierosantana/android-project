package es.upgrade;

import java.util.ArrayList;
import java.util.List;

import es.upgrade.entidad.Category;
import es.upgrade.entidad.Product;

// Esto seria como nuestra BBDD de productos
public class ProductRepository {
    public static List<Product> getLimpiezaProducts() {
        List<Product> limpieza = new ArrayList<>();
        limpieza.add(new Product(Category.CLEANER, "gel de limpieza", 1, "CeraVe", 10.5));
        limpieza.add(new Product(Category.CLEANER, "espuma facial", 2, "espuma facial", 10.5));
        limpieza.add(new Product(Category.CLEANER, "jabon dermatologico", 3, "jabon", 10.5));
        limpieza.add(new Product(Category.CLEANER, "agua micelar", 4, "micelar", 10.5));
        return limpieza;
    }

    public static List<Product> getHidratarProducts() {
        List<Product> hidratar = new ArrayList<>();
        hidratar.add(new Product(Category.MOISTURIZER, "crema hidratante", 5, "crema", 10.5));
        hidratar.add(new Product(Category.MOISTURIZER, "grl hidratante", 6, "gel", 10.5));
        hidratar.add(new Product(Category.MOISTURIZER, "serum", 7, "serum", 10.5));
        hidratar.add(new Product(Category.MOISTURIZER, "aceite facial", 8, "aceite", 10.5));
        return hidratar;
    }

    public static List<Product> getTonificarProducts() {
        List<Product> tonificar = new ArrayList<>();
        tonificar.add(new Product(Category.TONIC, "crema hidratante", 5, "tonico", 10.5));
        tonificar.add(new Product(Category.TONIC, "grl hidratante", 6, "tonico blando", 10.5));
        tonificar.add(new Product(Category.TONIC, "serum", 7, "tonico duro", 10.5));
        tonificar.add(new Product(Category.TONIC, "aceite facial", 8, "facial", 10.5));
        return tonificar;
    }

    public static List<Product> getTratamientoProducts() {
        List<Product> tratamiento = new ArrayList<>();
        tratamiento.add(new Product(Category.CREAM_TREATMENT, "crema hidratante", 5, "puntos negros", 10.5));
        tratamiento.add(new Product(Category.CREAM_TREATMENT, "grl hidratante", 6, "acne", 10.5));
        tratamiento.add(new Product(Category.CREAM_TREATMENT, "serum", 7, "cosas", 10.5));
        tratamiento.add(new Product(Category.CREAM_TREATMENT, "aceite facial", 8, "croissant", 10.5));
        return tratamiento;
    }

    public static List<Product> getProtectorSolarProducts() {
        List<Product> protectorSolar = new ArrayList<>();
        protectorSolar.add(new Product(Category.SUNSCREEN, "crema hidratante", 5, "nivea", 10.5));
        protectorSolar.add(new Product(Category.SUNSCREEN, "grl hidratante", 6, "loreal", 10.5));
        protectorSolar.add(new Product(Category.SUNSCREEN, "serum", 7, "DR g", 10.5));
        protectorSolar.add(new Product(Category.SUNSCREEN, "aceite facial", 8, "vichy", 10.5));
        return protectorSolar;
    }
}
