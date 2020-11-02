package personaje;

import static org.junit.Assert.*;

import org.junit.*;

import personaje.atributos.Habilidad;
import personaje.atributos.Tipo;


public class LeyendaTest {
	
public Leyenda l1;
public Leyenda l2;
	
	@Before
	public void setUp() {
		Tipo[] tipitos= {Tipo.FUEGO,Tipo.AGUA};
		Habilidad[] nada= new Habilidad[4];
		l1= new Leyenda("Zeus", "Dios del rayo", tipitos, nada, 12, 32, 32, 123);
		Tipo[] tipitos1= {Tipo.FUEGO,null};
		l2= new Leyenda("Pepe", "Destructor de mundos", tipitos1, nada, 24, 78, 32, 123);
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
	
	@Test
	public void testFusion() {
		//TODO faltan comprobaciones
		int nAtk = ((l1.getAtaque() + l2.getAtaque()) / 2) + 5;
		int nVida = ((l1.getVidaMax() + l2.getVidaMax()) / 2) + 50;
		Leyenda fusionada = Leyenda.fusion(l1, l2);
		assertEquals(nAtk, fusionada.getAtaque());
		assertEquals(nVida, fusionada.getVidaMax());
		
	}

}
