package objetos;

import java.util.Random;

import personaje.Leyenda;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

public class Combate {
	public Jugador j1;
	public Jugador j2;

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

	public Jugador getJ1() {
		return j1;
	}

	public void setJ1(Jugador j1) {
		this.j1 = j1;
	}

	public Jugador getJ2() {
		return j2;
	}

	public void setJ2(Jugador j2) {
		this.j2 = j2;
	}

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
}
