package es.upgrade.entidad;

public class Product {
    //Esto es para poder identificar cada producto
    private int id;
    private String name;
    private Double price;
    private String brand;
    private CategoryProduct categoryProduct;
    private SkinType skinType;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





    public SkinType getSkintype() {
        return skinType;
    }

    public void setSkintype(SkinType skintype) {
        this.skinType = skintype;
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

    public void setCategoryProduct(CategoryProduct categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product(CategoryProduct categoryProduct, String brand, int id, String name, Double price, SkinType skinType) {
        this.categoryProduct = categoryProduct;
        this.brand = brand;
        this.id = id;
        this.name = name;
        this.price = price;
        this.skinType = skinType;
    }

    public SkinType getSkinType() {
        return skinType;
    }

    public void setSkinType(SkinType skinType) {
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
}
