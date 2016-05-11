import java.util.HashMap;
class RegexAnalyzer {

    HashMap<String, Boolean> cache = new HashMap<String, Boolean>();

    public boolean isEvilRegex(String regex){
	if(cache.containsKey(regex)){
	    return cache.get(regex);
	}
	cache.put(regex, false);
	return false;
    }

}
