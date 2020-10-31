package personaje;

import personaje.atributos.Tipo;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Especie {
	String nombre;
	String descripcion;
	Tipo[] tipos = new Tipo[2];

	

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

}
