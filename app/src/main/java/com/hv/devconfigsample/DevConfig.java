package com.hv.devconfigsample;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.hivedi.devconfig.BaseDevConfig;

import java.io.File;

/**
 * Created by Hivedi2 on 2015-11-24.
 *
 */
public class DevConfig extends BaseDevConfig {

	public static final int CONFIG_1 = 1;
	public static final int CONFIG_2 = 2;
	public static final int CONFIG_3 = 3;

	@Override
	public int getBoolConfigCount() {
		return 4;
	}

	@Override
	public boolean getDefaultValue(int configIndex) {
		return true;
	}

	@NonNull
	@Override
	public File getConfigFile() {
		return new File(Environment.getExternalStorageDirectory(), "conf");
	}

	@NonNull
	@Override
	public String getConfigKey() {
		return "sample hash";
	}

}
