package com.harsh.shah.threads.clone.interfaces.profile;

import androidx.annotation.NonNull;

import com.google.api.client.util.Value;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harsh.shah.threads.clone.BaseActivity;
import com.harsh.shah.threads.clone.Constants;
import com.harsh.shah.threads.clone.model.UserModel;

public interface onProfileUpdate {

    DatabaseReference mUsersDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.USERS_DB_REF);
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if ((user != null)) {
                    assert firebaseUser != null;
                    firebaseUser.getUid();
                    if (user.getUid().equals(firebaseUser.getUid())) {
                        onProfileUpdated(user);
                        break;
                    }
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    default void setup(){
        mUsersDatabaseReference.addValueEventListener(valueEventListener);
    }

    static void onProfileUpdated(UserModel user){
        BaseActivity.mUser = user;
    }

    default void onProfileUpdatePause(){
        mUsersDatabaseReference.removeEventListener(valueEventListener);
    }

    default void onProfileUpdateResume(){
        mUsersDatabaseReference.addValueEventListener(valueEventListener);
    }
}
