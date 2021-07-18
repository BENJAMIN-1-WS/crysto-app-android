package com.example.tempcrysto;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tempcrysto.databinding.ActivityMain2Binding;
import com.jjoe64.graphview.GraphView;

import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {
    public static  Coin coin_;
    public static GraphView graph;
    public static Intent intent;
    public static  int id_coin;
    ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        intent = getIntent();  //  get global Intent from main_activity
        Button stamp2= findViewById(R.id.stamp2);
        Button stamp1= findViewById(R.id.stamp1);
        Button stamp= findViewById(R.id.stamp);
        Button back= findViewById(R.id.back);
        TextView textV=findViewById(R.id.textView);
        Intent HomePage=new Intent(this,MainActivity.class); // graph Intent
        graph= (GraphView) findViewById(R.id.graph);
        id_coin = intent.getIntExtra("id",0);// id coin
        icon = findViewById(R.id.icon);

        double ils= 3.2; // api ils new iraeli shakel nis ils
        try {
            coin_=new Coin(String.valueOf(id_coin));
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            textV.setText(coin_.name_+"\n"+String.format("%,.2f", (coin_.price_))+ " $\n" +String.format("%,.2f", (coin_.price_*ils))+ " ₪\n" + coin_.change_h.toString() + " %\n"  );
            Glide.with(this).load(coin_.iconUrl_).into(icon);//  Drawable
            icon.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (coin_.series24h!=null){
            graph.removeAllSeries();
            graph.clearSecondScale();
            graph.addSeries(coin_.series24h);
        }
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stamp2.setOnClickListener(new View.OnClickListener() {  //  1y
            @Override
            public void onClick(View v) {
                graph.removeAllSeries();
                graph.clearSecondScale();
                graph.addSeries(coin_.series1y);
                textV.setText(coin_.name_+"\n"+String.format("%,.2f", (coin_.price_))+ " $\n" +String.format("%,.2f", (coin_.price_*ils))+ " ₪\n" + coin_.change_y.toString() + " %\n"  );
            }
        });
        stamp1.setOnClickListener(new View.OnClickListener() {  //  7d
            @Override
            public void onClick(View v) {
                graph.removeAllSeries();
                graph.clearSecondScale();
                graph.addSeries(coin_.series7d);

                textV.setText(coin_.name_+"\n"+String.format("%,.2f", (coin_.price_))+ " $\n" +String.format("%,.2f", (coin_.price_*ils))+ " ₪\n" + coin_.change_d.toString() + " %\n"  );
            }
        });
        stamp.setOnClickListener(new View.OnClickListener() { //  24h
            @Override
            public void onClick(View v) {
                graph.removeAllSeries();
                graph.clearSecondScale();
                graph.addSeries(coin_.series24h);
                textV.setText(coin_.name_+"\n"+String.format("%,.2f", (coin_.price_))+ " $\n" +String.format("%,.2f", (coin_.price_*ils))+ " ₪\n" + coin_.change_h.toString() + " %\n"  );


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HomePage);
            }
        });
    }
}