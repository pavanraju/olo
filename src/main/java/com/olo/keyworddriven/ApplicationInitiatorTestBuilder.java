package com.olo.keyworddriven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.olo.util.Commons;

public class ApplicationInitiatorTestBuilder {
	
	private Class<? extends Keywords> keywords;
	
	public Object[] create(String path) throws Exception {
		return create(path, Keywords.class);
    }
	
	public Object[] create(String path, Class<? extends Keywords> keywords) throws Exception {
		if(path.startsWith("/") || path.startsWith("\\")){
			return create(new File(ApplicationInitiatorTestBuilder.class.getResource(path).getPath()), keywords);
		}else{
			return create(new File(ApplicationInitiatorTestBuilder.class.getClassLoader().getResource(path).getPath()), keywords);
		}
    }
	
	public Object[] create(final File directoryOrFile) throws Exception {
		return create(directoryOrFile, Keywords.class);
    }
	
	public Object[] create(final File directoryOrFile, Class<? extends Keywords> keywords) throws Exception {
		this.keywords = keywords;
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
			if((fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))){
				if(Commons.getWorkbookFromXls(directoryOrFile).getSheet("dataProvider")!=null){
					keywordDrivenTests.add(new ApplicationInitiatorDataDrivenRunner(directoryOrFile.getAbsolutePath(),keywords));
				}else{
					keywordDrivenTests.add(new ApplicationInitiatorRunner(directoryOrFile.getAbsolutePath(),keywords));
				}
			}
		}
		return keywordDrivenTests;
	}
	
}
