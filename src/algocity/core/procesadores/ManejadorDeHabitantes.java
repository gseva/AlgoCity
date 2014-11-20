package algocity.core.procesadores;

import java.util.ArrayList;
import java.util.Iterator;

import algocity.core.capas.Hectarea;
import algocity.core.construibles.Residencial;
import algocity.core.exceptions.EspacioInsuficienteException;

public class ManejadorDeHabitantes implements Procesador {
	
	int indice;
	int habitantes;
	ArrayList<Residencial> casas;
	
	public ManejadorDeHabitantes() {
		casas = new ArrayList<Residencial>();
	}
	
	public int getHabitantes() {
		return habitantes;
	}
	
	public void setHabitantes(int habitantes) {
		this.habitantes = habitantes;
	}

	@Override
	public void procesarHectarea(Hectarea hectarea) {
		String tipo = hectarea.contieneUn();
		if (tipo.equals("Residencial")) {
			Residencial casa = (Residencial) hectarea.getConstruible();
			if (casa.disponibilidad() > 0) casas.add(casa);
		}
	}

	public void finalizarProceso() {
		for (int i = 0; i < casas.size(); i++) {
			Residencial casa = casas.get(i);
			int disponibilidad = casa.disponibilidad();
			try {
				if (disponibilidad > habitantes) {
					casa.agregarHabitantes(habitantes);
					habitantes = 0;
					break;
				} else {
					casa.agregarHabitantes(disponibilidad);
					habitantes -= disponibilidad;
				}
			} catch (EspacioInsuficienteException e) {
				e.printStackTrace();
			}
			
		}
	}
	

}
