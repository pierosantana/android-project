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

    
   /**
    * The function `getStepCount` returns the number of steps based on the routine type and whether it
    * is a night routine.
    * 
    * @return The `getStepCount` method returns the number of steps based on the `routineType` and
    * `isNightRoutine` conditions. If `routineType` is `BASIC`, it returns 2. If `routineType` is
    * `COMPLETE` and `isNightRoutine` is true, it returns 4. If `routineType` is `COMPLETE` and
    * `isNightRoutine`
    */
    public int getStepCount(){
        if (routineType == RoutineType.BASIC) {
            return 2;
        } else if (routineType == RoutineType.COMPLETE) {
            return isNightRoutine ? 4 : 5;
        }
        return -1; 
    }

   /**
    * The toString method is overridden to provide a string representation of a Routine object's
    * attributes.
    * 
    * @return A string representation of a Routine object is being returned. It includes the values of
    * the schedule, routineType, budget, budgetProducts, productList, and skinType properties of the
    * Routine object.
    */
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
