package personaje.atributos;

import java.io.Serializable;

import interfaces.ToolTipAble;

/**
 * 
 * @author jon ander y danel
 *
 */
public class Habilidad implements ToolTipAble,Serializable {

	
	private static final long serialVersionUID = 1L;
	
	protected String nombre;
	protected String descripcion = "";
	protected Tipo tipo;
	protected int potencia;
	protected double precision;

	/**
	 * Crea un movimiento
	 * 
	 * @param nombre
	 * @param tipo
	 * @param potencia
	 * @param precision
	 */
	public Habilidad(String nombre, Tipo tipo, int potencia, double precision) {
		setNombre(nombre);
		setTipo(tipo);
		setPotencia(potencia);
		setPrecision(precision);
	}

	/**
	 * Crea un movimiento
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param tipo
	 * @param potencia
	 * @param precision
	 */
	public Habilidad(String nombre, String descripcion, Tipo tipo, int potencia, double precision) {
		setNombre(nombre);
		setDescripcion(descripcion);
		setTipo(tipo);
		setPotencia(potencia);
		setPrecision(precision);
	}

	/**
	 * Devuelve el nombre del movimiento
	 * 
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del movimiento
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripcion del movimiento
	 * 
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripcion del movimiento
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

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
		return potencia;
	}

	/**
	 * Devuelve la precision del movimiento
	 * 
	 * @return
	 */

	public double getPrecision() {
		return precision;
	}

	/**
	 * Establece la potencia del movimiento
	 * 
	 * @throws IllegalArgumentException
	 * @param potencia
	 */

	public void setPotencia(int potencia) throws IllegalArgumentException {
		if (potencia >= 0 && potencia <= 99) {
			this.potencia = potencia;
		} else
			throw new IllegalArgumentException("Introducido: " + potencia + "La potencia debe ser 0 <= potencia <= 99");
	}

	/**
	 * Establece la precision del movimiento
	 * 
	 * @throws IllegalArgumentException
	 * @param precision
	 */

	public void setPrecision(double precision) throws IllegalArgumentException {
		if (precision >= 0 && precision <= 1) {
			this.precision = precision;
		} else
			throw new IllegalArgumentException(
					"Introducido: " + precision + "La precision debe ser 0 <= precision <= 1");
	}

	/**
	 * Establece el tipo del movimiento
	 * 
	 * @param tipo
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Habilidad [nombre=" + nombre + ", descripcion=" + descripcion + ", tipo=" + tipo + ", potencia="
				+ potencia + ", precision=" + precision + "]";
	}

	@Override
	public String getToolTipInfo() {
		return tipo.toString() + ". Pot: "+potencia+", Prec: "+(precision*100)+"%";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + potencia;
		long temp;
		temp = Double.doubleToLongBits(precision);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habilidad other = (Habilidad) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (potencia != other.potencia)
			return false;
		if (Double.doubleToLongBits(precision) != Double.doubleToLongBits(other.precision))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	

}
