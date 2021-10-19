package com.aumaid.bochihhott.Utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TextFieldHelperClass {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Check field if empty
     */
    public static boolean emptyField(String field) {
        return field.matches("");
    }

    /**
     * These methods will be used to clear*/
    public static boolean clearField(EditText textField){
        textField.setText("");
        return true;
    }

    public static boolean clearField(EditText textField1,EditText textField2){
        textField1.setText("");
        textField2.setText("");
        return true;
    }

    public static boolean clearField(EditText textField1,EditText textField2,EditText textField3){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        return true;
    }

    public static boolean clearField(EditText textField1,EditText textField2,EditText textField3,EditText textField4){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        return true;
    }

}
