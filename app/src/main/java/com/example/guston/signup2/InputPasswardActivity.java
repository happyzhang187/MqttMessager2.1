package com.example.guston.signup2;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guston.signup2.databinding.ActivityInputPasswardBinding;


/**
 * Created by Guston on 10/12/17.
 */

public class InputPasswardActivity extends AppCompatActivity {

   private  ActivityInputPasswardBinding mbinding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mbinding = DataBindingUtil.setContentView(this , R.layout.activity_input_passward);



    }


}
