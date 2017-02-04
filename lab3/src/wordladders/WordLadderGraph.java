package wordladders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class WordLadderGraph implements SimpleGraph {


	private Map<String, HashSet<String>> words;
	public WordLadderGraph(String fname, GraphStrategy gs) {
		words = new HashMap<String, HashSet<String>>();
		try (Scanner scan = new Scanner(new File(fname))){
			scan.forEachRemaining(s -> words.put(s, new HashSet<String>()));
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
			System.exit(1);
		}
		
		for (String w1 : words.keySet()) 
			for (String w2 : words.keySet()) 
				if(gs.adjacent(w1,w2)){
					words.get(w1).add(w2);
				}
	}
	
	@Override
	public Set<String> adjacentTo(String s) {
		return words.containsKey(s) ? words.get(s) : null;
	}

}
