package objetosCombate;

import static org.junit.Assert.*;

import org.junit.Test;

import objetosCombate.Jugador;
import personaje.Leyenda;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

public class JugadorTest {
	Jugador j1= new Jugador("J1");
	Tipo[] tipitos= {Tipo.FUEGO,Tipo.AGUA};
	 Habilidad[] nada= new Habilidad[4];
	 Leyenda l1= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 Leyenda l2= new Leyenda("Poseidon", "Dios del mar", tipitos, nada, 43, 32, 45, 123);
	 Leyenda l3= new Leyenda("Afordita", "Diosa del amor", tipitos, nada, 13, 32, 56, 123);
	 Leyenda l4= new Leyenda("Hades", "Dios del inframundo", tipitos, nada, 22, 32, 67, 123);
	 Leyenda l5= new Leyenda("Hercules", "Dios del poder", tipitos, nada, 33, 32, 34, 123);
	 Leyenda l6= new Leyenda("Shiva", "Dios de la destruccion", tipitos, nada, 44, 32, 22, 123);
	 int Numero=6;
	 public void setUp() {
			
	
	 }
	 @Test
	public void test() {
		j1.anyadirAEquipo(0, l1);
		j1.anyadirAEquipo(1, l5); 
		j1.anyadirNuevasLeyendas(l4,l6,l2,l3);
		j1.anyadirNuevasLeyendas(l4,l6,l2,l3);
		assertEquals(Numero, j1.getNumLeyendasEnEquipo());
		assertEquals(l6, j1.getLeyendaEquipo(3));
		assertEquals(4, j1.getNumLeyendasEnEternidad());
		assertEquals(6, j1.getNumLeyendasEnEquipo());
		assertEquals(l2, j1.getLeyendaEternidad(2));
		j1.getLeyendaEternidad(2);
		j1.delLeyendaEternidad(2);
		assertEquals(l3, j1.getLeyendaEternidad(2));
	}
	 
	 @Test(expected=ArrayIndexOutOfBoundsException.class)
		public void testErrores() {
		 	j1.anyadirAEquipo(0, l1);
			j1.anyadirAEquipo(1, l5); 
			j1.anyadirNuevasLeyendas(l4,l6,l2,l3);
			j1.anyadirNuevasLeyendas(l4,l6,l2,l3);
			assertEquals(Numero, j1.getNumLeyendasEnEquipo());
			assertEquals(l1, j1.getLeyendaEquipo(7));
			assertEquals(3, j1.getNumLeyendasEnEternidad());
			assertEquals(1, j1.getNumLeyendasEnEquipo());
			assertEquals(l3, j1.getLeyendaEternidad(2));
			j1.delLeyendaEternidad(23);;
		}
}
