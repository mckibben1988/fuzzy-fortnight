import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TopoSortJava {
	
	private static Scanner f1;
	private static Scanner f2;

	public static void main(String[] args) {
		ArrayList<String> clothingList = new ArrayList<>();
		ArrayList<String> constraintList = new ArrayList<>();
		ArrayList<String> sorted = new ArrayList<>();
		
    	f1 = new Scanner(System.in);
    	System.out.println("Item List File Name:");
    	String file1 = f1.nextLine();
    	
    	f2 = new Scanner(System.in);
    	System.out.println("Constraint List File Name:");
    	String file2 = f2.nextLine();
    	
    	try {
			readFileToString(file1, clothingList);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	
    	try {
			readFileToString(file2, constraintList);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	
		String[][] matrix = new String[constraintList.size()][]; 
		
		array2Dimensional(constraintList, matrix);
		sortItems(sorted, matrix, clothingList);
		
		if(sorted.size()>0) {
			System.out.println("The sorted list is: ");
			System.out.println(sorted.toString());
		}
	} // end main

	static void readFileToString(String fileName, ArrayList<String> list) throws IOException {
		BufferedReader fileIn = new BufferedReader(new FileReader (fileName));
		String line;
		while ((line = fileIn.readLine()) != null)
		    list.add(line);
		fileIn.close();
		
	} // end readFileToString
	
	static void array2Dimensional(ArrayList<String> list, String[][] matrix) {
		int r = 0;
		for (String row : list) {
			if (row.indexOf('<') < 0) {
				if(row.endsWith("first"))
					row = row.substring(0, row.length()-6) + "<" + "first";
				else if (row.endsWith("last"))
					row = row.substring(0, row.length()-5) + "<" +"last";
				else
					row = "empty < empty";
			}
			matrix[r++] = row.split("\\<");	
		}
		for (int i = 0; i < matrix.length; i++) 
			for (int j =0; j < 2; j++)
				matrix[i][j] = matrix[i][j].trim();
	} // end array2Dimensional 
	
	static void sortItems(ArrayList<String> sorted, String[][] matrix, ArrayList<String> clothingList) {
		ArrayList<String> temp = new ArrayList<String>();
		int countFirst = 0;
		int countLast = 0;
		for(int i = 0; i < matrix.length; i++) {
			if (matrix[i][1].equals("first"))
			    countFirst++;
			if (matrix[i][1].equals("last"))
				countLast++;
		}
		if(countFirst > 1 || countLast > 1) 
			System.out.println("The list cannot be sorted");
		else {
			for(int i = 0; i < matrix.length; i++)
				if (matrix[i][1].equals("first")) {
					if(clothingList.contains(matrix[i][0])){
						String item = matrix[i][0];
						sorted.add(item);
						for(int k = 0; k < matrix.length; k++)
							if(matrix[k][0].equals(item)) {
								matrix[k][0] = "empty";
								matrix[k][1] = "empty";
							}
						for(int j = clothingList.size() -1; j >=0; j--) {
							if (clothingList.get(j).equals(item))
								clothingList.remove(j);
						}
					}
				}
			String lastItem = null;
			for(int i = 0; i < matrix.length; i++)
				if(matrix[i][1].equals("last")) {
					if(clothingList.contains(matrix[i][0])) {
						lastItem = matrix[i][0];
						for(int k =0; k < matrix.length; k++) {
							if(matrix[k][0].equals(lastItem) && !matrix[k][1].equals("last")) {
								System.out.println("The list cannot be sorted.");
								break;
							}
							else
								for(int h = clothingList.size() -1; h>=0; h--) {
									if(clothingList.get(h).equals(lastItem))
										clothingList.remove(h);
								}	
						}
					}
				}
			while(!clothingList.isEmpty()) {
				int size = clothingList.size();
				for(int i = clothingList.size()-1; i >=0; i--) {
					boolean constrained = false;
					for(int j = 0; j < matrix.length; j++) {
						if(matrix[j][1].equals(clothingList.get(i)))
							constrained = true;
					}
					if(constrained)
						temp.add(clothingList.get(i));
					else {
						sorted.add(clothingList.get(i));
						for(int k = 0; k < matrix.length; k++)
							if(matrix[k][0].equals(clothingList.get(i))) {
								matrix[k][0] = "empty";
								matrix[k][1] = "empty";
							}	
					}
					clothingList.remove(i);
				}
				if(temp.size() == size) {
					System.out.println("There is no solution for the sort.");
					break;
				}
				else {
					for(int h = temp.size()-1; h >=0; h--) {
						clothingList.add(temp.get(h));
						temp.remove(h);
					}
				}
			}
			if (lastItem != null)
				sorted.add(lastItem);
		}
	}
} // end class
