package com.hivedi.devconfig;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.FileInputStream;

public class Dev {

	//private static File CONFIG_FILE;
	//private static String CONFIG_KEY;
	//private static long CONFIG_MAX_FILE_SIZE = 20 * 1024;
	//private static BaseDevConfig mBaseDevConfig;

	@Nullable
	public static <T extends BaseDevConfig> T load(@NonNull BaseDevConfig config) {
		BaseDevConfig mBaseDevConfig = config;
		if (mBaseDevConfig.getConfigFile().exists()) {
			try {

				String configStr = "";
				FileInputStream fis = new FileInputStream(mBaseDevConfig.getConfigFile());
				byte[] buff = new byte[8*1024];
				int read, total = 0;
				while ( (read = fis.read(buff)) > 0) {
					configStr += new String(buff, 0, read);
					total += read;

					if (total > mBaseDevConfig.getConfigMaxFileRead()) {
						// not need read rest file - skip
						break;
					}
				}
				fis.close();

				if (configStr.length() > 0) {
					String[] cSplit = configStr.split(";");
					if (cSplit[0].equals(mBaseDevConfig.getConfigKey())) {
						if (cSplit.length > 1) {
							String configLine = cSplit[1];
							int boolConfigs = mBaseDevConfig.getBoolConfigCount();
							if (boolConfigs > 0) {
								for(int i=0; i<boolConfigs; i++) {
									mBaseDevConfig.addConfig(getConfigValue(configLine, i, mBaseDevConfig.getDefaultValue(i)));
								}
							}
						}

						if (cSplit.length > 2) {
							for(int i=2; i<cSplit.length; i++) {
								mBaseDevConfig.addConfig(cSplit[i]);
							}
						}
					}
				}

			} catch (Exception ignore) {

			}
		}

		//noinspection unchecked
		return (T) mBaseDevConfig;
	}

	private static boolean getConfigValue(String configLine, int index, boolean defaultValue) {
		try {
			return configLine.charAt(index) != '0';
		} catch (IndexOutOfBoundsException ignore) {
			return defaultValue;
		}
	}

}
