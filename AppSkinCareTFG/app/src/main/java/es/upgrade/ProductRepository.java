package es.upgrade;

import java.util.ArrayList;
import java.util.List;

import es.upgrade.entidad.CategoryProduct;
import es.upgrade.entidad.Product;
import es.upgrade.entidad.SkinType;

// Esto seria como nuestra BBDD de productos
public class ProductRepository {
    public static List<Product> getLimpiezaProducts() {
        List<Product> limpieza = new ArrayList<>();

        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel seca", 1, "The Ordinary Limpieador Hidratante de Escualano", 10.90, SkinType.DRY));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel seca", 2, "Revuele limpiador facil con ceramidas", 2.98, SkinType.DRY));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel seca", 3, "CeraVe Limpiador Hidratante", 8.50, SkinType.DRY));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel seca", 4, "La Roche-Posay Cicaplast B5 Gel Lavante", 7.99, SkinType.DRY));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel seca", 5, "Garnier Pure Active Gel Limpiadior Hidratante", 5.99, SkinType.DRY));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel mixta", 6, " Ziaja Manuka ", 3.99, SkinType.COMBINATION));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel mixta", 7, " Ziaja Pepino y Menta", 3.99, SkinType.COMBINATION));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel mixta", 8, "Ziaja Jeju", 3.49, SkinType.COMBINATION));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel mixta", 9, "Ziaja Anti-Imperfeccciones", 4.49, SkinType.COMBINATION));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel normal", 10, "Clarins Espuma Limpiadora piel nueva", 33.50, SkinType.NORMAL));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel normal", 11, "Deliplus Facial Clean", 2.50, SkinType.NORMAL));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel normal", 12, "SKIN 1004", 4.40, SkinType.NORMAL));
        limpieza.add(new Product(CategoryProduct.CLEANER, "Limpiador piel normal", 13, "CeraVe Gel Limpiador Hidratante", 7.95, SkinType.NORMAL));



        return limpieza;
    }

    public static List<Product> getHidratarProducts() {
        List<Product> hidratar = new ArrayList<>();

        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 14, "Weleda Skin food light ", 7.96, SkinType.DRY));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 15, "Ziaja Leche de cabra", 4.10, SkinType.DRY));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 16, "Ziaja Caléndula", 2.80, SkinType.DRY));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 17, "Clarins Multi-Intensiva", 130.0, SkinType.DRY));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 18, "Laneige Crema con Ácido Hialurónico", 5.15, SkinType.NORMAL));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 19, "CeraVe Crema Hidrante Rostro", 9.18, SkinType.NORMAL));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 20, "Bioma crema hidratante", 18.00, SkinType.NORMAL));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 21, "Eucerin Aquaporin Active ", 17.95, SkinType.NORMAL));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 22, "Biotherm Crema Hidratante", 57.00, SkinType.COMBINATION));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 23, "Nivea Crema de dia refrescante", 5.50, SkinType.COMBINATION));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 24, "Ziaja MANUKA crema facial de noche", 4.99, SkinType.COMBINATION));
        hidratar.add(new Product(CategoryProduct.MOISTURIZER, "crema hidratante", 25, "Ziaja Pepino crema facila", 2.89, SkinType.COMBINATION));


        return hidratar;
    }

    public static List<Product> getTonificarProducts() {
        List<Product> tonificar = new ArrayList<>();

        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 26, "Beauty Drops", 3.99, SkinType.DRY));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 27, "Nivea tonico suave", 4.99, SkinType.DRY));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 28, "Clinique", 18.09, SkinType.DRY));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 29, "The Ordinary tonico exfoliante", 16.80, SkinType.DRY));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 30, " Ziaja Acai Berra", 3.18, SkinType.NORMAL));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 31, "Ziaja Vitamina C.B3", 3.98, SkinType.NORMAL));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 32, "Byphasse", 1.99, SkinType.NORMAL));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 33, "Loreal Paris revitalift", 11.25, SkinType.NORMAL));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 34, "Ziaja Manuka Tree tonico", 3.49, SkinType.COMBINATION));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 35, "Ziaja Cucumber Tónico", 2.90, SkinType.COMBINATION));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 36, "Clarins Loción tonificante  purificante", 30.25, SkinType.COMBINATION));
        tonificar.add(new Product(CategoryProduct.TONIC, "tonico", 37, "Revuele No problem tonico", 1.99, SkinType.COMBINATION));


        return tonificar;
    }

    public static List<Product> getTratamientoProducts() {
        List<Product> tratamiento = new ArrayList<>();

        // Para todas las pieles en vez de un tratamiento en especifico meter niacinamida y acido hialuronico para pieles secas que es lo que mas hidrata.
        // Para la piel mixta meter acido salicilico y niacinamida
        // Para la piel normal niacinamida
        // Gama baja The Ordinary
        // Gama alta La roche posai
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "Puntos negros", 38, "Niacinamida The Ordinary", 6.00, SkinType.DRY));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "Puntos negros", 39, "Niacinamida de Roche Posai", 33.1, SkinType.DRY));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "crema hidratante", 40, "Ácido hialuronico The Ordinary", 7.0, SkinType.DRY));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "aceite facial", 41, "Ácido hialuronico Roche Posai", 39.5, SkinType.DRY));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "Puntos negros", 42, "Niacinamida The Ordinary", 6.00, SkinType.NORMAL));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "Puntos negros", 43, "Niacinamida de Roche Posai", 33.1, SkinType.NORMAL));

        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "serum", 44, "Niacinamida The Ordinary", 6.00, SkinType.COMBINATION));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "serum", 45, "Niacinamida de Roche Posai", 33.1, SkinType.COMBINATION));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "serum", 46, "Ácido salicilico The Ordinary", 7.50, SkinType.COMBINATION));
        tratamiento.add(new Product(CategoryProduct.CREAM_TREATMENT, "serum", 47, "Ácido salicilico Roche Posai", 27.95, SkinType.COMBINATION));

        return tratamiento;
    }

    public static List<Product> getProtectorSolarProducts() {
        List<Product> protectorSolar = new ArrayList<>();

        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 48, "Bioderma Photderm Crema FPS 50+", 20.0, SkinType.DRY));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 49, "Cetphill Sun FPS 50+", 25.0, SkinType.DRY));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 50, "Avene Bloqueador FPS 50+", 30.0, SkinType.DRY));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 51, "Isdin Fotoultra Isdin 50+", 40.0, SkinType.DRY));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 52, "La Roche-Posay Anthelios Ultra-Light Mineral Sunscreen SPF 50", 35.0, SkinType.NORMAL));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 53, "EltaMD UV Clear Broad-Spectrum SPF 46", 45.00, SkinType.NORMAL));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 54, "Neutrogena Ultra Sheer Dry-Touch Sunscreen SPF 100", 10.0, SkinType.NORMAL));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 55, "Vichy Idéal Soleil SPF 50", 20.0, SkinType.NORMAL));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 56, "Heliocare 360 Gel Oil-Free SPF 50", 25.0, SkinType.COMBINATION));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 57, "SVR Sun Secure Fluid SPF 50+", 20.0, SkinType.COMBINATION));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 58, "Vichy Idéal Soleil Tinted Mattifying Face Fluid SPF 50", 25.0, SkinType.COMBINATION));
        protectorSolar.add(new Product(CategoryProduct.SUNSCREEN, "protector solar", 59, "Avene Cleanance Sunscreen SPF 50+", 20.0, SkinType.COMBINATION));

        return protectorSolar;
    }

}
