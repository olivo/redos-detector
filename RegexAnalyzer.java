import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Process;
import java.lang.Runtime;
import java.util.HashMap;

class RegexAnalyzer {

    HashMap<String, Boolean> cache;
    final String regexFilename;
    final String resultFilename;

    public RegexAnalyzer() {
	this.cache = new HashMap<String, Boolean>();
	this.regexFilename =  "regex.txt";
	this.resultFilename =  "result.txt";
    }

    // Main method for checking whether a regular expression
    // is evil.
    public boolean isEvilRegex(String regex){
	// Return a result from the cache if it's available.
	if(cache.containsKey(regex)){
	    return cache.get(regex);
	}
	// Call the RXXR regex checker for new regular expressions.
	// The interaction is handled through files.
	File file = new File(this.regexFilename);
	try {
	    if(!file.exists()){
		file.createNewFile();
	    }
	} catch(IOException e){
	    System.out.println("Could not create file: " + regexFilename);
	    return false;
	}
	
	// Write the regular expression to the file.
	try {
	    FileWriter fw = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(regex);
	    bw.close();
	} catch(IOException e){
	    System.out.println("Could not write to file: " + regexFilename);
	    return false;
	}

	// Call the RXXR regular expression checker.
	try {
	    Runtime rt = Runtime.getRuntime();
	    Process pr = rt.exec("./regex_checker/rxxr/code/scan.bin -i regex.txt > " + resultFilename);
	    // Read the output of RXXR.
	    File outFile = new File(resultFilename);
	    FileReader fr = new FileReader(outFile);
	    BufferedReader br = new BufferedReader(fr);
	    String line;
	    boolean vulnerable = false;
	    while((line = br.readLine()) != null){
		if(line.contains("VULNERABLE: YES")){
		    vulnerable = true;
		    break;
		}
	    }
	    cache.put(regex, vulnerable);
	    return vulnerable;
	} catch(IOException e){
	    System.out.println("Could not run the RXXR regex checker.");
	    return false;
	}
    }
}
