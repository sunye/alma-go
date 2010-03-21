package fr.alma.modele.arbres;

import java.util.ArrayList;

public class main {
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		String unestring="Mouahahhahah1,";
		System.out.println(unestring.substring(0, unestring.length()-1));
		
		
		
		
		
		
		System.out.println("Test liste:");
		
		
		
		ArrayList<String> temp= new ArrayList<String> ();
		String t1="truc1";
		String t2="truc2";
		String t3="truc3";
		
		String t4="truc4";
		String t5="truc5";
		String t6="truc6";
		
		for (int i=0; i< 20; i++){
			
			temp.add(null);
			
			
		}
		
		
		
		
		//System.out.println(temp.size());
		temp.set(2, t3);
		System.out.println(temp.get(4));
		//System.out.println(temp.size());
		
		temp.set(3, t2);
		temp.set(1, t1);
		//System.out.println(temp.size());
		
		System.out.println(temp.get(4));
		System.out.println(temp.get(0));
		System.out.println(temp.get(1));
		System.out.println(temp.get(2));
		
		try {
			temp.get(30);
		}catch (IndexOutOfBoundsException e){
			
		}

	}

}
