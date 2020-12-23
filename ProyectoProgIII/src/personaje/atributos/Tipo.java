package personaje.atributos;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * @author jon ander y danel
 *
 */
public enum Tipo implements Serializable {
	FUEGO, AGUA, PLANTA, CIELO, TIERRA, ELECTRICIDAD, HIELO, LUZ, OSCURIDAD, TIEMPO, ESPACIO, MENTE, CUERPO, MUERTE;

	private static final Color[] COLORES = {Color.RED, Color.BLUE,new Color(0,255,50), new Color(180, 220, 255), new Color(60,40,10), Color.YELLOW, Color.CYAN,Color.WHITE,Color.BLACK,Color.LIGHT_GRAY,new Color(150,0,255),Color.MAGENTA, new Color(200,150,100),Color.DARK_GRAY};

	private static final Eficacia[][] MULTIPLICADORES_AAD = {
			{ Eficacia.NULO, Eficacia.NO_EFICAZ, Eficacia.MUY_EFICAZ, Eficacia.NO_EFICAZ, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ }, // FUEGO
			{ Eficacia.MUY_EFICAZ, Eficacia.NULO, Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO }, // AGUA
			{ Eficacia.NO_EFICAZ, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ,
					Eficacia.NEUTRO, Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO }, // PLANTA
			{ Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO }, // CIELO
			{ Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NO_EFICAZ, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO,
					Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO }, // TIERRA
			{ Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NULO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO }, // ELECTRICIDAD
			{ Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO }, // HIELO
			{ Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NULO, Eficacia.MUY_EFICAZ, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ }, // LUZ
			{ Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.NULO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO }, // OSCURIDAD
			{ Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ,
					Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO }, // TIEMPO
			{ Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ,
					Eficacia.NEUTRO, Eficacia.NO_EFICAZ, Eficacia.NEUTRO }, // ESPACIO
			{ Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NO_EFICAZ, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO }, // MENTE
			{ Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ,
					Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO }, // CUERPO
			{ Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.NEUTRO, Eficacia.NO_EFICAZ, Eficacia.NEUTRO, Eficacia.NEUTRO,
					Eficacia.NEUTRO, Eficacia.MUY_EFICAZ, Eficacia.MUY_EFICAZ, Eficacia.NULO } }; // MUERTE

	/**
	 * Devuelve la lista de efectividades del tipo contra el resto
	 * 
	 * @return
	 */
	public Eficacia[] getMultiplicadoresAaD() { // Atacante a defensor
		return MULTIPLICADORES_AAD[this.ordinal()];
	}

	/**
	 * Metodo que usaremos para saber la eficacia del ataque de un atacante hacia un
	 * defensor en funcion de sus tipos
	 * 
	 * @param defensor
	 * @return
	 */
	public Eficacia getMultiplicadorAaD(Tipo defensor) {
		return MULTIPLICADORES_AAD[this.ordinal()][defensor.ordinal()];
	}

	/**
	 * Metodo que devuelve el elemento del enum que coincide con su .toString() En
	 * caaso de que no coincida con ninguno devuelve null
	 * 
	 * @param nombre
	 * @return
	 */
	public static Tipo getTipoPorNombre(String nombre) {
		Tipo dev = null;
		for (Tipo t : values()) {
			if (t.toString().equals(nombre)) {
				dev = t;
				break;
			}
		}
		return dev;
	}
	/**
	 * devuelve el color del tipo
	 * @return
	 */
	public Color getColor() {
		return COLORES[this.ordinal()];
	}

}
