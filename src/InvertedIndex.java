import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


public class InvertedIndex {
	
	private HashMap<String,HashSet<Integer>> TermsHashTable ; 
	private int numOfFreq ; 
	
	public InvertedIndex() {
		TermsHashTable = new HashMap<String,HashSet<Integer>>() ;
		numOfFreq = 0 ; 
	}
	
	public void put(String term, int DocID) {
		if(TermsHashTable.containsKey(term)) {
			TermsHashTable.get(term).add(DocID) ; 
		}
		else {
			HashSet<Integer> postingList = new HashSet<Integer>() ; 
			postingList.add(DocID) ; 
			TermsHashTable.put(term, postingList) ; 
		}
	}
	
	public void getTerm(String term) {
		ArrayList<Integer> postingList = new ArrayList<Integer>() ;
		if(!TermsHashTable.containsKey(term)) {
			System.out.println("There is no term called " + term);
		}
		else {
			HashSet<Integer> postingSet = TermsHashTable.get(term) ; 
			for(Integer i : postingSet) {
				postingList.add(i) ; 
			}
		}
		Collections.sort(postingList);
		numOfFreq = postingList.size() ; 
		System.out.println("Doc freq. : " + numOfFreq) ; 
		System.out.print("Doc Id : " + postingList.get(0));
		for(int i=1; i<postingList.size(); i++) {
			System.out.print(", " + postingList.get(i));
		}
	}
	
	private ArrayList<Integer> GetPostinglist(String Term) { 
		ArrayList<Integer> postingList = new ArrayList<Integer>() ;
		if(!TermsHashTable.containsKey(Term)) {
			System.out.println("There is no term called " + Term);
		}
		else {
			HashSet<Integer> PS = TermsHashTable.get(Term) ; // PS = Posting Set
			for(Integer i : PS) {
				postingList.add(i) ; 
			}
		}
		Collections.sort(postingList);
		return postingList ; 
	}
	
	public void DisplayPostingList(ArrayList<Integer> PL) {  //  PL --> stands for Posting list 
		int numOfFreqPS = PL.size() ; 
		System.out.println("Doc freq. : " + numOfFreqPS) ; 
		System.out.print("Doc Id : " + PL.get(0));
		for(int i=1; i<PL.size(); i++) {
			System.out.print(", " + PL.get(i));
		}
		System.out.println(); 
	}
	
	public void AND(String FirstTerm, String SecondTerm) { // AND means intersect 
		ArrayList<Integer> First_postingList = GetPostinglist(FirstTerm) ;
		ArrayList<Integer> Second_postingList = GetPostinglist(SecondTerm) ;
		ArrayList<Integer> resultOfIntersect = new ArrayList<Integer>() ;  // AND means intersect
		
		for(int i=0, j=0; i<First_postingList.size() && j<Second_postingList.size();) {
			if(First_postingList.get(i) == Second_postingList.get(j)) {
				resultOfIntersect.add(First_postingList.get(i)) ; 
				i++ ; 
				j++ ; 
			}
			else if(First_postingList.get(i) < Second_postingList.get(j)) {
				i++ ; 
			}
			else {
				j++ ; 
			}
		}
		
		System.out.println("\nFirst Posting list :- ") ; 
		DisplayPostingList(First_postingList);
		
		System.out.println("\nSecond Posting list :- ") ; 
		DisplayPostingList(Second_postingList);
		
		System.out.println("\nFirst AND Second  (Intersect) Posting list: ") ; 
		DisplayPostingList(resultOfIntersect);
		
	}
	
	
	public void OR(String FirstTerm, String SecondTerm) {
		ArrayList<Integer> First_postingList = GetPostinglist(FirstTerm) ;
		ArrayList<Integer> Second_postingList = GetPostinglist(SecondTerm) ;
		HashSet<Integer> resultOfORSet = new HashSet<Integer>() ;
		ArrayList<Integer> resultOfOR = new ArrayList<Integer>() ; 
		int i=0, j=0 ; 

		while(i<First_postingList.size()) {
			resultOfORSet.add(First_postingList.get(i)) ;
			i++ ; 
		}
		while(j<Second_postingList.size()) {
			resultOfORSet.add(Second_postingList.get(j)) ;
			j++ ; 
		}
		
		for(Integer myInt : resultOfORSet) {
			resultOfOR.add(myInt) ;
		}
		
		System.out.println("\nFirst Posting list :- ") ; 
		DisplayPostingList(First_postingList);
		
		System.out.println("\nSecond Posting list :- ") ; 
		DisplayPostingList(Second_postingList);
		
		System.out.println("\nFirst OR Second Posting list: ") ; 
		DisplayPostingList(resultOfOR);
	}
	
	public void NOT(String FirstTerm, String SecondTerm) {
		ArrayList<Integer> First_postingList = GetPostinglist(FirstTerm) ;
		ArrayList<Integer> Second_postingList = GetPostinglist(SecondTerm) ;
		ArrayList<Integer> resultOfNOT = new ArrayList<Integer>() ;
		HashSet<Integer> resultOfNotSet = new HashSet<Integer>() ; 
		int i=0, j=0 ; 
		
		while(i<First_postingList.size()) {
			resultOfNotSet.add(First_postingList.get(i)) ;
			i++ ; 
		}
		while(j<Second_postingList.size()) {
			if(resultOfNotSet.contains(Second_postingList.get(j))) {
				resultOfNotSet.remove(Second_postingList.get(j)) ; 
			}
			j++ ; 
		}
		
		for(Integer myInt : resultOfNotSet) {
			resultOfNOT.add(myInt) ;
		}
		
		System.out.println("\nFirst Posting list :- ") ; 
		DisplayPostingList(First_postingList);
		
		System.out.println("\nSecond Posting list :- ") ; 
		DisplayPostingList(Second_postingList);
		
		System.out.println("\n(First AND (NOT Second)) Posting list: ") ; 
		DisplayPostingList(resultOfNOT);
	}
	
	
	
	

}
