package integracion;

import algocity.core.Mapa;
import algocity.core.Partida;
import algocity.core.capas.HectareaLlana;
import algocity.core.construibles.CentralEolica;
import junit.framework.TestCase;

public class IntegracionTest extends TestCase {
	
	public void test01agregarUnaCentralEolicaAlimenta4Hectareas() {
		
		Mapa mapa = new Mapa(10,10);
		
		while (!mapa.cargado()) {
			mapa.cargarHectareaNueva(new HectareaLlana());
		}
		
		Partida partida = new Partida(mapa);
		CentralEolica central = new CentralEolica();
		
		partida.agregarConstruible(central, 4, 4);
		
		assertTrue ((partida.getHectarea(1, 2).redElectricaConectada()));
		assertTrue ((partida.getHectarea(8, 8).redElectricaConectada()));
		assertTrue ((partida.getHectarea(0, 0).redElectricaConectada()));
		assertTrue ((partida.getHectarea(0, 8).redElectricaConectada()));
		assertTrue ((partida.getHectarea(8, 0).redElectricaConectada()));
		assertTrue ((partida.getHectarea(4, 4).redElectricaConectada()));
		assertFalse ((partida.getHectarea(9, 9).redElectricaConectada()));
	}
	
	public void test02agregarUnaCentralEolicaAlimenta4Hectareas() {
		
		Mapa mapa = new Mapa(10,10);
		
		while (!mapa.cargado()) {
			mapa.cargarHectareaNueva(new HectareaLlana());
		}
		
		Partida partida = new Partida(mapa);
		CentralEolica central = new CentralEolica();
		
		partida.agregarConstruible(central, 0, 0);
		
		assertTrue ((partida.getHectarea(0, 0).redElectricaConectada()));
		assertTrue ((partida.getHectarea(4, 4).redElectricaConectada()));
		assertTrue ((partida.getHectarea(0, 4).redElectricaConectada()));
		assertTrue ((partida.getHectarea(4, 0).redElectricaConectada()));
		assertFalse ((partida.getHectarea(5, 0).redElectricaConectada()));
		assertFalse ((partida.getHectarea(5, 4).redElectricaConectada()));
		assertFalse ((partida.getHectarea(9, 9).redElectricaConectada()));
	}
}
