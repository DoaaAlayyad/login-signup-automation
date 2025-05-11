package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    public static ExtentReports createReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/Report_" + timestamp + ".html");
        
        spark.config().setDocumentTitle("Rainbow Login Test Report");
        spark.config().setReportName("Automation Test Results");
        spark.config().setTheme(Theme.STANDARD);
        
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        
        extent.setSystemInfo("Application", "Rainbow Login");
        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Environment", "Production");
        
        return extent;
    }
}