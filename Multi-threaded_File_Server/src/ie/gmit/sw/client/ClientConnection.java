package ie.gmit.sw.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import ie.gmit.sw.client.config.Context;
import ie.gmit.sw.client.config.Parse;

public class ClientConnection {

	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String op = "";
	String clientResonse;
	Scanner clientInput;

	String username;
	String ipaddress;
	int port;
	String downloadDir;

	ClientConnection() {
	}


	void run() throws Throwable {
		clientInput = new Scanner(System.in);
		Context ctx = new Context();
		Parse cp = new Parse(ctx);
		cp.init();

		//System.out.println(op);

		// 3: Communicating with the server
		do {
			System.out.println("\n---------------------------");
			System.out.println("1. Connect to Server");
			System.out.println("2. Print File Listing");
			System.out.println("3. Download File");
			System.out.println("4. Quit");

			System.out.println("\nType Option [1-4]");

			op = clientInput.next();

			System.out.println("---------------------------");

//			// Receive MSG from the server
//			op = (String) in.readObject();
//			System.out.println(op);

			if (op.compareTo("1") == 0) {
				// 1. creating a socket to connect to the server
				ipaddress = ctx.getHost();
				port = ctx.getPort();

				requestSocket = new Socket(ipaddress, port);

				System.out.println("Connected to " + ipaddress + " in port " + port);

				// 2. get Input and Output streams
				out = new ObjectOutputStream(requestSocket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(requestSocket.getInputStream());
			}


			if (op.compareTo("2") == 0) {

				sendMessage("2");

				clientResonse = (String)in.readObject();
				System.out.println(clientResonse);

			}

			if (op.compareTo("3") == 0) {

			}

			if (op.compareTo("4") == 0) {
				sendMessage("4");
			}

		} while (!op.equals("4"));
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("User OP.: " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

}