package es.upgrade.entidad;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private SkinType skinType;
    private List<Routine> routineList;

    public User() {}

   

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
}
