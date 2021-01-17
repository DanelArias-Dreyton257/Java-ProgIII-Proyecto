package objetosCombate;

import java.util.Random;

import gestion.GestorConfiguracion;
import gestion.GestorDeDatos;
import personaje.Leyenda;
import visuales.VenValhalla;

public class Contrincante extends Jugador {

	private static final long serialVersionUID = 1L;

	/**
	 * crea un contricante aleatorio
	 */
	public Contrincante() {
		super(GestorDeDatos.selectRandContrincante());
		java.util.Random r = new java.util.Random();
		setNvDificultad(r.nextDouble());
		generarContrincante();
	}

	/**
	 * Crea un contrincante aleatorio con un nivel de dificultad
	 * @param dif
	 */
	public Contrincante(double dif) {
		super(GestorDeDatos.selectRandContrincante());
		setNvDificultad(dif);
		generarContrincante();
	}


	/**
	 * genera un contricante con un nivel de dificultad determinado
	 */
	private void generarContrincante() {
		int numLeyendas = (int) Math.round(nvDificultad * Double.parseDouble(  GestorConfiguracion.getValue(GestorConfiguracion.MULT_GEN_NUM_LEY_CONT)));
		this.anyadirLeyendasRandom(numLeyendas, nvDificultad);
		int doblones = (int) Math.round(nvDificultad * VenValhalla.COSTE_CONTRATO * Double.parseDouble(  GestorConfiguracion.getValue(GestorConfiguracion.MULT_GEN_DOB_CONT)));
		this.setDoblones(doblones);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * Funcion que devuelve el indice de la habilidad que la IA del contricante
	 * eligira
	 * 
	 * @param indLeyEnEquipo
	 * @return
	 */
	public int getIndHabIA(int indLeyEnEquipo) {
		int indHab = 0;

		Leyenda l = this.getLeyendaEquipo(indLeyEnEquipo);
		Random r = new Random();
		
		indHab = r.nextInt(4);
		while (l.getHabilidades()[indHab] == null) {
			indHab = r.nextInt(4);
		}
		return indHab;
	}

	/**
	 * Cambia a las leyendas que estan en combate muertas o que sean nulas en
	 * combate si es quehay una leyenda saludable en el banquillo
	 */
	public void cambiarLeyendaIA() {
		for (int i = 0; i < 3; i++) {
			Leyenda l = this.getLeyendaEquipo(i);
			if (l == null || l.estaMuerto()) {
				for (int j = 3; j < 6; j++) {
					Leyenda l2 = this.getLeyendaEquipo(j);
					if (l2 != null && !l2.estaMuerto()) {
						intercambiarEnEquipo(i, j);

					}
				}

			}
		}

	}

	/**
	 * Devuelve un indice 0,1 o 2 que representa a donde atacara la leyenda del
	 * contrincante
	 * 
	 * @return
	 */
	public int getIndDeAtaqueIA() {
		return new java.util.Random().nextInt(3);
	}
}
