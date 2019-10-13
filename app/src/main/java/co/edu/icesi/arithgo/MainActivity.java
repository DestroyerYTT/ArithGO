package co.edu.icesi.arithgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import co.edu.icesi.arithgo.model.data.CRUDPoints;
import co.edu.icesi.arithgo.model.entity.Point;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button playBtn;
    private Button instructionBtn;
    private Button configBtn;

    private Map mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playBtn = findViewById(R.id.btn_play);
        playBtn.setOnClickListener(this);
        instructionBtn = findViewById(R.id.btn_instructions);
        instructionBtn.setOnClickListener(this);
        configBtn = findViewById(R.id.btn_config);
        configBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == playBtn.getId()){
          //CRUDPoints.create(new Point("1", "myPoints", 0));
          Intent i = new Intent(this, Map.class);
          startActivity(i);
        }else if(view.getId() == instructionBtn.getId()){

        }else if(view.getId() == configBtn.getId()){

        }
    }
}
