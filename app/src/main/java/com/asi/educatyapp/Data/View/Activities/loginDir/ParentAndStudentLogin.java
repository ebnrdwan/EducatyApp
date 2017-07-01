package com.asi.educatyapp.Data.View.Activities.loginDir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.asi.educatyapp.Data.Data.Local.SQLiteHandler;
import com.asi.educatyapp.Data.Data.helper.SessionManager;
import com.asi.educatyapp.Data.View.Activities.AppController;
import com.asi.educatyapp.Data.View.Activities.Home;
import com.asi.educatyapp.Data.View.Activities.MainActivity;
import com.asi.educatyapp.Data.View.Utils.Constants;

public class ParentAndStudentLogin extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private String isLogin;
    private String googlePlusId;
    EditText email, pass;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parent_and_student_login);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
//        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());


            googlePlusId = acct.getId();
            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            txtName.setText(personName);
            txtEmail.setText(email);
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);

            loginWithGooglePlus();
            // updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.btn_sign_out:
                signOut();
                break;

            case R.id.btn_revoke_access:
                revokeAccess();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {

//            btnSignIn.setVisibility(com.asi.educatyapp.Data.View.GONE);
//            btnSignOut.setVisibility(com.asi.educatyapp.Data.View.VISIBLE);
//            btnRevokeAccess.setVisibility(com.asi.educatyapp.Data.View.VISIBLE);
//            llProfileLayout.setVisibility(com.asi.educatyapp.Data.View.VISIBLE);
        } else {
//            btnSignIn.setVisibility(com.asi.educatyapp.Data.View.VISIBLE);
//            btnSignOut.setVisibility(com.asi.educatyapp.Data.View.GONE);
//            btnRevokeAccess.setVisibility(com.asi.educatyapp.Data.View.GONE);
//            llProfileLayout.setVisibility(com.asi.educatyapp.Data.View.GONE);
        }
    }

    public void loginWithGooglePlus() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://educatyapp.890m.com/Apis/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server

                progressDialog.dismiss();
                try {
                    JSONObject ob = new JSONObject(response);
                    String message = ob.getString("msg");
                    isLogin = ob.getString("isExist");

                    if (isLogin.equals("1")) {


                        Toast.makeText(ParentAndStudentLogin.this, "User Exist", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ParentAndStudentLogin.this, Home.class));
                    } else if (isLogin.equals("0")) {
                        Toast.makeText(ParentAndStudentLogin.this, "New Acount Added", Toast.LENGTH_LONG).show();
                    } else {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(ParentAndStudentLogin.this, "There are an error try again later", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
//                HashMap<String, String> user = db.getUserDetails();
//                String id = user.get("uid");
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", txtName.getText().toString());
                params.put("email", txtEmail.getText().toString());
                params.put("gender", "Male");
                params.put("id", googlePlusId);
                params.put("bdate", "12-12-2000");
                return params;
            }

        };
        // Adding request to request queue
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");


    }

    public void Login(View view) {

        if(getIntent().getStringExtra("type").equals("Student"))
        {
            logInAsStudent();
        }else if (getIntent().getStringExtra("type").equals("parent")){
            logInAsParent();
        }

    }

    public void logInAsStudent() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL + "StudentLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server

                Log.e("ERROR==>", response);
                progressDialog.dismiss();
                try {
                    JSONObject ob = new JSONObject(response);

                    String isLogin = ob.getString("isExist");



                    if (isLogin.equals("1")) {
                        String name = ob.getString("name");
                        String username = ob.getString("username");
                        String path=ob.getString("path");
                        String id = ob.getString("id");
                        db.addUser(name, username, id,path,"Student","d");
                        session.setLogin(true);
                        startActivity(new Intent(ParentAndStudentLogin.this,Home.class));
                        finish();

                    } else if (isLogin.equals("0")) {
                        Toast.makeText(ParentAndStudentLogin.this, "Check your username or password", Toast.LENGTH_LONG).show();
                    } else {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(ParentAndStudentLogin.this, "There are an error try again later", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email.getText().toString());
                params.put("pass", pass.getText().toString());

                return params;
            }

        };
        // Adding request to request queue
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }

    public void logInAsParent() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL + "ParentLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server


                progressDialog.dismiss();
                try {
                    JSONObject ob = new JSONObject(response);

                    Log.e("Response===> Parent",response);
                    String isLogin = ob.getString("isExist");

                    if (isLogin.equals("1")) {
                        String name = ob.getString("name");
                        String username = ob.getString("username");
                        String path=ob.getString("path");
                        String id = ob.getString("id");
                        db.addUser(name, username, id,path,"parent","ds");
                        session.setLogin(true);
                        startActivity(new Intent(ParentAndStudentLogin.this,Home.class));
                        finish();

                    } else if (isLogin.equals("0")) {
                        Toast.makeText(ParentAndStudentLogin.this, "Check your username or password", Toast.LENGTH_LONG).show();
                    } else {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(ParentAndStudentLogin.this, "There are an error try again later", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email.getText().toString());
                params.put("pass", pass.getText().toString());

                return params;
            }

        };
        // Adding request to request queue
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }


}
