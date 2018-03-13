package com.example.xox_ua.homeworks_09;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//определяем адаптер
// ArrayAdapter<Xxx> - Xxx тип данных, которые должен отображать адаптер
public class CountryAdapter extends ArrayAdapter<Country> {
    Context context;
    List<Country> countries = new ArrayList<>();

    // конструктор кастомного адаптера
    public CountryAdapter(@NonNull Context context, int resource, List<Country> countries) {
        super(context, resource, countries);
        this.context = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    // в методе getView описываем процесс преобразования объектов в представление
    // getView () - это метод, который возвращает фактическое представление, используемое как строка в ListView в определенной позиции.
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = null;
        // проверяем, представление (convertView) новое или повторно созданное
        // если не используется (равно null), заполняем представление (inflate View) данными
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.list_item, parent, false);
        } else {
            rootView = convertView;
        }

        // определяем вьюхи для отображения данных
        // заполняем вьюхи соответствующими объектами данных
        //ImageView ivFlg = (ImageView) rootView.findViewById(R.id.ivFlg);
        //ivFlg.setImageResource(countries.get(position).flagId);

        Picasso.get()
                .load(countries.get(position).getFlagId())
                .placeholder(R.drawable.zz_image_loading)
                .error(R.drawable.zz_image_error_loading)
                .into((ImageView) rootView.findViewById(R.id.ivFlg));

        TextView tvCountry = (TextView) rootView.findViewById(R.id.tvCountry);
        tvCountry.setText(countries.get(position).getCountryName());

        TextView tvCapital = (TextView) rootView.findViewById(R.id.tvCapital);
        tvCapital.setText(countries.get(position).getCapitalName());

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        ratingBar.setRating(countries.get(position).getRatingBar());

        // возвращаем заполненный вид (View rootView) для визуализации на экране
        return rootView;
    }
}
