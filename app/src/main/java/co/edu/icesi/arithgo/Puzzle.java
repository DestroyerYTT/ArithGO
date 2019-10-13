package co.edu.icesi.arithgo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.file.Files;
import java.util.Random;

import co.edu.icesi.arithgo.model.data.CRUDPoints;
import co.edu.icesi.arithgo.model.entity.Point;

public class Puzzle extends AppCompatActivity implements View.OnClickListener {

    private Button returnBtn;
    private Button generateBtn;
    private TextView num1;
    private TextView num2;
    private TextView operator;
    private EditText input;
    private TextView points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        returnBtn = findViewById(R.id.return_btn);
        generateBtn = findViewById(R.id.generate_btn);
        num1 = findViewById(R.id.num1_tv);
        num2 = findViewById(R.id.num2_tv);
        operator = findViewById(R.id.operator_tv);
        input = findViewById(R.id.input_et);
        points = findViewById(R.id.question_points_tv);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    System.out.println(editable.toString());
            }
        });

        generateBtn.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
    }

    public void generatePuzzle(){
        Random r = new Random();
        num1.setText(""+r.nextInt(100));
        num2.setText(""+r.nextInt(100));
        int num3 = r.nextInt(100);

        if(num3 % 4 == 0){
            operator.setText("+");
        }else if(num3 % 4 == 1){
            operator.setText("-");
        }else if(num3 % 4 == 2){
            operator.setText("*");
        }else if(num3 % 4 == 3){
            operator.setText("/");
        }
    }

    public void verifyAnwer(int anwer){
        int correctAnwer = 0;
        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());

        if(operator.equals("+")){
            correctAnwer = n1 + n2;
        }else if(operator.equals("*")){
            correctAnwer = n1 * n2;
        }else if(operator.equals("-")){
            correctAnwer = n1 - n2;
        }else if(operator.equals("/")){
            correctAnwer = n1 / n2;
        }
        int temp = CRUDPoints.retrieve("1").getPoints().intValue();

        if(correctAnwer == anwer){
            temp++;
            CRUDPoints.update(new Point("1", "points", temp));
        }else{
            temp--;
            CRUDPoints.update(new Point("1", "points", temp));
        }
        

    }

    @Override
    public void onClick(View view) {
        if(returnBtn.getId() == view.getId()) {
            Map.updatePoint();
            onBackPressed();
        }else if(generateBtn.getId() == view.getId()){
            generatePuzzle();
        }
    }
}
