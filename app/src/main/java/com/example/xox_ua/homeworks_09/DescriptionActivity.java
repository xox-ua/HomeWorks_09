package com.example.xox_ua.homeworks_09;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.xox_ua.homeworks_09.Base.BaseActivity;

import butterknife.BindView;

public class DescriptionActivity extends BaseActivity {
    @BindView(R.id.ivFlgDescr) ImageView ivImgDescr;
    @BindView(R.id.tvCountryDescr) TextView tvCountryDescr;
    @BindView(R.id.tvCityDescr) TextView tvCityDescr;
    @BindView(R.id.tvDescr) TextView tvDescr;
    @BindView(R.id.ratingBarDescr) RatingBar ratingBarDescr;
    @BindView(R.id.btnBack) Button btnBack;
    @BindView(R.id.btnShare) Button btnShare;
    String descrCo;
    String descrCi;
    int descrR;
    String descrD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_description);
        super.onCreate(savedInstanceState);

        // добавляем прокручивание для данной текстовой области
        tvDescr.setMovementMethod(new ScrollingMovementMethod());

        if (getIntent().getBooleanExtra("Notification", false)) {
            // получаем интент
            Bundle extras = getIntent().getExtras();
            assert extras != null;
            Bitmap descrI = (Bitmap) extras.getParcelable("getImage");
            Intent intent = getIntent();
            descrCo = intent.getStringExtra("getCountry");
            descrCi = intent.getStringExtra("getCapital");
            descrR = intent.getIntExtra("getRating", 0);
            descrD = intent.getStringExtra("getDescr");
            // выводим полученные данные в соотвествующих областях
            ivImgDescr.setImageBitmap(descrI);
            tvCountryDescr.setText(descrCo);
            tvCityDescr.setText(descrCi);
            ratingBarDescr.setRating(descrR);
            tvDescr.setText(descrD);
        } else {
            //интент не получен
            Toast.makeText(getApplicationContext(), R.string.toast1, Toast.LENGTH_SHORT).show();
        }

        // кнопка назад
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // кнопка поделиться
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = descrCo + "\n" + descrCi + "\nРейтинг: " + descrR + "\n" + descrD;
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }
}
