package com.hivedi.devconfig;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;

public class Dev {

	private static File CONFIG_FILE;
	private static String CONFIG_KEY;
	private static long CONFIG_MAX_FILE_SIZE = 20 * 1024;

	public static void init(@NonNull File configFile, @NonNull String configKey) {
		CONFIG_FILE = configFile;
		CONFIG_KEY = configKey;
	}

	public static void setMaxFileReadSize(long size) {
		CONFIG_MAX_FILE_SIZE = size;
	}

	@Nullable
	public static <T extends BaseDevConfig> T load(Class<T> className) {
		T res = null;
		try {
			res = className.newInstance();

			if (CONFIG_FILE.exists()) {
				try {
					String configStr = "";
					FileInputStream fis = new FileInputStream(CONFIG_FILE);
					byte[] buff = new byte[8*1024];
					int read, total = 0;
					while ( (read = fis.read(buff)) > 0) {
						configStr += new String(buff, 0, read);
						total += read;

						if (total > CONFIG_MAX_FILE_SIZE) {
							// not need read rest file - skip
							break;
						}
					}
					fis.close();

					if (configStr.length() > 0) {
						String[] cSplit = configStr.split(";");
						if (cSplit[0].equals(CONFIG_KEY)) {
							if (cSplit.length > 1) {
								String configLine = cSplit[1];
								int boolConfigs = res.getBoolConfigCount();
								if (boolConfigs > 0) {
									for(int i=0; i<boolConfigs; i++) {
										res.addConfig(getConfigValue(configLine, i, res.getDefaultValue(i)));
									}
								}
							}

							if (cSplit.length > 2) {
								for(int i=2; i<cSplit.length; i++) {
									res.addConfig(cSplit[i]);
								}
							}
						}
					}

				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
			} else {
				throw new RuntimeException("Config file not exists: " + CONFIG_FILE);
			}

		} catch (InstantiationException ignore) {
			res = null;
			ignore.printStackTrace();
		} catch (IllegalAccessException ignore) {
			ignore.printStackTrace();
		}

		return res;
	}

	private static boolean getConfigValue(String configLine, int index, boolean defaultValue) {
		try {
			return configLine.charAt(index) != '0';
		} catch (IndexOutOfBoundsException ignore) {
			return defaultValue;
		}
	}

}
