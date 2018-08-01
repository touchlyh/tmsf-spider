package org.yuanhong.li;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;
import org.yuanhong.li.repository.TmsfService;
import org.yuanhong.li.tmsf.ProjectInfo;
import org.yuanhong.li.tmsf.ProjectParser;
import org.yuanhong.li.tmsf.TMSFModel;
import org.yuanhong.li.tmsf.TMSFParser;

/**
 * 方法执行的总入口
 * 初始化spring-bean
 * 传入透明售房网网签入口链接进行批量爬取
 * @author yuanhong.li
 * 
 */
public class ApplicationMain {
	
	private static int projectPage = 5;//爬取预售项目5页，因为后面的数据基本都卖完了
	private static int pageSize = 14;//预售表格一页的数量
	private static String projectPattern = "presell_([0-9]+)_([0-9]+)_([0-9]+)\\.htm";
	private static String projectFormat = "http://www.tmsf.com/newhouse/property_%s_%s_price.htm?isopen=&presellid=%s&buildingid=&area=&allprice=&housestate=&housetype=&page=%s";
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext("spider-datasource.xml");
		TmsfService tmsfService = (TmsfService) context.getBean("tmsfService");
		Pattern urlPattern = Pattern.compile(ApplicationMain.projectPattern);
		//网签开盘页面入口
		String url = "http://www.tmsf.com/newhouse/OpenReport_show.htm";
		for(int i=1; i<=ApplicationMain.projectPage; i++) {
			String page = String.valueOf(i);
			ProjectParser parser = new ProjectParser(url,page);
			List<ProjectInfo> projectInfoList = parser.parserUrl();
			tmsfService.saveProjectInfo(projectInfoList);
			Thread.sleep(2000);
			if(CollectionUtils.isEmpty(projectInfoList)) {
				continue;
			}
			for(ProjectInfo project : projectInfoList) {
				String openUrl = project.getProjectUrl();
				Integer total = Integer.parseInt(project.getTotal());
				Matcher pMatcher = urlPattern.matcher(openUrl);
				String num1,num2,num3;
				if(pMatcher.find()) {
					num1 = pMatcher.group(1);
					num2 = pMatcher.group(2);
					num3 = pMatcher.group(3);
				}else {
					continue;
				}
				int index = total/ApplicationMain.pageSize;
				index = total%ApplicationMain.pageSize !=0 ? index+1 : index;
				for(int j=1; j<=index; j++) {
					//预售列表页爬取入口
					String projectUrl = String.format(ApplicationMain.projectFormat, num1,num2,num3,j);
					TMSFParser tmsfParser = new TMSFParser(projectUrl, project.getProject(),project.getCompany(),project.getSaleDate());
					List<TMSFModel> models = tmsfParser.doParseUrl();
					tmsfService.saveSaleData(models);
					Thread.sleep(1000);
				}
			}
		}
	}

}
