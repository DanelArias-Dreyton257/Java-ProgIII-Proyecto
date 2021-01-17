package gestion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

public class GestorConfiguracion {

	private static HashMap<String, Object> mapa = new HashMap<String, Object>();

	private static final File propFile = new File(".properties");

	public static final String PATH_SAVES = "path-saves";// "src/saves/Jugadores.dat" Gestor de datos
	public static final String PATH_DB = "path-db"; // "BaseDatos.db" Gestor de datos
	public static final String INC_DIF_CONTRINCANTE = "inc-dif-contrincante"; // 0.05 Combate
	public static final String MULT_GEN_NUM_LEY_CONT = "mult-gen-num-ley-cont"; // 10 Contrincante
	public static final String MULT_GEN_DOB_CONT = "mult-gen-dob-cont"; // 7.5 Contrincante
	public static final String RATIO_INC_DIF = "ratio-inc-dif"; // 0.8 Jugador
	public static final String MIN_VIDA = "min-vida"; // 150 Leyenda
	public static final String MIN_ATAQUE = "min-ataque"; // 25 Leyenda
	public static final String MIN_DEFENSA = "min-defensa"; // 25 Leyenda
	public static final String MIN_VELOCIDAD = "min-velocidad"; // 25 Leyenda
	public static final String EXTRA_PTS_FUSION = "extra-pts-fusion"; // 5 Leyenda
	public static final String MAX_DIF_LEY_RANDOM = "max-dif-ley-random"; // 0.75 Leyenda
	public static final String COSTE_CONTRATO = "coste-contrato"; // 500 VenValhalla
	public static final String INIT_DOB = "init-dob"; // 2000 Jugador

	private static final String[] LISTA_STRINGS = { "path-saves", "path-db", "inc-dif-contrincante",
			"mult-gen-num-ley-cont", "mult-gen-dob-cont", "ratio-inc-dif", "min-vida", "min-ataque", "min-defensa",
			"min-velocidad", "extra-pts-fusion", "max-dif-ley-random", "coste-contrato", "init-dob" };

	private static final Object[] LISTA_DEFAULT = { "src/saves/Jugadores.dat", "BaseDatos.db", 0.05, 10, 7.5, 0.8, 150,
			25, 25, 25, 5, 0.75, 500, 2000 };

	static {
		if (propFile.exists()) {
			leerProperties();
		} else {
			initDefaultMapa();
			updateProperties();
		}
	}

	private static void initDefaultMapa() {
		for (int i = 0; i < LISTA_STRINGS.length; i++) {
			mapa.put(LISTA_STRINGS[i], LISTA_DEFAULT[i]);
		}
	}

	public static void leerProperties() {

		try (InputStream input = new FileInputStream(propFile)) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			for (String s : LISTA_STRINGS) {
				mapa.put(s, prop.get(s));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void updateProperties() {
		try (OutputStream output = new FileOutputStream(propFile)) {

			Properties prop = new Properties();

			// set the properties value
			for (String s : LISTA_STRINGS) {
				prop.setProperty(s, mapa.get(s).toString());
			}

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public static String getValue(String indicator) throws IllegalArgumentException {
		if (mapa.containsKey(indicator))
			return (String) mapa.get(indicator);
		else
			throw new IllegalArgumentException("El indicador debe ser uno de los Strings posibles");
	}

}
