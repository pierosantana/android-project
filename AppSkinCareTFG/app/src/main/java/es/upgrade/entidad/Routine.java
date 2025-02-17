package es.upgrade.entidad;

import android.util.Log;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Routine implements Serializable {

    private Schedule schedule;
    private RoutineType routineType;
    private Budget budget; //
    private Double budgetProducts;
    private List<Product> productList;
    private SkinType skinType;
    private boolean isNightRoutine; // Indicador para rutina de noche

    // Constructor público para crear nuevas instancias
    public Routine() {
        productList = new ArrayList<>();
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

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
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

    // Método para añadir un producto a la lista de productos
    public void addProduct(Product product){
        if(!productList.contains(product)){
            productList.add(product);
        }
    }

    // Método para limpiar la lista de productos
    public void clearProduct(){
        productList.clear();
    }

    public SkinType getSkinType() {
        return skinType;
    }

    public void setSkinType(SkinType skinType) {
        this.skinType = skinType;
    }

    public void setNightRoutine(boolean isNightRoutine) {
        this.isNightRoutine = isNightRoutine;
    }

    public boolean isNightRoutine() {
        return isNightRoutine;
    }

    // Método para obtener la cantidad de pasos de la rutina
    public int getStepCount(){
        if (routineType == RoutineType.BASIC) {
            return 2;
        } else if (routineType == RoutineType.COMPLETE) {
            return isNightRoutine ? 4 : 5;
        }
        return -1; // Si el tipo de rutina no es válido
    }

    @Override
    public String toString() {
        return "Routine{" +
                "schedule=" + schedule +
                ", routineType=" + routineType +
                ", budget=" + budget +
                ", budgetProducts=" + budgetProducts +
                ", productList=" + productList +
                ", skinType=" + skinType +
                '}';
    }
}
