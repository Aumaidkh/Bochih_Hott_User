package com.aumaid.bochihhott.Utils;

import com.aumaid.bochihhott.Models.Address;
import com.aumaid.bochihhott.Models.Addresss;

import java.util.ArrayList;

public class AddressHelper {


    public static String generateFullAddress(Addresss address){

        String full_address =
                "Near "+address.getLandmark()
                        +", "+address.getAddress()
                        +", "+address.getCity()
                        +", "+address.getPin_code();

        return full_address;

    }

  /*  public static String[] splitFullAddress(String address){
        String str = "Near Bridge, Kadapora, Anantnag, 192101";
        String[] res = str.split("[,]", 0);
        for(String myStr: res) {
            System.out.println(myStr);
        }
    }*/
}
