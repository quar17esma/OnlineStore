package com.serhii.shutyi.controller.action;

import com.serhii.shutyi.controller.action.impl.*;


/**
 * Possible Commands.
 */
public enum ActionEnum {
    LOGIN {
        {
            this.command = new Login();
        }
    },
    LOGOUT {
        {
            this.command = new Logout();
        }
    },
    REGISTRATION {
        {
            this.command = new Registration();
        }
    };

    Action command;

    public Action getCurrentCommand() {
        return command;
    }
}
