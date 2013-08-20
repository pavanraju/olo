package com.olo.keyworddriven;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class DataDrivenBuilder extends TestBuilder{
	
	public Object[] create(final File baseDirectory) {
		List<Object> keywordDrivenTests = new ArrayList<Object>();
        HashMap<String, String> keywordTests = getKeywordTests(baseDirectory);
        Iterator<Entry<String, String>> it = keywordTests.entrySet().iterator();
        while(it.hasNext()){
        	Entry<String, String> eachTest = it.next();
        	keywordDrivenTests.add(new DataDrivenRunner(eachTest.getKey(), eachTest.getValue()));
        }
        return keywordDrivenTests.toArray();
    }
	
}
