package com.tan_ds.painter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
        Switch swch = (Switch) findViewById(R.id.switch1);
        TextView ColVo = (TextView) findViewById(R.id.col_vo);
        clearButt.setOnClickListener(this);
        ColVo.setText(((Integer)painView.colInt).toString());
        swch.setChecked(false);

        swch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });
    }



    @Override
    public void onClick(View v) {
        painView.clear();
    }
}
