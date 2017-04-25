package com.tan_ds.painter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {


    com.tan_ds.painter.PaintingView painView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ochistka
        painView = (PaintingView) findViewById(R.id.paintView);
        Button clearButt = (Button) findViewById(R.id.clearButt);
        //Switch swch = (Switch) findViewById(R.id.switch1);
        clearButt.setOnClickListener(this);




        //swch.setChecked(false);

       /* swch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    painView.flag = true;
                    Toast.makeText(getApplicationContext(),"you'll draw a rectangle", Toast.LENGTH_SHORT).show();
                }else {
                    painView.flag = false;
                    Toast.makeText(getApplicationContext(),"you'll draw a line", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.groupRadio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case -1:
                        Toast.makeText(getApplicationContext(), "Ничего не выбрано", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.buttLine:
                        Toast.makeText(getApplicationContext(),"you'll draw a line", Toast.LENGTH_SHORT).show();
                        painView.figure = PaintingView.Perechisl.lining;
                        break;
                    case R.id.buttRect:
                        Toast.makeText(getApplicationContext(),"you'll draw a rectangle", Toast.LENGTH_SHORT).show();
                        painView.figure = PaintingView.Perechisl.rect;
                        break;
                    case R.id.buttDraw:
                        Toast.makeText(getApplicationContext(), "you'll draw a drawable", Toast.LENGTH_SHORT).show();
                        painView.figure = PaintingView.Perechisl.fignia;
                        break;
                    default:
                        break;

                }
            }
        });



    }



    @Override
    public void onClick(View v) {
        painView.clear();
    }
}
