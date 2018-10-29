package org.yuanhong.li;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.yuanhong.li.comic.qq.AcImageParser;

public class ApplicationAcqqImg {

	public static void main(String[] args) throws InterruptedException {
		if(args == null || args.length<2) {
			System.err.println("input args error.");
			return ;
		}
		String comicId=args[0];
		String sectionId = args[1];
		System.setProperty("webdriver.chrome.driver", "D://tools//chromedriver.exe");
		// add headness option
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--window-size=1920,1080");
		
		WebDriver driver = new ChromeDriver(chromeOptions);//启动headness

		String webUrl = String.format("http://m.ac.qq.com/chapter/index/id/%s/cid/%s", comicId,sectionId);
		driver.get(webUrl);

		for (int i = 0; i < 200; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scrollBy(0,1000)");// 模拟滑动
			Thread.sleep(50);
		}
		Thread.sleep(200);
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
