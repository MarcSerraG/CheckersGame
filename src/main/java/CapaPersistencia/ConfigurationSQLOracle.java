package CapaPersistencia;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationSQLOracle {
	private Properties propietats;
	private static ConfigurationSQLOracle instancia;

	private ConfigurationSQLOracle() {

		propietats = new Properties();
		try {

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("configuration.properties");

			propietats.load(inputStream);

		} catch (IOException e) {
			System.out.println("ConfigurationSQLOracle: " + e.getMessage());
			System.exit(0);
		}
	}

	public static ConfigurationSQLOracle getInstancia() {
		if (instancia == null) {
			instancia = new ConfigurationSQLOracle();
		}
		return instancia;
	}

	public String getPropietat(String clau) {
		return propietats.getProperty(clau);
	}
}
