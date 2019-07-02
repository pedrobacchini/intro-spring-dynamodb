package com.github.pedrobacchini.introspringdynamodb.repository.user;

import java.io.IOException;

public interface CustomUserRepository {

    void createTable();

    void loadData() throws IOException;
}
