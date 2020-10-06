package personaje.atributos;

public class Movimiento {
	Tipo[] tipo;
	int Potencia;
	int Precision;

	/**
	 * Devuelve la potencia del movimiento
	 * 
	 * @return
	 */
	public int getPotencia() {
		return Potencia;
	}

	/**
	 * Devuelve la precision del movimiento
	 * 
	 * @return
	 */
	public int getPrecision() {
		return Precision;
	}

	/**
	 * Introduce la potencia del movimiento
	 * 
	 * @return
	 */
	public void setPotencia(int potencia) {
		Potencia = potencia;
	}

	/**
	 * Introduce la precision del movimiento
	 * 
	 * @return
	 */
	public void setPrecision(int precision) {
		Precision = precision;
	}

}
