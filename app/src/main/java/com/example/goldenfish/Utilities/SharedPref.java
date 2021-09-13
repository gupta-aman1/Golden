package com.example.goldenfish.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.goldenfish.Constants.ConstantsValue;

public class SharedPref {

    //    String secretKeyShared;
    private static SharedPreferences mPref;
    private static SharedPref mRef;
    private SharedPreferences.Editor mEditor;

    public static SharedPref getInstance(Context context){


        if ( mRef == null) {
            try {
                mRef = new SharedPref();
                String secretKeyShared = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                mPref = EncryptedSharedPreferences.create(
                        ConstantsValue.filename,
                        secretKeyShared,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

            } catch (Exception e) {
                
            }
            return mRef;
        }
        return mRef;
    }


    /**
     * Put long value into sharedpreference
     **/
    public void putLong(String key, long value) {
        try {
            mEditor = mPref.edit();
            mEditor.putLong(key, value);
            mEditor.apply();
        } catch (Exception e) {
            
        }
    }

    /**
     * Get long value from sharedpreference
     **/
    public long getLong(String key) {
        try {
            long lvalue;
            lvalue = mPref.getLong(key, 0);
            return lvalue;
        } catch (Exception e) {
            
            return 0;
        }
    }

    /**
     * Put int value into sharedpreference
     **/
    public void putInt(String key, int value) {
        try {
            mEditor = mPref.edit();
            mEditor.putInt(key, value);
            mEditor.apply();
        } catch (Exception e) {
            
        }
    }

    /**
     * Get int value from sharedpreference
     **/
    public int getInt(String key) {
        try {
            int lvalue;
            lvalue = mPref.getInt(key, 0);
            return lvalue;
        } catch (Exception e) {
            
            return 0;
        }
    }

    /**
     * Get int value from sharedpreference
     **/
    public int getIntWithValue(String key, int value) {
        try {
            int lvalue;
            lvalue = mPref.getInt(key, value);
            return lvalue;
        } catch (Exception e) {
            
            return value;
        }
    }

    /**
     * Put String value into sharedpreference
     **/
    public void putString(String key, String value) {
        try {
            mEditor = mPref.edit();
            mEditor.putString(key, value);
            mEditor.apply();
        } catch (Exception e) {
            
        }
    }

    /**
     * Get String value from sharedpreference
     **/
    public String getString(String key, String value) {
        try {
            String lvalue;
            lvalue = mPref.getString(key, value);
            return lvalue;
        } catch (Exception e) {
            
            return value;
        }
    }

    public String getStringWithNull(String key) {
        try {
            String lvalue;
            lvalue = mPref.getString(key, null);
            return lvalue;
        } catch (Exception e) {
            
            return null ;
        }
    }

    /**
     * Put String value into sharedpreference
     **/
    public void putBoolean(String key, Boolean value) {
        try {
            mEditor = mPref.edit();
            mEditor.putBoolean(key, value);
            mEditor.apply();
        } catch (Exception e) {
            
        }
    }


    /**
     * Get String value from sharedpreference
     **/
    public Boolean getBoolean(String key) {
        try {
            Boolean lvalue;
            lvalue = mPref.getBoolean(key, false);
            return lvalue;
        } catch (Exception e) {
            
            return false;
        }
    }

    public Boolean getBooleanWithTrue(String key) {
        try {
            Boolean lvalue;
            lvalue = mPref.getBoolean(key, true);
            return lvalue;
        } catch (Exception e) {
            
            return false;
        }
    }


    public void clearData() {
        try {
            mEditor = mPref.edit();
            mEditor.clear().commit();
//            mEditor.apply();
        } catch (Exception e) {
            
        }
    }

}
