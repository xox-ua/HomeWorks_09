package com.example.xox_ua.homeworks_09;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// определение модели адаптера
// описываем как представлять объект внутри ListView
// класс обязательно должен быть public, т.к. к нему нужен доступ из ListActivity

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private @Getter @Setter String countryName;     // сохраняем название страны
    private @Getter @Setter String capitalName;     // сохраняем название столицы
    private @Getter @Setter int flagId;             // сохраняем id флага
    private @Getter @Setter int ratingBar;          // сохраняем цифровое значение рейтинга

    // эту хрень Lombok убрал -=- в CountryAdapter добавил к идентификаторам get (было flagId => стало getFlagId() )
//    // конструктор, используемый для создания экземпляра объекта Country
//    public Country(String countryName, String capitalName, int flagId, int ratingBar) {
//        this.countryName = countryName;
//        this.capitalName = capitalName;
//        this.flagId = flagId;
//        this.ratingBar = ratingBar;

}
