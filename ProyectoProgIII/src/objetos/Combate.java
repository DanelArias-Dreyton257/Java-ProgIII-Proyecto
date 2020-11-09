package objetos;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import personaje.Leyenda;
import personaje.atributos.Eficacia;
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
				int v1 = o1.getVelocidad();
				int v2 = o2.getVelocidad();
				// Si tienen la misma velocidad se decide aleatoriamente quien va primero
				if (v1 == v2) {
					Random r = new Random();
					boolean b = r.nextBoolean();
					if (b)
						v1++;
					else
						v2++;
				}
				return v2 - v1;
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
	 * @return devuelve un String que informa de lo ocurrido
	 */
	public String leyendaAtacaLeyenda(boolean j1Aj2, int iLey1, int iLey2, int iHab) {
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
		if (atacante == null || atacante.estaMuerto()) {
			return atacante.getNombre() + " esta muerto, no puede atacar";
		} else if (defensor == null) {
			return atacante.getNombre() + " ataco al aire";
		} else if (defensor.estaMuerto()) {
			return atacante.getNombre() + " dejalo, " + defensor.getNombre() + " YA ESTA MUERTO...";
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
			return atacante.getNombre() + " fallo " + ataque.getNombre() + " por no ser preciso";
		}
		// Calculo del multiplicador
		Eficacia eficacia;
		if (tiposLey[1] == null) {
			eficacia = tipoHab.getMultiplicadorAaD(tiposLey[0]);
		} else {
			eficacia = tipoHab.getMultiplicadorAaD(tiposLey[0])
					.getMediaEficacia(tipoHab.getMultiplicadorAaD(tiposLey[1]));
		}

		double danyo = (atkLey + potHab) * (eficacia.getValor());

		danyo = defensor.danyar(danyo);

		return atacante.getNombre() + " hizo " + ataque.getNombre() + " a " + defensor.getNombre() + ". "
				+ eficacia.getTextoEficacia() + " causando " + (int) danyo + " de danyo";

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

	/**
	 * Comprueba si los integrantes del equipo de cada jugador estan vivos, si un
	 * jugador tiene a todos los integrantes muertos mientras que el otro jugador
	 * tiene al menos uno vivo, se considera que este ultimo ha ganado la partida.
	 * 
	 * @return devuelve -1 si ningun jugador ha ganado, 0 si ha ganado el Jugador 1
	 *         del combate y 1 si ha ganado el Jugador 2
	 */
	public int checkGanadorBatalla() {
		boolean eq1Muerto = true;
		for (Leyenda l : j1.getEquipo()) {
			if (!l.estaMuerto()) {
				eq1Muerto = false;
				break;
			}
		}
		boolean eq2Muerto = true;
		for (Leyenda l : j2.getEquipo()) {
			if (!l.estaMuerto()) {
				eq2Muerto = false;
				break;
			}
		}
		if (!eq1Muerto && !eq2Muerto)
			return -1; // no gana nadie
		else if (!eq1Muerto && eq2Muerto)
			return 0; // gana j1
		else if (eq1Muerto && !eq2Muerto)
			return 1; // gana j2
		else
			return -1;
	}

}
