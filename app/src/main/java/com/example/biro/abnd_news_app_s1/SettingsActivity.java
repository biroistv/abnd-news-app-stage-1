package com.example.biro.abnd_news_app_s1;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference newsNumber = findPreference(getString(R.string.settings_news_number_key));
            bindPreferenceSummaryToValue(newsNumber);
        }

        private void bindPreferenceSummaryToValue(Preference newsNumber) {
            newsNumber.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newsNumber.getContext());
            String preferenceString = preferences.getString(newsNumber.getKey(), "");
            onPreferenceChange(newsNumber, preferenceString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            preference.setSummary(stringValue);
            return true;
        }
    }
}
