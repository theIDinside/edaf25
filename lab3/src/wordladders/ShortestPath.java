package wordladders;
import java.util.HashSet;
import java.util.Set;

public class ShortestPath {
	public static int shortestPath(SimpleGraph g,
						String source, String dest) {
		/*
		 * distance = 0;
		 * markera u som besökt
		 * actlevell = tom mängde
		 * lägg till u till actlevel
		 * 		så länge actlevel inte är tom
		 * 			nextlevel = tom mängd
		 * 			för varje nod w i actlevel
		 * 				om w = v
		 */
		int dist = 0;
		Set<String> visited_nodes = new HashSet<String>();
		Set<String> actual_level = new HashSet<String>();
		
		actual_level.add(source);
		visited_nodes.add(source);
		
		while(!actual_level.isEmpty()) {
		
			HashSet<String> next_level = new HashSet<>();
			
			for(String w : actual_level) {
			
				if(w.equals(dest)) return dist;
				
				for(String n : g.adjacentTo(w)) {
				
					if(!visited_nodes.contains(n)) {
						visited_nodes.add(n);
						next_level.add(n);
					}
				}
			}
			dist++;
			actual_level = next_level;
		}
		return -1; // not found
	}

}
