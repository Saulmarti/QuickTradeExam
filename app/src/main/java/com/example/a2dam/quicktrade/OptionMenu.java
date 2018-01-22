package com.example.a2dam.quicktrade;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by paco on 04/01/2018.
 */

public class OptionMenu extends Activity {

    int Request_ValueProd=0, Request_ValueUsu=1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

}
