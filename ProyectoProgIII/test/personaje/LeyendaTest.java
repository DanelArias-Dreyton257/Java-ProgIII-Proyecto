package personaje;

import static org.junit.Assert.*;

import org.junit.*;

import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;


public class LeyendaTest {
	
public Leyenda l1;	
	
	@Before
	public void setUp() {
		Tipo[] tipitos= {Tipo.FUEGO,Tipo.AGUA};
		 Habilidad[] nada= new Habilidad[4];
		l1= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
	}
	
	@After
	public void tearDown() {

	}
	
	@Test
	public void testSetAtaque() {
		l1.setAtaque(42);
		assertEquals(42, l1.getAtaque());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testSetAtaqueExc() {
		l1.setAtaque(1000);
	}

}
