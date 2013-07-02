package com.olo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SearchAndReplace {

private static String testsFolder="Tests",propFolder="config",controlName,action,newControlName=null, oldControlName=null,oldAction=null,newAction=null,controlKey=null,actionKey=null,xlsFile;
private static int currentExcelRow,keyCount=0,fileCount=0,unusedCount=0;
private static ArrayList<String> propArray = new ArrayList<String>();
private static int option;

	
	public static void main(String args[]) throws InterruptedException,
	ParseException, IOException {
		while(true) {
			 keyCount=0;
			 fileCount=0;
			 unusedCount=0;
			 oldControlName=null;
			 oldAction=null;
			 newControlName=null;
			 newAction=null;
			 actionKey=null;
			 controlKey=null;
			 
			 System.out.println("Please choose among the options 1,2,3,4,5,6,7"+"\n"+"1:Replace an old Control Name"+"\n"+"2:Replace an old Action"+"\n"+"3:Find xls file for a specified Control Name"+"\n"+"4:Find xls file for a specified Action"+"\n"+"5.Find unused Control Names and their location in the properties file"+"\n"+"6.Replace Dynamic Keywords"+"\n"+"7:Find xls file for a specified Dynamic Control Action"+"\n"+"8:Exit");
			 Scanner scan = new Scanner(System.in);
			 try {
			   option=scan.nextInt();
			  if(option>=1 && option <=8) {
			    switch(option) {
			    case 1: System.out.println("Please enter Old ControlName  :");
			        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			        oldControlName = bf.readLine();
			        System.out.println("The Old Control Name entered is :");
			        System.out.println(oldControlName);
			        System.out.println("Please enter New ControlName  :");
			        BufferedReader bf1 = new BufferedReader(new InputStreamReader(System.in));
			        newControlName = bf1.readLine();
			        if(newControlName.isEmpty()) {
			        	System.out.println("You cannot replace with an empty value"+"\n"+"Please enter desired Control name and then press enter");
			        	BufferedReader bf2 = new BufferedReader(new InputStreamReader(System.in));
				        if(bf2.readLine().isEmpty()) {
				        	System.out.println("Replacing with empty value not allowed"+"\n"+"Exiting.....");
				        	System.exit(0);
				        }
				        else {
				        	newControlName = bf2.readLine();
				        }
			        }
			        System.out.println("The New Control Name entered is :");
			        System.out.println(newControlName);
			        break;
			        
			    case 2: System.out.println("Please enter Old Action  :");
			        BufferedReader bf3 = new BufferedReader(new InputStreamReader(System.in));
			        oldAction = bf3.readLine();
			        System.out.println("The Old Action entered is :");
			        System.out.println(oldAction);
			        System.out.println("Please enter New Action  :");
			        BufferedReader bf4= new BufferedReader(new InputStreamReader(System.in));
			        newAction = bf4.readLine();
			        if(newAction.isEmpty()) {
			        	System.out.println("You cannot replace with an empty value"+"\n"+"Please enter desired action and then press enter");
			        	BufferedReader bf5 = new BufferedReader(new InputStreamReader(System.in));
				        if(bf5.readLine().isEmpty()) {
				        	System.out.println("Replacing with empty value not allowed"+"\n"+"Exiting.....");
				        	System.exit(0);
				        }
				        else {
				        	newAction = bf5.readLine();
				        }
			        }
			        System.out.println("The New Control Action entered is :");
			        System.out.println(newAction);
				    break;
			    case 3: System.out.println("Please enter Control Name whose location is required :");
			        BufferedReader bf6= new BufferedReader(new InputStreamReader(System.in));
	                controlKey = bf6.readLine();
	                System.out.println("Entered Control key is:"+controlKey);
	                break;
			
			    case 4: System.out.println("Please enter Action whose location is required :");
	                BufferedReader bf7= new BufferedReader(new InputStreamReader(System.in));
	                actionKey = bf7.readLine();
	                System.out.println("Entered Action key is:"+actionKey);
	                break;
			    case 5: System.out.println("Searching for the unused Control Names...");
			         readProperty(propFolder);
			         System.out.println("The total no. of unused Control Names is "+unusedCount);
			         break;
			    
			    case 6: System.out.println("Please enter Old Dynamic Action  :");
		        BufferedReader bn6 = new BufferedReader(new InputStreamReader(System.in));
		        oldAction = bn6.readLine();
		        System.out.println("The Old Dynamic Action entered is :");
		        System.out.println(oldAction);
		        System.out.println("Please enter New Dynamic Action  :");
		        BufferedReader b6= new BufferedReader(new InputStreamReader(System.in));
		        newAction = b6.readLine();
		        if(newAction.isEmpty()) {
		        	System.out.println("You cannot replace with an empty value"+"\n"+"Please enter desired action and then press enter");
		        	BufferedReader b5 = new BufferedReader(new InputStreamReader(System.in));
			        if(b5.readLine().isEmpty()) {
			        	System.out.println("Replacing with empty value not allowed"+"\n"+"Exiting.....");
			        	System.exit(0);
			        }
			        else {
			        	newAction = b5.readLine();
			        }
		        }
		        System.out.println("The New Dynamic Control Action entered is :");
		        System.out.println(newAction);
			    break;
			    
				case 7: System.out.println("Please enter Dynamic Action whose location is required :");
	                BufferedReader bn= new BufferedReader(new InputStreamReader(System.in));
	                actionKey = bn.readLine();
	                System.out.println("Entered Action key is:"+ actionKey);
	                break;
				
				
				case 8: System.exit(0);
			  }
			 
			    folderTest(testsFolder);
			    if(option==3|option==4|option==7) {
			    	System.out.println("Total no. of occurences in all the files for the specified key is: "+keyCount);
			    	System.out.println("Total no. of files where the key occurs is: "+fileCount);
			    }
			  }  else {
				  System.out.println("You have entered an invalid option....Please enter a value from 1 to 6");
			  }
			}catch(Exception e) {
				 System.out.println("You have entered an invalid option....Please enter an integer value from 1 to 6");
			}
			System.out.println("\n"+"\n");
			}
		}



	public static void ModifyExcel(String xlsPath){

			try{
				int count=0;
				int flag=0;
				
				//InputStream inp = new FileInputStream(xlsPath);
				//POIFSFileSystem fileSystem = new POIFSFileSystem(inp);
				//Workbook workBook = PlmCommons.getDataProviderSheetData(xlsPath);
				Workbook workBook=Commons.getWorkbookFromXls(xlsPath);
				Sheet sheet = workBook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				Row row;
				rows.next();
			    while (rows.hasNext()) {
					row = (Row) rows.next();
				   currentExcelRow = row.getRowNum();
				// once get a row its time to iterate through cells.
				   Iterator<Cell> cells = row.cellIterator();
				   Cell cell;
				   while (cells.hasNext()) { 
					cell = (Cell) cells.next();
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							RichTextString richTextString = cell.getRichStringCellValue();
							if (cell.getColumnIndex() == 0) {
								controlName = richTextString.getString();
								controlName = controlName.trim();
								propArray.add(controlName);
								if(controlName.equals(oldControlName)&&(controlName != null))
								{
									controlName=newControlName;
									System.out.println("The replacement is made in Row Number "+currentExcelRow+"  in the excel file "+xlsPath);
									cell.setCellValue(controlName);
									count=1;
								}
							    if(controlName.equals(controlKey)&&(controlName != null))
							    {
								    xlsFile=xlsPath;
								    keyCount++;
								    if(flag==0) {
								    	System.out.println("The Excel File where the control key occurs is: "+xlsFile);
								    	fileCount++;
								    	flag=1;
								    }
								    
				
							    }
							}else if (cell.getColumnIndex() == 1) {
								action = richTextString.getString();
								action = action.trim();
								
								if(option==2){
								if(action.equals(oldAction)&&(action!= null))
								{
									action=newAction;
									System.out.println("The replacement is made in Row Number "+currentExcelRow+"  in the excel file "+xlsPath);
									cell.setCellValue(action);
									count=1;
								}
								}
								
								if(option==6){
									if(action.startsWith(oldAction)&&(action!=null)) {
										if((action.equals(oldAction)==false)){
											action=action.replace(oldAction, newAction);
											//action=newAction;
											System.out.println("The replacement is made in Row Number "+currentExcelRow+"  in the excel file "+xlsPath);
											cell.setCellValue(action);
											count=1;
									    }
									}
								
								}
								
								if(option==4){
								  if(action.equals(actionKey)&&(action!= null)) {
									xlsFile=xlsPath;
								    keyCount++;
								    if(flag==0) {
								    	System.out.println("The excel File where the action key occurs is: "+xlsFile);
								    	fileCount++;
								    	flag=1;
								    }
								} 
								}
								
								
								if(option==7){
									if(action.startsWith(actionKey)&&(action!=null)) {
										if((action.equals(actionKey)==false)){
										xlsFile=xlsPath;
									    keyCount++;
									    if(flag==0) {
									    	System.out.println("The excel File where the dynamic action key occurs is: "+xlsFile);
									    	fileCount++;
									    	flag=1;
									    }
									}
									}
								}
						
							}
							
							break;
							
							
						case Cell.CELL_TYPE_NUMERIC:
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						default: 
							break;
					}
				  }
				}
			    if(flag==1){
			    	flag=0;
			    }
				if(count==1)
				{
					FileOutputStream fileOut = new FileOutputStream(xlsPath);
					workBook.write(fileOut);
				    fileOut.close();
				}
				//inp.close();
			}catch (Exception e) {
				System.out.println("The exception is  :"+e +"   The filename is "+xlsPath+"  And the row number is :"+currentExcelRow);
//				System.exit(0);
			}
		}

		
	public static void readProperty(String propFolder)  {
			File propDir=new File(propFolder);
			String[] filesinFolder = propDir.list();
			folderTest(testsFolder);
			for(int i= 0;i < filesinFolder.length; i++) {
				int lineNumber=0;
				try {
					if(!filesinFolder[i].contains("CVS")) {
						File file = new File(propFolder+"/"+filesinFolder[i]);      
						Scanner in = new Scanner(file);
						while (in.hasNextLine()) {
							lineNumber++;
							String thisLine=in.nextLine();
							if(!thisLine.isEmpty() && !thisLine.trim().startsWith("#")&& thisLine.contains("->")) {
								String[] tempProp=thisLine.split("=");
								if (!propArray.contains(tempProp[0].trim())) {
									System.out.println(tempProp[0].trim()+" is an unused Control Name and the property file in which it is present is " + filesinFolder[i]+" at line number "+lineNumber);
									unusedCount++;
								}
							}  
						}
					}
				}catch (FileNotFoundException e) {
					System.out.println("Unable to read Property File");
					e.printStackTrace();
				}
			}
		}

	public static void folderTest(String testsFolder){
			File TestFolder = new File(testsFolder);
			String[] filesInDir = TestFolder.list();
			if (filesInDir != null) {
				String fName = "";
				for (int i = 0; i < filesInDir.length; i++) {
					fName = filesInDir[i];
					if(!fName.contains(".")){
						folderTest(testsFolder+"/"+fName);
					}else if (fName.endsWith(".xls") || fName.endsWith(".xlsx")) {
						fName = testsFolder + "/" + fName;
						ModifyExcel(fName);
					}
				}
			}
		}
}
