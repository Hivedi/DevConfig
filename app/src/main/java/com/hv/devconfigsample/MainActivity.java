package com.hv.devconfigsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hivedi.devconfig.Dev;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DevConfig dev = Dev.load(new DevConfig());
		if (dev.isConfigValid()) {
			Log.i("tests", "CONFIG=" + dev.toString());
			Log.i("tests", "Config1=" + dev.getBoolConfig(DevConfig.CONFIG_1));
			Log.i("tests", "Config2=" + dev.getBoolConfig(DevConfig.CONFIG_2));
			Log.i("tests", "Config3=" + dev.getBoolConfig(DevConfig.CONFIG_3));
		} else {
			Log.w("tests", "Config is invalid");
		}
	}
}
