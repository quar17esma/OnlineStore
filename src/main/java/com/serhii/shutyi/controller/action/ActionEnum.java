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
    },
    SHOW_GOODS {
        {
            this.command = new ShowGoods();
        }
    },
    DELETE_GOOD {
        {
            this.command = new DeleteGood();
        }
    },
    EDIT_GOOD {
        {
            this.command = new EditGood();
        }
    },
    ADD_GOOD {
        {
            this.command = new AddGood();
        }
    };

    Action command;

    public Action getCurrentCommand() {
        return command;
    }
}
