package com.aumaid.bochihhott.Utils;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TextFieldHelperClass {

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
