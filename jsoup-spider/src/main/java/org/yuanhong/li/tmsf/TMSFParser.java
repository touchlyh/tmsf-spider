package org.yuanhong.li.tmsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 使用jsoup的selector解析楼盘预售列表
 * @author yuanhong.li
 * Date 2018-07-31
 */
public class TMSFParser {
	
	private String url;
	
	private String project;
	
	private String company;
	
	private String saleDate;

	public TMSFParser() {
		super();
	}
	
	public TMSFParser(String url, String project,String company, String saleDate) {
		this.url = url;
		this.project = project;
		this.company = company;
		this.saleDate = saleDate;
		this.spanMapping.put("numberzero", "0");
		this.spanMapping.put("numberone", "1");
		this.spanMapping.put("numbertwo", "2");
		this.spanMapping.put("numberthree", "3");
		this.spanMapping.put("numberfour", "4");
		this.spanMapping.put("numberfive", "5");
		this.spanMapping.put("numbersix", "6");
		this.spanMapping.put("numberseven", "7");
		this.spanMapping.put("numbereight", "8");
		this.spanMapping.put("numbernine", "9");
		this.spanMapping.put("numberdor", ".");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public List<TMSFModel> doParseUrl() {
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
				.cookie("b61f24991053b634_gr_session_id_5b405f92-b533-4fcb-961b-51d47b4dde28", "true").get();
//			Document doc = Jsoup.connect(url).get();
			Elements trs = doc.select("[class=onbuildshow_contant colordg ft14] table tbody > tr");
			List<TMSFModel> modelList = new ArrayList<TMSFModel>();
			for(int t=1; t<trs.size(); t++) {
				Element tr = trs.get(t);
//				Document trDoc = Jsoup.(tr.outerHtml());
//				Elements tds = trDoc.select("td");
				Elements tds = tr.select("td");
				TMSFModel model = new TMSFModel();
				model.setProject(this.project);
				model.setCompany(this.company);
				model.setSaleDate(this.saleDate);
				modelList.add(model);
				for(int i=0; i<tds.size(); i++) {
					Element td = tds.get(i);
					if(i==0) {
						model.setLoc(td.text());
					}
					if(i==1) {
						model.setRomNum(td.text());
					}
					if(i==6) {
						model.setFitPrice(td.text());
					}
					if(i==8) {
						model.setSaleStatus(td.text());
					}
					if(i==2) {
						String num = this.assemberSpanClass(td);
						model.setArea(num);
					}
					if(i==3) {
						String num = this.assemberSpanClass(td);
						model.setInnerArea(num);
					}
					if(i==4) {
						String num = this.assemberSpanClass(td);
						model.setAcquireRate(num);
					}
					if(i==5) {
						String num = this.assemberSpanClass(td);
						model.setPrice(num);
					}
					if(i==7) {
						String num = this.assemberSpanClass(td);
						model.setTotalPrice(num);
					}
				}
			}
			return modelList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String assemberSpanClass(Element td) {
		Elements spans = td.select("span");
		StringBuilder sb = new StringBuilder();
		for(Element span : spans) {
			String attr = span.attr("class");
			String num = this.spanMapping.get(attr);
			sb.append(num);
		}
		return sb.toString();
	}
	
	private Map<String,String> spanMapping = new HashMap<String,String>();
}
