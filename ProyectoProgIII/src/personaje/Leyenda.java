package personaje;

import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import gestion.GestorConfiguracion;
import gestion.GestorDeDatos;
import interfaces.ToolTipAble;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;
import visuales.objetos.BotonEsp;

/**
 * 
 * @author danel y jon ander
 *
 */
public class Leyenda extends Especie implements ToolTipAble, Serializable {
	private static final long serialVersionUID = 1L;
	// Estado estado;
	Habilidad[] habilidades = new Habilidad[4];
	int ataque;
	int defensa;
	int velocidad;
	int vida;
	int vidaMax;

	/**
	 * Constructor que te recibe todos los datos de la leyenda
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param tipos
	 * @param habilidades
	 * @param ataque
	 * @param defensa
	 * @param velocidad
	 * @param vida
	 */
	public Leyenda(String nombre, String descripcion, Tipo[] tipos, Habilidad[] habilidades, int ataque, int defensa,
			int velocidad, int vida) {
		super(nombre, descripcion, tipos);
		setHabilidades(habilidades);
		setAtaque(ataque);
		setDefensa(defensa);
		setVelocidad(velocidad);
		setVida(vida);
		setVidaMax(vida);

	}

	/**
	 * Constructor para crear personaje desde cero
	 * 
	 * @param especie
	 */
	public Leyenda(Especie esp, double dif) {
		super(esp.getNombre(), esp.getDescripcion(), esp.getTipos());
		generarStatsRandom(dif);

	}

	/**
	 * Constructor que te recibe todos los datos de la leyenda menos las habilidades
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param tipos
	 * @param ataque
	 * @param defensa
	 * @param velocidad
	 * @param vida
	 */
	public Leyenda(String nombre, String descripcion, Tipo[] tipos, int ataque, int defensa, int velocidad, int vida) {
		super(nombre, descripcion, tipos);
		setAtaque(ataque);
		setDefensa(defensa);
		setVelocidad(velocidad);
		setVida(vida);
		setVidaMax(vida);
	}

	/**
	 * devuelve la vidaMax del personaje
	 * 
	 * @return
	 */
	public int getVidaMax() {
		return vidaMax;
	}

	/**
	 * establece la VidaMax del personaje
	 * 
	 * @param vidaMax
	 */
	public void setVidaMax(int vidaMax) {
		if (vidaMax <= 999) {
			this.vidaMax = vidaMax;
		} else
			throw new IllegalArgumentException("Introducido: " + vidaMax + "La vida debe ser vida <= 999");

	}

	/**
	 * Genera estadistica aleatorias de un personaje con un nivel de dificultad
	 * @param dif
	 */
	private void generarStatsRandom(double dif) {
		Random r = new Random();
		
		int minVida = Integer.parseInt( GestorConfiguracion.getValue(GestorConfiguracion.MIN_VIDA));
		int topVida = (int) ((999-minVida) * dif);
		int vidaN = r.nextInt(topVida + 1) + minVida;
		setVida(vidaN);
		setVidaMax(vidaN);
		
		int minAtk = Integer.parseInt( GestorConfiguracion.getValue(GestorConfiguracion.MIN_ATAQUE));
		int topAtk = (int) ((99-minAtk) * dif);
		int ataqueN = r.nextInt(topAtk + 1) + minAtk;
		setAtaque(ataqueN);
		
		int minDef = Integer.parseInt( GestorConfiguracion.getValue(GestorConfiguracion.MIN_DEFENSA));
		int topDef = (int) ((99-minDef) * dif);
		int defensaN = r.nextInt(topDef + 1) + minDef;
		setDefensa(defensaN);
		
		int minVel = Integer.parseInt( GestorConfiguracion.getValue(GestorConfiguracion.MIN_VELOCIDAD));
		int topVel = (int) ((99-minVel) * dif);
		int velocidadN = r.nextInt(topVel + 1) + minVel;
		setVelocidad(velocidadN);
		
		habilidades[0] = GestorDeDatos.buscarHabilidadEnBD(tipos[0]);
		Habilidad hOtra = GestorDeDatos.buscarHabilidadEnBD(tipos[0]);
		if (habilidades[0]!=null && !habilidades[0].equals(hOtra)) {
			habilidades[2] = hOtra;
		}
		
		if (tipos[1] != null) {
			habilidades[1] = GestorDeDatos.buscarHabilidadEnBD(tipos[1]);
			Habilidad hOtra1 = GestorDeDatos.buscarHabilidadEnBD(tipos[1]);
			if (habilidades[1]!=null && !habilidades[1].equals(hOtra1)) {
				habilidades[3] = hOtra1;
			}
		}
	}

	/**
	 * devuelve el ataque del personaje
	 * 
	 * @return
	 */
	public int getAtaque() {
		return ataque;
	}

