package com.example.theshoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity
{
    private EditText nameEditText, phoneEditText, addressEditText, cityEditText,pincode,statename;
    private Button confirmOrderBtn;

    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        this.setTitle("Change or Add Address");
        //set the back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalAmount = getIntent().getStringExtra("ORDER TOTAL");
        //Toast.makeText(this, "Total Order ₹ " + totalAmount, Toast.LENGTH_SHORT).show();


        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shippment_name);
        phoneEditText = (EditText) findViewById(R.id.shippment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shippment_address);
        cityEditText = (EditText) findViewById(R.id.shippment_city);
        pincode=(EditText) findViewById(R.id.shippment_pincode);
        statename=(EditText) findViewById(R.id.shippment_state);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });
    }



    private void Check()
    {
        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {

            StyleableToast.makeText(this, "Full Name Required.", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            StyleableToast.makeText(this, "Phone Number Required.", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }

        else if (TextUtils.isEmpty(pincode.getText().toString()))
        {
            StyleableToast.makeText(this, "Your Pincode Required", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }


        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            StyleableToast.makeText(this, "Your Address Required", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }
        else if (TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            StyleableToast.makeText(this, "Your City Name Required", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }

        else if (TextUtils.isEmpty(statename.getText().toString()))
        {
            StyleableToast.makeText(this, "Your State Required", Toast.LENGTH_SHORT,R.style.Maintoastred).show();
        }

        else
        {
            ConfirmOrder();
        }
    }



    private void ConfirmOrder()
    {
        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("TotalAmount", totalAmount);
        ordersMap.put("Name", nameEditText.getText().toString());
        ordersMap.put("Phone", phoneEditText.getText().toString());
        ordersMap.put("Pincode", pincode.getText().toString());
        ordersMap.put("Address", addressEditText.getText().toString());
        ordersMap.put("City", cityEditText.getText().toString());
        ordersMap.put("Statename", statename.getText().toString());
        ordersMap.put("Date", saveCurrentDate);
        ordersMap.put("Time", saveCurrentTime);
        ordersMap.put("State", "not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {


                                        StyleableToast.makeText(ConfirmFinalOrderActivity.this, "Your Order Has Been Placed Successfully.", Toast.LENGTH_LONG,R.style.Maintoastgreen).show();

                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, AfterorderActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


    }
}
