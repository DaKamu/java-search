package search; 

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class search {
	
	public static void main (String[] args) throws IOException { 
		//List of words including duplicates
		List<String> wsList = new ArrayList<String>();
		//List of words without duplicates 
		List<String> cList = new ArrayList<String>();
		//Create hash table
		Hashtable<Integer,String> hashTable = new Hashtable<Integer,String>();
		//HashMap<char,String> hashMap = new HashMap<char,String>();
		String file = "wordlist.txt";
		
		
			//Unsorted arraylist
			readFtList(file, wsList);
			///This file contains all elements for concordance list.
			file = "conList.txt";
			readFtList(file,cList);
			System.out.println("Linear search - First 10 and last 10 elements");
			System.out.println("There are " + cList.size() + " unique words in the concordance");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			linearSearch(wsList, cList,0,10);
			linearSearch(wsList, cList,cList.size() - 10,cList.size());
			
			//Clear the list so the sorted list can replace it
			wsList.clear();
			System.out.println("");
			System.out.println("");
			System.out.println("");
			
			
			//This file has the same list as linear search, but it in sorted order. 
			file = "sortedList.txt";
			readFtList(file,wsList);
			System.out.println("Binary search - First 10 and last 10 elements");
			System.out.println("There are " + cList.size() + " unique words in the concordance");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			binarySearch(wsList,cList,0, 10);
			binarySearch(wsList,cList,cList.size() - 10, cList.size());
			
			System.out.println("");
			System.out.println("");
			System.out.println("");
			
			//Populate the hashtable
			String currentWord;
			for(int i = 0; i < wsList.size();i++) {
				currentWord = wsList.get(i);
				hashTable.put(i,wsList.get(i));
			}
			
			System.out.println("Hashtable search - First 10 and last 10 elements");
			System.out.println("There are " + cList.size() + " unique words in the concordance");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			hashSearch(hashTable, cList,0, 10);
			hashSearch(hashTable, cList,cList.size() - 10, cList.size());
			
	}
	public static void write(String aFile,List<String> aList) throws IOException {
		try	{
			BufferedWriter writer = new BufferedWriter(new FileWriter(aFile));
			for(int i = 0; i < aList.size(); i++) {
				writer.write(aList.get(i));
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {			
		}
	}

	public static void readFtList(String aFile,List<String> aList) throws FileNotFoundException {
		try	{
			BufferedReader reader = new BufferedReader(new FileReader(aFile));
			String line;
			while((line = reader.readLine()) != null) { 
			if(line.length() != 0) {
				 if((line.charAt(0) >= 97d && line.charAt(0) <= 122d) || (line.charAt(0) >= 65d && line.charAt(0) <= 90d))
					aList.add(line.toLowerCase());
				}
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {			
		}
	}
	//Search only keeps count of how many occurrences of the searched word is found
	public static void linearSearch(List<String> aList, List<String> cList,int start, int end) {
			int count = 0;
			int cmpCount = 0;
			for(int j = start; j < end; j++) {
				for (int i = 0; i < aList.size(); i++) {
					if (aList.get(i).equals(cList.get(j))) {
						count += 1;
					}
					cmpCount += 1;
				}
				System.out.println(cList.get(j) + " has " + count + " occurences and took " + cmpCount + " comparisons");
				count = 0;
				cmpCount = 0;
			}
	}
	public static void createConList(List<String> aList,List<String> cList) { 
		Boolean duplicateFlag = false;
		long cmpCount = 0;
		int aCount = 0;
		for(int i = 0; i < aList.size(); i++) {
			 if(cList.size() == 0) { 
				cList.add(aList.get(i));
				aCount += 1;
			 }
			 else {
				for(int j = 0; j < cList.size(); j++) { 
					if(cList.get(j).equals(aList.get(i))) { 
						duplicateFlag = true;
					}
					cmpCount += 1;
				}
				//If item doesnt exist in the list already, add it to the list
				if(!duplicateFlag) { 
				 cList.add(aList.get(i));
				 aCount += 1;
				}
				duplicateFlag = false;
			}
		}
		System.out.println("There were " + cmpCount + " comparisons and " + aCount + " assignments");
	}
	
	public static void binarySearch(List<String> sortedList, List<String> conList,int start, int end) {
		int low = 0;
		int high = sortedList.size() - 1;
		int mid = 0;
		int startIndex = -1; //If the start/end index are equal to -1 by the end of both while loops, then the search item is not in the list
		int endIndex = -1;
		int count = 0;
		int cmpCount = 0;
		for(int i = start; i < end; i++) {
			count = 0; 
			cmpCount = 0;
			startIndex = -1;
			endIndex = -1;
			low = 0;
			high = sortedList.size() - 1;
			while (low <= high) { 
				mid = (low+high)/2;
				//If list value is greater than search item
				cmpCount += 1;
				if(sortedList.get(mid).compareTo(conList.get(i)) > 0) {
					high = mid - 1;
				}
				else if(sortedList.get(mid).equals(conList.get(i))) {
					startIndex = mid;
					high = mid -1;
					cmpCount += 1;
				}
				else {
					low = mid + 1;
					cmpCount += 1;
				}
			}
			low = 0;
			high = sortedList.size() - 1;
			while (low <= high) { 
				mid = (low+high)/2;
				//If list value is greater than search item
				cmpCount += 1;
				if(sortedList.get(mid).compareTo(conList.get(i)) > 0) {
					high = mid - 1;
				}
				else if(sortedList.get(mid).equals(conList.get(i))) {
					endIndex = mid;
					low = mid + 1;
					cmpCount += 1;
				}
		
				else {
					low = mid + 1;
					cmpCount += 1;
				}			
			}
			count = (endIndex-startIndex) + 1;
			System.out.println(conList.get(i) + " has " + count + " occurences with " + cmpCount + " comparisons");
		}	
	}
	//Hash search takes a hashtable and list (for the concordance list) as arguments.
	public static void hashSearch(Hashtable<Integer,String> someTable, List<String> cList, int start, int end) {
		int count = 0; //Occurrences count
		int cmpCount = 0; //Comparisons count
		for(int i = start; i < end; i++) {
			cmpCount += 1;
			//This if statement is just 1 comparison operation... this is where we see that search on hashtable is O(1)
			if(someTable.containsValue(cList.get(i))) {
				//This for loop is just going to find all duplicates of the search item - this will run at 'n' since it iterates through all keys
				//The comparison count is not used here since it's just finding duplicates and not the actual search.
				for(int key: someTable.keySet()){	
					if(someTable.get(key).equals(cList.get(i))) {
						count += 1;
					}
				}
				System.out.println(cList.get(i) + " has "  + count + " occurence(s) and " + cmpCount + " comparison(s)");
				count = 0;
				cmpCount = 0;
			}
		}			
	}
	
}