	/**
	 * devuelve la defensa del personaje
	 * 
	 * @return
	 */
	public int getDefensa() {
		return defensa;
	}

	/**
	 * devuelve la velocidad del personaje
	 * 
	 * @return
	 */
	public int getVelocidad() {
		return velocidad;
	}

	/**
	 * devuelve la vida del personaje
	 * 
	 * @return
	 */
	public int getVida() {
		return vida;
	}

	/**
	 * devuelve las habilidades del personaje
	 * 
	 * @return
	 */
	public Habilidad[] getHabilidades() {
		return habilidades;
	}

	/**
	 * Establece el ataque del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param ataque
	 */
	public void setAtaque(int ataque) throws IllegalArgumentException {
		if (ataque >= 1 && ataque <= 99) {
			this.ataque = ataque;
		} else
			throw new IllegalArgumentException("Introducido: " + ataque + "El ataque debe ser 1 <= ataque <= 99");

	}

	/**
	 * Establece la defensa del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param defensa
	 */
	public void setDefensa(int defensa) throws IllegalArgumentException {
		if (defensa >= 1 && defensa <= 99) {
			this.defensa = defensa;
		} else
			throw new IllegalArgumentException("Introducido: " + defensa + "La defensa debe ser 1 <= defensa <= 99");
	}

	/**
	 * Establece la velocidad del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param velocidad
	 */
	public void setVelocidad(int velocidad) throws IllegalArgumentException {
		if (velocidad >= 1 && velocidad <= 99) {
			this.velocidad = velocidad;
		} else
			throw new IllegalArgumentException(
					"Introducido: " + velocidad + "La velocidad debe ser 1 <= velocidad <= 99");
	}

	/**
	 * Establece la vida del personaje
	 * 
	 * @throws IllegalArgumentException
	 * @param vida
	 */
	public void setVida(int vida) throws IllegalArgumentException {
		if (vida <= 999) {
			this.vida = vida;
		} else
			throw new IllegalArgumentException("Introducido: " + vida + "La vida debe ser vida <= 999");
	}

	/**
	 * Establece los movimientos del personaje
	 * 
	 * @param movimientos
	 */
	public void setHabilidades(Habilidad[] habilidades) {
		this.habilidades = habilidades;
	}

	/**
	 * Fusiona dos personajes y devuelve el resultado como parametro
	 * 
	 * @param p1 personaje a fusionar 1
	 * @param p2 personaje a fusionar 2
	 * @return personaje fusionado
	 */
	public static Leyenda fusion(Leyenda p1, Leyenda p2) {
		// Calculo de los atributos numericos
		int extraPts = Integer.parseInt(  GestorConfiguracion.getValue(GestorConfiguracion.EXTRA_PTS_FUSION));;
		int nAtk = ((p1.getAtaque() + p2.getAtaque()) / 2) + extraPts;
		int nDef = ((p1.getDefensa() + p2.getDefensa()) / 2) + extraPts;
		int nVel = ((p1.getVelocidad() + p2.getVelocidad()) / 2) + extraPts;
		int nVida = ((p1.getVidaMax() + p2.getVidaMax()) / 2) + extraPts * 10;

		// Seleccion de los movimientos
		ArrayList<Habilidad> posHabs = new ArrayList<>();

		posHabs.addAll(Arrays.asList(p1.getHabilidades()));
		posHabs.addAll(Arrays.asList(p2.getHabilidades()));

		int numHabs = 4;
		Habilidad[] habsElegidas = new Habilidad[numHabs];

		Collections.shuffle(posHabs);
		int cont = 0;
		for(Habilidad h:posHabs) {
			if (h!=null) {
				habsElegidas[cont] = h;
				cont++;
				if (cont>=4) {
					break;
				}
			}
		}

		Especie esp = null;
		while (esp == null) {
			// Seleccion de los tipos
			ArrayList<Tipo> posTipos = new ArrayList<>();
			posTipos.addAll(Arrays.asList(p1.getTipos()));
			posTipos.addAll(Arrays.asList(p2.getTipos()));

			Tipo tipo1 = null;
			int id = -1;
			while (tipo1 == null) {
				Random r = new Random();
				id = r.nextInt(posTipos.size());
				tipo1 = posTipos.get(id);
			}

			Tipo tipo2 = tipo1;
			while (tipo1.equals(tipo2)) {
				posTipos.remove(id); // primero elimina el tipo elegido en el primer while y luego va borrando el
										// anterior seleccionado ya que ha vuelto a ejecutar el while por lo que
										// significa que es un tipo no valido
				Random r = new Random();
				id = r.nextInt(posTipos.size());
				tipo2 = posTipos.get(id);
			}

			esp = GestorDeDatos.buscarEspecieEnBD(tipo1, tipo2);
		}

		Leyenda l = new Leyenda(esp.nombre, esp.descripcion, esp.tipos, habsElegidas, 1, 1, 1, 1);
		try {
			l.setAtaque(nAtk);
		} catch (IllegalArgumentException e) {
			l.setAtaque(99);
		}
		try {
			l.setDefensa(nDef);
		} catch (IllegalArgumentException e) {
			l.setDefensa(99);
		}
		try {
			l.setVelocidad(nVel);
		} catch (IllegalArgumentException e) {
			l.setVelocidad(99);
		}
		try {
			l.setVida(nVida);
			l.setVidaMax(nVida);
		} catch (IllegalArgumentException e) {
			l.setVida(999);
			l.setVidaMax(999);
		}

		return l;
	}

