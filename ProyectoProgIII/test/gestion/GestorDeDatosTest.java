package gestion;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import personaje.Especie;
import personaje.atributos.Tipo;

public class GestorDeDatosTest {

	private ArrayList<String> nombres;
	private String testPrimer;
	private String testLast;

	private Especie esp1;
	private Especie esp2;
	
	//private Especie espPrueba; // PARA EL INSERT

	@Before
	public void setUp() {
//		Tipo[] tiposPrueba = {Tipo.FUEGO,Tipo.TIERRA};
//		espPrueba = new Especie("Prueba", "No descripcion", tiposPrueba); //PAAR EL INSERT
	}

	@After
	public void tearDown() {
		//Esto es por si se intenta comprobar el insert
//		Connection conn = null;
//
//		try {
//
//			conn = DriverManager.getConnection("jdbc:sqlite:" + "BaseDatos.db");
//			conn.setAutoCommit(false);
//			// Eliminar insert de prueba
//			Statement stmt = conn.createStatement();
//			
//			stmt.executeUpdate("DELETE FROM ESPECIE WHERE NOMBRE='"+espPrueba.getNombre()+"'");
//			
//			stmt.close();
//			
//			conn.commit();
//			
//		} catch (SQLException e) {
//			try {
//
//				conn.rollback(); // HECHA PA TRAS
//			} catch (SQLException excep) {
//			}
//		} finally {
//			try {
//				conn.setAutoCommit(true);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void getNombresEspeciesTest() {
		testPrimer = "Afrodita";
		testLast = "Zeus";
		nombres = GestorDeDatos.getNombresEspecies();
		assertEquals(testPrimer, nombres.get(0));
		assertEquals(testLast, nombres.get(nombres.size() - 1));
	}

	@Test
	public void getInfoEspecieTest() {
		Tipo[] ts = { Tipo.CUERPO, null };
		esp1 = new Especie("Afrodita",
				"Afrodita es, en la mitología griega, la diosa de la belleza, la sensualidad y el amor. Aunque a menudo se alude a ella en la cultura moderna como «la diosa del amor», es importante señalar que antiguamente no se refería al amor en el sentido romántico sino erótico. La ‘surgida de la espuma’ Afrodita nació del mar, cerca de Pafos (Chipre) después de que Crono cortase los genitales a Urano con una hoz adamantina y los arrojase tras él al mar. Afrodita no tuvo infancia: en todas las imágenes y referencias nació adulta, núbil e infinitamente deseable.",
				ts);
		Tipo[] ts1 = { Tipo.ELECTRICIDAD, Tipo.CIELO };
		esp2 = new Especie("Zeus",
				"En la religión griega, Zeus es una divinidad a la que se denomina a veces con el título de «padre de los dioses y los hombres», que gobierna a los dioses del Olimpo como un padre a una familia, de forma que incluso los que no eran sus hijos naturales se dirigen a él como tal. Es el rey de los dioses y supervisa el universo. Es el dios del cielo y el trueno y por ende de la energía.",
				ts1);
		assertEquals(esp1, GestorDeDatos.getInfoEspecie(esp1.getNombre()));
		assertEquals(esp2, GestorDeDatos.getInfoEspecie(esp2.getNombre()));

	}
	
//	@Test
//	public void insertEspecieBDTest() { //YA LO COMPROBE! NO hacerlo otra vez para no andar jodiendo la BD
//		GestorDeDatos.insertEspecieBD(espPrueba);
//		assertEquals(espPrueba, GestorDeDatos.getInfoEspecie(espPrueba.getNombre())); //Ya se que el metodo getInfo funciona
//	}

}
