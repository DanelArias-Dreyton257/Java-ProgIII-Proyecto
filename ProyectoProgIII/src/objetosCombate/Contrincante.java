package objetosCombate;

import gestion.GestorDeDatos;
import visuales.VenValhalla;

public class Contrincante extends Jugador {

	private static final long serialVersionUID = 1L;
	
	private double nvDificultad = 0.1;
	
	public Contrincante() {
		super("");
		java.util.Random r = new java.util.Random();
		setNvDificultad(r.nextDouble());
		generarContrincante();
	}
	
	public Contrincante(double dif) {
		super("");
		setNvDificultad(dif);
		generarContrincante();
	}

	public double getNvDificultad() {
		return nvDificultad;
	}


	public void setNvDificultad(double nvDificultad) {
		this.nvDificultad = nvDificultad;
	}
	
	private void generarContrincante() {
		int numLeyendas = (int) Math.round(nvDificultad * 50);
		this.anyadirLeyendasRandom(numLeyendas); //TODO falta que las leyendas anyadidas sean según dificultad
		int doblones = (int) Math.round(nvDificultad * VenValhalla.COSTE_CONTRATO * 10);
		this.setDoblones(doblones);
		setNombre(GestorDeDatos.selectRandContrincante());
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}
