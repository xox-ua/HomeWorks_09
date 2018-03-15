package com.example.xox_ua.homeworks_09.data;

import com.example.xox_ua.homeworks_09.Country;
import com.example.xox_ua.homeworks_09.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RepositoryMockCountries implements IRepositoryMock<Country> {

    @Override
    public List<Country> fetchMocks() {
        List<Country> contries = new ArrayList<>();
        String[] countryNames = { "Андорра", "Австрия", "Бельгия", "Кипр", "Чехия", "Германия", "Дания",
                "Испания", "Эстония", "Финляндия", "Франция", "Великобритания", "Греция", "Хорватия",
                "Венгрия", "Ирландия", "Италия", "Литва", "Люксембург", "Латвия", "Монако",
                "Мальта", "Нидерланды", "Польша", "Португалия", "Румыния", "Сан-Марино", "Словакия",
                "Словения", "Украина", "Ватикан" };

        String[] capitalNames = { "Андорра-ла-Велья ", "Вена", "Брюссель", "Никосия", "Прага", "Берлин", "Копенгаген",
                "Мадрид", "Таллин", "Хельсинки", "Париж", "Лондон", "Афины", "Загреб",
                "Будапешт", "Дублин", "Рим", "Вильнюс", "Люксембург", "Рига", "Монако",
                "Валлетта", "Амстердам", "Варшава", "Лиссабон", "Бухарест", "Сан-Марино", "Братислава",
                "Любляна", "Киев", "Ватикан" };

        int[] flags = { R.drawable.zz_flg_and, R.drawable.zz_flg_aut, R.drawable.zz_flg_bel, R.drawable.zz_flg_cyp,
                R.drawable.zz_flg_cze, R.drawable.zz_flg_deu, R.drawable.zz_flg_dnk, R.drawable.zz_flg_esp, R.drawable.zz_flg_est,
                R.drawable.zz_flg_fin, R.drawable.zz_flg_fra, R.drawable.zz_flg_gbr, R.drawable.zz_flg_grc, R.drawable.zz_flg_hrv,
                R.drawable.zz_flg_hun, R.drawable.zz_flg_irl, R.drawable.zz_flg_ita, R.drawable.zz_flg_ltu, R.drawable.zz_flg_lux,
                R.drawable.zz_flg_lva, R.drawable.zz_flg_mco, R.drawable.zz_flg_mlt, R.drawable.zz_flg_nld, R.drawable.zz_flg_pol,
                R.drawable.zz_flg_prt, R.drawable.zz_flg_rou, R.drawable.zz_flg_smr, R.drawable.zz_flg_svk, R.drawable.zz_flg_svn,
                R.drawable.zz_flg_ukr, R.drawable.zz_flg_vat };


            // создаём массив случайных чисел для рейтинга
            Random random = new Random();
            int ratingBar[] = new int[countryNames.length];
            for (int z = 0; z < countryNames.length; z++) {
                ratingBar[z] = random.nextInt(5);
            }

            // наполняем массив данными (страны, столицы, флаги, рейтинги)
            List<Country> countries = new ArrayList<>();
            for (int i = 0; i < countryNames.length; i++)
                countries.add(new Country(countryNames[i], capitalNames[i], flags[i], ratingBar[i]));

            return countries;

    }
}