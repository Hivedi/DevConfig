package com.hivedi.devconfig;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;

public abstract class BaseDevConfig {

	private ArrayList<Boolean> configs = new ArrayList<>();
	private ArrayList<String> customConfigs = new ArrayList<>();

	public abstract int getBoolConfigCount();
	public abstract boolean getDefaultValue(int configIndex);
	public abstract @NonNull File getConfigFile();
	public abstract @NonNull String getConfigKey();
	public long getConfigMaxFileRead() {
		return 20 * 1024;
	}

	public void addConfig(boolean value) {
		configs.add(value);
	}

	public boolean getBoolConfig(int idx) {
		try {
			return configs.get(idx);
		} catch (IndexOutOfBoundsException e) {
			return getDefaultValue(idx);
		}
	}

	public void addConfig(String value) {
		customConfigs.add(value);
	}

	public String getCustomConfig(int idx) {
		return customConfigs.get(idx);
	}

	@Override
	public String toString() {
		String res = "";

		for(int i=0; i<configs.size(); i++) {
			res += "CONFIG[" + i + "]=" + configs.get(i) + ", ";
		}

		for(int i=0; i<customConfigs.size(); i++) {
			res += "CONFIG_STR[" + i + "]=" + customConfigs.get(i) + ", ";
		}

		return res.length() > 0 ? res.substring(0, res.length() - 2) : res;
	}
}
