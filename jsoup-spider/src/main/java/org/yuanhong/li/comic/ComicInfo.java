package org.yuanhong.li.comic;

/**
 * 持久化漫画和漫画章节
 * @author yuanhong.li
 *
 */
public class ComicInfo {

	private Long id;
	
	private String comicId;
	
	private String source;//NET,QQ...
	
	private String title;
	
	private String author;
	
	private String description;
	
	private String rawTags;
	
	private String cover;
	
	private Integer status;//0初始化，1已下载，2已同步

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRawTags() {
		return rawTags;
	}

	public void setRawTags(String rawTags) {
		this.rawTags = rawTags;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getComicId() {
		return comicId;
	}

	public void setComicId(String comicId) {
		this.comicId = comicId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

}
