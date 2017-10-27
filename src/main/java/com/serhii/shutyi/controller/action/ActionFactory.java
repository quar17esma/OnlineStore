package com.serhii.shutyi.controller.action;

import com.serhii.shutyi.controller.action.impl.Empty;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

    public Action defineAction(HttpServletRequest request) {
        Action currentAction = new Empty();

        String action = request.getRequestURI().replaceAll(".*/rest/", "");
        if (action == null || action.isEmpty()) {
            return currentAction;
        }

        ActionEnum currentEnum = ActionEnum.valueOf(action.toUpperCase());
        currentAction = currentEnum.getCurrentCommand();
        
        return currentAction;
    }
}
