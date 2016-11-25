package com.inputdetector.test;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView text_default_input_id;
    TextView text_default_input_properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_default_input_id = (TextView) findViewById(R.id.text_default_input_id);
        text_default_input_properties = (TextView) findViewById(R.id.text_default_input_properties);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDefaultInputId();
        setDefaultInputProperties();
    }

    private void setDefaultInputId() {
        String id = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.DEFAULT_INPUT_METHOD
        );

        text_default_input_id.setText(
                "Default Input Method:\n\t" + id);
    }

    private void setDefaultInputProperties() {
        StringBuilder message = new StringBuilder("Version Info:");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> mInputMethodProperties = imm.getEnabledInputMethodList();

        final int N = mInputMethodProperties.size();

        for (int i = 0; i < N; i++) {

            InputMethodInfo imi = mInputMethodProperties.get(i);

            if (imi.getId().equals(Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.DEFAULT_INPUT_METHOD))) {

                List<PackageInfo> packages = getPackageManager()
                        .getInstalledPackages(0);

                for (PackageInfo packageInfo : packages) {

                    if (!packageInfo.packageName.equals(imi.getPackageName())) continue;

                    message.append("\n\nPackage Name : ").append(packageInfo.packageName)
                            .append("\nVersion : ").append(packageInfo.versionName);
                }

                break;
            }
        }

        text_default_input_properties.setText(message.toString());
    }


}
