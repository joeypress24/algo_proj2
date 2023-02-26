import java.io.*;
import java.util.*;

public class Single {

    /*
    Returns true if the two names have an edit distance of exactly 1, which means they are one addition, subtraction, removal,
    or switch from eachother.
    */
    private static boolean is_similar(String n1, String n2)
    {
        // convert both strings to lowercase to avoid errors with capitalization
        n1 = n1.toLowerCase();
        n2 = n2.toLowerCase();
        if(n1.equals(n2))
        {
            return true;
        }
        else if(n1.length() > (n2.length() + 1) || n1.length() < (n2.length() - 1)) // if the names are not the +-1 length of eachother
        {
            return false;
        }
        else if(n1.length() == n2.length()) // if lengths are the same but not equal strings
        {
            // find number of characters that are different
            int numDiff = 0;
            for(int i = 0; i < n2.length(); i++)
            {
                if(n1.charAt(i) != n2.charAt(i))
                {
                    numDiff++;
                }
            }
            if(numDiff > 1) // there are more than 1 different characters
            {
                return false;
            }
            else{ // there are 0 or 1 different characters
                return true;
            }
        }
        else { // this means names are +- one letter but are not identical
            
            // figure out which string is larger
            String temp1; // holds larger string
            String temp2;
            if(n1.length() > n2.length())
            {
                temp1 = n1;
                temp2 = n2;
            }
            else{ // if we get here n2 must be larger
                temp1 = n2;
                temp2 = n1;
            }

            // check for edge case (limited by substring() method)
            if(temp2.equals(temp1.substring(0, temp1.length()-1)))
            {
                return true;
            }
            // ex: Dan
            //     Daan
            for(int i = 0; i < temp2.length(); i++)
            {
                if(temp1.charAt(i) != temp2.charAt(i))
                {
                    String newString = temp2.substring(0, i) + temp1.charAt(i) + temp2.substring(i);
                   // System.out.println("new string: " + newString);
                    if(newString.equals(temp1))
                    {
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                
            }
            return false;

        }
    }
    
    public static void main(String[] args)
    {
        // read in initial data from user
        Scanner sc = new Scanner(System.in);
        System.out.print("File: ");
        String fname = sc.next();

        System.out.print("Name: ");
        String name = sc.next();

        // read in the new file
        Scanner fileReader = null;
        try{
            fileReader = new Scanner(new File(fname));
        }
        catch(FileNotFoundException e)
        {
            System.exit(1);
        }

        // create the hashtable to store names
        Hashtable<String, Integer> names = new Hashtable<>();
        
        // read in names and record popularity
        while(fileReader.hasNext())
        {
            String inputName = fileReader.next();
          
            if(names.containsKey(inputName) == false) // if the name is not already in the hashtable
            {
               if(is_similar(name, inputName)) // only add the name if it's similer (don't care about others)
               {
                   names.put(inputName, 1);
               }
                
            }
            else{ // this would mean that the name is already in the table
                // increase the frequency of that name
                
                int oldVal = names.get(inputName);
                oldVal += 1;
                names.replace(inputName, oldVal);
            }

        }
        
        // iterate through hashtable and calculate probabilities
        double sum = 0;
        int popName = names.get(name); // save it here to limit amount of get() operations
        Set<String> keySet = names.keySet();
        for(String thisKey : keySet)
        {
            if(thisKey.equals(name) == false)
            {
                int numOccurances = names.get(thisKey);
                if(numOccurances < popName) // if X is less popular than the target name
                {
                    sum += names.get(thisKey);
                }
            }
            
        }
        int misspellability = (int)((sum / popName)*100 + 0.5);

        System.out.println(misspellability);

    }


 // test main used for is_similar()
        
    // public static void main(String[] args)
    // {
    //     String n1 = "Joan";
    //     String n2 = "Juan";

    //     System.out.println(n1 + ", " + n2 + " " + is_similar(n1, n2));
    //     // System.out.println("n1: " + n1 + " n2: " + n2 + " is_similar: " + is_similar(n1, n2));
    
    //     n1 = "Carol";
    //     n2 = "Carl";

    //     System.out.println(n1 + ", " + n2 + " " + is_similar(n1, n2));

    //     n1 = "dan";
    //     n2 = "dane";

    //     System.out.println(n1 + ", " + n2 + " " + is_similar(n1, n2));
    // } 

}
