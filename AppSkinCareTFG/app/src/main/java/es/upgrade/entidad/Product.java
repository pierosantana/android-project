package es.upgrade.entidad;

import android.util.Log;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    
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
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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



   /**
    * The toString method is overridden to provide a string representation of a Product object's
    * attributes.
    * 
    * @return A string representation of a Product object is being returned. It includes the values of
    * the categoryProduct, id, name, price, brand, and skinType fields of the Product object.
    */
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
   /**
    * The equals method compares two Product objects based on their IDs.
    * 
    * @param obj The `obj` parameter in the `equals` method represents the object to be compared with
    * the current object for equality. In the provided code snippet, the method is overriding the
    * `equals` method of the Object class to compare two `Product` objects based on their `id`
    * attribute. The method
    * @return The `equals` method is being overridden to compare two `Product` objects based on their
    * `id` attribute. It first checks if the two objects are the same reference, then checks if the
    * object is null or not an instance of the `Product` class. If these conditions are not met, it
    * compares the `id` attribute of the two `Product` objects and returns `true` if
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        Log.d("Product", "Comparing this product " + this.getName() + " with " + product.getName());
        return id == product.id;  // Compara los IDs de los productos
    }
    /**
     * The `hashCode` function in Java uses the `id` field to ensure correct comparisons when
     * generating hash codes.
     * 
     * @return The `hashCode()` method is returning the hash code value calculated using the `id`
     * field. This is done to ensure that objects with the same `id` value will have the same hash
     * code, which is important for correct comparisons and usage in hash-based collections like
     * HashMap or HashTable.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);  // Usa el mismo campo para asegurar que las comparaciones sean correctas
    }
}
