package org.yuanhong.li;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yuanhong.li.comic.ComicInfo;
import org.yuanhong.li.comic.ComicParser;
import org.yuanhong.li.comic.ComicSection;
import org.yuanhong.li.comic.ComicSpider;
import org.yuanhong.li.comic.nte.NteComicParser;
import org.yuanhong.li.comic.qq.AcqqParser;
import org.yuanhong.li.repository.ComicService;

public class ApplicationComic {

	private static Map<String,ComicParser> sourceParserMap = new HashMap<String,ComicParser>();
	
	static {
		sourceParserMap.put("NTE", new NteComicParser());
		sourceParserMap.put("QQ", new AcqqParser());
	}
	
	public static void main(String[] args) {
		if(args == null || args.length<1) {
			System.err.println("input args error.");
			return ;
		}
		String op = args[0];
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spider-datasource.xml");
		ComicService comicService = (ComicService) context.getBean("comicService");
		
		if(op.equalsIgnoreCase("--save")) {
			//爬取到数据库
			if(args == null || args.length<3) {
				System.err.println("input args error. --save source comicId");
				return ;
			}
			String source = args[1];
			String comicId = args[2];
			ComicParser parser = sourceParserMap.get(source);
			if(parser == null) {
				System.err.println("unsupport source"+source);
				return ;
			}
			ComicSpider spider = new ComicSpider();
			spider.setSource(source);
			spider.setComicParser(parser);
			spider.setComicService(comicService);
			spider.doCrawelMainInfo(comicId);
			spider.doCrawelSections(comicId);
			return ;
		}
		
		
		if(op.equalsIgnoreCase("--get")) {
			//查询各种状态数据
			if(args == null || args.length<3) {
				System.err.println("input args error. --get source [comic|section] status");
				return ;
			}
			String source = args[1];
			String table = args[2];
			String status = args[3];
			if(table.equalsIgnoreCase("comic")) {
				List<ComicInfo> infoList = comicService.getComicListByStatus(source, Integer.parseInt(status));
				if(infoList == null || infoList.size() == 0) {
					System.err.println("no data get. --get "+source+" comic "+status);
					return ;
				}
				printComicList(infoList);
				return;
			}
			if(table.equalsIgnoreCase("section")) {
				List<ComicSection> sectionList = comicService.getSectionListByStatus(source, Integer.parseInt(status));
				if(sectionList == null || sectionList.size() == 0) {
					System.err.println("no data get. --get "+source+" section "+status);
					return ;
				}
				printSectionList(sectionList);
				return;
			}
		}
		
		if(op.equalsIgnoreCase("--update")) {
			if(args == null || args.length<2) {
				System.err.println("input args error. --update [comic|section] id");
				return ;
			}
			String table = args[1];
			String id = args[2];
			if(table.equalsIgnoreCase("comic")) {
				comicService.updateComicStatus(Long.parseLong(id), 1);
				return;
			}
			if(table.equalsIgnoreCase("section")) {
				comicService.updateSectionStatus(Long.parseLong(id), 1);
				return;
			}
		}
		
		System.out.println("unsupported op "+op);
	}

	private static void printSectionList(List<ComicSection> sectionList) {
		for(ComicSection section : sectionList) {
			System.out.print(section.getId());
			System.out.print("\t");
			System.out.print(section.getSource());
			System.out.print("\t");
			System.out.print(section.getComicId());
			System.out.print("\t");
			System.out.println(section.getSectionId());
		}
		
	}

	private static void printComicList(List<ComicInfo> infoList) {
		for(ComicInfo info : infoList) {
			System.out.print(info.getId());
			System.out.print("\t");
			System.out.print(info.getSource());
			System.out.print("\t");
			System.out.print(info.getComicId());
			System.out.print("\t");
			System.out.println(info.getCover());
		}
	}
}
