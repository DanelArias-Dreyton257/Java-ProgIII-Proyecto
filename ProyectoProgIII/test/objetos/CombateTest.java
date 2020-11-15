package objetos;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import personaje.Leyenda;

public class CombateTest {
	
	/*Jugador j1= new Jugador("J1");
	Jugador j2= new Jugador("J2");
	Tipo[] tipitos= {Tipo.FUEGO,Tipo.AGUA};
	 Habilidad[] nada= new Habilidad[4];
	 Leyenda l1= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	 Leyenda l2= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 45, 123);
	 Leyenda l3= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 56, 123);
	 Leyenda l4= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 67, 123);
	 Leyenda l5= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 34, 123);
	 Leyenda l6= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 22, 123);*/
	 Combate pp=new Combate();
	@Before
	public void setUp() {
		/*j1.anyadirAEquipo(0, l1);
		j1.anyadirAEquipo(1, l2);
		j1.anyadirAEquipo(2, l3);
		j2.anyadirAEquipo(0, l4);
		j2.anyadirAEquipo(1, l5);
		j2.anyadirAEquipo(2, l6);
		pp=new Combate(j1,j2);*/

	}
	
	@After
	public void tearDown() {

	}
	
	@Test
	public void testordenVelocidad(){
		TreeSet<Leyenda> ss = pp.ordenVelocidad();
		Leyenda p1=ss.pollFirst();
		Leyenda p2=ss.pollFirst();
		Leyenda p3=ss.pollFirst();
		Leyenda pf=ss.pollLast();
		assertTrue(p1.getVelocidad()>=p2.getVelocidad());
		assertTrue(p2.getVelocidad()>=p3.getVelocidad());
		assertTrue(p3.getVelocidad()>=pf.getVelocidad());
	}
	

}
