package algocity.core.capas.tendido;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import algocity.core.capas.tendido.NodoTendido;

public class Tendido {

	ArrayList<NodoTendido> nodos;
		
	public Tendido () {
		this.nodos = new ArrayList<NodoTendido>();		
	}

	public void agregarNodo (int coordenadaX , int coordenadaY){
		NodoTendido nodoNuevo = new NodoTendido(coordenadaX, coordenadaY);
		
		NodoTendido vecino = this.getNodo(coordenadaX+1, coordenadaY);
		this.hacerVecinos (nodoNuevo, vecino);
		
		vecino = this.getNodo(coordenadaX, coordenadaY+1);
		this.hacerVecinos (nodoNuevo, vecino);
		
		vecino = this.getNodo(coordenadaX-1, coordenadaY);
		this.hacerVecinos (nodoNuevo, vecino);
		
		vecino = this.getNodo(coordenadaX, coordenadaY-1);
		this.hacerVecinos (nodoNuevo, vecino);
		
		nodos.add(nodoNuevo);
	}
	
	public void eliminarNodo (int coordenadaX , int coordenadaY){
		
		NodoTendido nodo = getNodo (coordenadaX , coordenadaY);
		quitarReferenciasDeVecinos(nodo);
		this.nodos.remove(nodo);
	}
	
	public NodoTendido getNodo (int coordenadaX , int coordenadaY){
		Iterator<NodoTendido> iter = nodos.iterator();

		while ( iter.hasNext() ){

			NodoTendido nodoActual = iter.next();
			
			int XActual = nodoActual.getCoordenadaX();
			int YActual = nodoActual.getCoordenadaY();	
			
			if ( (XActual == coordenadaX) && ( YActual == coordenadaY) ){
				return nodoActual;				
			}
		}return null;
	}
	
	private void hacerVecinos (NodoTendido nodoNuevo, NodoTendido vecino){
		if ( vecino != null) {
			nodoNuevo.agregarVecino(vecino);
			vecino.agregarVecino(nodoNuevo);
		}	
	}
	
	private void quitarReferenciasDeVecinos(NodoTendido nodo){
	
		int coordenadaX = nodo.getCoordenadaX();
		int coordenadaY = nodo.getCoordenadaY();
		
		nodo.borrarVecino(coordenadaX+1, coordenadaY);
		nodo.borrarVecino(coordenadaX, coordenadaY+1);
		nodo.borrarVecino(coordenadaX-1, coordenadaY);
		nodo.borrarVecino(coordenadaX, coordenadaY-1);

	}
	
	public boolean existeConexionBFS (NodoTendido nodoInicial, NodoTendido nodoFinal){
		//Implementa el algoritmo de recorrido BFS para saber si dos elementos se encuentran en el mismo subgrafo conexo.
		
		ConcurrentLinkedQueue<NodoTendido> q = new ConcurrentLinkedQueue<NodoTendido>();
		Hashtable<NodoTendido, String> estadoVisitaNodo = new Hashtable<NodoTendido, String>();
		
		Iterator<NodoTendido> iter = nodos.iterator();
		while ( iter.hasNext() ){
			NodoTendido nodoActual = iter.next();
			estadoVisitaNodo.put(nodoActual, "NO_VISITADO");
		}
		
		estadoVisitaNodo.put(nodoInicial, "VISITADO");
		q.add(nodoInicial);
		
		while (!q.isEmpty()){
			NodoTendido nodoActual = q.poll();
			ArrayList<NodoTendido> vecinos = nodoActual.getVecinos();
			Iterator<NodoTendido> iterVecinos = vecinos.iterator();
			while ( iterVecinos.hasNext() ){
				NodoTendido vecino = iterVecinos.next();
				if (vecino.equals(nodoFinal))
					return true;
				if (estadoVisitaNodo.get(vecino) == "NO_VISITADO"){
					estadoVisitaNodo.put(vecino, "VISITADO");
					q.add(vecino);
				}
			}
						
		}return false;
	}
	
	private Hashtable<NodoTendido, Integer> distanciasMinimasDijkstra (NodoTendido nodoInicial){
		//Implementa el algoritmo de Dikjstra para calcular la distancia minimas entre cada elemento de un grafo conexo y un nodo inicial perteneciente al mismo.
		
		PriorityQueue<NodoTendido> q = new PriorityQueue<NodoTendido>();
		Hashtable<NodoTendido, Integer> distancias = new Hashtable<NodoTendido, Integer>();
		int INFINITO = Integer.MAX_VALUE;
		
		Iterator<NodoTendido> iter = nodos.iterator();
		while ( iter.hasNext() ){
			NodoTendido nodoActual = iter.next();
			distancias.put(nodoActual, INFINITO);
		}
		
		distancias.put(nodoInicial, 0);
		q.add(nodoInicial);
		while (!q.isEmpty()){
			NodoTendido nodoActual = q.poll();
			ArrayList<NodoTendido> vecinos = nodoActual.getVecinos();
			Iterator<NodoTendido> iterVecinos = vecinos.iterator();
			while ( iterVecinos.hasNext() ){
				NodoTendido vecino = iterVecinos.next();
				if ( distancias.get(vecino) > distancias.get(nodoActual) + 1 ){
					distancias.put ( vecino , distancias.get(nodoActual) + 1);
					q.add(vecino);
				}
			}
		}
		return distancias;
	}
	
	public int distanciaMinima(NodoTendido nodoInicial, NodoTendido nodoFinal){
		
		Hashtable<NodoTendido, Integer> distancias = distanciasMinimasDijkstra(nodoInicial);	
		return (distancias.get(nodoFinal));
	}
}