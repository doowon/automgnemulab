package com.sample;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.drools.runtime.StatefulKnowledgeSession;

public class EventListener
{
	private StatefulKnowledgeSession ksession = null;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private BufferedReader in = null;

	public EventListener(StatefulKnowledgeSession ksession){
		try {
			this.ksession = ksession;
			serverSocket = new ServerSocket(9999);
			System.out.println("Socket is listening....");
			socket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str = null;
			while((str = in.readLine()) != null){ 
				System.out.println("[EventListener] " + str); 
				SNMPModel snmpModel = new SNMPModel();
				snmpModel.setStatusMsg(9999,str);
				ksession.insert(snmpModel);
				ksession.fireAllRules();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
