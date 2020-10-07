package personaje.atributos;

public class Movimiento {
 Tipo tipo;
 int Potencia;
 int Precision;
 
 /**
	 * Devuelve el tipo del movimiento
	 * 
	 * @return
	 */
 public Tipo getTipo() {
	return tipo;
}
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
	 * Establece la potencia  del movimiento
	 * 
	 * @param potencia
	 */
 public void setPotencia(int potencia) {
	Potencia = potencia;
}
 /**
	 * Establece la precision  del movimiento
	 * 
	 * @param precision
	 */
 public void setPrecision(int precision) {
	Precision = precision;
}
 
 /**
	 * Establece el tipo del movimiento
	 * 
	 * @param tipo
	 */
 public void setTipo(Tipo tipo) {
	this.tipo = tipo;
}
 
 
}
