package com.example.theshoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{
    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;
    private TextView loginp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_pasword_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        loginp = (TextView) findViewById(R.id.main_login_btn);

        loadingBar = new ProgressDialog(this);



        loginp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });





        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();

            }
        });

    }


    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            StyleableToast.makeText(RegisterActivity.this, "Your Fullname Required", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            StyleableToast.makeText(RegisterActivity.this, "Your Phone Number Required", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            StyleableToast.makeText(RegisterActivity.this, "Your Password Required", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are cheaking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, phone, password);
        }
    }


    private void ValidatephoneNumber(final String name, final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Phone", phone);
                    userdataMap.put("Password", password);
                    userdataMap.put("Name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        StyleableToast.makeText(RegisterActivity.this," Your Account Has Been Created Successfully.", Toast.LENGTH_LONG,R.style.Maintoastgreen).show();

                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();

                                        StyleableToast.makeText(RegisterActivity.this,"Network Error: Please Try Again After Some Time", Toast.LENGTH_LONG,R.style.Maintoastred).show();
                                    }
                                }
                            });
                }
                else
                {
                    StyleableToast.makeText(RegisterActivity.this,"This " + phone +"Already Exists.", Toast.LENGTH_LONG,R.style.Maintoastred).show();

                    loadingBar.dismiss();
                    StyleableToast.makeText(RegisterActivity.this,"Please Try Again Using Another Phone Number.", Toast.LENGTH_LONG,R.style.Maintoastred).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
