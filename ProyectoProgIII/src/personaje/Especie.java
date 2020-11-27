package personaje;

import java.io.Serializable;
import java.util.Arrays;

import personaje.atributos.Tipo;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Especie implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String nombre;
	String descripcion;
	Tipo[] tipos = new Tipo[2];

	/**
	 * Constructor de especie
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param tipos
	 */
	public Especie(String nombre, String descripcion, Tipo[] tipos) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipos = tipos;
	}

	/**
	 * Devuelve el nombre de la especie
	 * 
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de la especie
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripcion de al especie
	 * 
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripcion de la especie
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve los tipos de la especie
	 * 
	 * @return
	 */
	public Tipo[] getTipos() {
		return tipos;
	}

	/**
	 * Establece los tipos de la especie
	 * 
	 * @param tipos
	 */
	public void setTipos(Tipo[] tipos) {
		this.tipos = tipos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + Arrays.hashCode(tipos);
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
		Especie other = (Especie) obj;
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
		if (!Arrays.equals(tipos, other.tipos))
			return false;
		return true;
	}
	
	

}
