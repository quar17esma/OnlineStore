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
    },
    BUY_NOW {
        {
            this.command = new BuyNow();
        }
    },
    ADD_TO_ORDER {
        {
            this.command = new AddToOrder();
        }
    },
    MY_ORDER {
        {
            this.command = new MyOrder();
        }
    },
    SEND_ORDER {
        {
            this.command = new SendOrder();
        }
    };

    Action command;

    public Action getCurrentCommand() {
        return command;
    }
}
