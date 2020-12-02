package gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import objetos.Jugador;

public class GestorJugadores implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Jugador> jugadores = new ArrayList<>();
	private HashMap<String,String> credenciales = new HashMap<>();
}
