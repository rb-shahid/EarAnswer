package com.byteshaft.earanswer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends ActionBarActivity implements CheckBox.OnCheckedChangeListener {

    private Helpers mHelpers;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCheckBox = (CheckBox) findViewById(R.id.auto_answer_checkbox);
        mCheckBox.setOnCheckedChangeListener(this);
        mHelpers = new Helpers(getApplicationContext());
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
}
