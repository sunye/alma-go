package truite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class testeurlocal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String,String> list= new HashMap<String, String>();
		
		list.put("rouge","rouge");
		list.put("vert","vert");
		list.put("pomme","pomme");
		
		Iterator<String> it= list.keySet().iterator();
		
		while (it.hasNext()){
			String temp=it.next();
			
			list.remove(it);
			
		}
		

	}

}
