package com.asi.educatyapp.Data.View.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.Data.Utility.Constants;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.Utility.SharedPreferencesUtils;
import com.asi.educatyapp.Data.View.Adapters.AttendanceAdapter;
import com.asi.educatyapp.Data.View.Adapters.MyPagerAdapter;
import com.asi.educatyapp.Data.View.Fragments.GroupsF;
import com.asi.educatyapp.Data.View.Fragments.HomeF;
import com.asi.educatyapp.Data.View.Fragments.ProgressF;
import com.asi.educatyapp.Data.View.Fragments.Skills;
import com.asi.educatyapp.Data.View.Fragments.TheGroupStudents;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TheGroup extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    MyPagerAdapter pagerAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference StudentDatabaseReference;
    DatabaseReference GroupDatabaseReference;
    List<StudentModel> list = new ArrayList<>();

    FirebaseUser user;
    public static String groupName;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_group);
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        firebaseDatabase = firebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        StudentDatabaseReference = firebaseDatabase.getReference().child(FirebaseUtil.studentObject);
        Intent intent = getIntent();
        groupName = intent.getStringExtra(GroupsF.GroupTag);
        GroupDatabaseReference = firebaseDatabase.getReference().child(FirebaseUtil.groupsObject).child(groupName).child(FirebaseUtil.studentObject);


        //// TODO: [in the previous line] --->  i can't set method of DisplayAsUpEnabled()  to make my activity navigate back to the main, please tell me the reason
        tabLayout = (TabLayout) findViewById(R.id.mytablayout);
        viewPager = (ViewPager) findViewById(R.id.myviewpager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new HomeF(), "Home ");
        pagerAdapter.addFragments(new TheGroupStudents(), "Students");
        pagerAdapter.addFragments(new Skills(), "Quizzes");
        pagerAdapter.addFragments(new ProgressF(), "Progress");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_attendace_menu:

                if (SharedPreferencesUtils.getTypeOfCurrentUser(TheGroup.this).equals(Constants.T_TEACHER)) {
                    AttendanceAdapter attendanceAdapter = new AttendanceAdapter(TheGroup.this);
                    final FirebaseListAdapter mAdapter = new FirebaseListAdapter<StudentModel>(this, StudentModel.class, R.layout.simple_grid_item, StudentDatabaseReference) {
                        @Override
                        protected void populateView(View view, StudentModel studentModel, int position) {
                            ((TextView) view.findViewById(R.id.text_view)).setText(studentModel.getName());
                            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
                            list.add(studentModel);
                            Toast.makeText(TheGroup.this,"list is"+String.valueOf(list.size()),Toast.LENGTH_SHORT).show();
                            Glide.with(TheGroup.this).load(Uri.parse(studentModel.getImage())).error(R.drawable.student).into(imageView);

                        }
                    };

                    Holder holder = new ListHolder();
                    showCompleteDialog(holder, Gravity.BOTTOM, mAdapter);
                } else {
                    Toast.makeText(TheGroup.this, "not allowed for students ", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.add_student_menu:
                Toast.makeText(TheGroup.this, "clicked add student menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCompleteDialog(Holder holder, final int gravity, BaseAdapter adapter) {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setHeader(R.layout.header)
                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setInAnimation(R.anim.slide_in_top)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setAdapter(adapter).setInAnimation(R.anim.abc_fade_in)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                    }
                })
                .setOnItemClickListener(new OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");

                        StudentModel model = (StudentModel) item;
                        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
                        map.put(model.getIdusername(), true);
                        FirebaseUtil.addingObjectFirebase(user, TheGroup.this, GroupDatabaseReference, model, false, model.getIdusername(), null);
                        StudentDatabaseReference.child(model.getKey()).child(FirebaseUtil.groupsObject).child(groupName).setValue(true);


                        Toast.makeText(TheGroup.this, "clicked " + model.getIdusername(), Toast.LENGTH_SHORT).show();
                        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
                        int radius = (int) Math.hypot(view.getWidth() / 2, view.getHeight() / 2);
                        Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight(), 0, radius);


                        imageView.setBackgroundColor(getResources().getColor(R.color.backmen));
                        animator.start();

                    }
                })
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {

                    }
                })
                .setExpanded(true)
//        .setContentWidth(800)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogPlus dialog) {

                    }
                })
                .setOverlayBackgroundResource(android.R.color.transparent)
                .create();
        dialog.show();
    }

}
