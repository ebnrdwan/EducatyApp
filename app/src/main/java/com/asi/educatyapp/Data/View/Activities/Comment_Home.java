package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.Models.CommentModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.asi.educatyapp.Data.View.Fragments.HomeF.keyTag;

public class Comment_Home extends AppCompatActivity {


    RecyclerView myrecylcer;
    Toast mToast;

    EditText commentTv;
    Button addComment;
    String comment;
    String time;
    String postKey;
    String commentKey;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference CommentReference;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment__home);
        commentTv = (EditText) findViewById(R.id.commenttv);

        Intent intent = getIntent();
        postKey = intent.getStringExtra(keyTag);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        CommentReference = firebaseDatabase.getReference().child(FirebaseUtil.postsObject).child(postKey).child(FirebaseUtil.CommentsPostObject);
        DateFormat df = new SimpleDateFormat(getString(R.string.commentDate));
        time = df.format(Calendar.getInstance().getTime());
        commentKey = CommentReference.push().getKey();

        addComment = (Button) findViewById(R.id.addComment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = commentTv.getText().toString();
                CommentModel model = new CommentModel(commentKey, postKey, user.getDisplayName(), user.getPhotoUrl().toString(), comment, time);
                FirebaseUtil.addingObjectFirebase(user, Comment_Home.this, CommentReference, model, true, null, commentKey);
                commentTv.getText().clear();
            }
        });


       adapter = new FirebaseRecyclerAdapter<CommentModel,CommentHolder>(CommentModel.class,R.layout.comment_layout,CommentHolder.class,CommentReference) {

            @Override
            protected void populateViewHolder(CommentHolder viewHolder, CommentModel model, int position) {
                viewHolder.name.setText(model.getName());
                viewHolder.time.setText(model.getTime());
                viewHolder.postContent.setText(model.getComment());
                Glide.with(Comment_Home.this).load(model.getImage()).error(R.drawable.student).into(viewHolder.profile);

            }
        };

        myrecylcer = (RecyclerView) findViewById(R.id.Recycler_activity_comment__home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Comment_Home.this, LinearLayoutManager.VERTICAL, false);
        myrecylcer.setLayoutManager(layoutManager);
        myrecylcer.setAdapter(adapter);
    }

    // must be static, i don't know why !
    public static class CommentHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView time;

        TextView postContent;

        public CommentHolder(View itemView) {
            super(itemView);
            profile = (ImageView) itemView.findViewById(R.id.imagecomment);
            name = (TextView) itemView.findViewById(R.id.nameComment);
            time = (TextView) itemView.findViewById(R.id.timeComment);
            postContent = (TextView) itemView.findViewById(R.id.comment);

        }
    }
}
