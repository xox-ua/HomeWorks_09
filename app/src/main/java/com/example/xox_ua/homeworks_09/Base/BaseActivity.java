package com.example.xox_ua.homeworks_09.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.xox_ua.homeworks_09.Data.DataManager;

import butterknife.ButterKnife;

// обязательно abstract - чтобы нельзя было создавать объекты этого класса
public abstract class BaseActivity extends AppCompatActivity {
    protected DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // вьюшка подключена через ButterKnife
        ButterKnife.bind(this);
        //
        mDataManager = new DataManager();
    }
}
