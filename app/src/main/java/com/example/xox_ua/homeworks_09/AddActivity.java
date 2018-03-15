package com.example.xox_ua.homeworks_09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import com.example.xox_ua.homeworks_09.base.BaseActivity;
import butterknife.BindView;

public class AddActivity extends BaseActivity {
    @BindView(R.id.etAddCountry)
    EditText etCountry;
    @BindView(R.id.etAddCapital)
    EditText etCapital;
    @BindView(R.id.etAddDescription)
    EditText etDescr;
    @BindView(R.id.addRating)
    RatingBar mRatingBar;
    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.btnSave)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add);
        super.onCreate(savedInstanceState);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // считываем введённые данные
                String dataCo = etCountry.getText().toString();
                String dataCi = etCapital.getText().toString();
                String dataD = etDescr.getText().toString();
                int dataR = (int) mRatingBar.getRating();
                // отдаём через intent
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("AddCountry", dataCo);
                intent.putExtra("AddCity", dataCi);
                intent.putExtra("AddRating", dataR);
                intent.putExtra("AddDescr", dataD);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
