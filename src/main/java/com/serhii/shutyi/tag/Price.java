package com.serhii.shutyi.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class Price extends TagSupport {

    private int price;

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
//            int integerPart = price / 100;
//            int fractionalPart = price % 100;
            String priceString = String.valueOf(price);
            String integerPart = priceString.substring(0, priceString.length()-2);
            String fractionalPart = priceString.substring(priceString.length()-2);

            pageContext.getOut().write(integerPart + "," + fractionalPart);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
