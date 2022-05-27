import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {
	
	private static String removePural(String givenString) {
		givenString = givenString.substring(0, givenString.length()-1) ;
		return givenString ; 
	}
	
	private static String remove_tion(String givenString) {
		givenString = givenString.substring(0, givenString.length()-4) ;
		return givenString ; 
	}
	
	private static String replaceData(String givenString) {
		givenString = givenString.replace("\'","") ;
		givenString = givenString.replace("/'","") ;
		givenString = givenString.replace(",","") ;
		givenString = givenString.replace("]","") ;
		givenString = givenString.replace("[","") ;
		givenString = givenString.replace("[","") ;
		givenString = givenString.replace("@gmail.com","") ;
		givenString = givenString.replace("@fci-cu.edu.eg","") ;
		givenString = givenString.replace("-","") ;
		givenString = givenString.replace("(","") ;
		givenString = givenString.replace(")","") ;
		givenString = givenString.replace("-","") ;
		givenString = givenString.replace("_","") ;
		givenString = givenString.replace(":","") ;
		givenString = givenString.replace(";","") ;
		givenString = givenString.replace(".","") ;

		
		return givenString ; 
	}
	
	private static String Modification(String dataString) {
		int lenString = dataString.length() ; 
		if(dataString.charAt(lenString-1) == 's') {
			String modifString = removePural(dataString) ; 
			dataString = modifString ;
		}
		if(dataString.contains("tion")) {
			dataString = remove_tion(dataString) ;  
		}
		if(true) {
			String modifString = replaceData(dataString) ; 
			dataString = modifString ; 
		}
		
		
		
		return dataString.toLowerCase() ; 
		
	}

	public static void main(String[] args) {
		
		ArrayList<String> propositionsList = new ArrayList<String>() ; 
		final String propositionsFileName  = "propositions.txt" ; 
		InvertedIndex invert = new InvertedIndex() ; 
		
		try {
			File propositionsFile = new File(propositionsFileName) ; 
			Scanner propScanner = new Scanner(propositionsFile) ;
			while(propScanner.hasNextLine()){
				String dataProposition = propScanner.next() ;
				propositionsList.add(dataProposition) ; 
			}
			propScanner.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		
		
		int numberWords = 0 ; // Number of all words in all file in this directory, after making some modification 
		for(int i=100; i<110; i++) {
			@SuppressWarnings("removal")
			Integer converted = new Integer(i) ;  
			String fileName = ".\\Docs\\" + converted.toString() +".txt" ;
			File myFile = new File(fileName) ; 
			Scanner myScanner = null;
			try {
				myScanner = new Scanner(myFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			while(myScanner.hasNext()) {
				String dataString = myScanner.next() ;
				dataString = Modification(dataString) ;
				if(!propositionsList.contains(dataString)) {
					invert.put(dataString, i); ; 
					numberWords++ ; 
				}
				 
			}
			myScanner.close();
		}
		
////////////////////////////////////////////////////////////////////////
//        **  AND Query Processing Inverted Indexes  ** 
		
		invert.AND("cairo", "university");  // Here is an example
//		First Posting list :- 
//		Doc freq. : 5
//		Doc Id : 100, 101, 103, 104, 108
//
//		Second Posting list :- 
//		Doc freq. : 5
//		Doc Id : 100, 101, 105, 107, 109
//
//		AND (Intersect) Posting list: 
//		Doc freq. : 2
//		Doc Id : 100, 101
////////////////////////////////////////////////////////////////////////
		

		
		
////////////////////////////////////////////////////////////////////////
//          **  OR Query Processing Inverted Indexes   **
		invert.OR("cairo", "software"); // Here is an example 
//		First Posting list :- 
//		Doc freq. : 6
//		Doc Id : 100, 101, 103, 104, 107, 108
//
//		Second Posting list :- 
//		Doc freq. : 5
//		Doc Id : 100, 101, 107, 108, 109
//
//		First OR Second Posting list: 
//		Doc freq. : 7
//		Doc Id : 100, 101, 103, 104, 107, 108, 109
////////////////////////////////////////////////////////////////////////		
		
		
		
////////////////////////////////////////////////////////////////////////
//			**  NOT Query Processing Inverted Indexes   **
		invert.NOT("cairo", "software"); // Here is an example 
//		First Posting list :- 
//		Doc freq. : 6
//		Doc Id : 100, 101, 103, 104, 107, 108
//
//		Second Posting list :- 
//		Doc freq. : 5
//		Doc Id : 100, 101, 107, 108, 109
//
//		(First AND (NOT Second)) Posting list: 
//		Doc freq. : 2
//		Doc Id : 103, 104
//////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////
//			**  NOT Query Processing Inverted Indexes   **
		invert.NOT("cairo", "university"); // Here is an example 
//		First Posting list :- 
//		Doc freq. : 6
//		Doc Id : 100, 101, 103, 104, 107, 108
//
//		Second Posting list :- 
//		Doc freq. : 8
//		Doc Id : 100, 101, 103, 104, 105, 106, 107, 108
//
//		(First AND (NOT Second)) Posting list: 
//		Doc freq. : 0
//		Doc Id : There are no Doc Id
//////////////////////////////////////////////////////////////////
		

//////////////////////////////////////////////////////////////////
//  	    ** Jecard similarity  **
		System.out.println() ;  
		System.out.print("Jecard similarity of file with given input is : ");
		System.out.println(invert.JecardSimilarity("Faculty of Computers and Artificial Intelligence", 0)); 
		
		//  Jecard similarity of file with given input is : 0.01
//////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////
//			** Jecard dissimilarity  **
			System.out.println() ;  
			System.out.print("Jecard dissimilarity of file with given input is : ");
			System.out.println(invert.JecardDisSimilarity("Faculty of Computers and Artificial Intelligence", 0)); 

			//  Jecard dissimilarity of file with given input is : 0.99
//////////////////////////////////////////////////////////////////

		
//		
//		try {
//			
////			System.out.println(new String(readFromFile(fileName,9,4)));
////			writeToFile(fileName, 9, "Let's write in Random Access file");
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	private static byte[] readFromFile(String filePath, int StartPos, int size) throws IOException {
		RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r") ;
		randomAccessFile.seek(StartPos);
		byte[] bytes = new byte[size] ; 
		randomAccessFile.read(bytes) ;
		randomAccessFile.close(); 
		return bytes ; 
	}
	
	private static void writeToFile(String filePath, int StartPos, String context) throws IOException {
		RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw") ; 
		randomAccessFile.seek(StartPos);
		randomAccessFile.write(context.getBytes());
		randomAccessFile.close();
	}
}
