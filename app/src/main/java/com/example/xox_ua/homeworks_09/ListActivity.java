package com.example.xox_ua.homeworks_09;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.xox_ua.homeworks_09.Base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListActivity extends BaseActivity {
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    ArrayAdapter<Country> ad;                           // адаптер
    List<Country> countriesData = new ArrayList<>();    // источник данных
    String getD;
    public static final String DESCR = "Description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
        super.onCreate(savedInstanceState);

        // Строим источник данных
        countriesData = mDataManager.fetchMocks();
        // Создаем адаптер для преобразования массива в представления (array to views)
        // 1: контекст, 2: идентификатор ресурса с разметкой для каждой строки, 3: массив данных
        ad = new CountryAdapter(this, R.layout.list_item, countriesData);
        // устанавливаем адаптер для ListView
        lv.setAdapter(ad);



        // КНОПКА ДОБАВИТЬ - добавление новой строки (из полученных данных из AddActivity)
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, 1975);
            }
        });

        // КОРОТКОЕ НАЖАТИЕ на строку в ListView (item)
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // определяем вьюхи в строке из которых надо взять данные
                ImageView imageView = (ImageView) view.findViewById(R.id.ivFlg);        // флаг
                TextView txtView1 = (TextView) view.findViewById(R.id.tvCountry);       // страна
                TextView txtView2 = (TextView) view.findViewById(R.id.tvCapital);       // столица
                RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);    // рейтинг

                // берём данные
                imageView.buildDrawingCache();                  // создаём кеш изображения imageView
                Bitmap getF = imageView.getDrawingCache();      // берём этот кэш
                String getCo = txtView1.getText().toString();   // берём текст из вьюхи страны
                String getCC = txtView2.getText().toString();   // берём текст из вьюхи столицы
                int getR = (int) ratingBar.getRating();         // берём цифру из вьюхи рейтинга
                // считываем сохранённое Описание
                SharedPreferences prefs = getSharedPreferences(DESCR, MODE_PRIVATE);
                String restoredText = prefs.getString("text", null);
                if (restoredText == null & !getCo.contains(" - NEW!")) {
                    // если описание отсутствует показываем рыбу
                    getD = getResources().getString(R.string.lorem);
                } else if (getCo.contains(" - NEW!")){
                    // если это введённое пользователем описание (в AddActivity),
                    // то передаём описание из пришедшего интента
                    getD = prefs.getString("newD", "No name defined");
                }
                // передаём данные с помощью бандла и интента
                Bundle extras = new Bundle();
                // окуда и куда передаём
                Intent intent = new Intent(ListActivity.this, DescriptionActivity.class);
                extras.putParcelable("getImage", getF);
                intent.putExtras(extras);
                intent.putExtra("getCountry", getCo);
                intent.putExtra("getCapital", getCC);
                intent.putExtra("getRating", getR);
                intent.putExtra("getDescr", getD);
                intent.putExtra("Notification", true);
                startActivity(intent);
            }
        });

        // ПРОДОЛЖИТЕЛЬНОЕ НАЖАТИЕ на строку в ListView (item) - удаление строки
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtView1 = (TextView) view.findViewById(R.id.tvCountry);
                String getCo = txtView1.getText().toString();   // берём текст из вьюхи страны

                // добавляем AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle(R.string.delete)
                        .setIcon(R.drawable.zz_alert)
                        .setMessage(getResources().getString(R.string.alert1) + " " + getCo + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();

                                // удаляем выбранную позицию
                               // countriesData.remove(position);
                                // уведомляем, что данные изменились
                                ad.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ListActivity.this, "No it's mean no", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();


                return true;
            }
        });
    }

    // получение интента из AddActivity и добавление новой строки в ListView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 1975:
                if (resultCode == RESULT_OK) {
                    // получаем интент из AddActivity
                    String newCountry = intent.getStringExtra("AddCountry");
                    String newCo = newCountry + " - NEW!";
                    String newCi = intent.getStringExtra("AddCity");
                    int newR = intent.getIntExtra("AddRating", 0);
                    String newD = intent.getStringExtra("AddDescr");
                    // сохраняем полученный Description
                    SharedPreferences.Editor editor = getSharedPreferences(DESCR, MODE_PRIVATE).edit();
                    editor.putString("newD", newD);
                    editor.apply();
                    // задаём общую картинку для всех новодобавленных строк
                    int newF = R.drawable.zz_flg_eu;

                    // пришедший интент добавляем в коллекцию
                    // всю мешпуху в один обект и в список с которым работает адаптер
                    countriesData.add(new Country(newCo, newCi, newF, newR));
                    // уведомляем, что данные изменились
                    ad.notifyDataSetChanged();
                    // после добавления нового пункта проматываем ListView в самый конец
                    lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                }else {
                    Toast.makeText(getApplicationContext(), R.string.toast3, Toast.LENGTH_SHORT).show();
                }
        }
    }
}
