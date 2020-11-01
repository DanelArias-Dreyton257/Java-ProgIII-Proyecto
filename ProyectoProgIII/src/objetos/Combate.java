package objetos;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import personaje.Leyenda;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

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
	 * @param j1
	 * @param j2
	 */
	public Combate() {
		combateDePrueba();
	}

	/**
	 * Esta clase crea unos personajes y unos jugadores
	 */
	private void combateDePrueba() {
		Jugador j1 = new Jugador("DeltaWolf33");
		Jugador j2 = new Jugador("Dreyton257");
		j1.anyadirLeyendasRandom(30);
		j2.anyadirLeyendasRandom(30);
		setJ1(j1);
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
				// TODO Auto-generated method stub
				return o2.getVelocidad() - o1.getVelocidad();
			}
		};

		TreeSet<Leyenda> lista = new TreeSet<Leyenda>(c);
		lista.add(j1.getLeyendaEquipo(0));
		lista.add(j1.getLeyendaEquipo(1));
		lista.add(j1.getLeyendaEquipo(2));
		lista.add(j2.getLeyendaEquipo(0));
		lista.add(j2.getLeyendaEquipo(1));
		lista.add(j2.getLeyendaEquipo(2));
		return lista;

	}

	/**
	 * Clase que mira quien ataca a quien y hace el calculo de danio
	 * 
	 * @param j1Aj2
	 * @param iLey1
	 * @param iLey2
	 * @param iHab
	 */
	public void leyendaAtacaLeyenda(boolean j1Aj2, int iLey1, int iLey2, int iHab) {
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
			return;
		}
		double multiplicador;
		if (tiposLey[1] == null) {
			multiplicador = tipoHab.getMultiplicadorAaD(tiposLey[0]);
		} else {
			multiplicador = (tipoHab.getMultiplicadorAaD(tiposLey[0]) + tipoHab.getMultiplicadorAaD(tiposLey[1])) / 2;
		}

		double danyo = (atkLey + potHab) * (multiplicador);

		defensor.danyar(danyo);

	}

	/**
	 * Si es del jugador 1 devuelve su indice en el equipo Si es del jugador 2
	 * devuelve su indice en el equipo + 3 sino es ninguno, return -1
	 * 
	 * @param leyendaEnCurso
	 * @return
	 */
	public int indiceEnBatalla(Leyenda leyendaEnCurso) {

		Leyenda[] eq1 = this.j1.getEquipo(); // {l1,l2,l3,l4,l5,l6}
		Leyenda[] eq2 = this.j2.getEquipo(); // {l7,l8,l9,l10,l11,l12}

		int indice = -1;

		for (int i = 0; i < 3; i++) {
			if (eq1[i].equals(leyendaEnCurso)) {
				indice = i;
			} else if (eq2[i].equals(leyendaEnCurso)) {
				indice = i + 3;
			}
		}

		return indice;
	}

}
