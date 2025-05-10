package es.upgrade.entidad;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static User instance;
    private String name;
    private String email;
    private String password;
    @PropertyName("skinType")
    private SkinType skinType;
    private List<Routine> routineList;
    private String imageUri;

    private User() {
        routineList = new ArrayList<>();
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = new User();
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

    public void addRoutine(Routine routine) {
        this.routineList.add(routine);
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", skinType=" + skinType +
                ", imageUri=" + imageUri +
                ", routineList=" + routineList +
                '}';
    }
}