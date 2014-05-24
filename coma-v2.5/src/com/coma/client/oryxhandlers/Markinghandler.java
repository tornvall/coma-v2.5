package com.coma.client.oryxhandlers;

import java.util.HashMap;

import com.coma.client.widgets.CallbackHandler;
import com.coma.v2.Comav200;


/**
 * 
 * @author XePOS cvba
 *
 */
public class Markinghandler implements CallbackHandler {

    @Override
    public void callBack(final HashMap<String, String> data) {
        if (data.get("action").equals("ownermarked")) {
        	Comav200.problemOwner = data.get("message");
        } else if (data.get("action").equals("locationmarked")) {
        	Comav200.problemLocation = data.get("message");
        }
    }
}

