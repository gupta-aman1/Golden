package com.example.goldenfish.Utilities;

import com.example.goldenfish.Constants.ConstantsValue;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyUtils {

    public static String encryption(String endPint,String params,String user_id)
    {
        try {
            // SimpleDateFormat hour = new SimpleDateFormat("HH");
            //  String strHour = hour.format(new Date());
            String input="";
            if (params.equals(""))
            {
                input = endPint+"|"+ ConstantsValue.uniqueKey+"|"+user_id;
            }
            else
            {
                input = endPint+"|"+ConstantsValue.uniqueKey+"|"+user_id+"|"+params;
            }

            System.out.println("INPUT STRING "+input);
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 128) {
                hashtext = "0" + hashtext;
            }
            // return the HashText
            System.out.println("CHECKSUM "+endPint+"|"+hashtext);
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}
