package com.lollipop50.croaker.repository;

import android.content.Context;

import com.lollipop50.croaker.repository.test.TestRepository;

public class RepositoryCreator {

    private static Repository INSTANCE;

    public static Repository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TestRepository();
        }

        return INSTANCE;
    }
}
