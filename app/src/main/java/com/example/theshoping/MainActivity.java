package com.example.theshoping;

import Model.Users;
import Prevalent.Prevalent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity
{
    private Button loginButton;
    private ProgressDialog loadingBar;


    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;

    private TextView AdminLink, NotAdminLink,joinNowButton,ForgetPasswordLink;

    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = (TextView) findViewById(R.id.main_join_now_btn);

        loadingBar = new ProgressDialog(this);


        ConnectivityManager managercheck = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkcheck = managercheck.getActiveNetworkInfo();
        if (null != activeNetworkcheck) {

        } else {
            StyleableToast.makeText(this, "NETWORK NOT AVAILABLE", Toast.LENGTH_LONG, R.style.Maintoastred).show();

        }


        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_pasword_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        AdminLink = (TextView) findViewById(R.id.admin_panel);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(this);

        ForgetPasswordLink=findViewById(R.id.forget_password_link);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();

            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Admin_login.class);
                startActivity(intent);
                finish();
            }
        });


        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);

            }

        });




        Paper.init(this);


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });




        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserPhoneKey) &&  !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setTitle("Already Logged In");
                loadingBar.setMessage("Please wait.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }


    private void AllowAccess(final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {


                           // StyleableToast.makeText(MainActivity.this,"You Are Successfully logged In", Toast.LENGTH_LONG,R.style.Maintoastgreen).show();

                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this, checkerquestion.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            loadingBar.dismiss();

                            StyleableToast.makeText(MainActivity.this,"Password Is Incorrect.", Toast.LENGTH_LONG,R.style.Maintoastred).show();
                        }
                    }
                }
                else
                {

                    StyleableToast.makeText(MainActivity.this, "Account With This " + phone + " Number Do Not Exists", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }





    private void LoginUser()
    {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {


            StyleableToast.makeText(this, "Phone Number Required", LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            StyleableToast.makeText(this, "Password Required", LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait, While We Are Cheaking");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone, password);

        }
    }

    private void AllowAccessToAccount(final String phone, final String password)
    {
        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {

                            if(parentDbName.equals("Users"))
                            {
                               // StyleableToast.makeText(MainActivity.this, "logged in a Successfully", LENGTH_SHORT,R.style.Maintoastgreen).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, checkerquestion.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                                finish();
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            StyleableToast.makeText(MainActivity.this, "Password Is Incorrect.", LENGTH_SHORT,R.style.Maintoastred).show();
                        }
                    }
                }
                else
                {
                    StyleableToast.makeText(MainActivity.this, "Account with this " + phone + " Number Do Not Exists", LENGTH_SHORT,R.style.Maintoastred).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}