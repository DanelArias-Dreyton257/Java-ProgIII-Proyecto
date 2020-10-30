package objetos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import personaje.Leyenda;
import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;

public class CombateTest {
	Jugador j1= new Jugador("J1");
	Jugador j2= new Jugador("J2");
	Tipo[] tipitos= {Tipo.FUEGO,Tipo.AGUA};
	 Habilidad[] nada= new Habilidad[4];
	 Leyenda l1= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 Leyenda l2= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 Leyenda l3= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 Leyenda l4= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 Leyenda l5= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 Leyenda l6= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 
	@Before
	public void setUp() {
		j1.anyadirAEquipo(0, l1);
		j1.anyadirAEquipo(1, l2);
		j1.anyadirAEquipo(2, l3);
		j2.anyadirAEquipo(0, l4);
		j2.anyadirAEquipo(1, l5);
		j2.anyadirAEquipo(2, l6);
	}
	
	@After
	public void tearDown() {

	}
	
	@Test
	public void testordenVelocidad(){
		l1.setAtaque(42);
		assertEquals(42, l1.getAtaque());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testSetAtaqueExc() {
		l1.setAtaque(1000);
	}

}