	/**
	 * Reduce la vida de la leyenda segun el danyo pasado como parametro
	 * 
	 * @param danyo
	 * @return danyo causado
	 */
	public double danyar(double danyo) {
		danyo -= this.defensa;
		if (danyo >= 0) {
			this.vida -= (int) danyo;
			return danyo;
		} else
			return 0;
	}

	/**
	 * Devuelve el String que se mostraria en combate
	 * 
	 * @return
	 */
	public String getNombreCombate() {
		String s = nombre;
		if (this.estaMuerto()) {
			s += ": MUERTO";
		} else {
			s += ": " + vida + " / " + vidaMax;
		}
		return s;
	}

	/**
	 * Devuelve true si el presonaje esta muerto
	 * 
	 * @return
	 */
	public boolean estaMuerto() {
		return vida <= 0;
	}

	/**
	 * Devuelve una leyenda aleatoria creada a traves de la seleccion aleatoria de
	 * tipos y de estadisticas entre ciertos valores
	 * @param dif 
	 * 
	 * @return
	 */
	public static Leyenda getLeyendaRandom(double dif) {
		Especie esp = null;
		ArrayList<String> nombres = GestorDeDatos.getNombresEspecies();
		java.util.Random r = new java.util.Random();
		int pos = r.nextInt(nombres.size());
		esp = GestorDeDatos.getInfoEspecie(nombres.get(pos));
		return new Leyenda(esp, dif);
	}
	
	
	/**
	 * Devuelve una leyenda random, y con un nivel de dificultad
	 * @return
	 */
	public static Leyenda getLeyendaRandom() {
		java.util.Random r = new java.util.Random();
		double dif = Double.parseDouble(  GestorConfiguracion.getValue(GestorConfiguracion.MAX_DIF_LEY_RANDOM) )* r.nextDouble() + 0.0001;
		return getLeyendaRandom(dif);
	}

	@Override
	public String getToolTipInfo() {
		String tipo2 = GestorDeDatos.NULL_STR;
		if (tipos[1] != null)
			tipo2 = tipos[1].toString();
		return tipos[0].toString() + ", " + tipo2 + ". ATK: " + ataque + ", DEF: " + defensa + ", VEL: " + velocidad;
	}

	/**
	 * Cura a la leyenda el numero indicado por parametrom, tras curar el personaje
	 * no superarara la vida maxima. Si los puntos a curar son un valor negativo no
	 * se realizara la accion.
	 * 
	 * @param puntosACurar
	 */
	public void curar(int puntosACurar) {
		if (puntosACurar >= 0) {
			int vidaTrasCurar = vida + puntosACurar;
			if (vidaTrasCurar > vidaMax)
				setVida(vidaMax);
			else
				setVida(vidaTrasCurar);
		}
	}

	/**
	 * Cura a la leyenda por completo
	 */
	public void curar() {
		curar(999);
	}

	

	/**
	 * obtienes la imagen del personaje con su nombre ya implementado
	 * 
	 * @param fuente
	 * @param scale
	 * @return
	 */
	public BotonEsp getBotonVentana(Font fuente, int scale) {
		return new BotonEsp(this, scale, fuente);
	}

	@Override
	public String toString() {
		return "Leyenda [habilidades=" + Arrays.toString(habilidades) + ", ataque=" + ataque + ", defensa=" + defensa
				+ ", velocidad=" + velocidad + ", vida=" + vida + ", vidaMax=" + vidaMax + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", tipos=" + Arrays.toString(tipos) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ataque;
		result = prime * result + defensa;
		result = prime * result + Arrays.hashCode(habilidades);
		result = prime * result + velocidad;
		result = prime * result + vida;
		result = prime * result + vidaMax;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leyenda other = (Leyenda) obj;
		if (ataque != other.ataque)
			return false;
		if (defensa != other.defensa)
			return false;
		if (!Arrays.equals(habilidades, other.habilidades))
			return false;
		if (velocidad != other.velocidad)
			return false;
		if (vida != other.vida)
			return false;
		if (vidaMax != other.vidaMax)
			return false;
		return true;
	}
	
	

}
