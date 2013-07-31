package com.olo.util;

import static com.olo.util.PropertyReader.allProp;
import static com.olo.util.PropertyReader.messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.olo.exceptions.KeywordConfigurationException;
import com.olo.propobject.KeywordPropObject;


public class Commons {
	
	private static final Logger logger = LogManager.getLogger(Commons.class.getName());
	
	public static final Random randomGenerator = new Random();
	public static final SimpleDateFormat defaultFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static final Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
	
	public static Workbook getWorkbookFromXls(String xlsPath) throws Exception{
		try {
			InputStream inputStream = new FileInputStream(xlsPath);
			Workbook workBook=null;
			if(xlsPath.substring(xlsPath.lastIndexOf(".")).equalsIgnoreCase(".xls")){
				workBook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
			}else{
				workBook = new XSSFWorkbook(inputStream);
			}
			return workBook;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static Workbook getWorkbookFromXls(File xlsFile) throws Exception{
		try {
			InputStream inputStream = new FileInputStream(xlsFile);
			Workbook workBook=null;
			String FileName = xlsFile.getName();
			if(FileName.substring(FileName.lastIndexOf(".")).equalsIgnoreCase(".xls")){
				workBook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
			}else{
				workBook = new XSSFWorkbook(inputStream);
			}
			return workBook;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static Workbook getWorkbookFromXls(URL xlsFileURL) throws Exception{
		try {
			File xlsFile = new File(xlsFileURL.toURI());
			Workbook workBook = getWorkbookFromXls(xlsFile);
			return workBook;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static Sheet getSheetFromXlsPath(String xlsPath) throws Exception {
		try {
			Workbook workBook=getWorkbookFromXls(xlsPath);
			Sheet sheet = workBook.getSheetAt(0);
			return sheet;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static Sheet getSheetFromXlsPath(File xlsFile) throws Exception {
		try {
			Workbook workBook=getWorkbookFromXls(xlsFile);
			Sheet sheet = workBook.getSheetAt(0);
			return sheet;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static Sheet getSheetFromXlsPath(URL xlsFileURL) throws Exception {
		try {
			Workbook workBook=getWorkbookFromXls(xlsFileURL);
			Sheet sheet = workBook.getSheetAt(0);
			return sheet;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public ArrayList<HashMap<String,String>> getDataProviderSheetData(String xlsPath) throws Exception {
		Sheet sheet = getSheetFromXlsPath(xlsPath);
		ArrayList<HashMap<String,String>> dataProvider = new ArrayList<HashMap<String,String>>();
		Iterator<Row> rows = sheet.rowIterator();
		ArrayList<String> headers = new ArrayList<String>();
		int columnCount = -1;
		Row headerRow = sheet.getRow(0);
		while (headerRow.getCell(++columnCount) != null) {
			headers.add(headerRow.getCell(columnCount).toString());
		}
		getDataProviderData(headers, dataProvider, rows);
		return dataProvider;
	}
	
	public ArrayList<HashMap<String,String>> getDataProviderSheetData(URL xlsPathURL) throws Exception {
		Sheet sheet = getSheetFromXlsPath(xlsPathURL);
		ArrayList<HashMap<String,String>> dataProvider = new ArrayList<HashMap<String,String>>();
		Iterator<Row> rows = sheet.rowIterator();
		ArrayList<String> headers = new ArrayList<String>();
		int columnCount = -1;
		Row headerRow = sheet.getRow(0);
		while (headerRow.getCell(++columnCount) != null) {
			headers.add(headerRow.getCell(columnCount).toString());
		}
		getDataProviderData(headers, dataProvider, rows);
		return dataProvider;
	}
	
	public ArrayList<KeywordPropObject> getExcelSteps(String xlsPath) throws Exception {
		ArrayList<KeywordPropObject> excelSteps = new ArrayList<KeywordPropObject>();
		Iterator<Row> rows = getRowsInExcel(xlsPath);
		rows.next();
		excelSteps.addAll(getStepsAsPropObject(rows));
		return excelSteps;
	}
	
	public ArrayList<KeywordPropObject> getExcelSteps(File xlsFile) throws Exception {
		ArrayList<KeywordPropObject> excelSteps = new ArrayList<KeywordPropObject>();
		Iterator<Row> rows = getRowsInExcel(xlsFile);
		rows.next();
		excelSteps.addAll(getStepsAsPropObject(rows));
		return excelSteps;
	}
	
	public ArrayList<KeywordPropObject> getExcelSteps(URL xlsFileURL) throws Exception {
		ArrayList<KeywordPropObject> excelSteps = new ArrayList<KeywordPropObject>();
		Iterator<Row> rows = getRowsInExcel(xlsFileURL);
		rows.next();
		excelSteps.addAll(getStepsAsPropObject(rows));
		return excelSteps;
	}
	
	private  ArrayList<KeywordPropObject> getStepsAsPropObject(Iterator<Row> rows) throws Exception{
		ArrayList<KeywordPropObject> excelRowsAsProbObject = new ArrayList<KeywordPropObject>();
		while (rows.hasNext()) {
			Row row = rows.next();
			KeywordPropObject prop = getKeywordPropObject(row);
			if(prop!=null){
				if(prop.getAction().equalsIgnoreCase("Else") || prop.getAction().equalsIgnoreCase("EndIf")){
					prop.setSkip(true);
					excelRowsAsProbObject.add(prop);
				}else if(prop.getAction().equalsIgnoreCase("StartDataTable")){
					prop.setSkip(true);
					excelRowsAsProbObject.add(prop);
					try {
						excelRowsAsProbObject.addAll(startDataProviderSteps(rows,Commons.class.getResource(prop.getValue())));
					}catch (KeywordConfigurationException e) {
						throw new Exception(e.getMessage());	
					} catch (Exception e) {
						throw e;
					}
					KeywordPropObject lprop = new KeywordPropObject();
					lprop.setAction("EndDataTable");
					lprop.setSkip(true);
					excelRowsAsProbObject.add(lprop);
				}else if(prop.getAction().equalsIgnoreCase("IncludeFile")){
					prop.setSkip(true);
					excelRowsAsProbObject.add(prop);
					excelRowsAsProbObject.addAll(getExcelSteps(Commons.class.getResource(prop.getValue())));
				}else if(prop.getAction().equalsIgnoreCase("EndDataTable")){
					break;
				}else{
					if(!prop.getPropertyName().isEmpty()){
						String property = prop.getPropertyName();
						String propFile = property.substring(0, property.indexOf("."));
						String propName = property.substring(property.indexOf(".")+1);
						if(allProp.containsKey(propFile)){
							if(allProp.get(propFile).containsKey(propName)){
								prop.setPropertyValue(allProp.get(propFile).getProperty(propName));
							}else{
								throw new KeywordConfigurationException("Missing Property Name at Line Number : "+(row.getRowNum()+1));
							}
						}else{
							throw new KeywordConfigurationException("Missing Property Name at Line Number : "+(row.getRowNum()+1));
						}
					}
					try {
						prop.setActualValue(Commons.replaceMessageMatchers(prop.getValue()));
					} catch (KeywordConfigurationException e) {
						throw new Exception(e.getMessage()+ " at Line Number "+(row.getRowNum()+1));
					} catch (Exception e) {
						throw e;
					}
					
					excelRowsAsProbObject.add(prop);
				}
			}
		}
		return excelRowsAsProbObject;
	}
	
	public Iterator<Row> getRowsInExcel(String xlsPath) throws Exception{
		Sheet sheet = getSheetFromXlsPath(xlsPath);
		Iterator<Row> rows = sheet.rowIterator();
		return rows;
	}
	
	public Iterator<Row> getRowsInExcel(File xlsFile) throws Exception{
		Sheet sheet = getSheetFromXlsPath(xlsFile);
		Iterator<Row> rows = sheet.rowIterator();
		return rows;
	}
	
	public Iterator<Row> getRowsInExcel(URL xlsFileURL) throws Exception{
		Sheet sheet = getSheetFromXlsPath(xlsFileURL);
		Iterator<Row> rows = sheet.rowIterator();
		return rows;
	}
	
	public KeywordPropObject getKeywordPropObject(Row row){
		KeywordPropObject prop = new KeywordPropObject();
		String locatorName = row.getCell(0) == null ? "" : row.getCell(0).toString().trim();
		String action = row.getCell(1) == null ? "" : row.getCell(1).toString().trim();
		String value = getCellValue(row,2);
		String options = row.getCell(3) == null ? "" : row.getCell(3).toString().trim();
		if (!(locatorName + value + action).trim().equals("")) {
			prop.setPropertyName(locatorName);
			prop.setValue(value);
			prop.setActualValue(value);
			prop.setAction(action);
			prop.setOptions(options);
			return prop;
		}else{
			return null;
		}
	}
	
	public String getCellValue(Row row,int cellNum){
		String value="";
		if(row.getCell(cellNum)!= null && row.getCell(cellNum).getCellType()==Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(row.getCell(cellNum))) {
		    value = String.valueOf(defaultFormat.format(row.getCell(cellNum).getDateCellValue()));
		} else if(row.getCell(cellNum)!= null && row.getCell(cellNum).getCellType()==Cell.CELL_TYPE_NUMERIC && !DateUtil.isCellDateFormatted(row.getCell(cellNum))) {
			value = row.getCell(cellNum) == null ? "" : String.valueOf(row.getCell(cellNum).getNumericCellValue());
			if(value.endsWith(".0")){
				value = String.valueOf(Float.valueOf(value).intValue());
			}
		}else {
			value = row.getCell(cellNum) == null ? "" : row.getCell(cellNum).getStringCellValue();
		}
		value=value.trim();
		return value;
	}

	public ArrayList<KeywordPropObject> startDataProviderSteps(Iterator<Row> rows,String xlsPath) throws Exception{
		ArrayList<HashMap<String,String>> dataProvider = getDataProviderSheetData(xlsPath);
		ArrayList<KeywordPropObject> startDataProviderSteps = new ArrayList<KeywordPropObject>();
		ArrayList<KeywordPropObject> lTemplate;
		ArrayList<KeywordPropObject> mTemplate = getDataProviderTemplate(rows);
		for (int i = 0; i < dataProvider.size(); i++) {
			HashMap<String,String> localSteps = dataProvider.get(i);
			lTemplate = new ArrayList<KeywordPropObject>(mTemplate);
			for (KeywordPropObject lPropObj : lTemplate) {
				KeywordPropObject localStep = lPropObj.clone();
				localStep.setActualValue(Commons.replaceDataSetHeaderMatchers(localStep.getValue(), localSteps));
				startDataProviderSteps.add(localStep);
			}
		}
		
		return startDataProviderSteps;
	}
	
	public ArrayList<KeywordPropObject> startDataProviderSteps(Iterator<Row> rows,URL xlsPathURL) throws Exception{
		ArrayList<HashMap<String,String>> dataProvider = getDataProviderSheetData(xlsPathURL);
		ArrayList<KeywordPropObject> startDataProviderSteps = new ArrayList<KeywordPropObject>();
		ArrayList<KeywordPropObject> lTemplate;
		ArrayList<KeywordPropObject> mTemplate = getDataProviderTemplate(rows);
		for (int i = 0; i < dataProvider.size(); i++) {
			HashMap<String,String> localSteps = dataProvider.get(i);
			lTemplate = new ArrayList<KeywordPropObject>(mTemplate);
			for (KeywordPropObject lPropObj : lTemplate) {
				KeywordPropObject localStep = lPropObj.clone();
				localStep.setActualValue(Commons.replaceDataSetHeaderMatchers(localStep.getValue(), localSteps));
				startDataProviderSteps.add(localStep);
			}
		}
		
		return startDataProviderSteps;
	}
	
	public ArrayList<KeywordPropObject> getDataProviderTemplate(Iterator<Row> rows) throws Exception {
		ArrayList<KeywordPropObject> lDataProviderTemplate = new ArrayList<KeywordPropObject>();
		lDataProviderTemplate.addAll(getStepsAsPropObject(rows));
		return lDataProviderTemplate;
	}
	
	private void getDataProviderData(ArrayList<String> headers,
			ArrayList<HashMap<String,String>> dataProvider, Iterator<Row> rows) {
		Row row = rows.next();
		while (rows.hasNext()) {
			HashMap<String,String> rowData = new HashMap<String,String>();
			row = rows.next();
			for (int i = 0; i < headers.size(); i++) {
				rowData.put(headers.get(i), getCellValue(row,i));
			}
			boolean addRowtoTotalData = false;
			for (int i = 0; i < headers.size(); i++) {
				if (rowData.get(headers.get(i)) != "") {
					addRowtoTotalData = true;
					break;
				}
			}
			if (addRowtoTotalData) {
				dataProvider.add(rowData);
			}
		}
	}
	
	public static ArrayList<String> getExcelfilesInFolder(String folderName) {
		ArrayList<String> pathOfFiles = new ArrayList<String>();
		File folder = new File(folderName);
		if (!folder.exists()) {
			logger.info("Skipping Folder because of folder does not exist : "+ folderName);
			return pathOfFiles;
		}
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles.length == 0) {
			logger.info("Skipping Folder because of no tests exist under folder : "+ folderName);
			return pathOfFiles;
		}
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileExtension = listOfFiles[i].getName().substring(
						listOfFiles[i].getName().lastIndexOf("."),
						listOfFiles[i].getName().length());
				if (fileExtension.equalsIgnoreCase(".xls") || fileExtension.equalsIgnoreCase(".xlsx")) {
					pathOfFiles.add(listOfFiles[i].getPath());
				}
			}
		}
		return pathOfFiles;
	}
	
	public static boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }catch( Exception e ) {
	        return false;
	    }
	}
	
	public static Date getDateInstanceForDay(int dayNumber){
		return DateUtils.addDays(new Date(), -dayNumber);
	}
	
	public static Long getStartOfDay(Date date) {
	    return DateUtils.truncate(date, Calendar.DATE).getTime();
	}
	
	public static Long getEndOfDay(Date date) {
	    return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1).getTime();
	}
	
	public static String percentageCalculator(int total,int whatis){
		float percent = (whatis * 100.0f) / total;
		return String.format("%.1f", percent);
	}

	public static String appendRandomNumber(String value){
		return value+getRandomNumber();
	}
	
	public static String getRandomNumber(){
		return String.valueOf(randomGenerator.nextInt(100000));
	}
	
	public static boolean expectedValueCheck(String expectedValue,String actualValue){
        StringBuffer sb = new StringBuffer(expectedValue);
        Matcher matcher = pattern.matcher(sb);
        while(matcher.find()){
               if(matcher.group(1).equals("!")){
                     int start = matcher.start();
                     int end=start + 5;
                     if(actualValue.startsWith(sb.substring(0, start)) && actualValue.endsWith(sb.substring(end))){
                            return true;
                     }else{
                            return false;
                     }
               }
        }
        if(sb.toString().equals(actualValue))
        	return true;
        else
        	return false;
	}
	
	public static String replaceStoredMatchers(String expectedValue,HashMap<String,String> storedData) throws Exception{
		StringBuffer sb = new StringBuffer(expectedValue);
		Matcher matcher = pattern.matcher(sb);
		
		while(matcher.find()){
			int matchIndexStart=matcher.start();
			int matchIndexEnd=matcher.end();
			String matchedValue=matcher.group(1);
			if(matchedValue.startsWith("get.")){
				String StoredKey = matchedValue.substring(4, matchedValue.length());
				if(storedData.containsKey(StoredKey)){
					sb.replace(matchIndexStart, matchIndexEnd, storedData.get(StoredKey));
					matcher = pattern.matcher(sb);
				}else{
					throw new KeywordConfigurationException("Haven't stored any value with : "+StoredKey);
				}
				
			}
		}
		return sb.toString();
	}
	
	public static String replaceMessageMatchers(String expectedValue) throws Exception{
		StringBuffer sb = new StringBuffer(expectedValue);
		Matcher matcher = pattern.matcher(sb);
		
		while(matcher.find()){
			int matchIndexStart=matcher.start();
			int matchIndexEnd=matcher.end();
			String matchedValue=matcher.group(1);
			if(matchedValue.startsWith("messages.")){
				String propFileKey=matchedValue.substring(9, matchedValue.indexOf(".", 9));
				String propValueKey=matchedValue.substring(matchedValue.indexOf(".", 9)+1);
				if(messages.containsKey(propFileKey) && messages.get(propFileKey).containsKey(propValueKey)){
					sb.replace(matchIndexStart, matchIndexEnd, messages.get(propFileKey).getProperty(propValueKey));
					matcher = pattern.matcher(sb);
				}else{
					throw new KeywordConfigurationException("Property in Value column Not Found");
				}
			}
		}
		return sb.toString();
	}
	
	public static String replaceDataSetHeaderMatchers(String expectedValue,HashMap<String,String> dataSetHeader) throws Exception{
		StringBuffer sb = new StringBuffer(expectedValue);
		Matcher matcher = pattern.matcher(sb);
		
		while(matcher.find()){
			int matchIndexStart=matcher.start();
			int matchIndexEnd=matcher.end();
			String matchedValue=matcher.group(1);
			if(matchedValue.startsWith("datatable.")){
				String dataSetKey = matchedValue.substring(10, matchedValue.length());
				if(dataSetHeader.containsKey(dataSetKey)){
					sb.replace(matchIndexStart, matchIndexEnd, dataSetHeader.get(dataSetKey));
					matcher = pattern.matcher(sb);
				}else{
					throw new KeywordConfigurationException("Missing Header in DataTable File: "+dataSetKey);
				}
				
			}
		}
		return sb.toString();
	}
	

}
