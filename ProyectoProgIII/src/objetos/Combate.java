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


	private void combateDePrueba() {
		Jugador j1 = new Jugador("Willyrex");
		Jugador j2 = new Jugador("Folagor");
		for (int i=0; i<10; i++) {
			Random r = new Random();
			int it1 = r.nextInt(Tipo.values().length);
			Random r1 = new Random();
			int it2 = r1.nextInt(Tipo.values().length);
			Tipo[] tipos = {Tipo.values()[it1],Tipo.values()[it2]};
			j1.anyadirNuevaLeyenda(new Leyenda(i+"", "",tipos ));
		}
		for (int i=0; i<10; i++) {
			Random r = new Random();
			int it1 = r.nextInt(Tipo.values().length);
			Random r1 = new Random();
			int it2 = r1.nextInt(Tipo.values().length);
			Tipo[] tipos = {Tipo.values()[it1],Tipo.values()[it2]};
			j2.anyadirNuevaLeyenda(new Leyenda(i+"", "",tipos ));
		}
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

	public TreeSet<Leyenda> ordenVelocidad() {
		Comparator<Leyenda> c = new Comparator<Leyenda>() {

			@Override
			public int compare(Leyenda o1, Leyenda o2) {
				// TODO Auto-generated method stub
				return o1.getVelocidad() - o2.getVelocidad();
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
