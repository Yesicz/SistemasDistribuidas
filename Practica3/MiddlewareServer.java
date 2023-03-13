import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;  

public class MiddlewareServer extends Thread  {
   public static void main(String args[]) {

		clientes = new Vector<PrintStream>();
		basicServers = new Vector<String>();

		try {
			Integer basicServerNumber;
		
			String ip = "";
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			ServerSocket s = new ServerSocket(8000);
			
			System.out.println("--Configuracion del Servidor--");
			System.out.print("Servidor conectado... ");
			//System.out.print("Ingrese numero de servidores que se usaran para las operaciones aritmeticas: ");
			//basicServerNumber = Integer.parseInt(teclado.readLine());
			basicServerNumber = 3;
			for (int i = 0; i < basicServerNumber; i++) {
					ip = "127.0.0.1";
				basicServers.add(ip);
			}
			while (true) {
				Socket conexionCliente = s.accept();
				Thread t1 = new MiddlewareServer(conexionCliente);
				t1.start();			
			}
		}
		catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}

	private static Vector<PrintStream> clientes;

	private static Vector<String> basicServers;

	
	private Socket conexionCliente;

	private Socket conexionBasicOperationsServer;



	//private String meuNome;

	public MiddlewareServer(Socket s) {
		conexionCliente = s;
	}

	public void BasicOperationsServer(Socket s) {
		conexionBasicOperationsServer = s;
	}


	public void run() {
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexionCliente.getInputStream()));
			PrintStream saida = new PrintStream(conexionCliente.getOutputStream());

			String operationNumber = "";
			String op1 = "";
			String op2 = "";
			String operator = "";
			int countFirstBasic = 0;
			
			String result = "";

			int countBasicConections = 0;
	;

			if (clientes.size() == 0) {
				clientes.add(saida);
			} else {
				Enumeration e = clientes.elements();
				while (e.hasMoreElements()) {
					PrintStream chat = (PrintStream) e.nextElement();
					if (chat != saida) {
						clientes.add(saida);
						break;	
					}
				}
			}

			while (op1 != null) {
				if (saida != null) {
					operationNumber = entrada.readLine();
					op1 = entrada.readLine();
					op2 = entrada.readLine();

					switch(operationNumber) {
						case "1":
							operator = "+";
							break;
						case "2":
							operator = "-";
							break;
						case "3":
							operator = "*";
							break;
						case "4":
							operator = "/";
							break;
					}

					if (
						operationNumber.equals("1")
						|| operationNumber.equals("2")
						|| operationNumber.equals("3")
						|| operationNumber.equals("4")
					) {	
						System.out.println("--------------------------");
						System.out.println("Operacion recibida: ");
						System.out.println("Operando 1: " + op1);
						System.out.println("operationNumber: " + operationNumber);
						System.out.println("Operando 2: " + op2);

						Socket conexionBasicOperationsServer = new Socket(basicServers.get(countBasicConections), 8080);
						countBasicConections++;
						PrintStream saida2 = new PrintStream(conexionBasicOperationsServer.getOutputStream());
						BufferedReader entradaBasicOperationsServer = new BufferedReader(new InputStreamReader(conexionBasicOperationsServer.getInputStream()));

						saida2.println(operationNumber);
						saida2.println(op1);
						saida2.println(op2);

						Thread t2 = new BasicOperationsServer(conexionBasicOperationsServer);
						t2.start();

						result = entradaBasicOperationsServer.readLine();
						sendToAll(saida, op1, operator, op2, " es igual a: ", result);						

						System.out.println("\nResultado: " + result);
						System.out.println("--------------------------\n");
						
						if (countBasicConections == basicServers.size()) {
							countBasicConections = 0;
						}
					
					}
				}
			}

		}
		catch (IOException e) {
			System.out.println("\nSe ha perdido la conexion con uno de los servidores 2!");
		}

		catch (NullPointerException e) {
			System.out.println("\nSe ha perdido la conexion con uno de los servidores 3!");
		}

		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Operacion invalida! Reinicie el servidor.");
		}
		
	}

	public void sendToAll(PrintStream saida, String op1, String operator, String op2, String text, String result) {
		Enumeration e = clientes.elements();
		while (e.hasMoreElements()) {
			PrintStream chat = (PrintStream) e.nextElement();
			if (operator.equals("sqrt")) {
				chat.println(operator + "(" + op1 + ")" + text + result);
			} else {
				chat.println(op1 + " " + operator + " " + op2 + text + result);
			}
		}
		
	}
}
