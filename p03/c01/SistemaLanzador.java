package p03.c01;

public class SistemaLanzador {
	public static void main(String[] args) {
		
		IParque parque = new Parque(20); // TODO
		char letra_puerta = 'A';
		
		System.out.println("�Parque abierto!");
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			ActividadEntradaPuerta salidas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (salidas).start();
			
		}
	}	
}