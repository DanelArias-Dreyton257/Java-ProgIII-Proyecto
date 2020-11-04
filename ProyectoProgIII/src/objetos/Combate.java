package objetos;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import personaje.Leyenda;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

/**
 * 
 * @author jon ander y danel
 *
 */
public class Combate {
	private Jugador j1;
	private Jugador j2;

	/**
	 * Constructor que recibe dos jugadores
	 * 
	 * @param j1
	 * @param j2
	 */
	public Combate(Jugador j1, Jugador j2) {
		setJ1(j1);
		setJ2(j2);
	}

	/**
	 * Constructor solo para desarrollo
	 * 
	 */
	public Combate() {
		combateDePrueba();
	}

	/**
	 * Constructor solo para desarrollo
	 * 
	 * @param usuario
	 * 
	 */
	public Combate(Jugador usuario) {
		combateDePrueba(usuario);
	}

	/**
	 * Esta clase crea unos personajes y unos jugadores
	 */
	private void combateDePrueba() {
		Jugador j1 = new Jugador("Un simple alumno");
		Jugador j2 = new Jugador("Andoni El Destructor de Mundos");
		j1.anyadirLeyendasRandom(10);
		j2.anyadirLeyendasRandom(10);
		setJ1(j1);
		setJ2(j2);
	}

	/**
	 * Esta clase crea unos personajes y unos jugadores
	 */
	private void combateDePrueba(Jugador usuario) {
		Jugador j2 = new Jugador("Andoni EL destructor");
		j2.anyadirLeyendasRandom(10);
		setJ1(usuario);
		setJ2(j2);
	}

	/**
	 * devuelve el parametro de jugador1
	 * 
	 * @return
	 */
	public Jugador getJ1() {
		return j1;
	}

	/**
	 * intoduce el parametro de jugador1
	 * 
	 * @param j1
	 */
	public void setJ1(Jugador j1) {
		this.j1 = j1;
	}

	/**
	 * devuelve el parametro de jugador2
	 * 
	 * @return
	 */
	public Jugador getJ2() {
		return j2;
	}

	/**
	 * intoduce el parametro de jugador2
	 * 
	 * @param j2
	 */
	public void setJ2(Jugador j2) {
		this.j2 = j2;
	}

	/**
	 * Clase que ordena a los personajes en funcion de la velocidad
	 * 
	 * @return
	 */
	public TreeSet<Leyenda> ordenVelocidad() {

		Comparator<Leyenda> c = new Comparator<Leyenda>() {
			@Override
			public int compare(Leyenda o1, Leyenda o2) {
				return o2.getVelocidad() - o1.getVelocidad();
			}
		};

		TreeSet<Leyenda> lista = new TreeSet<Leyenda>(c);
		for (int i = 0; i < 3; i++) {
			Leyenda eq1 = j1.getLeyendaEquipo(i);
			Leyenda eq2 = j2.getLeyendaEquipo(i);
			if (eq1 != null && !eq1.estaMuerto())
				lista.add(eq1);

			if (eq2 != null && !eq2.estaMuerto())
				lista.add(eq2);

		}
		return lista;

	}

	/**
	 * Clase que mira quien ataca a quien y hace el calculo de danio
	 * 
	 * @param j1Aj2
	 * @param iLey1
	 * @param iLey2
	 * @param iHab
	 * @return devuelve true si se ha realizado un ataque y false si se ha fallado
	 */
	public boolean leyendaAtacaLeyenda(boolean j1Aj2, int iLey1, int iLey2, int iHab) {
		// FIXME comprobar que i1 y i2 son validos
		Leyenda atacante;
		Leyenda defensor;

		if (j1Aj2) {
			atacante = j1.getLeyendaEquipo(iLey1);
			defensor = j2.getLeyendaEquipo(iLey2);
		} else {
			defensor = j1.getLeyendaEquipo(iLey1);
			atacante = j2.getLeyendaEquipo(iLey2);
		}
		if (atacante == null || defensor == null || atacante.estaMuerto() || defensor.estaMuerto()) {
			return false;
		}

		Habilidad ataque = atacante.getHabilidades()[iHab];

		int atkLey = atacante.getAtaque();
		double precHab = ataque.getPrecision();
		int potHab = ataque.getPotencia();
		Tipo[] tiposLey = defensor.getTipos();
		Tipo tipoHab = ataque.getTipo();

		// comprobar la precision
		Random r = new Random();
		double randomN = r.nextDouble();
		if (randomN > precHab) {
			return false;
		}
		// Calculo del multiplicador
		double multiplicador;
		if (tiposLey[1] == null) {
			multiplicador = tipoHab.getMultiplicadorAaD(tiposLey[0]);
		} else {
			multiplicador = (tipoHab.getMultiplicadorAaD(tiposLey[0]) + tipoHab.getMultiplicadorAaD(tiposLey[1])) / 2;
		}

		double danyo = (atkLey + potHab) * (multiplicador);

		defensor.danyar(danyo);

		return true;

	}

	/**
	 * Si es del jugador 1 devuelve su indice en el equipo Si es del jugador 2
	 * devuelve su indice en el equipo + 3 sino es ninguno, return -1
	 * 
	 * @param leyendaEnCurso
	 * @return
	 */
	public int indiceEnBatalla(Leyenda leyendaEnCurso) {

		Leyenda[] eq1 = this.j1.getEquipo(); // {l1, l2, l3, l4, l5, l6}
		Leyenda[] eq2 = this.j2.getEquipo(); // {l7, l8, l9, l10,l11,l12}

		int indice = -1;

		for (int i = 0; i < 3; i++) {

			if (leyendaEnCurso.equals(eq1[i])) {
				indice = i;

			} else if (leyendaEnCurso.equals(eq2[i])) {
				indice = i + 3;

			}
		}

		return indice;
	}

}
