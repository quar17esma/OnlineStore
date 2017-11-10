package com.serhii.shutyi.service;

import com.serhii.shutyi.entity.Client;

public interface ILoginService {

    Client login(String login, String password);
}
