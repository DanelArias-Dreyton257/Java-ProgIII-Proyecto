package personaje.atributos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import personaje.atributos.Habilidad;

public class HabilidadTest {
	public Habilidad h1;

	@Before
	public void setUp() {
		h1 = new Habilidad("Rayo", Tipo.ELECTRICIDAD, 53, 0.2);

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testSetPotencia() {
		h1.setPotencia(34);
		assertEquals(34, h1.getPotencia());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPotencia1() {
		h1.setPotencia(-32);
		h1.setPotencia(31232);
	}

	@Test
	public void testSetPrecision() {
		h1.setPrecision(0.4);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPrecision1() {
		h1.setPrecision(-0.4);
		h1.setPrecision(31232);
		h1.setPrecision(2);
	}

}
