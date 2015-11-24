package com.hivedi.devconfig;

import android.support.annotation.NonNull;

import java.io.FileInputStream;

public class Dev {

	@NonNull
	public static <T extends BaseDevConfig> T load(@NonNull BaseDevConfig config) {
		if (config.getConfigFile().exists()) {
			try {
				// read file
				String configStr = "";
				FileInputStream fis = new FileInputStream(config.getConfigFile());
				byte[] buff = new byte[8*1024];
				int read, total = 0;
				while ( (read = fis.read(buff)) > 0) {
					configStr += new String(buff, 0, read);
					total += read;

					if (total > config.getConfigMaxFileRead()) {
						// not need read rest file - skip
						break;
					}
				}
				fis.close();

				// parse file string
				if (configStr.length() > 0) {
					String[] cSplit = configStr.split(";");
					if (cSplit[0].equals(config.getConfigKey())) {
						if (cSplit.length > 1) {
							String configLine = cSplit[1];
							int boolConfigs = config.getBoolConfigCount();
							if (boolConfigs > 0) {
								for(int i=0; i<boolConfigs; i++) {
									config.addConfig(getConfigValue(configLine, i, config.getDefaultValue(i)));
								}
							}
						}

						if (cSplit.length > 2) {
							for(int i=2; i<cSplit.length; i++) {
								config.addConfig(cSplit[i]);
							}
						}
					}
				}

			} catch (Exception ignore) {

			}
		}

		//noinspection unchecked
		return (T) config;
	}

	private static boolean getConfigValue(String configLine, int index, boolean defaultValue) {
		try {
			return configLine.charAt(index) != '0';
		} catch (IndexOutOfBoundsException ignore) {
			return defaultValue;
		}
	}

}
