package es.upgrade.entidad;

import android.util.Log;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Routine implements Serializable {


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
    private boolean isNightRoutine; // Indicador para rutina de noche

    // Constructor privado
    private Routine() {
        productList = new ArrayList<>();
    }

    // Método público para acceder a la instancia
    public static Routine getInstance() {
        if (instance == null) {
            Log.e("Routine", "❌ Se está creando una nueva instancia de Routine (NO DEBERÍA PASAR)");

            instance = new Routine();
        }else{
            Log.d("Routine", "✅ Se está reutilizando la misma instancia de Routine");

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
    // Este metodo lo que hace es añadir a la lista de productos un producto
    public void addProduct(Product product){
        if(!productList.contains(product)){
            productList.add(product);
        }
    }
    // Este método lo que hace es limpiar la lista de productos
    public void clearProduct(){
        productList.clear();
    }
    public void setNightRoutine(boolean isNightRoutine) {
        this.isNightRoutine = isNightRoutine;
    }

    public boolean isNightRoutine() {
        return isNightRoutine;
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
    //Método para obtener la cantidad de pasos de la rutina
    public int getStepCount(){
        if(routineType == RoutineType.BASIC){
            return 2;
        } else if (routineType == RoutineType.COMPLETE) {
            return isNightRoutine ? 4 : 5;
        }
        return 0; //Si no se ha definido
    }
}
