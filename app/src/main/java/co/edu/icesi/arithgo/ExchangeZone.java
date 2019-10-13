package co.edu.icesi.arithgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.edu.icesi.arithgo.Map;
import co.edu.icesi.arithgo.R;
import co.edu.icesi.arithgo.model.data.CRUDPoints;
import co.edu.icesi.arithgo.model.entity.Point;

public class ExchangeZone extends AppCompatActivity implements View.OnClickListener {

    private Button penBtn;
    private Button bookBtn;
    private Button noteBookBtn;
    private Button shirtBtn;
    private Button coatBtn;
    private TextView tiendaIcesiPoints;
    private Button returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_zone);

        returnBtn = findViewById(R.id.return_btn);
        penBtn = findViewById(R.id.pen_btn);
        bookBtn = findViewById(R.id.book_btn);
        noteBookBtn = findViewById(R.id.notebook_btn);
        shirtBtn = findViewById(R.id.t_shirt_btn);
        coatBtn = findViewById(R.id.coat_btn);
        tiendaIcesiPoints = findViewById(R.id.tienda_icesi_points_tv);
        //CRUDPoints.update(new Point("1", "points", 100));
        tiendaIcesiPoints.setText("You have " + CRUDPoints.retrieve("1").getPoints() + " points");

        returnBtn.setOnClickListener(this);
        penBtn.setOnClickListener(this);
        bookBtn.setOnClickListener(this);
        noteBookBtn.setOnClickListener(this);
        shirtBtn.setOnClickListener(this);
        coatBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if(returnBtn.getId() == view.getId()){
            Map.updatePoint();
            onBackPressed();
        }else{
            int cost = 0;
            String item = "";
            if(penBtn.getId() == view.getId()){
                cost = 20;
                item = "Pen";
            }else if(bookBtn.getId() == view.getId()){
                cost = 30;
                item = "Book";
            }else if(noteBookBtn.getId() == view.getId()){
                cost = 40;
                item = "Notebook";
            }else if(shirtBtn.getId() == view.getId()){
                cost = 80;
                item = "T-Shirt";
            }else if(coatBtn.getId() == view.getId()){
                cost = 100;
                item = "Coat";
            }
            buy(cost, item);
        }

    }

    public void buy(final int cost, final String item){
        final int points =  CRUDPoints.retrieve("1").getPoints().intValue();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

        if(cost <= points){
            builder.setMessage("Are you sure you want to buy a "+ item + "?")
                    .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            CRUDPoints.update(new Point("1", "point", points - cost));
                            builder2.setMessage("You bought a " + item).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    tiendaIcesiPoints.setText("You have " + CRUDPoints.retrieve("1").getPoints() +" points");
                                }
                            });
                                builder2.create().show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }else{
            builder.setMessage("Sorry, you do not have enough points to buy the item")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create().show();
        }
    }
}
