package com.example.xox_ua.homeworks_09.Data;

import java.util.List;

public interface IRepositoryMock<T> {
    List<T> fetchMocks();
}