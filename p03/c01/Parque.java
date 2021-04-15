package p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque {

	private int contadorPersonasTotales;
	private int maxPersonas;
	private Hashtable<String, Integer> contadoresPersonasPuerta;

	public Parque(int maxP) {
		maxPersonas = maxP;
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}

	@Override
	public synchronized void entrarAlParque(String puerta) { // TODO

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		comprobarAntesDeEntrar();

		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		notifyAll();
		checkInvariante();

	}

	@Override
	public synchronized void salirDelParque(String puerta) {
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		comprobarAntesDeSalir();
		// Disminuimos el contador total y el individual
		contadorPersonasTotales--;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);

		imprimirInfo(puerta, "Salida");
		notifyAll();
		checkInvariante();

	}

	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); // + " tiempo medio de estancia: " +
																					// tmedio);

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet()) {
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}

	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
		Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
		while (iterPuertas.hasMoreElements()) {
			sumaContadoresPuerta += iterPuertas.nextElement();
		}
		return sumaContadoresPuerta;
	}

	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales
				: "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales <= maxPersonas : "El maximo numero de personas en el parque es 50";
		assert contadorPersonasTotales >= 0 : "Ha de haber persona/s dentro para poder salir";
	}

	protected void comprobarAntesDeEntrar() { // TODO
		if(maxPersonas == contadorPersonasTotales) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	protected void comprobarAntesDeSalir(){		// TODO
		while(contadorPersonasTotales <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}

}
