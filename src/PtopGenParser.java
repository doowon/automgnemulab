import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PtopGenParser {

	private static ArrayList<String> phyNodes = new ArrayList<String>();
	private static HashMap<String, Integer> phyNicMap = new HashMap<String, Integer>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File("sample.txt"));
			while(scanner.hasNext()){
				String line = scanner.nextLine();
				String[] tokens = line.split(" ");
				if(tokens[0].equals("node")){
					String pcname = tokens[1];
					if(pcname.startsWith("pc")){
						phyNodes.add(pcname);
						phyNicMap.put(pcname, 0);
					}
				}
				else if(tokens[0].equals("link")){
					String nic = tokens[1];
					if(nic.startsWith("link-pc")){
						for(String node : phyNodes){
							if(nic.contains(node)){
								Integer num = phyNicMap.get(node);
								phyNicMap.put(node, ++num);
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(String name : phyNodes){
			System.out.println(name);
		}

	}

}
