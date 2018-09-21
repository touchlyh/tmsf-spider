package org.yuanhong.li.huajiao;

import java.io.Serializable;

/**
 * 爬取视频信息
 * @author yuanhong.li
 *
 */
public class VideoInfo implements Serializable{

	private static final long serialVersionUID = -5085664057280875668L;

	private Long id;
	
	private String authorId;
	
	private String uid;
	
	private String uidName;
	
	private Integer playCount;
	
	private String mp4;
	
	private Long gmtCreate;
	
	private String md5sum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUidName() {
		return uidName;
	}

	public void setUidName(String uidName) {
		this.uidName = uidName;
	}

	public Integer getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}

	public String getMp4() {
		return mp4;
	}

	public void setMp4(String mp4) {
		this.mp4 = mp4;
	}

	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getMd5sum() {
		return md5sum;
	}

	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}
	
}
