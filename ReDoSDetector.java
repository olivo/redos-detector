import java.io.File;
import java.util.ArrayList;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.android.config.SootConfigForAndroid;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;
import soot.jimple.infoflow.android.source.AndroidSourceSinkManager;
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

	if(args.length > 0) {
	    apkFileLocation = args[0];
	}

	System.out.println("Analyzing APK: " + apkFileLocation);
	SetupApplication app = new SetupApplication(androidJar, apkFileLocation);
	EasyTaintWrapper easyTaintWrapper = new EasyTaintWrapper(new File("EasyTaintWrapperSource.txt"));
	TaintWrapper taintWrapper = new TaintWrapper(easyTaintWrapper);
	app.setTaintWrapper(taintWrapper);

	InfoflowAndroidConfiguration config = app.getConfig();

	config.setAccessPathLength(3);
	config.setComputeResultPaths(false);
	config.setEnableCallbacks(false);
	config.setEnableCallbackSources(false);
	config.setEnableArraySizeTainting(true);
	config.setEnableExceptionTracking(false);
	config.setEnableImplicitFlows(false);
	config.setEnableStaticFieldTracking(true);
	config.setEnableTypeChecking(true);
	config.setIgnoreFlowsInSystemPackages(true);
	config.setInspectSources(false);
	config.setInspectSinks(false);
	config.setFlowSensitiveAliasing(true);
	config.setLayoutMatchingMode(AndroidSourceSinkManager.LayoutMatchingMode.NoMatch);
	config.setMergeNeighbors(false);
	config.setOneResultPerAccessPath(false);
	config.setPathAgnosticResults(true);
	config.setStopAfterFirstFlow(true);
	config.setUseRecursiveAccessPaths(true);
	config.setUseThisChainReduction(true);
	config.setUseTypeTightening(true);

	app.setConfig(config);

	app.calculateSourcesSinksEntrypoints("SourcesAndSinks.txt");
	app.runInfoflow();
	
	System.out.println("=== Finishing ReDoS Detector ===");
    }
}
