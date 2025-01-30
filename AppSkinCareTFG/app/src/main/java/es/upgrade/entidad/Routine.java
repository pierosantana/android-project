package es.upgrade.entidad;

import java.util.List;

public class Routine {
    private static Routine instance;  // Instancia única de la clase

    private Schedule schedule;
    private RoutineType routineType;
    private Double budget;
    private List<Product> productList;
    private SkinType skinType;

    public SkinType getSkinType() {
        return skinType;
    }

    public void setSkinType(SkinType skinType) {
        this.skinType = skinType;
    }

    // Constructor privado
    private Routine() {
    }

    // Método público para acceder a la instancia
    public static Routine getInstance() {
        if (instance == null) {
            instance = new Routine();
        }
        return instance;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public RoutineType getRoutineType() {
        return routineType;
    }

    public void setRoutineType(RoutineType routineType) {
        this.routineType = routineType;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
