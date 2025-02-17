package proyecto1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.*;

public class Graph {

	private ArrayList<Integer> nodos;
	private ArrayList<Edge> arcos;
	private static Logger logger;
	
	public Graph() {
		nodos = new ArrayList<>();
		arcos = new ArrayList<>();
		
		if (logger == null) {
			logger = Logger.getLogger(Graph.class.getName());

			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.WARNING);
			logger.addHandler(hnd);

			logger.setLevel(Level.WARNING);
			
			Logger rootLogger = logger.getParent();
			for (Handler h : rootLogger.getHandlers()) {
				h.setLevel(Level.OFF);
			}
		}
	}
	
	public void addNode(int node) {
		Integer nuevo = node;
		if (!nodos.contains(nuevo)) {
			nodos.add(nuevo);
			logger.fine("Nodo " + nuevo + " agregado correctamente.");
		}
		else {
			logger.warning("FALLO addNode(" + node + "): ya existe el mismo elemento en el grafo.");
		}
	}
	
	public void addEdge(int node1, int node2) {
		Integer nodo1 = node1;
		Integer nodo2 = node2;
		Edge nuevo_arco = new Edge(nodo1, nodo2);
		Edge arco_invertido = new Edge(nodo2, nodo1);
		
		boolean tiene_un_arco = false;
		boolean contiene_nodo1 = false;
		boolean contiene_nodo2 = false;
		
		for (Edge arco : this.arcos) {
			tiene_un_arco = arco.equals(nuevo_arco) || arco.equals(arco_invertido);
			if (tiene_un_arco) {
				logger.warning("FALLO addEdge(" + nodo1 + ", " + nodo2 + "): los nodos ya están conectados.");
				break;
			}
		}
		
		if (!tiene_un_arco) {
			contiene_nodo1 = nodos.contains(nodo1);
			contiene_nodo2 = nodos.contains(nodo2);
			
			if (contiene_nodo1 && contiene_nodo2) {
				arcos.add(nuevo_arco);
				logger.fine("Arco " + nuevo_arco + " agregado correctamente.");
			}
			else {
				logger.warning("FALLO addEdge(" + nodo1 + ", " + nodo2 + "): No existe al menos uno de los nodos parametrizados.");
			}
		}
		
	}

	public void removeNode(int node){
		Integer a_remover = node;
		boolean tiene_arco = false, esta_nodo = false;
		
		Iterator<Edge> it = arcos.iterator();
		while (it.hasNext()) {
			Edge arco = it.next();
			tiene_arco = (arco.obtenerOrigen() == a_remover || arco.obtenerDestino() == a_remover);
			if (tiene_arco) {
				esta_nodo = true;
				it.remove();
				logger.info("El arco " + arco + " conectaba el nodo " + node + " y ha sido eliminado.");
			}
		}
		
		if (esta_nodo) {
			nodos.remove(a_remover);
			logger.fine("Nodo " + a_remover +" removido exitosamente con todos los arcos que lo unen.");
		}
		else {
			esta_nodo = nodos.contains(a_remover);
			if (esta_nodo) {
				nodos.remove(a_remover);
				logger.fine("Nodo " + a_remover +" removido exitosamente. No había arcos apuntando a él.");
			}
			else {
				logger.warning("FALLÓ removeNode(" + node + "): El nodo " + a_remover + " no se encuentra en el grafo.");
			}
		}
	}
	
	public void removeEdge(int node1, int node2) {
		Integer nodo1 = node1;
		Integer nodo2 = node2;
		boolean tiene_arco = false;
		
		for (Edge arco : this.arcos){
			tiene_arco = (arco.obtenerOrigen() == nodo1 && arco.obtenerDestino() == nodo2);
			if (tiene_arco) {
				arcos.remove(arco);
				logger.fine("Arco " + arco + " removido exitosamente.");
				break;
			}
		}
		
		if (!tiene_arco) {
			logger.warning("FALLÓ removeEdge(" + node1 + ", " + node2 + 
					"): No hay un arco que una " + nodo1 + " con " + nodo2 + ".");
		}
	}
	
	public String toString() {
		return "Nodos: " + this.nodos.toString() + "\n" + 
				"Arcos: " + this.arcos.toString();
	}
	
}
