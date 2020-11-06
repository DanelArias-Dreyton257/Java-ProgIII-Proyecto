package personaje.atributos;

public enum Eficacia {
	NULO, NO_MUY_EFICAZ, NO_EFICAZ, NEUTRO, EFICAZ, MUY_EFICAZ;

	/**
	 * Devuelve el valor de la eficacia
	 * 
	 * @return
	 */
	public double getValor() {
		if (this == NULO)
			return 0;
		else if (this == NO_MUY_EFICAZ)
			return 0.25;
		else if (this == NO_EFICAZ)
			return 0.5;
		else if (this == NEUTRO)
			return 1;
		else if (this == EFICAZ)
			return 1.5;
		else
			return 2;
	}

	/**
	 * Devuelve el texto que indicara al jugador la eficacia de la habilidad
	 * 
	 * @return
	 */
	public String getTextoEficacia() {
		if (this == NULO)
			return "No fue efectivo";
		else if (this == NO_MUY_EFICAZ)
			return "Fue muy poco eficaz";
		else if (this == NO_EFICAZ)
			return "Fue poco eficaz";
		else if (this == NEUTRO)
			return "Fue efectivo";
		else if (this == EFICAZ)
			return "Fue muy efectivo";
		else
			return "Fue super efectivo";
	}

	/**
	 * Devuelve la eficacia, que sera la media de las dos eficacias a sus tipos
	 * correspondientes
	 * 
	 * @param otra
	 * @return
	 */
	public Eficacia getMediaEficacia(Eficacia otra) {
		double valor = (this.getValor() + otra.getValor()) / 2;

		double minDif = Math
				.abs(Eficacia.values()[Eficacia.values().length - 1].getValor() - Eficacia.values()[0].getValor()) + 1;
		Eficacia ret = null;
		
		for (Eficacia e : Eficacia.values()) {
			double dif = Math.abs(e.getValor() - valor);
			if (minDif > dif) {
				minDif = dif;
				ret = e;
			}
		}
		return ret;
	}
}
