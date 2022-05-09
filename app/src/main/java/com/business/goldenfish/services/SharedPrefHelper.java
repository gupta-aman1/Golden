package com.business.goldenfish.services;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.util.HashMap;
import java.util.Map;

public class SharedPrefHelper {
    private static final String PREF_FILE = "GCMSettingNew";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private static Map<Context, SharedPrefHelper> instances = new HashMap<Context, SharedPrefHelper>();

    public SharedPrefHelper(Context context)
    {
        try {
//			mRef = new SharedPref();
            String secretKeyShared = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            settings = EncryptedSharedPreferences.create(
                    PREF_FILE,
                    secretKeyShared,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            editor = settings.edit();

        } catch (Exception e) {

        }
    }
    public static SharedPrefHelper getInstance(Context context) {
        if (!instances.containsKey(context))
            instances.put(context, new SharedPrefHelper(context));
        return instances.get(context);
    }

    public String getString(String key, String defValue)
    {
        return settings.getString(key, defValue);
    }


    public SharedPrefHelper setString(String key, String value)
    {
        editor.putString(key, value);
        editor.commit();
        //System.out.print(this);
        return this;
    }
    public int getInt(String key, int defValue)
    {
        return settings.getInt(key, defValue);
    }
    public SharedPrefHelper setInt(String key, int value)
    {
        editor.putInt(key, value);
        editor.commit();

        return this;
    }


    public boolean getBoolean(String key, boolean defValue)
    {
        return settings.getBoolean(key, defValue);
    }

    public SharedPrefHelper setBoolean(String key, boolean value)
    {
        editor.putBoolean(key, value);
        editor.commit();
        return this;
    }
}
