package wordladdertests;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import wordladders.GraphStrategy;
import wordladders.Main;
import wordladders.OneLetterDifference;

import org.junit.After;
import org.junit.Before;

public class TestOneLetterStrategy {
	String wordfile;
	String infile;
	String outfile;

	@Test
	public void test7words() {
		wordfile = "words-7.txt";
		infile = "words-7-test.in";
		outfile = "words-7-test.out";
		runTest();
	}
	
	@Test
	public void test5757words() {
		wordfile = "words-5757.txt";
		infile = "words-5757-test.in";
		outfile = "words-5757-test.out";
		runTest();
	}
	
	private void runTest() {
		PrintStream oldOut = System.out;
		File myResult = new File("mysolution-" + wordfile);
		PrintStream ps = null;
		try {
			ps = new PrintStream(myResult);
		}
		catch(FileNotFoundException e) {
			System.err.println(e);
		}
		System.setOut(ps);
// anv. lambda-uttryck direkt här istället
		Main.processRequests(wordfile, infile, (w1, w2) -> {
			if(w1.length() != w2.length() || w1.equals(w2)) return false;
			else {
				int diff = 0;
				for(int i = 0; i < w1.length() && diff < 2; i++) {
					if (w1.charAt(i) != w2.charAt(i)) diff++;
				}
				return diff == 1;
			}
		});
		
		System.setOut(oldOut);
		File solution = new File(outfile);		
		assertTrue(FileContents.fileCompare(solution,myResult));
	}

}
