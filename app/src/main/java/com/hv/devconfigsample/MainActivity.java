package com.hv.devconfigsample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hivedi.devconfig.Dev;

import java.io.File;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Dev.init(new File(Environment.getExternalStorageDirectory(), "conf"), "sample hash");
		DevConfig dev = Dev.load(DevConfig.class);
		if (dev != null) {
			Log.i("tests", "CONFIG=" + dev.toString());
			Log.i("tests", "Config1=" + dev.getBoolConfig(DevConfig.CONFIG_1));
			Log.i("tests", "Config2=" + dev.getBoolConfig(DevConfig.CONFIG_2));
			Log.i("tests", "Config3=" + dev.getBoolConfig(DevConfig.CONFIG_3));
		}
	}
}