package personaje.atributos;

public enum Tipo {
	FUEGO, AGUA, PLANTA, CIELO, TIERRA, ELECTRICIDAD, HIELO, LUZ, OSCURIDAD, TIEMPO, ESPACIO, MENTE, CUERPO, MUERTE;

	private static final double NEUTRO = 1;
	private static final double EFICAZ = 2;
	private static final double NO_EFICAZ = 0.5;
	private static final double SUPER_EFICAZ = 3;

	private static final double[][] MULTIPLICADORES_AAD = { {}, // FUEGO
			{}, // AGUA
			{}, // PLANTA
			{}, // CIELO
			{}, // TIERRA
			{}, // ELECTRICIDAD
			{}, // HIELO
			{}, // LUZ
			{}, // OSCURIDAD
			{}, // TIEMPO
			{}, // ESPACIO
			{}, // MENTE
			{}, // CUERPO
			{} };// MUERTE

	public double[] getMultiplicadoresAaD() { // Atacante a defensor
		return MULTIPLICADORES_AAD[this.ordinal()];
	}

	public double getMultiplicadorAaD(Tipo defensor) {
		return MULTIPLICADORES_AAD[this.ordinal()][defensor.ordinal()];
	}
}
