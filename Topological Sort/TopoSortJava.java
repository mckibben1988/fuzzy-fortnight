/*So a couple of notes: Wrote three methods which I think should be all encompassing for the three buttons.
I also take care of first and last for what we talked about at lunch, Clayton, but checking for a substring.
Wrote the constraints into two ArrayLists instead of 1 2D array like I'd done in Javascript, so make sure you have:
String first="", String last="", and 4 ArrayLists for the objects, constraint set 1, constraint set 2, and the final/sorted
(I know you'd said you already had names for those four)
Otherwise, I think all you have to do is plug your GUI variable names into the methods as they're called.
It all compiles at this point.  going to do some basic bug testing, but I think we're mostly there.
***Didn't write a Reset method yet.  If you or Sarah could tackle that, I've noted a couple places where it might be nice to call it.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//Assumes an arraylist for objects, both constraints, and final sorted list already created
//Also assumes variables first and last created.

public class TopSort_GUI_Methods{

	String last="";
	String first="";
	
// objects

// Do three things:  Make sure item not empty whitespace  
// Check if the object is in the object list already
// add items to an object list.

public void addObjects(ArrayList<String> arr, String item) {
String temp= item.trim();
boolean flag = false;
if (temp == null || temp == "")								// covers null/empty strings
	flag = true;
else {
	for (int i = 0; i<arr.size(); i++) {					//check if item already in the list
	if (temp.equals(arr.get(i)))
		flag=true;
}															//ends for
}															// ends else
if (!(flag))												//if the item is not already in the list
	arr.add(item);
}															//ends addObjects method


// constraints

public void setConstraints(ArrayList<String> con1, ArrayList<String> con2, ArrayList<String> obj, String constraint1, String constraint2) {
String temp1 = constraint1.trim();
String temp2 = constraint2.trim();
if (!(temp1 == null || temp1=="")) {										// need to check for first, last, and null
	if (temp1.substring(0,4).equals("first")) {
		if (!(first.equals(temp2)) || first.equals(""))	{				// first method
			System.out.println("There are multiple firsts.  The list will reset.  Hit any key to continue");
			Scanner kb= new Scanner(System.in);
			String placeholder = kb.nextLine();
//How can I run the reset method here?  just "reset();"?
		}																//end if case of multiple firsts
		else {
			first=temp2;												// set first
			if (obj.contains(temp2))									// check if in list
				obj.remove(temp2);										// remove if it is
			while (con1.contains(temp2)) {
				int varR = con1.indexOf(temp2);
				con1.remove(varR);
				con2.remove(varR);
				}														//ends if first in constraints
			}
				
		}																//ends first case						
																		
	else if	(temp1.substring(0,3).equals("last")) {
		if (!(last.equals(temp2)) || last.equals(""))	{
			System.out.println("There are multiple lasts.  The list will reset.  Hit any key to continue");
			Scanner kb= new Scanner(System.in);
			String placeholder = kb.nextLine();
//How can I run the reset method here?  just "reset();"?
		}																//ends if case of multiple lasts
		else  {
			last=temp2;													// set last
			if (obj.contains(temp2))									// check if last is in objects
				obj.remove(temp2);										//Remove it if it is
			while (con1.contains(temp2)) {
				int varR = con1.indexOf(temp2);
				con1.remove(varR);
				con2.remove(varR);
			}
		}	
	}																	//ends last case
	else {
	con1.add(temp1);
	con2.add(temp2);
	}																	//ends regular case																
} 																		//ends null/temp if
}																		//ends setConstraints method


public void sortObjects(ArrayList<String> sorted, ArrayList<String> con1,ArrayList<String> con2,ArrayList<String> items){
if (!(first==""))
	sorted.add(first);

for (int k=0; k<items.size();)  {
    String temp = items.get(k);
    boolean flag = false;
      for (int m = 0; m<con2.size(); m++) { //while flag is false
    if (temp.equals(con2.get(m)))
    	flag = true;
      }
    if (flag)
    	k++;
   	else {
   		 sorted.add(temp);
   		 items.remove(k);
   		 k=0;
   	}
     
    for (int n=0; n<con1.size();)
    	if (temp.equals(con1.get(n))) {
    	  con1.remove(n);
    	  con2.remove(n);
    	} //if inside loop to remove constraints
    	 else
    	  n++;
    	}
if (!(last==""))
	sorted.add(last);
}


//  reset method, named "reset"


	public static void main(String[] args) {
		// TODO Auto-generated method stub


}
}
