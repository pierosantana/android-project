package es.upgrade.dao;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.upgrade.entidad.User;

public class UserDao {
    private final DatabaseReference userReference;

    public UserDao(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users"); // NODE "Users" in Firebase
    }
    public void saveUser(User user, OnCompleteListener<Void> onCompleteListener){
        String userId = user.getEmail().replace(".","_");// Reemplazar '.' porque Firebase no los permite
        userReference.child(userId).setValue(user).addOnCompleteListener(onCompleteListener);
    }
}
