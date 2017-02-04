package wordladders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		WordLadderGraph wlg = new WordLadderGraph("words-7.txt", new OneLetterDifference());
	}
	
	public static void processRequests(String wordfile, String infile, GraphStrategy strategy) {
		WordLadderGraph wlg = new WordLadderGraph(wordfile, strategy);
		try(Scanner scan = new Scanner(new File(infile))) {
			while(scan.hasNext()) {
				String src = scan.next();
				String dst = scan.next();
				int dist = ShortestPath.shortestPath(wlg, src, dst);
				System.out.println(dist);
			} 
		}
		catch (FileNotFoundException fe) {
			System.out.println("File not found. Message: " + fe.getMessage());
			System.err.println(fe.getStackTrace());
			System.exit(1);
		}
	}

}
