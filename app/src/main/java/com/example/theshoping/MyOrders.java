package com.example.theshoping;

import Model.UserOrders;
import Prevalent.Prevalent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import static Prevalent.Prevalent.currentOnlineUser;


public class MyOrders extends AppCompatActivity
{
    private RecyclerView ordersList;
   private DatabaseReference ordersRef;
    private DatabaseReference productRef;
    public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress,pincode,statename,cart_product_name,cart_product_quantity,cart_product_price;
    public Button ShowcancelOrdersBtn,show_all_products_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);


        this.setTitle("Your Orders");
        //set the back arrow button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



         ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
         productRef = FirebaseDatabase.getInstance().getReference().child("Cart List/Admin View");



        userName = findViewById(R.id.order_user_name);
        userPhoneNumber = findViewById(R.id.order_phone_number);
        statename = findViewById(R.id.statename);
        pincode = findViewById(R.id.pincode);
        userTotalPrice = findViewById(R.id.order_total_price);
        userDateTime = findViewById(R.id.order_date_time);
        userShippingAddress = findViewById(R.id.order_address_city);
        ShowcancelOrdersBtn = findViewById(R.id.cancel_all_products_btn);
        show_all_products_btn= findViewById(R.id.show_all_products_btn);



        userName.setVisibility(View.GONE);
        userPhoneNumber.setVisibility(View.GONE);
        statename.setVisibility(View.GONE);
        pincode.setVisibility(View.GONE);
        userTotalPrice.setVisibility(View.GONE);
        userDateTime.setVisibility(View.GONE);
        userShippingAddress.setVisibility(View.GONE);
        ShowcancelOrdersBtn.setVisibility(View.GONE);
        show_all_products_btn.setVisibility(View.GONE);





        getOrdersDetails();
        getOrderspro();

    }



    private void getOrdersDetails()
    {



        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());


            productsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserOrders model = dataSnapshot.getValue(UserOrders.class);



                        userName.setVisibility(View.VISIBLE);
                        userPhoneNumber.setVisibility(View.VISIBLE);
                        statename.setVisibility(View.VISIBLE);
                        pincode.setVisibility(View.VISIBLE);
                        userTotalPrice.setVisibility(View.VISIBLE);
                        userDateTime.setVisibility(View.VISIBLE);
                        userShippingAddress.setVisibility(View.VISIBLE);
                        ShowcancelOrdersBtn.setVisibility(View.VISIBLE);
                        show_all_products_btn.setVisibility(View.VISIBLE);





                        userName.setText("Name: " + model.getName());
                        userPhoneNumber.setText("Phone: " + model.getPhone());
                        statename.setText("State: " + model.getStatename());
                        pincode.setText("Pincode: " + model.getPincode());
                        userTotalPrice.setText("Total Amount =  ₹" + model.getTotalAmount());

                        userDateTime.setText("Order Time: " + model.getDate() + "  " + model.getTime());
                        userShippingAddress.setText("Shipping Address: " + model.getAddress() + ", " + model.getCity());


                        ShowcancelOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "YES",
                                                "NO"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyOrders.this);
                                builder.setTitle("Are You Sure You want To Cancle This Order ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            RemoverOrder(Prevalent.currentOnlineUser.getPhone());
                                            StyleableToast.makeText(MyOrders.this, "Your Order has been Cancelled Successfully", Toast.LENGTH_SHORT,R.style.Maintoastgreen).show();
                                            Intent intent = new Intent(MyOrders.this, MyOrders.class);
                                            startActivity(intent);
                                            finish();

                                        }



                                        if (i == 1) {

                                            Intent intent = new Intent(MyOrders.this, MyOrders.class);
                                            startActivity(intent);
                                            finish();
                                        }


                                    }




                                });
                                builder.show();
                            }
                        });






                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }







    private void getOrderspro()
    {



        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View").child(currentOnlineUser.getPhone()).child("Products");



        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {





                   show_all_products_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            Intent intent = new Intent(MyOrders.this, Userorderedproduct.class);
                            startActivity(intent);
                        }
                    });







                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }












    private void RemoverOrder(String cur)
    {
        ordersRef.child(cur).removeValue();
        productRef.child(cur).removeValue();
    }




}

