package com.olo.keyworddriven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.olo.util.Commons;

public class TestBuilder {
	
	public Object[] create() throws Exception {
        return create(new File(TestBuilder.class.getClassLoader().getResource(".").getPath()));
    }
	
	public Object[] create(final File directoryOrFile) throws Exception {
        return getKeywordTests(directoryOrFile).toArray();
    }
	
	private List<Object> getKeywordTests(final File directoryOrFile) throws Exception{
		List<Object> keywordDrivenTests = new ArrayList<Object>();
		if (!directoryOrFile.exists()) {
			throw new IllegalArgumentException("file does not exist "+directoryOrFile);
		}
		
		if(directoryOrFile.isDirectory()){
			File[] files = directoryOrFile.listFiles();
			for (File file : files) {
				keywordDrivenTests.addAll(getKeywordTests(file));
			}
		}else{
			String fileName = directoryOrFile.getName();
			if(fileName.startsWith("KWD-") && (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))){
				if(Commons.getWorkbookFromXls(directoryOrFile).getSheet("dataProvider")!=null){
					keywordDrivenTests.add(new DataDrivenTestRunner(directoryOrFile.getAbsolutePath()));
				}else{
					keywordDrivenTests.add(new TestRunner(directoryOrFile.getAbsolutePath()));
				}
			}
		}
		
		return keywordDrivenTests;
	}

	
}
