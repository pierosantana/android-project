package es.upgrade.entidad;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static User instance; // Instancia única
    private String name;
    private String email;
    private String password;
    private SkinType skinType;
    private List<Routine> routineList;

    // Constructor privado para evitar instanciación externa
    private User() {
        routineList = new ArrayList<Routine>();
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

    public SkinType getSkynType() {
        return skinType;
    }

    public void setSkynType(SkinType skynType) {
        this.skinType = skynType;
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
}
