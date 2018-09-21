package org.yuanhong.li.utils;

import org.springframework.util.StringUtils;

public class EmojiFilter {

	/**
	 * 检测是否有emoji字符
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtils.isEmpty(source)) {
			return false;
		}

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (!isNotEmojiCharacter(codePoint)) {
				//do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 过滤emoji字符
	 * @param source 源字符串
	 * @return 过滤后的字符串
	 */
	public static String filterEmojiCharacter(String source){
		if(StringUtils.isEmpty(source)){
			return source;
		}
		StringBuilder sb = new StringBuilder();
		int len = source.length();
		for(int i=0; i<len; i++){
			char codePoint = source.charAt(i);
			if(isNotEmojiCharacter(codePoint)){
				sb.append(codePoint);
			}
		}
		return sb.toString();
	}

	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) ||
			(codePoint == 0x9) ||
			(codePoint == 0xA) ||
			(codePoint == 0xD) ||
			((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
			((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
			((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	public static void main(String[] args) {
		System.out.println(filterEmojiCharacter("我asdasasdas👌"));
	}
}