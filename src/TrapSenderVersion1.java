import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class TrapSenderVersion1 {

	public static final String COMMUNITY = "public";

	// Sending Trap for sysLocation of RFC1213
	public static final String OID_IF_STATUS = "1.3.6.1.2.1.2.2.1.7.4";

	//IP of Local Host
	public static final String IP = "155.99.181.227";

	//Ideally Port 162 should be used to send receive Trap, any other available Port can be used
	public static final int port = 9999;

	public static void main(String[] args) {
		SNMPHelper snmpHelper = new SNMPHelper();
		while(true){
			String returnMsg = snmpHelper.snmpGet("172.16.59.129", "public", OID_IF_STATUS);
			System.out.println(returnMsg);
			if(returnMsg.equals(" 2")){
				TrapSenderVersion1 trapV1 = new TrapSenderVersion1();
				trapV1.sendTrap_Version1(2);
				return;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This methods sends the V1 trap to the Localhost in port 162
	 */
	public void sendTrap_Version1(int status) {
		try {
			// Create Transport Mapping
			TransportMapping transport = new DefaultUdpTransportMapping();
			transport.listen();

			// Create Target
			CommunityTarget cTarget = new CommunityTarget();
			cTarget.setCommunity(new OctetString(COMMUNITY));
			cTarget.setVersion(SnmpConstants.version1);
			cTarget.setAddress(new UdpAddress(IP + "/" + port));
			cTarget.setTimeout(5000);
			cTarget.setRetries(2);

			PDUv1 pdu = new PDUv1();
			pdu.setType(PDU.V1TRAP);
			pdu.setEnterprise(new OID(OID_IF_STATUS));
			pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
			pdu.setSpecificTrap(1);
			pdu.setAgentAddress(new IpAddress(IP));
			
			pdu.add(new VariableBinding(new OID(OID_IF_STATUS), new OctetString(String.valueOf(status))));

			// Send the PDU
			Snmp snmp = new Snmp(transport);
			snmp.send(pdu, cTarget);
			snmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
