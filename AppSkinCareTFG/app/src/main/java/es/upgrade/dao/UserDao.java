package es.upgrade.dao;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.upgrade.entidad.Product;
import es.upgrade.entidad.Routine;
import es.upgrade.entidad.User;

public class UserDao {
    private static UserDao instance = null;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final DatabaseReference userReference;

    private UserDao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users");
    }

    public void saveUser(User user, OnCompleteListener<Void> onCompleteListener) {
        String userId = firebaseAuth.getUid();
        if (user.getRoutineList() == null) {
            user.setRoutineList(new ArrayList<>());
        }
        userReference.child(userId).setValue(user).addOnCompleteListener(onCompleteListener);
    }

    public boolean verifyFirebaseUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser != null;
    }

    public void recoveryUser(OnUserRecoveredListener listener) {
        User user = User.getInstance();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        String userId = firebaseAuth.getUid();

        userRef.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User userInfo = task.getResult().getValue(User.class);
                if (userInfo != null) {
                    user.setName(userInfo.getName());
                    user.setEmail(userInfo.getEmail());
                    user.setPassword(userInfo.getPassword());
                    user.setSkinType(userInfo.getSkinType());
                    user.setImageUri(userInfo.getImageUri());

                    List<Routine> routines = userInfo.getRoutineList();
                    user.setRoutineList(routines != null ? routines : new ArrayList<>());

                    if (listener != null) {
                        listener.onUserRecovered(user);
                    }
                } else {
                    if (listener != null) {
                        listener.onUserRecovered(null);
                    }
                }
            } else {
                if (listener != null) {
                    listener.onUserRecovered(null);
                }
            }
        });
    }

    public void updateUser() {
        String userId = firebaseAuth.getUid();
        if (userId == null) return;

        User user = User.getInstance();
        DatabaseReference userRef = userReference.child(userId);

        List<Routine> routines = user.getRoutineList();
        if (routines == null) {
            routines = new ArrayList<>();
        }

        List<Map<String, Object>> routineList = new ArrayList<>();
        for (Routine r : routines) {
            if (r != null) {
                Map<String, Object> routineMap = new HashMap<>();
                routineMap.put("schedule", r.getSchedule() != null ? r.getSchedule().toString() : "null");
                routineMap.put("routineType", r.getRoutineType() != null ? r.getRoutineType().toString() : "null");
                routineMap.put("budget", r.getBudget() != null ? r.getBudget().toString() : "null");
                routineMap.put("skinType", r.getSkinType() != null ? r.getSkinType().toString() : "null");
                routineMap.put("budgetProducts", r.getBudgetProducts() != null ? r.getBudgetProducts() : new ArrayList<>());

                List<Map<String, Object>> productList = new ArrayList<>();
                for (Product p : r.getProductList()) {
                    if (p != null) {
                        Map<String, Object> productMap = new HashMap<>();
                        productMap.put("categoryProduct", p.getCategoryProduct() != null ? p.getCategoryProduct().toString() : "DEFAULT_CATEGORY");
                        productMap.put("id", p.getId());
                        productMap.put("name", p.getName());
                        productMap.put("price", p.getPrice());
                        productMap.put("url", p.getUrl());
                        productMap.put("brand", p.getBrand());
                        productMap.put("skinType", p.getSkinType() != null ? p.getSkinType().toString() : "DEFAULT");
                        productList.add(productMap);
                    }
                }
                routineMap.put("productList", productList);
                routineList.add(routineMap);
            }
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", user.getEmail());
        updates.put("name", user.getName());
        updates.put("password", user.getPassword());
        updates.put("skinType", user.getSkinType());
        updates.put("routineList", routineList);
        updates.put("imageUri", user.getImageUri());

        userRef.updateChildren(updates);
    }

    public void deleteUserData(String userId, OnCompleteListener<Void> onCompleteListener) {
        userReference.child(userId).removeValue().addOnCompleteListener(onCompleteListener);
    }

    public interface OnUserRecoveredListener {
        void onUserRecovered(User user);
    }
}