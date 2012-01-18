package edu.kit.eorg.client;

public class Reset1und1 {

	public static void main(String[] args) {

		try {
			Client client = new Client("158341849", "emergent");
			String[] vmids = { "53135", "53128", "53129", "53131" };
			for (String vmId : vmids) {
				System.out.println(client.doPutServerStateChange(vmId,
						"CAN_STOP"));
			}
			Thread.sleep(180000);
			for (String vmId : vmids) {
				System.out.println(client.doPutInstallImage(vmId, "10094"));
			}
			Thread.sleep(1200000);
			for (String vmId : vmids) {
				System.out.println(client.doPutServerStateChange(vmId, "CAN_STOP"));
			}
			Thread.sleep(180000);
			for (String vmId : vmids) {
				System.out.println(client.doPutHardwareConfiguration(vmId, "2",
						"300", "2"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
