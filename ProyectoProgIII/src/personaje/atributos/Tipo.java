package personaje.atributos;
/**
 * 
 * @author jon ander y danel
 *
 */
public enum Tipo {
	FUEGO, AGUA, PLANTA, CIELO, TIERRA, ELECTRICIDAD, HIELO, LUZ, OSCURIDAD, TIEMPO, ESPACIO, MENTE, CUERPO, MUERTE;
	
	private static final Eficacia[][] MULTIPLICADORES_AAD = { 
			{Eficacia.NULO,			Eficacia.NO_EFICAZ,	Eficacia.MUY_EFICAZ,Eficacia.NO_EFICAZ,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.MUY_EFICAZ,	Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ}, // FUEGO
			{Eficacia.MUY_EFICAZ,	Eficacia.NULO,		Eficacia.NO_EFICAZ,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NO_EFICAZ,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO}, // AGUA
			{Eficacia.NO_EFICAZ,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,		Eficacia.NO_EFICAZ,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO}, // PLANTA
			{Eficacia.MUY_EFICAZ,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NO_EFICAZ,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO}, // CIELO
			{Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NO_EFICAZ,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO}, // TIERRA
			{Eficacia.NEUTRO,		Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NULO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO}, // ELECTRICIDAD
			{Eficacia.NO_EFICAZ,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO}, // HIELO
			{Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NULO,			Eficacia.MUY_EFICAZ,Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ}, // LUZ
			{Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.MUY_EFICAZ,	Eficacia.NULO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.NEUTRO}, // OSCURIDAD
			{Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.MUY_EFICAZ,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,	Eficacia.NO_EFICAZ,	Eficacia.NEUTRO,	Eficacia.NEUTRO}, // TIEMPO
			{Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,	Eficacia.NEUTRO,	Eficacia.NO_EFICAZ,	Eficacia.NEUTRO}, // ESPACIO
			{Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NO_EFICAZ,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO}, // MENTE
			{Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.NEUTRO}, // CUERPO
			{Eficacia.NO_EFICAZ,	Eficacia.NEUTRO,	Eficacia.MUY_EFICAZ,Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.NEUTRO,		Eficacia.NO_EFICAZ,		Eficacia.NEUTRO,	Eficacia.NEUTRO,	Eficacia.NEUTRO,		Eficacia.MUY_EFICAZ,Eficacia.MUY_EFICAZ,Eficacia.NULO}};  // MUERTE
	
	/**
	 * Devuelve la lista de efectividades del tipo contra el resto
	 * @return
	 */
	public Eficacia[] getMultiplicadoresAaD() { // Atacante a defensor
		return MULTIPLICADORES_AAD[this.ordinal()];
	}
	/**
	 * Metodo que usaremos para saber la eficacia del ataque de un atacante hacia un defensor en funcion de sus tipos 
	 * @param defensor
	 * @return
	 */
	public Eficacia getMultiplicadorAaD(Tipo defensor) {
		return MULTIPLICADORES_AAD[this.ordinal()][defensor.ordinal()];
	}
	
}
