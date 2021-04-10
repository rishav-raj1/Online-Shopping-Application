package com.example.theshoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import Prevalent.Prevalent;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check=" ";
    private EditText phoneNumber,question1,question2;
    private TextView titlequestions;
    private Button verifyButton;
    Spinner mSpinner1,mSpinner2;
    String[] mOptinos1={"Select First Box Question","Your Oldest Cousin's Name ?","Your Favorite Songs? ",
    "What Is Your Work Address?","Your Oldest Sibling's Name?","Which Country Do You Like?",
    "Your Favorite School Teacher's Name?"};


    String[] mOptinos2={"Select Second Box Question","What Is The Name Of Your Oldest Child ?","What is Your Birth Month?",
            "Your Favorite Childhood Hero?","Your Favorite Girl's name?"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);



        check=getIntent().getStringExtra("check");

        titlequestions=findViewById(R.id.title_question);
        phoneNumber=findViewById(R.id.find_phone_number);
        question1=findViewById(R.id.question_1);
        question2=findViewById(R.id.question_2);
        verifyButton=findViewById(R.id.verify_btn);

              mSpinner1=findViewById(R.id.spinner);

              mSpinner2=findViewById(R.id.spinner2);


        ArrayAdapter var1= new ArrayAdapter(this,android.R.layout.simple_spinner_item,mOptinos1);
        var1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(var1);

        ArrayAdapter var2= new ArrayAdapter(this,android.R.layout.simple_spinner_item,mOptinos2);
        var2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner2.setAdapter(var2);






    }

    @Override
    protected void onStart()
    {
        super.onStart();
        phoneNumber.setVisibility(View.GONE);


        if(check.equals("settings"))
        {
            titlequestions.setText("Security Questions");
            verifyButton.setText("Update Questions");
            displayPreviousAnswer();

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {



                    setAnswer();

                }
            });
        }

       else if(check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });

        }


    }

    private void setAnswer()
    {
        String answer1=question1.getText().toString().toLowerCase().trim();;
        String answer2=question2.getText().toString().toLowerCase().trim();;

        if(answer1.equals(""))
        {

            StyleableToast.makeText(ResetPasswordActivity.this,"Please Answer Both Question",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else if(answer2.equals(""))
        {
            StyleableToast.makeText(ResetPasswordActivity.this,"Please Answer Both Question",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", answer1);
            userdataMap.put("answer2", answer2);
            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        StyleableToast.makeText(ResetPasswordActivity.this, "Successfully Updated", Toast.LENGTH_SHORT,R.style.Maintoastgreen).show();

                        Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void displayPreviousAnswer()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String ans1=dataSnapshot.child("answer1").getValue().toString();
                    String ans2=dataSnapshot.child("answer2").getValue().toString();
                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void verifyUser()
    {
        final String phone =phoneNumber.getText().toString();
        final String answer1=question1.getText().toString().toLowerCase().trim();;
        final String answer2=question2.getText().toString().toLowerCase().trim();;

        if(!phone.equals("") && !answer1.equals("") && !answer2.equals("") )
        {

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        String mphone=dataSnapshot.child("Phone").getValue().toString();

                        if(dataSnapshot.hasChild("Security Questions"))
                        {
                            String ans1=dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2=dataSnapshot.child("Security Questions").child("answer2").getValue().toString();
                            if(!ans1.equals(answer1))
                            {
                                StyleableToast.makeText(ResetPasswordActivity.this,"Wrong Answer",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
                            }
                            else if(!ans2.equals(answer2))
                            {
                                StyleableToast.makeText(ResetPasswordActivity.this,"Wrong Answer",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
                            }
                            else
                            {
                                AlertDialog.Builder builder =new AlertDialog.Builder(ResetPasswordActivity.this);

                                builder.setTitle("THESHOPING");

                                final EditText newpassword=new EditText(ResetPasswordActivity.this);
                                newpassword.setHint("Enter New Password");
                                builder.setView(newpassword);
                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!newpassword.getText().toString().equals(""))
                                        {

                                            ref.child("Password").setValue(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){

                                                        StyleableToast.makeText(ResetPasswordActivity.this,"Password Change Successfully",Toast.LENGTH_SHORT,R.style.Maintoastgreen).show();
                                                   Intent intent =new Intent(ResetPasswordActivity.this,MainActivity.class);
                                                   startActivity(intent);
                                                    }



                                                }
                                            });


                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        dialog.cancel();

                                    }
                                });
                                builder.show();
                            }
                        }

                        else
                        {
                            StyleableToast.makeText(ResetPasswordActivity.this,"You have Not Set The Security Questions",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
                        }
                    }
                    else
                    {
                        StyleableToast.makeText(ResetPasswordActivity.this,"Phone Number Does Not Exist",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else
        {
            StyleableToast.makeText(ResetPasswordActivity.this,"All Filds Are Mandatory",Toast.LENGTH_SHORT,R.style.Maintoastred).show();

        }




    }


}
