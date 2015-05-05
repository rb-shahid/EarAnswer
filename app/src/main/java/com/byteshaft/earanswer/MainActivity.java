package com.byteshaft.earanswer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends Activity implements CheckBox.OnCheckedChangeListener, View.OnClickListener {

    private Helpers mHelpers;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCheckBox = (CheckBox) findViewById(R.id.auto_answer_checkbox);
        mCheckBox.setOnCheckedChangeListener(this);
        mHelpers = new Helpers(getApplicationContext());
        setFinishOnTouchOutside(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCheckBox.setChecked(mHelpers.isCallAutoAnswerEnabled());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.auto_answer_checkbox:
                if (isChecked) {
                    startService(new Intent(this, SensorService.class));
                } else {
                    stopService(new Intent(this, SensorService.class));
                }
                mHelpers.enableCallAutoAnswer(isChecked);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bClose:
                finish();
        }
    }
}
