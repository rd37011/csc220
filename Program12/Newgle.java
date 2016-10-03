package prog12;

import java.util.*;

public class Newgle implements SearchEngine{
	
	HardDisk <PageFile> pageDisk = new HardDisk<PageFile>();
    
	PageTrie page2index = new PageTrie();
	
	HardDisk <List<Long>> wordDisk = new HardDisk <List<Long>>();
	
	WordTable word2table = new WordTable();
	
	public Long indexPage(String url){
		
		Long index = pageDisk.newFile();
	    PageFile file = new PageFile(index, url);
		pageDisk.put(index, file);
		page2index.put(url, index);		
		System.out.println(index + url);
		return index;
	}
	
	public Long indexWord(String word){
		
		Long index = wordDisk.newFile();
        List<Long> aList = new ArrayList<Long>();
        wordDisk.put(index, aList);
        word2table.put(word, index);
        System.out.println(index + "" + word);
        return index;
	}

	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		
		Queue<Long> theQueue = new ArrayDeque();
		Long index = 0L;
		
		for(String url : startingURLs){
			if(!page2index.containsKey(url)){
				theQueue.offer(indexPage(url));
			}
		}
		while(!theQueue.isEmpty()){
			Long pageIndex = theQueue.poll();
			if(browser.loadPage(pageDisk.get(pageIndex).url)){
				List<String> URLs = browser.getURLs();
				List<String> words = browser.getWords();
				Set<Long> list1 = new HashSet<Long>();
				Set<Long> list2 = new HashSet<Long>();
				
				for(String url : words){
 					if(!word2table.containsKey(url)){
 						Long temp = indexWord(url);
 						list1.add(temp);
 					}
 					else{
 						list1.add(word2table.get(url));
 					}
 				}
 				for(String url : URLs){
					if(!page2index.containsKey(url)){
						Long temp = indexPage(url);
						theQueue.offer(indexPage(url));
						list2.add(temp);
					}else{
						list2.add(page2index.get(url));
					}
				}
 			
			}
			for(String url : startingURLs){
				PageFile.incRefCount();
				Set <String> urlSet = new HashSet<String>();
				urlSet.add(url);
			}
			
		System.out.println(pageDisk);
		System.out.println(wordDisk);
		}
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		return new String[0];
	}
	
	
	


}
