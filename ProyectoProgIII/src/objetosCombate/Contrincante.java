package objetosCombate;

import gestion.GestorDeDatos;
import personaje.Leyenda;
import visuales.VenValhalla;

public class Contrincante extends Jugador {

	private static final long serialVersionUID = 1L;
	
	private double nvDificultad = 0.1;
/**
 * 	crea un contricante aleatorio
 */
	public Contrincante() {
		super(GestorDeDatos.selectRandContrincante());
		java.util.Random r = new java.util.Random();
		setNvDificultad(r.nextDouble());
		generarContrincante();
	}
	
	public Contrincante(double dif) {
		super(GestorDeDatos.selectRandContrincante());
		setNvDificultad(dif);
		generarContrincante();
	}
/**
 * devuelve un nivel de dificultad
 * @return
 */
	public double getNvDificultad() {
		return nvDificultad;
	}

/**
 * introduce un nivel de dificultad
 * @param nvDificultad
 */
	public void setNvDificultad(double nvDificultad) {
		this.nvDificultad = nvDificultad;
	}
/**
 * genera un contricante con un nivel de dificultad determinado	
 */
	private void generarContrincante() {
		int numLeyendas = (int) Math.round(nvDificultad * 10);
		this.anyadirLeyendasRandom(numLeyendas, nvDificultad);
		int doblones = (int) Math.round(nvDificultad * VenValhalla.COSTE_CONTRATO * 10);
		this.setDoblones(doblones);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public void cambiarLeyendaIA() {
		for (int i = 0; i < 3; i++) {
			Leyenda l = this.getLeyendaEquipo(i);
				if (l==null || l.estaMuerto()) {
					for (int j = 3; j < 6; j++) {
						Leyenda l2 = this.getLeyendaEquipo(j);
						if (l2!=null && !l2.estaMuerto()) {
							intercambiarEnEquipo( i, j);
							
						}
					}
					
			}
		}
		
			
		
		
	}
}
