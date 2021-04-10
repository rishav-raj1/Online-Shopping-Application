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

public class Admin_login extends AppCompatActivity
{
    private Button loginButton;
    private ProgressDialog loadingBar;


    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;

    private TextView AdminLink, NotAdminLink,joinNowButton;

    private String parentDbName = "Admins";
    private CheckBox chkBoxRememberMe;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);


        loadingBar = new ProgressDialog(this);



        ConnectivityManager managercheck =(ConnectivityManager)getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkcheck=managercheck.getActiveNetworkInfo();
        if(null!=activeNetworkcheck) {

        }
        else
        {
            StyleableToast.makeText(this,"NETWORK NOT AVAILABLE",Toast.LENGTH_LONG,R.style.Maintoastred).show();

        }









        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_pasword_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);

        loadingBar = new ProgressDialog(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_admin);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginAdmin();

            }
        });







        Paper.init(this);





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
                if (dataSnapshot.child("Admins").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Admins").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {


                            StyleableToast.makeText(Admin_login.this,"You Are Successfully logged In", Toast.LENGTH_LONG,R.style.Maintoastgreen).show();

                            loadingBar.dismiss();

                            Intent intent = new Intent(Admin_login.this, AdminCategoryActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                        }
                        else
                        {
                            loadingBar.dismiss();

                            StyleableToast.makeText(Admin_login.this,"Password Is Incorrect.", Toast.LENGTH_LONG,R.style.Maintoastred).show();
                        }
                    }
                }
                else
                {

                    StyleableToast.makeText(Admin_login.this, "Account With This " + phone + " Number Do Not Exists", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });











    }


    private void LoginAdmin()
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

                             if(parentDbName.equals("Admins"))
                            {
                                StyleableToast.makeText(Admin_login.this, "logged in a Successfully", LENGTH_SHORT,R.style.Maintoastgreen).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Admin_login.this,  AdminCategoryActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            StyleableToast.makeText(Admin_login.this, "Password Is Incorrect.", LENGTH_SHORT,R.style.Maintoastred).show();
                        }
                    }
                }
                else
                {
                    StyleableToast.makeText(Admin_login.this, "Account with this " + phone + " Number Do Not Exists", LENGTH_SHORT,R.style.Maintoastred).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}