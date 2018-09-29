package org.yuanhong.li.selenium;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.yuanhong.li.comic.qq.AcImageParser;

public class HelloWordTest {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D://tools//chromedriver.exe");
		// add headness option
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--window-size=1920,1080");
		
//		WebDriver driver = new ChromeDriver();//启动常规
		WebDriver driver = new ChromeDriver(chromeOptions);//启动headness

		driver.get("http://m.ac.qq.com/chapter/index/id/622755/cid/153");
		// System.out.println(driver.getTitle() + "helloworld");

		for (int i = 0; i < 100; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scrollBy(0,2000)");// 模拟滑动
			// js.executeScript("scrollTo(0,1000000)");//直接滑动到底
			Thread.sleep(50);
		}
		String html = driver.getPageSource();
		AcImageParser parser = new AcImageParser();
		List<String> urlList = parser.parseImageList(html);
		if (urlList != null && urlList.size() != 0) {
			for (String url : urlList) {
				System.out.println(url);
			}
		}
		driver.quit();
	}

}
