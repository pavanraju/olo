package com.olo.keyworddriven;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class TestBuilder {
	
	public Object[] create() {
        return create(new File(TestBuilder.class.getClassLoader().getResource(".").getPath()));
    }
	
	public Object[] create(final File baseDirectory) {
		List<Object> keywordDrivenTests = new ArrayList<Object>();
        HashMap<String, String> keywordTests = getKeywordTests(baseDirectory);
        Iterator<Entry<String, String>> it = keywordTests.entrySet().iterator();
        while(it.hasNext()){
        	Entry<String, String> eachTest = it.next();
        	keywordDrivenTests.add(new Runner(eachTest.getKey(), eachTest.getValue()));
        }
        return keywordDrivenTests.toArray();
    }
	
	public HashMap<String, String> getKeywordTests(final File baseDirectory){
		
		HashMap<String, String> testFiles = new HashMap<String, String>();
		if (!baseDirectory.exists()) {
			throw new IllegalArgumentException("file does not exist ");
		}
		
		if(baseDirectory.isDirectory()){
			File[] files = baseDirectory.listFiles();
			for (File file : files) {
				testFiles.putAll(getKeywordTests(file));
			}
		}else{
			String fileName = baseDirectory.getName();
			if(fileName.startsWith("KWD-") && (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))){
				testFiles.put(fileName, baseDirectory.getAbsolutePath());
			}
		}
		
		return testFiles;
	}

	
}
