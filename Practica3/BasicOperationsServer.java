import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicOperationsServer extends Thread  {
	 public static void main(String args[]) {

		try {

			ServerSocket s = new ServerSocket(8080);
			while (true) {
				Socket conexion = s.accept();
				Thread t = new BasicOperationsServer(conexion);
				t.start();

			}
		}
		catch (IOException e) {

			System.out.println("IOException: " + e);
		}
	}

	private Socket conexion;

	//private String meuNome;

	public BasicOperationsServer(Socket s) {
		conexion = s;
	}

	public void run() {
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			PrintStream saida5 = new PrintStream(conexion.getOutputStream());
			
			
			if (saida5 != null) {
				String operationNumber = entrada.readLine();
				String op1 = entrada.readLine();
				String op2 = entrada.readLine();

				operationNumber = operationNumber.trim();
				op1 = op1.trim();
				op2 = op2.trim();

				System.out.println("--------------------------");

				
				switch(operationNumber) {
					case "1":
						System.out.println("Operacion recibida: ");
						System.out.println("operando 1: " + op1);
						System.out.println("               + " );
						System.out.println("operando 2: " + op2);
						System.out.println("Resultado: " + String.valueOf(Double.parseDouble(op1) + Double.parseDouble(op2)));
						saida5.println("Resultado: " + String.valueOf(Double.parseDouble(op1) + Double.parseDouble(op2)));
						break;
					case "2":
						System.out.println("Operacion recibida: ");
						System.out.println("operando 1: " + op1);
						System.out.println("                 -" );
						System.out.println("operando 2: " + op2);
						System.out.println("resultado: " + String.valueOf(Double.parseDouble(op1) - Double.parseDouble(op2)));
						saida5.println("Resultado: " + String.valueOf(Double.parseDouble(op1) - Double.parseDouble(op2)));
						break;
					case "3":
						System.out.println("Operacion recibida: ");
						System.out.println("operando 1: " + op1);
						System.out.println("                  *");
						System.out.println("operando 2: " + op2);
						System.out.println("Resultado: " + String.valueOf(Double.parseDouble(op1) * Double.parseDouble(op2)));
						saida5.println("Resultado: " + String.valueOf(Double.parseDouble(op1) * Double.parseDouble(op2)));
						break;
					case "4":
						System.out.println("Operacion recibida: ");
						System.out.println("operando 1: " + op1);
						System.out.println("                  /");
						System.out.println("operando 2: " + op2);
						System.out.println("Resultado: " + String.valueOf(Double.parseDouble(op1) / Double.parseDouble(op2)));
						saida5.println("Resultado: " + String.valueOf(Double.parseDouble(op1) / Double.parseDouble(op2)));
						break;
				}

				System.out.println("--------------------------\n");
				
			}
		}
		catch (IOException e) {
			System.out.println("IOException: " + e);
		}

		catch (NullPointerException e) {
			System.out.println("\nSe perdio la conexion con uno de los servidores 1!");
		}
	}
}