package es.upgrade.entidad;

import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

public class Product implements Serializable {
    //Esto es para poder identificar cada producto
    private int id;
    private String name;
    private Double price;
    private CategoryProduct categoryProduct;
    private String brand;
    private SkinType skinType;
    private String url;
    private boolean isSelected;

    public Product() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCategoryProduct(CategoryProduct categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public SkinType getSkinType() {
        return skinType;
    }

    public void setSkinType(SkinType skinType) {
        this.skinType = skinType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return Double.parseDouble(String.format("%.2f", price));
    }

    public void setPrice(Double price) {
        this.price = Double.parseDouble(String.format("%.2f", price));
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public CategoryProduct getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategory(CategoryProduct categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public Product(CategoryProduct categoryProduct, String brand, int id, String name, Double price, SkinType skinType) {
        this.categoryProduct = categoryProduct;
        this.brand = brand;
        this.id = id;
        this.name = name;
        this.price = price;
        this.skinType = skinType;
    }



    @Override
    public String toString() {
        return "Product{" +
                "categoryProduct=" + categoryProduct +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", skinType=" + skinType +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        Log.d("Product", "Comparing this product " + this.getName() + " with " + product.getName());
        return id == product.id;  // Compara los IDs de los productos
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);  // Usa el mismo campo para asegurar que las comparaciones sean correctas
    }
}
