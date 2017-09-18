package com.serhii.shutyi.controller.action;

import com.serhii.shutyi.controller.action.impl.Empty;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    /**
     * Method defines concrete action to execute from request parameter.
     *
     * @param request HttpServletRequest to get action parameter
     * @return Command
     */
    public Action defineAction(HttpServletRequest request) {
        Action currentAction = new Empty();

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            return currentAction;
        }

        ActionEnum currentEnum = ActionEnum.valueOf(action.toUpperCase());
        currentAction = currentEnum.getCurrentCommand();

        return currentAction;
    }
}
