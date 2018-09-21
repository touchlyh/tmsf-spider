package org.yuanhong.li.huajiao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.yuanhong.li.utils.CheckSumBuilder;
import org.yuanhong.li.utils.EmojiFilter;
import org.yuanhong.li.utils.JacksonUtil;

public class VideoJsonParser {

	private InputStream jsonStream;
	
	private String fileName;
	
	private String datetime;
	
	public VideoJsonParser(InputStream fileStream, String fileName, String datetime) {
		this.jsonStream = fileStream;
		this.fileName = fileName;
		this.datetime = datetime;
	}
	
	public List<VideoInfo> parseJson() {
		if(this.jsonStream == null) {
			return null;
		}
		List<VideoInfo> videoList = new ArrayList<VideoInfo>();
		JsonNode root = JacksonUtil.parseJsonToTree(this.jsonStream);
		JsonNode data = root.get("data");
		JsonNode feedsList = data.get("feeds");
		Iterator<JsonNode> feedsIt = feedsList.getElements();
		while(feedsIt.hasNext()) {
			VideoInfo info = new VideoInfo();
			try {
				JsonNode feeds = feedsIt.next();
				JsonNode feed = feeds.get("feed");
				String mp4 = feed.get("mp4").getTextValue();
				int playCount = feed.get("watches").getIntValue();
				JsonNode author = feed.get("origin").get("author");
				String uid = author.get("uid").getTextValue();
				String uidName = author.get("nickname").getTextValue();
				uidName = EmojiFilter.filterEmojiCharacter(uidName);
				String md5sum = CheckSumBuilder.getMD5(mp4);
				
				info.setAuthorId(fileName);
				info.setGmtCreate(Long.parseLong(this.datetime));
				info.setMd5sum(md5sum);
				info.setMp4(mp4);
				info.setPlayCount(playCount);
				info.setUid(uid);
				info.setUidName(uidName);
			} catch (Exception e) {
				System.err.print("parse json excepton skip.continue"+fileName);
				continue;
			}
			videoList.add(info);
		}
		return videoList;
	}
}
