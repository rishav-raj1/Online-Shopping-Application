package com.example.theshoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class checkerquestion extends AppCompatActivity {


    private EditText phoneNumber,question1,question2;
    private TextView titlequestions,headtxt;
    private Button updateButton;
    Spinner mSpinner1,mSpinner2;
    String[] mOptinos1={"Select First Box Question","Your Oldest Cousin's Name ?","Your Favorite Songs? ",
            "What Is Your Work Address?","Your Oldest Sibling's Name?","Which Country Do You Like?",
            "Your Favorite School Teacher's Name?"};


    String[] mOptinos2={"Select Second Box Question","What Is The Name Of Your Oldest Child ?","What is Your Birth Month?",
            "Your Favorite Childhood Hero?","Your Favorite Girl's name?"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkerquestion);




        question1=findViewById(R.id.question_1);
        question2=findViewById(R.id.question_2);
        updateButton=findViewById(R.id.updatebtn);

        mSpinner1=findViewById(R.id.spinner);

        mSpinner2=findViewById(R.id.spinner2);
        titlequestions=findViewById(R.id.title_question);
        headtxt=findViewById(R.id.txthead);



        ArrayAdapter var1= new ArrayAdapter(this,android.R.layout.simple_spinner_item,mOptinos1);
        var1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(var1);

        ArrayAdapter var2= new ArrayAdapter(this,android.R.layout.simple_spinner_item,mOptinos2);
        var2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner2.setAdapter(var2);



        question1.setVisibility(View.GONE);
        question2.setVisibility(View.GONE);
        updateButton.setVisibility(View.GONE);
        mSpinner1.setVisibility(View.GONE);
        mSpinner2.setVisibility(View.GONE);
        titlequestions.setVisibility(View.GONE);
        headtxt.setVisibility(View.GONE);



      CheckQuestion();

    }




    private void CheckQuestion()
    {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("answer1").exists() && dataSnapshot.child("answer2").exists())
                {
                    //StyleableToast.makeText(checkerquestion.this, "logged in a Successfully", LENGTH_SHORT,R.style.Maintoastgreen).show();

                    Intent intent = new Intent(checkerquestion.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                    question1.setVisibility(View.VISIBLE);
                    question2.setVisibility(View.VISIBLE);
                    updateButton.setVisibility(View.VISIBLE);
                    mSpinner1.setVisibility(View.VISIBLE);
                    mSpinner2.setVisibility(View.VISIBLE);
                    titlequestions.setVisibility(View.VISIBLE);
                    headtxt.setVisibility(View.VISIBLE);

                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {

                            setAnswer();


                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





    private void setAnswer()
    {

        question1.setVisibility(View.VISIBLE);
        question2.setVisibility(View.VISIBLE);
        updateButton.setVisibility(View.VISIBLE);
        mSpinner1.setVisibility(View.VISIBLE);
        mSpinner2.setVisibility(View.VISIBLE);
        titlequestions.setVisibility(View.VISIBLE);
        headtxt.setVisibility(View.VISIBLE);




        String answer1=question1.getText().toString().toLowerCase().trim();;
        String answer2=question2.getText().toString().toLowerCase().trim();;

        if(answer1.equals(""))
        {

            StyleableToast.makeText(checkerquestion.this,"Please Answer Both Question",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else if(answer2.equals(""))
        {
            StyleableToast.makeText(checkerquestion.this,"Please Answer Both Question",Toast.LENGTH_SHORT,R.style.Maintoastred).show();
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
                       // StyleableToast.makeText(checkerquestion.this, "logged in a Successfully", Toast.LENGTH_SHORT,R.style.Maintoastgreen).show();

                        Intent intent = new Intent(checkerquestion.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }







}



