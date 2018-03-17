package com.example.xox_ua.homeworks_09;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.xox_ua.homeworks_09.base.BaseActivity;
import com.example.xox_ua.homeworks_09.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

import static android.widget.Toast.LENGTH_SHORT;

public class ListActivity extends BaseActivity {
    @BindView(R.id.rv) RecyclerView mRecyclerView;
    @BindView(R.id.btnAdd) ImageView btnAdd;
    @BindView(R.id.layoutWithBTN) LinearLayout layoutWithBTN;
    @BindView(R.id.swipe) SwipeRefreshLayout mSwipeRefreshLayout;
    CustomRVAdapter mCustomRVAdapter;                   // адаптер
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
        mCustomRVAdapter = new CustomRVAdapter();
        mCustomRVAdapter.setCountries(countriesData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        // разделитель для ячеек
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        // устанавливаем адаптер для RecyclerView
        mRecyclerView.setAdapter(mCustomRVAdapter);

        // SWIPE-обновлялка контейнера
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // заново наполняем данными RecyclerView
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(ListActivity.this));
                        // устанавливаем адаптер для RecyclerView
                        mRecyclerView.setAdapter(mCustomRVAdapter);
                        mCustomRVAdapter.setCountries(countriesData);
                        // что-то типа подтверждения события -=- меняем цвет фона в подвале с кнопками
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        layoutWithBTN.setBackgroundColor(color);

                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });


        // КНОПКА ДОБАВИТЬ - добавление новой строки (из полученных данных из AddActivity)
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, 1975);
            }
        });

        // НАЖАТИЕ на ячейку списка
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    // КОРОТКОЕ НАЖАТИЕ на строку в ListView (item)
                    @Override public void onItemClick(View view, int position) {
                        // берём данные из countriesData (по позиции)
                        int getF = (countriesData.get(position)).getFlagId();           // берём id: флаг
                        String getCo = (countriesData.get(position)).getCountryName();  // берём текст: страна
                        String getCi = (countriesData.get(position)).getCapitalName();  // берём текст: столица
                        int getR = (countriesData.get(position)).getRatingBar();        // берём id: рейтинг

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
                        // передаём данные с помощью интента
                        // окуда и куда передаём
                        Intent intent = new Intent(ListActivity.this, DescriptionActivity.class);
                        intent.putExtra("getFlag", getF);
                        intent.putExtra("getCountry", getCo);
                        intent.putExtra("getCapital", getCi);
                        intent.putExtra("getRating", getR);
                        intent.putExtra("getDescr", getD);
                        intent.putExtra("Notification", true);
                        startActivity(intent);
                    }

                    // ПРОДОЛЖИТЕЛЬНОЕ НАЖАТИЕ на строку в ListView (item) - удаление строки
                    @Override public boolean onLongItemClick(View view, final int position) {
                        String getCo = (countriesData.get(position)).getCountryName();  // берём текст: страна
                        // добавляем AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                        builder.setTitle(R.string.delete)
                                .setIcon(R.drawable.zz_alert)
                                .setMessage(getResources().getString(R.string.alert1) + " " + getCo + "?")
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.cancel();
                                        // удаляем выбранную позицию
                                        countriesData.remove(position);
                                        // уведомляем, что данные изменились
                                        mCustomRVAdapter.setCountries(countriesData);
                                        mCustomRVAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ListActivity.this, R.string.no2, LENGTH_SHORT).show();
                                    }
                                })
                                .setCancelable(false)
                                .create()
                                .show();
                        return true;
                    }
                })
        );
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
                    // заново наполняем адаптер списком
                    mCustomRVAdapter.setCountries(countriesData);
                    // уведомляем, что данные изменились
                    mCustomRVAdapter.notifyDataSetChanged();
                    // после добавления нового пункта проматываем RecyclerView в самый конец
                    mRecyclerView.scrollToPosition(mCustomRVAdapter.getItemCount()-1);

                }else {
                    Toast.makeText(getApplicationContext(), R.string.toast3, LENGTH_SHORT).show();
                }
        }
    }
}