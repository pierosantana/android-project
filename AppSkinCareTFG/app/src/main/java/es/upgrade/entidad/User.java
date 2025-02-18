package es.upgrade.entidad;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static User instance; // Instancia única
    private String name;
    private String email;
    private String password;
    @PropertyName("skinType")
    private SkinType skinType;
    private List<Routine> routineList;


    // Constructor privado para evitar instanciación externa
    private User() {
        routineList = new ArrayList<>();
    }

    // Método para obtener la única instancia (Singleton)
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PropertyName("skinType")
    public SkinType getSkinType() {

        return skinType;
    }
    @PropertyName("skinType")
    public void setSkinType(SkinType skinType) {
        this.skinType = skinType;
    }

    public List<Routine> getRoutineList() {
        return routineList;
    }

    public void setRoutineList(List<Routine> routineList) {
        this.routineList = routineList;
    }

    public void addRoutine(Routine routine){
        this.routineList.add(routine);
    }

    /**
     * The toString method in the User class returns a string representation of the User object's
     * attributes.
     * 
     * @return A string representation of a User object is being returned. It includes the user's name,
     * email, password, skin type, and a list of routines.
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", skinType=" + skinType +
                ", routineList=" + routineList +
                '}';
    }
    
    /**
     * The `resetInstance` function creates a new instance of the `User` class.
     */
    public static void resetInstance() {
        instance = new User();
    }
}
