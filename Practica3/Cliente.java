import java.io.*;
import java.net.*;
public class Cliente extends Thread {
//	Flag que indica cusndo se debe terminar la ejecucion
	private static boolean done = false;

	private Socket conexion;
	public Cliente(Socket s) {
		conexion = s;
	}

	public static void main(String args[]) {
		try {
			Socket conexion = new Socket("127.0.0.1", 8000);
			String linea;
			String operationNumber;
			String op1;
			String op2;

			PrintStream saida = new PrintStream(conexion.getOutputStream());
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Calculadora distribuida");
				System.out.println(
					"Operaciones disponibles: \n" +
					"1 - SUMA --------------(+)\n" +
					"2 - RESTA ---------(-)\n" +
					"3 - MULTIPLICACION -----(*)\n" +
					"4 - DIVISION -----------(/)\n"
				);

			Thread t = new Cliente(conexion);
			t.start();

			System.out.print("Introduzca el numero de la operacion que desea realizar:");
			
			while (true) {
				operationNumber = teclado.readLine();
				System.out.println();
				if (done) {break;}

				switch(operationNumber) {
					case "1":
						System.out.print("Introduzca el primer operando: ");
						op1 = teclado.readLine();
						System.out.print("Operacion seleccionada: " + "       +\n");
						System.out.print("Ingrese el segundo operando:  ");
						op2 = teclado.readLine();

						if (op1.equals("") || op1 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op1);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						if (op2.equals("") || op2 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op2);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						

						saida.println(operationNumber);
						saida.println(op1);
						saida.println(op2);

						break;
					case "2":
						System.out.print("Introduzca el primer operando: ");
						op1 = teclado.readLine();
						System.out.print("Operacion seleccionada: " + "       -\n");
						System.out.print("Ingrese un segundo operando:  ");
						op2 = teclado.readLine();

						if (op1.equals("") || op1 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op1);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						if (op2.equals("") || op2 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op2);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						saida.println(operationNumber);
						saida.println(op1);
						saida.println(op2);

						break;
					case "3":
						System.out.print("Introduzca el primer operando: ");
						op1 = teclado.readLine();
						System.out.print("Operacion seleccionada: " + "       *\n");
						System.out.print("Ingrese un segundo operando:  ");
						op2 = teclado.readLine();

						if (op1.equals("") || op1 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op1);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						if (op2.equals("") || op2 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op2);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						saida.println(operationNumber);
						saida.println(op1);
						saida.println(op2);

						break;
					case "4":
						System.out.print("Introduzca el primer operando: ");
						op1 = teclado.readLine();
						System.out.print("Operacion seleccionada: " + "       /\n");
						System.out.print("Ingrese el segundo operando:  ");
						op2 = teclado.readLine();

						if (op1.equals("") || op1 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op1);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						if (op2.equals("") || op2 == null) {
							System.out.print("Ingrese numeros validos!");
							break;
						}

						try {
							Double.parseDouble(op2);
						} catch (NumberFormatException e) {
							System.out.println("Ingrese numeros validos!");
							break;
						}

						saida.println(operationNumber);
						saida.println(op1);
						saida.println(op2);

						break;
					
				}
			}
		}
		catch (IOException e) {
			System.out.println("No hay servidores disponibles para la conexion!");
		} 
		
	}
	
//	ejecucion thread
	public void run() {
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			String linha;
			while (true) {
				linha = entrada.readLine();

				if (linha == null) {
					System.out.println("Conexion cerrada!");
					break;
				}

				System.out.println();
				System.out.println(linha);
				System.out.print("----------------------------\n");
				System.out.print("Introduzca el numero de la operacion que desea realizar: ");
				// System.out.print("...> ");
			}
		}
		catch (IOException e) {
			System.out.println("\nConexion perdida. Reinicia la aplicacion!");
		}

		done = true;
	}
}