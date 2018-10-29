package org.yuanhong.li;

import java.util.List;

import org.yuanhong.li.mm.MmjpgParser;

public class ApplicationMM {

	public static void main(String[] args) {
		String listId = args[0];
		
		MmjpgParser parser = new MmjpgParser();
		
		List<String> imgs = parser.getMztList(listId);
		for(String img : imgs) {
			System.out.print(img);
			System.out.println("\n");
		}
	}
}
