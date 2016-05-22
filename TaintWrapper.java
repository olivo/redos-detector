import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.jimple.*;
import soot.jimple.infoflow.data.Abstraction;
import soot.jimple.infoflow.data.AccessPath;
import soot.jimple.infoflow.data.AccessPathFactory;
import soot.jimple.infoflow.taintWrappers.AbstractTaintWrapper;
import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
import soot.SootMethod;
import soot.Value;

class TaintWrapper extends AbstractTaintWrapper {

    private final EasyTaintWrapper easyTaintWrapper;

    private final RegexAnalyzer regexAnalyzer;

    public TaintWrapper(String easyTaintWrapperFilename){
	EasyTaintWrapper etw = null;
	try{
	    etw = new EasyTaintWrapper(new File(easyTaintWrapperFilename));
	} catch(IOException e){
	    System.out.println("ERROR: Could not create taint wrapper from file: " + easyTaintWrapperFilename);
	}
	this.easyTaintWrapper = etw;
	this.regexAnalyzer = new RegexAnalyzer();
    }

    private boolean anyTainted(AccessPath taintedPath, List<Value> values){
	Value taintedValue = taintedPath.getPlainValue();
	for(Value val: values){
	    if(taintedValue.equals(val)){
		return true;
	    }
	}
	return false;
    }

    @Override
    public Set<Abstraction> getAliasesForMethod(Stmt stmt, Abstraction a, Abstraction taintedPath){
	return null;
    }

    @Override
    public Set<AccessPath> getTaintsForMethodInternal(Stmt stmt, AccessPath taintedPath){
	Set<AccessPath> taints = new HashSet<AccessPath>();
	SootMethod method = stmt.getInvokeExpr().getMethod();
	// Propagating taint from easy taint wrapper.
	taints.addAll(easyTaintWrapper.getTaintsForMethodInternal(stmt, taintedPath));

	// Propagating custom taint over the receiving object
	// of a call to put with a tainted value.
	if(method.getSignature().equals("<java.util.regex.Pattern: java.util.regex.Matcher matcher(java.lang.CharSequence)>")){
		System.out.println("FOUND SINK!");
	}
	/*
	if(method.getSignature().equals("<java.util.EnumMap: java.lang.Object put(java.lang.Enum,java.lang.Object)>")){
	    InvokeExpr invokeExpr = stmt.getInvokeExpr();
	    InstanceInvokeExpr instanceInvokeExpr = (InstanceInvokeExpr)invokeExpr;
	    Value base = instanceInvokeExpr.getBase();
	    List<Value> args = instanceInvokeExpr.getArgs();
	    if(anyTainted(taintedPath, args)){
	       taints.add(AccessPathFactory.v().createAccessPath(base, true));
	    }
	}
	*/

	return taints;
    }

    @Override
    public boolean isExclusiveInternal(Stmt stmt, AccessPath taintedPath){
	return false;
    }

    @Override
    public boolean supportsCallee(SootMethod method){
	return true;
    }

    @Override
    public boolean supportsCallee(Stmt callSite){
	return true;
    }
}
