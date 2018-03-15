package com.example.xox_ua.homeworks_09.data;

import com.example.xox_ua.homeworks_09.Country;
import java.util.List;

public class DataManager {
    private RepositoryMockCountries mRepositoryMockCountries;

    public DataManager() {
        mRepositoryMockCountries = new RepositoryMockCountries();
    }

    public List<Country> fetchMocks() {
        return mRepositoryMockCountries.fetchMocks();
    }
}