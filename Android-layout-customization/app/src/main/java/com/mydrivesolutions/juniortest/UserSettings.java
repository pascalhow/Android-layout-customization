package com.mydrivesolutions.juniortest;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by pascalh on 9/12/2015.
 */
public class UserSettings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }
}
