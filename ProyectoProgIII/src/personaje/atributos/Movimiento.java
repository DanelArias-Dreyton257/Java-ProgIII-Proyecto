package personaje.atributos;

public class Movimiento {
	Tipo tipo;
	int potencia;
	int precision;

	/**
	 * Devuelve la potencia del movimiento
	 * 
	 * @return
	 */
	public int getPotencia() {
		return potencia;
	}

	/**
	 * Devuelve la precision del movimiento
	 * 
	 * @return
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * Introduce la potencia del movimiento
	 * 
	 * @return
	 */
	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	/**
	 * Introduce la precision del movimiento
	 * 
	 * @return
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}

}
