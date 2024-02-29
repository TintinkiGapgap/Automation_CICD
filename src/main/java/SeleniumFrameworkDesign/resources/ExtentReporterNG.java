package SeleniumFrameworkDesign.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	
	public static ExtentReports getReportObject()
	{
		//ExtentReports, ExtentSparkReporter
				String path = System.getProperty("user.dir")+"\\reports\\index.html";
				ExtentSparkReporter reporter = new ExtentSparkReporter(path);//ExtentSparkReporter class is used to configure the report
				reporter.config().setReportName("Automation Results");
				reporter.config().setDocumentTitle("Test Results");
				
				ExtentReports extent = new ExtentReports(); // this is the main class to create report
				extent.attachReporter(reporter); // attach configured report
				extent.setSystemInfo("Tester", "Devarti");	
				return extent;
	}

}
