package personaje;

import personaje.atributos.Tipo;

public abstract class Especie {
	String nombre;
	String descripcion;
	Tipo[] tipos = new Tipo[2];

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * devuelve los tipos del personaje
	 * 
	 * @return
	 */
	public Tipo[] getTipos() {
		return tipos;
	}

	/**
	 * Establece los tipos del personaje
	 * 
	 * @param tipos
	 */
	public void setTipos(Tipo[] tipos) {
		this.tipos = tipos;
	}

}
