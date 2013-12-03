package com.olo.listeners;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.collections.Lists;
import org.testng.collections.Maps;
import org.testng.collections.Sets;
import org.testng.internal.MethodHelper;
import org.testng.internal.Utils;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.olo.reporter.Utility;

public class SuiteListener implements ISuiteListener{
	
	private static final Logger logger = LogManager.getLogger(SuiteListener.class.getName());
	
	private XmlSuite m_xmlSuite;
	
	public void onFinish(ISuite suite) {
		try {
			Map<String, ISuiteResult> results = suite.getResults();
			String suiteName = suite.getName();
			int suiteFailedTests = 0;
			int suitePassedTests = 0;
			int suiteSkippedTests = 0;
			int suiteTotalTests = 0;
			int ctr = 0;
			long suiteStartTime = 0;
			long suiteEndTime = 0;
			long temp = 0;
			StringBuffer textContextSummaryAndDetailedReport = new StringBuffer();
			StringBuffer suiteContextSummaryReport = new StringBuffer();
			StringBuffer passedtextContextSummaryReport = new StringBuffer();
			StringBuffer failedtextContextSummaryReport = new StringBuffer();
			StringBuffer skippedtextContextSummaryReport = new StringBuffer();
			
			StringBuffer passedTextContextReport = new StringBuffer();
			StringBuffer failedTextContextReport = new StringBuffer();
			StringBuffer skippedTextContextReport = new StringBuffer();
			
			StringBuffer errorModelWindow = Utility.getErrorModelWindow();
			
			for (ISuiteResult suiteResult : results.values()) {
				ITestContext suiteTestContext = suiteResult.getTestContext();
				if(ctr == 0){
					suiteStartTime = suiteTestContext.getStartDate().getTime();
					suiteEndTime = suiteTestContext.getEndDate().getTime();
					ctr++;
				}else{
					temp = suiteTestContext.getStartDate().getTime();
					if(temp < suiteStartTime){
						suiteStartTime = temp;
					}
					temp = suiteTestContext.getEndDate().getTime();
					if(temp > suiteEndTime){
						suiteEndTime = temp;
					}
				}
				int contextFailedTests=suiteTestContext.getFailedTests().size();
				suiteFailedTests+= contextFailedTests;
				int contextPassedTests=suiteTestContext.getPassedTests().size();
				suitePassedTests+= contextPassedTests;
				int contextSkippedTests=suiteTestContext.getSkippedTests().size();
				suiteSkippedTests+=contextSkippedTests;
				int contextTotalTests=contextPassedTests+contextFailedTests+contextSkippedTests;
				String testContextAsId = suiteTestContext.getName().replaceAll(" ", "-");
				suiteContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td><td class='danger'>"+contextFailedTests+"</td><td class='warning'>"+contextSkippedTests+"</td><th>"+contextTotalTests+"</th></tr>");
			    /**
			     * All Context
			     */
			    StringBuffer textContextSummary = Utility.contextSummaryReport(suiteTestContext);
			    textContextSummaryAndDetailedReport.append(textContextSummary);
			    StringBuffer currentTextContextDetailedReport = Utility.contextDetailedReport(suiteTestContext, false);
			    textContextSummaryAndDetailedReport.append(currentTextContextDetailedReport);
			    
			    /**
			     * Passed Context
			     */
			    
			    if(contextPassedTests>0){
			    	passedtextContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td></tr>");
			    	passedTextContextReport.append(Utility.passedContextDetailedReport(suiteTestContext));
			    }
			    
			    /**
			     * Failed Context
			     */
			    
			    if(contextFailedTests>0){
			    	failedtextContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='danger'>"+contextFailedTests+"</td></tr>");
			    	failedTextContextReport.append(Utility.failedContextDetailedReport(suiteTestContext));
			    }
			    
			    /**
			     * Skipped Context
			     */
			    
			    if(contextSkippedTests>0){
			    	skippedtextContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='warning'>"+contextSkippedTests+"</td></tr>");
			    	skippedTextContextReport.append(Utility.skippedContextDetailedReport(suiteTestContext));
			    }
			    
			}
			
			suiteTotalTests=suiteFailedTests+suitePassedTests+suiteSkippedTests;
		    
			StringBuffer suiteReportDetailed = new StringBuffer();
			suiteReportDetailed.append(Utility.getHtmlToHead());
			suiteReportDetailed.append("<title>"+suiteName+" Suite Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+Utility.getModelJs());
			suiteReportDetailed.append(Utility.getGoogleChartsJs());
			suiteReportDetailed.append(Utility.getGooglePieChart());
			suiteReportDetailed.append(Utility.googleChartDraw(suitePassedTests, suiteFailedTests, suiteSkippedTests));
			suiteReportDetailed.append(Utility.endHeadAndStartBody());
			suiteReportDetailed.append(Utility.startNavigationBar());
			suiteReportDetailed.append(Utility.backToSuitesSummaryLink(false));
			suiteReportDetailed.append(Utility.suiteIndexLink(suiteName, false));
			suiteReportDetailed.append(Utility.suiteActiveAll());
			suiteReportDetailed.append(Utility.suiteAllDropDownMenu(suiteName, suitePassedTests, suiteFailedTests, suiteSkippedTests));
			suiteReportDetailed.append(Utility.endNavigationBar());
			
			suiteReportDetailed.append(Utility.startContainerWithMargin());
			suiteReportDetailed.append(Utility.startRow());
			suiteReportDetailed.append(Utility.startColumn(4));
			
			suiteReportDetailed.append(Utility.suiteSummaryAllInfo(suiteName, suiteStartTime, suiteEndTime));
			
			suiteReportDetailed.append(Utility.startRow());
			suiteReportDetailed.append(Utility.startColumn(12));
			suiteReportDetailed.append(Utility.chartDivID());
			suiteReportDetailed.append(Utility.endColumn());
			suiteReportDetailed.append(Utility.endRow());
			
			suiteReportDetailed.append(Utility.suiteContextSummaryAllInfo(suiteContextSummaryReport, suiteTotalTests, suitePassedTests, suiteFailedTests, suiteSkippedTests));
			
			suiteReportDetailed.append(Utility.endColumn());
			
			suiteReportDetailed.append(Utility.startColumn(8));
			suiteReportDetailed.append(textContextSummaryAndDetailedReport);
			suiteReportDetailed.append(Utility.endColumn());
			suiteReportDetailed.append(errorModelWindow);
			suiteReportDetailed.append(Utility.endRow());
			suiteReportDetailed.append(Utility.endContainer());
			suiteReportDetailed.append(Utility.getDescriptionTooltipJs());
			suiteReportDetailed.append(Utility.endBodyAndHtml());
			String fileName = "suite-"+suiteName+"-index.html";
		    Utils.writeFile(suite.getOutputDirectory(), fileName, suiteReportDetailed.toString());
		    
		    /**
		     * Writing suite passed results
		     */
		    if(suitePassedTests>0){
		    	StringBuffer suiteReportPassed = new StringBuffer();
		    	suiteReportPassed.append(Utility.getHtmlToHead());
		    	suiteReportPassed.append("<title>"+suiteName+" Passed Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getJqueryJs()+Utility.getBootstrapJs());
		    	suiteReportPassed.append(Utility.endHeadAndStartBody());
		    	suiteReportPassed.append(Utility.startNavigationBar());
		    	suiteReportPassed.append(Utility.backToSuitesSummaryLink(false));
		    	suiteReportPassed.append(Utility.suiteIndexLink(suiteName, false));
		    	suiteReportPassed.append(Utility.suiteActivePassed());
		    	suiteReportPassed.append(Utility.suitePassedDropDownMenu(suiteName, suiteFailedTests, suiteSkippedTests));
	            suiteReportPassed.append(Utility.endNavigationBar());
	            
	            suiteReportPassed.append(Utility.startContainerWithMargin());
	            suiteReportPassed.append(Utility.startRow());
	            suiteReportPassed.append(Utility.startColumn(3));
	            suiteReportPassed.append(Utility.suiteSummaryStatusInfo(suiteName));
		    	
		    	suiteReportPassed.append(Utility.suitePassedContextSummaryInfo(passedtextContextSummaryReport, suitePassedTests));
				suiteReportPassed.append(Utility.endColumn());
				
				suiteReportPassed.append(Utility.startColumn(9));
				suiteReportPassed.append(passedTextContextReport);
				suiteReportPassed.append(Utility.endColumn());
				suiteReportPassed.append(Utility.endRow());
				suiteReportPassed.append(Utility.endContainer());
				suiteReportPassed.append(Utility.getDescriptionTooltipJs());
				suiteReportPassed.append(Utility.endBodyAndHtml());
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-passed.html", suiteReportPassed.toString());
		    }
		    
		    /**
		     * Writing suite failed results
		     */
		    if(suiteFailedTests>0){
		    	StringBuffer suiteReportFailed = new StringBuffer();
		    	suiteReportFailed.append(Utility.getHtmlToHead());
		    	suiteReportFailed.append("<title>"+suiteName+" Failed Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+Utility.getModelJs());
		    	suiteReportFailed.append(Utility.endHeadAndStartBody());
		    	suiteReportFailed.append(Utility.startNavigationBar());
		    	suiteReportFailed.append(Utility.backToSuitesSummaryLink(false));
		    	suiteReportFailed.append(Utility.suiteIndexLink(suiteName, false));
		    	suiteReportFailed.append(Utility.suiteActiveFailed());
		    	suiteReportFailed.append(Utility.suiteFailedDropDownMenu(suiteName, suitePassedTests, suiteSkippedTests));
	            suiteReportFailed.append(Utility.endNavigationBar());
		    	
	            suiteReportFailed.append(Utility.startContainerWithMargin());
	            suiteReportFailed.append(Utility.startRow());
	            suiteReportFailed.append(Utility.startColumn(3));
	            suiteReportFailed.append(Utility.suiteSummaryStatusInfo(suiteName));
		    	
		    	suiteReportFailed.append(Utility.suiteFailedContextSummaryInfo(failedtextContextSummaryReport, suiteFailedTests));
		    	
		    	suiteReportFailed.append(Utility.endColumn());
		    	
				suiteReportFailed.append(Utility.startColumn(9));
		    	suiteReportFailed.append(failedTextContextReport);
		    	suiteReportFailed.append(Utility.endColumn());
		    	suiteReportFailed.append(errorModelWindow);
		    	suiteReportFailed.append(Utility.endRow());
		    	suiteReportFailed.append(Utility.endContainer());
		    	suiteReportFailed.append(Utility.getDescriptionTooltipJs());
		    	suiteReportFailed.append(Utility.endBodyAndHtml());
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-failed.html", suiteReportFailed.toString());
		    }
		    
		    /**
		     * Writing suite skipped results
		     */
		    if(suiteSkippedTests>0){
		    	StringBuffer suiteReportSkipped = new StringBuffer();
		    	suiteReportSkipped.append(Utility.getHtmlToHead());
		    	suiteReportSkipped.append("<title>"+suiteName+" Skipped Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+Utility.getModelJs());
		    	suiteReportSkipped.append(Utility.endHeadAndStartBody());
		    	suiteReportSkipped.append(Utility.startNavigationBar());
		    	suiteReportSkipped.append(Utility.backToSuitesSummaryLink(false));
		    	suiteReportSkipped.append(Utility.suiteIndexLink(suiteName, false));
		    	suiteReportSkipped.append(Utility.suiteActiveSkipped());
		    	suiteReportSkipped.append(Utility.suiteSkippedDropDownMenu(suiteName, suitePassedTests, suiteFailedTests));
		    	
	            suiteReportSkipped.append(Utility.endNavigationBar());
		    	
	            suiteReportSkipped.append(Utility.startContainerWithMargin());
	            suiteReportSkipped.append(Utility.startRow());
	            suiteReportSkipped.append(Utility.startColumn(3));
	            suiteReportSkipped.append(Utility.suiteSummaryStatusInfo(suiteName));
		    	
		    	suiteReportSkipped.append(Utility.suiteSkippedContextSummaryInfo(skippedtextContextSummaryReport, suiteSkippedTests));
		    	
		    	suiteReportSkipped.append(Utility.endColumn());
				suiteReportSkipped.append(Utility.startColumn(9));
		    	suiteReportSkipped.append(skippedTextContextReport);
		    	suiteReportSkipped.append(Utility.endColumn());
		    	suiteReportSkipped.append(errorModelWindow);
		    	suiteReportSkipped.append(Utility.endRow());
		    	suiteReportSkipped.append(Utility.endContainer());
		    	suiteReportSkipped.append(Utility.getDescriptionTooltipJs());
		    	suiteReportSkipped.append(Utility.endBodyAndHtml());
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-skipped.html", suiteReportSkipped.toString());
		    }
		    
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void onStart(ISuite suite) {
		
	}
	
	protected void generateFailureSuite(XmlSuite xmlSuite, ISuite suite, String outputDir) {
	    XmlSuite failedSuite = (XmlSuite) xmlSuite.clone();
	    failedSuite.setName(xmlSuite.getName());
	    m_xmlSuite= failedSuite;
	    Map<String, XmlTest> xmlTests= Maps.newHashMap();
	    for(XmlTest xmlT: xmlSuite.getTests()) {
	      xmlTests.put(xmlT.getName(), xmlT);
	    }
	    
	    Map<String, ISuiteResult> results = suite.getResults();

	    for(Map.Entry<String, ISuiteResult> entry : results.entrySet()) {
	      ISuiteResult suiteResult = entry.getValue();
	      ITestContext testContext = suiteResult.getTestContext();

	      generateXmlTest(suite,
	                      xmlTests.get(testContext.getName()),
	                      testContext,
	                      testContext.getFailedTests().getAllResults(),
	                      testContext.getSkippedTests().getAllResults());
	    }

	    if(null != failedSuite.getTests() && failedSuite.getTests().size() > 0) {
	      Utils.writeUtf8File(outputDir, suite.getName()+"-failed.xml", failedSuite.toXml());
	    }
	}
	

	
	private void generateXmlTest(ISuite suite,  XmlTest xmlTest, ITestContext context, Collection<ITestResult> failedTests, Collection<ITestResult> skippedTests) {
		// Note:  we can have skipped tests and no failed tests
		// if a method depends on nonexistent groups
		if (skippedTests.size() > 0 || failedTests.size() > 0) {
			Set<ITestNGMethod> methodsToReRun = Sets.newHashSet();
			
			// Get the transitive closure of all the failed methods and the methods
			// they depend on
			Collection[] allTests = new Collection[] {failedTests, skippedTests};
			
			for (Collection<ITestResult> tests : allTests) {
				for (ITestResult failedTest : tests) {
					ITestNGMethod current = failedTest.getMethod();
					if (current.isTest()) {
						methodsToReRun.add(current);
						ITestNGMethod method = failedTest.getMethod();
						// Don't count configuration methods
						if (method.isTest()) {
							List<ITestNGMethod> methodsDependedUpon =MethodHelper.getMethodsDependedUpon(method,
							context.getAllTestMethods());
				
							for (ITestNGMethod m : methodsDependedUpon) {
								if (m.isTest()) {
									methodsToReRun.add(m);
								}
							}
						}
					}
				}
			}
			List<ITestNGMethod> result = Lists.newArrayList();
			for (ITestNGMethod m : context.getAllTestMethods()) {
				if (methodsToReRun.contains(m)) {
					result.add(m);
				}
			}
			
			methodsToReRun.clear();
			Collection<ITestNGMethod> invoked= suite.getInvokedMethods();
			for(ITestNGMethod tm: invoked) {
				if(!tm.isTest()) {
					methodsToReRun.add(tm);
				}
			}				
			result.addAll(methodsToReRun);
			createXmlTest(context, result, xmlTest);
		}
	}
	
	 private void createXmlTest(ITestContext context, List<ITestNGMethod> methods, XmlTest srcXmlTest) {
		    XmlTest xmlTest = new XmlTest(m_xmlSuite);
		    xmlTest.setName(context.getName());
		    xmlTest.setBeanShellExpression(srcXmlTest.getExpression());
		    xmlTest.setIncludedGroups(srcXmlTest.getIncludedGroups());
		    xmlTest.setExcludedGroups(srcXmlTest.getExcludedGroups());
		    xmlTest.setParallel(srcXmlTest.getParallel());
		    xmlTest.setJUnit(srcXmlTest.isJUnit());
		    List<XmlClass> xmlClasses = createXmlClasses(methods, srcXmlTest);
		    xmlTest.setXmlClasses(xmlClasses);
	}

	private List<XmlClass> createXmlClasses(List<ITestNGMethod> methods, XmlTest srcXmlTest) {
	    List<XmlClass> result = Lists.newArrayList();
	    Map<Class, Set<ITestNGMethod>> methodsMap= Maps.newHashMap();

	    for (ITestNGMethod m : methods) {
	      Object[] instances= m.getInstances();
	      Class clazz= instances == null || instances.length == 0 || instances[0] == null
	          ? m.getRealClass()
	          : instances[0].getClass();
	      Set<ITestNGMethod> methodList= methodsMap.get(clazz);
	      if(null == methodList) {
	        methodList= new HashSet<ITestNGMethod>();
	        methodsMap.put(clazz, methodList);
	      }
	      methodList.add(m);
	    }

	    // Ideally, we should preserve each parameter in each class but putting them
	    // all in the same bag for now
	    Map<String, String> parameters = Maps.newHashMap();
	    for (XmlClass c : srcXmlTest.getClasses()) {
	      parameters.putAll(c.getLocalParameters());
	    }

	    int index = 0;
	    for(Map.Entry<Class, Set<ITestNGMethod>> entry: methodsMap.entrySet()) {
	      Class clazz= entry.getKey();
	      Set<ITestNGMethod> methodList= entry.getValue();
	      XmlClass xmlClass= new XmlClass(clazz.getName(), index++, false /* don't load classes */);
	      List<XmlInclude> methodNames= Lists.newArrayList(methodList.size());
	      int ind = 0;
	      for(ITestNGMethod m: methodList) {
	        methodNames.add(new XmlInclude(m.getMethod().getName(), m.getFailedInvocationNumbers(),ind++));
	      }
	      
	      xmlClass.setIncludedMethods(methodNames);
	      
	      xmlClass.setParameters(parameters);
	      result.add(xmlClass);

	    }

	    return result;
	}
	
}
