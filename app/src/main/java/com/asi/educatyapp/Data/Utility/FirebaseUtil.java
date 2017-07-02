package com.asi.educatyapp.Data.Utility;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Abdulrhman on 19/06/2017.
 */

public class FirebaseUtil {
    private static final long SPLASH_DISPLAY_LENGTH =1000 ;
    public boolean childStatus = false;
    static HashMap<String, String> groupmap = new HashMap<>();
    static HashMap<String, String> studentmap = new HashMap<>();
    static HashMap<String, String> teachermap = new HashMap<>();
    public static String studentObject ="students";
    public static String groupsObject ="Groups";
    public static String teacherObject ="teachers";
    public static String messageObject ="messages";



    public static HashMap<String, String> getGroupmap() {
        return groupmap;
    }

    // get user status
    public static boolean isUserLogined(String url) {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String id = user.getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(url);
        boolean islogined;

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                boolean childStatus;
                if (dataSnapshot.hasChild(id)) {
                    childStatus = true;


                } else if (!dataSnapshot.hasChild(id)) {
                    childStatus = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return true;

    }

    //get user

    public static Integer id;

    public static String handleIds() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("ids");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                id = dataSnapshot.getValue(Integer.class);
                id++;

                databaseReference.setValue(id);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("IDID", "can't get your id ");
            }
        });
        return id.toString();
    }

    public static void SetGroupsMap(String UsedName, String username) {
        if (groupmap.containsValue(username)) {
         
            //do nothing for now
        } else {
            groupmap.put(UsedName, username);

        }
    }

    public static String getGroupmap(String usedname) {
        return groupmap.get(usedname);
    }
    public static void SetStudentsMap(String UsedName, String username) {
        if (studentmap.containsValue(username)) {
            //do nothing for now
        } else {
           studentmap.put(UsedName, username);

        }
    }

    public static String getStudentMap(String usedname) {
        return studentmap.get(usedname);
    }

    public static void SetTeachersMap(String UsedName, String username) {
        if (teachermap.containsValue(username)) {
            //do nothing for now
        } else {
            teachermap.put(UsedName, username);

        }
    }

    public static String getTeachermap(String usedname) {
        return teachermap.get(usedname);
    }

}
