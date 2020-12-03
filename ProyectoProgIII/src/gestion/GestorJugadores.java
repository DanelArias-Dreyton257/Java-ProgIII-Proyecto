package gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import objetos.Jugador;

public class GestorJugadores implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Jugador> jugadores = new ArrayList<>();
	private HashMap<String,String> credenciales = new HashMap<>();
	
	public GestorJugadores() {
	}
	
	public void anyadirJugador(Jugador j, String contrasena) {
		while (credenciales.containsKey(j.getNombre())){
			j.setNombre(j.getNombre()+" 1");
		}
		credenciales.put(j.getNombre(), contrasena);
		jugadores.add(j);
		
	}
	
	public String[] getNombresJugadores() {
		String[] strs = new String[jugadores.size()-1];
		int i=0;
		for (Jugador j: jugadores) {
			strs[i] = j.getNombre();
			i++;
		}
		return strs;
	}
	
	public Jugador getJugador(String nombre, String contrasena) {
		Jugador j = null;
		if (credenciales.containsValue(nombre)) {
			if (credenciales.get(nombre).equals(contrasena)) {
				j = getJugadorPorNombre(nombre);
			}
		}
		return j;
	}

	private Jugador getJugadorPorNombre(String nombre) {
		return getJugadorPorNombre(nombre, 0);
	}
	
	private Jugador getJugadorPorNombre(String nombre, int pos) {
		if (pos>=jugadores.size() || pos<0) {
			return null;
		}
		else if (jugadores.get(pos).getNombre().equals(nombre)){
			return jugadores.get(pos);
		}
		else {
			return getJugadorPorNombre(nombre, pos+1);
		}
	}
}
