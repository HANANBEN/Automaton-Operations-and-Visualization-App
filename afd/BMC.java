package afd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
public class BMC {
	
	void affich(Map<String, Map<Character, String>> T) {
		Iterator<String> iterator = T.keySet().iterator();
		
		
		while (iterator.hasNext() ) {
		
		    String j = iterator.next();
		    System.out.println("Valeurs pour la clé \"" + j + "\":");
	            for (Character i : T.get(j).keySet()) {
	               
	                System.out.println("   Clé interne: " + i + ", Valeur interne: " + T.get(j).get(i));
	            }
	        }
	}
	
	 boolean herSelf(Map<String, Map<String, String>> T, String etat,String symbol) {
			if(  etat.equals(T.get(etat).get(symbol)) ){
				return true;
			}
			else return false;
		}
	 String herSelfSymbol(Map<String, Map<String, String>> T, String etat) {
	    	for(String s:T.get(etat).keySet()) {
			
				if(etat.equals(T.get(etat).get(s))) {
					return s;
				}
			}
			return null;
		}
	    
	 String allerRetour(Map<String, Map<String, String>> T, String etat1,String etat2,String symbol,Set<String> alphabet) {
		if (T.get(etat2) != null) {
			for(String i:T.get(etat2).keySet()) {
				if(T.get(etat2).get(i).equals(etat1) && etat2.equals(T.get(etat1).get(symbol))) {
					return i;
				}
			}}
			return null;
		}

    
   
    

    
    
    Map<String, Map<String, String>> minimiserEtat(Map<String, Map<String, String>> T, Set<String> etatF, Set<String> alphabet) {
		//this.affich(T);
	    ArrayList<String> listN = new ArrayList<>();
	    String concat = null;
	    Iterator<String> iterator = T.keySet().iterator();

	    while (iterator.hasNext() && !listN.contains(iterator.next()) && T.size()>2) {
	        String etat = iterator.next();
	        System.out.println("je suis " + etat);

	        if (etat.equals("F") || etat.equals("I")) {
	            continue;
	        }

	        for (String s : alphabet) {
	            System.out.println("je suis " + s);
	            String nextEtat = T.get(etat).get(s);
	            String expression = s;
	            HashMap<String, String> temp = new HashMap<>();

	            for (String i : alphabet) {
	                if (!herSelf(T, nextEtat, i)) {
	                    String nextnextEtat = T.get(nextEtat).get(i);
	                    for (String j : alphabet) {
	                        concat = i;
	                        if (allerRetour(T, nextEtat, nextnextEtat, i, alphabet) != null) {
	                        	concat="";
	                            String a = allerRetour(T, nextEtat, nextnextEtat, i, alphabet);
	                            if (herSelfSymbol(T, nextnextEtat) != null) {
	                                String b = herSelfSymbol(T, etat);
	                                if(b!=null) {
	                                	 concat += b + "*";
	                                }
	                                
	                               
	                            }
	                            concat += "("+a;
	                            if (herSelfSymbol(T, nextEtat) != null) {
	                                String c = herSelfSymbol(T, etat);
	                                if(c!=null) {
	                                	concat += c + "*";
	                                }
	                                
	                                
	                            }
	                            concat += i+")" + "*";
	                            concat = i + "|" + concat;
	                        }
	                        temp.put(nextnextEtat, expression + concat);
	                    }
	                }
	                listN.add(nextEtat);

	                for (String e : temp.keySet()) {
	                    if (e != null && !e.equals(nextEtat)) {
	                        Iterator<String> iterator2 = T.keySet().iterator();
	                        while (iterator2.hasNext()) {
	                            String et = iterator2.next();
	                            for (String p : alphabet) {
	                                if (T.get(et).get(p) != null && T.get(et).get(p).equals(nextEtat)) {
	                                    T.get(et).put(temp.get(e), e);
	                                    T.get(et).remove(p);
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }

	    System.out.println("_________________________________");
	    Iterator<String> iterator2 = listN.iterator();
	    while (iterator2.hasNext()) {
	        String n = iterator2.next();

		    System.out.println("l'etat supprimée"+n);
	        T.remove(n);
	        iterator2.remove(); // Utiliser iterator.remove() pour supprimer en toute sécurité
	    }
	    


	    return T;
	}
}
