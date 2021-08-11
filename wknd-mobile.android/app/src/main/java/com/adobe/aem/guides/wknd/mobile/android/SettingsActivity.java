/*
 * Copyright 2019 Adobe. All rights reserved.
 * This file is licensed to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.adobe.aem.guides.wknd.mobile.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.adobe.aem.guides.wknd.mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.commons.lang3.StringUtils;

public class SettingsActivity extends AppCompatActivity {

    private static final String AEM_UNSTRUCTURED_PATH = "/content/experience-fragments/wknd/language-masters/en/featured/camping-western-australia/master.html";
    private String AEM_DEFAULT_HOST = "http://localhost:4503";
    private String AEM_ANDROID_EMULATOR_LOCALHOST = "http://localhost";
    private String AEM_ANDROID_EMULATOR_LOCALHOST_PROXY = "http://10.0.2.2";

    private SharedPreferences sharedPreferences;
    private WebView webView;
    private SettingsActivity that;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that = this;
        setContentView(R.layout.settings_activity);
        navigation = findViewById(R.id.navigation);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String aemUrl = getAemUrl();
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(aemUrl);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigation_unstructured:
                    intent = new Intent(that, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_structured:
                    intent = new Intent(that, MainActivity.class);
                    startActivity(intent);
                    return true;
            }

            return false;
        }
    };

    private String getAemUrl() {
        return getAemHost() + getAemPath();
    }

    private String getAemHost() {
        String host = sharedPreferences.getString("host", AEM_DEFAULT_HOST);

        if (StringUtils.startsWith(host, AEM_ANDROID_EMULATOR_LOCALHOST)) {
            host = StringUtils.replaceOnce(host, AEM_ANDROID_EMULATOR_LOCALHOST, AEM_ANDROID_EMULATOR_LOCALHOST_PROXY);
        }

        return host;
    }

    private String getAemPath() {
        return AEM_UNSTRUCTURED_PATH;
    }

}