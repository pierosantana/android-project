package es.upgrade.entidad;

import java.util.List;

public class Routine {
    private static Routine instance;  // Instancia única de la clase

    private Schedule schedule;
    private RoutineType routineType;
    private Budget Budget;
    private Double budgetProducts;
    private List<Product> productList;
    private SkinType skinType;

    public Budget getBudget() {
        return Budget;
    }

    public void setBudget(Budget budget) {
        Budget = budget;
    }

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

    public Double getBudgetProducts() {
        return budgetProducts;
    }

    public void setBudgetProducts(Double budget) {
        this.budgetProducts = budget;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }


    @Override
    public String toString() {
        return "Routine{" +
                "schedule=" + schedule +
                ", routineType=" + routineType +
                ", Budget=" + Budget +
                ", budgetProducts=" + budgetProducts +
                ", productList=" + productList +
                ", skinType=" + skinType +
                '}';
    }
}
