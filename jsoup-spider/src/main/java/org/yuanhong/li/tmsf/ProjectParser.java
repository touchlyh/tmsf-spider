package org.yuanhong.li.tmsf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 使用正则表达式解析楼盘项目信息
 * @author yuanhong.li
 * Date 2018-07-31
 */
public class ProjectParser {

	private String url;
	
	private String pageId;
	
	private List<String> patterns;
	
	private String urlPattern = "open\\('(.+)'\\)";
	private String namePattern = "项目名称：</font>(.+)</td>";
	private String preSalePattern = "预售证号：</font>(.+)</li>";
	private String locPattern = "幢号：</font>(.+)</li>";
	private String openPattern = "开盘时间：</font>(.+)</li>";
	private String companyPattern = "开发公司：</font>(.+)</li>";
	private String addrPattern = "坐落地址：</font>(.+)</td>";
	private String saleAddrPattern = "销售地址：</font>(.+)</td>";
	private String totalPattern = "可售\\d+套/共(\\d+)套";
	
	public ProjectParser(String  url, String pageId) {
		super();
		this.url=url;
		this.pageId = pageId;
		patterns = new ArrayList<String>();
		patterns.add(urlPattern);
		patterns.add(namePattern);
		patterns.add(preSalePattern);
		patterns.add(locPattern);
		patterns.add(openPattern);
		patterns.add(companyPattern);
		patterns.add(addrPattern);
		patterns.add(saleAddrPattern);
		patterns.add(totalPattern);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public List<ProjectInfo> parserUrl(){
		try {
			Document doc = Jsoup.connect(url).header("Connection", "keep-alive").header("Cache-Control", "max-age=0").header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent", "Mozilla//5.0 (Windows NT 6.1; Win64; x64) AppleWebKit//537.36 (KHTML, like Gecko) Chrome//67.0.3396.99 Safari//537.36")
					.header("Accept", "text//html,application//xhtml+xml,application//xml;q=0.9,image//webp,image//apng,*//*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.9")
					.cookie("gr_user_id", "45b198a0-09cc-470a-93d9-9dfde64b3bfd")
					.cookie("grwng_uid", "0f88d0a9-a140-42a8-92f3-cb493f9c2d7c")
					.cookie("UM_distinctid", "45b198a0-09cc-470a-93d9-9dfde64b3bfd")
					.cookie("gr_user_id", "164d18e8d072fa-074c314517ba82-5b193413-100200-164d18e8d0838f")
					.cookie("CNZZDATA1253675216", "798606768-1532519853-http%253A%252F%252Fwww.tmsf.com%252F%7C1532519853")
					.cookie("Hm_lvt_bbb8b9db5fbc7576fd868d7931c80ee1", "1532524126,1532932482")
					.cookie("JSESSIONID", "11D5C7FFB03C593FB337549AC9B5AACF")
					.cookie("Hm_lpvt_bbb8b9db5fbc7576fd868d7931c80ee1", "1532940305")
					.cookie("b61f24991053b634_gr_session_id", "5b405f92-b533-4fcb-961b-51d47b4dde28")
					.cookie("b61f24991053b634_gr_session_id_5b405f92-b533-4fcb-961b-51d47b4dde28", "true")
					.data("page", this.pageId).data("sid","33").post();
			Elements projects = doc.select("div.loupan");
			List<ProjectInfo> projectInfoList = new ArrayList<ProjectInfo>();
			for(Element project : projects) {
				boolean div = project.hasAttr("title");
				if(div) {
					String pHtml = project.outerHtml();
					List<String> info = new ArrayList<String>();
					for(String p : patterns) {
						Pattern pattern = Pattern.compile(p);
						Matcher match = pattern.matcher(pHtml);
						if(match.find()) {
							info.add(match.group(1));
						} else {
							info.add("");
						}
					}
					ProjectInfo projectDO = new ProjectInfo(info);
					projectInfoList.add(projectDO);
				}else {
					System.out.println("预售信息");
				}
			}
			return projectInfoList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
