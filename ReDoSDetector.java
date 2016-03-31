import java.io.File;
import java.util.ArrayList;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.android.config.SootConfigForAndroid;
import soot.jimple.infoflow.entryPointCreators.AndroidEntryPointCreator;
import soot.jimple.infoflow.Infoflow;
import soot.jimple.infoflow.results.InfoflowResults;
import soot.jimple.infoflow.source.DefaultSourceSinkManager;
import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
import soot.options.Options;
import soot.Scene;

class ReDoSDetector {

    public static void main(String[] args) throws Exception{

	System.out.println("=== Starting ReDoS Detector ===");

	// Configuration information.
	String androidJar = "soot/platforms";
	String apkFileLocation = "benchmarks/com.facebook.katana.apk";
	boolean forceAndroidJar = false;

	SetupApplication app = new SetupApplication(androidJar, apkFileLocation);
	EasyTaintWrapper easyTaintWrapper = new EasyTaintWrapper(new File("EasyTaintWrapperSource.txt"));
	app.setTaintWrapper(easyTaintWrapper);
	app.calculateSourcesSinksEntrypoints("SourcesAndSinks.txt");
	app.runInfoflow();
	
	System.out.println("=== Finishing ReDoS Detector ===");
    }
}
