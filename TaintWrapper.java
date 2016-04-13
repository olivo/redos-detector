import java.util.HashSet;
import java.util.Set;

import soot.jimple.*;
import soot.jimple.infoflow.data.Abstraction;
import soot.jimple.infoflow.data.AccessPath;
import soot.jimple.infoflow.taintWrappers.AbstractTaintWrapper;
import soot.jimple.infoflow.taintWrappers.EasyTaintWrapper;
import soot.SootMethod;

class TaintWrapper extends AbstractTaintWrapper {

    private EasyTaintWrapper easyTaintWrapper;

    public TaintWrapper(EasyTaintWrapper easyTaintWrapper){
	this.easyTaintWrapper = easyTaintWrapper;
    }

    @Override
    public Set<Abstraction> getAliasesForMethod(Stmt stmt, Abstraction a, Abstraction taintedPath){
	return null;
    }

    @Override
    public Set<AccessPath> getTaintsForMethodInternal(Stmt stmt, AccessPath taintedPath){
	Set<AccessPath> taints = new HashSet<AccessPath>();
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
