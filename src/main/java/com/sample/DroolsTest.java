package com.sample;

import java.io.IOException;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.snmp4j.smi.UdpAddress;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

	//information for SNMP
	public static final String READ_COMMUNITY = "public";
	public static final String WRITE_COMMUNITY= "private";
	public static final int mSNMPVersion =0; // 0 represents SNMP version=1
	public static final String OID_UPS_OUTLET_GROUP1 = "1.3.6.1.4.1.318.1.1.1.12.3.2.1.3.1";
	public static final String OID_IF_NUMBER = 	"1.3.6.1.2.1.2.1.0";
	public static final String OID_IF_NAME = 	"1.3.6.1.2.1.2.2.1.2";
	public static final String OID_IF_STATUS = 	"1.3.6.1.2.1.2.2.1.7.4";

	//drools
	private static KnowledgeBase kbase = null;
	private static StatefulKnowledgeSession ksession = null;
	private static KnowledgeRuntimeLogger logger = null;

	public static final void main(String[] args) {
		try {

//			String strIPAddress = "172.16.59.129";
//			SNMPTester snmpTester = new SNMPTester();
//
			kbase = readKnowledgeBase();
			ksession = kbase.newStatefulKnowledgeSession();
			logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
//			
//			String returnMsg = snmpTester.snmpGet(strIPAddress, READ_COMMUNITY, OID_IF_NUMBER);
//			int numOfIf = Integer.parseInt(returnMsg.substring(1));
//			System.out.println("Number of NIC: " + numOfIf);
//			for(int i = 1 ; i <= numOfIf ; ++i){
//				returnMsg = snmpTester.snmpGet(strIPAddress, READ_COMMUNITY, OID_IF_NAME + "." + i);
//				System.out.println(i + ": " + returnMsg);
//			}
			
//			while(true){
//				System.out.print("Checking if the eth1 is failed down by pooling ");
//				returnMsg = snmpTester.snmpGet(strIPAddress, READ_COMMUNITY, OID_IF_STATUS);
//				System.out.println(">> " + returnMsg);
//				int ifStatus = Integer.parseInt(returnMsg.substring(1));
//				SNMPModel snmpModel = new SNMPModel();
//				snmpModel.setStatus(ifStatus);
//				ksession.insert(snmpModel);
//				ksession.fireAllRules();
//				Thread.sleep(500);
//			}

//			SNMPModel snmpModel = new SNMPModel();
//			TrapListener snmp4jTrapReceiver = new TrapListener(snmpModel, ksession);
//			try {
//				snmp4jTrapReceiver.listen(new UdpAddress("155.99.181.227/9999"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			

			EventListener listener = new EventListener(ksession);
			

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			logger.close();
		}
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}
