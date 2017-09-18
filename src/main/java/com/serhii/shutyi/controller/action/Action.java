package com.serhii.shutyi.controller.action;

import javax.servlet.http.HttpServletRequest;

public interface Action {
    String execute(HttpServletRequest request);
}
