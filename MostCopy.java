import java.util.*;
import java.io.*;

public class MostCopy {
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

        System.out.print("Count: ");
        int num = Integer.parseInt(sc.next());

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

        ArrayList<String> namesList = new ArrayList<>();
        
        int k = 0;

        // read in names and record popularity
        while(fileReader.hasNext())
        {
            String inputName = fileReader.next();
          
            if(names.containsKey(inputName) == false) // if the name is not already in the hashtable
            {
                names.put(inputName, 1); 
                namesList.add(inputName);
                k++;
            }
            else{ // this would mean that the name is already in the table
                // increase the frequency of that name
                int oldVal = names.get(inputName);
                oldVal += 1;
                names.replace(inputName, oldVal);
            }

        }
        
        // calc misspellability of each name and store in hashtable
        Hashtable<String, Integer> misspellabilityAll = new Hashtable<>();

        // arraylist declaraion to optimize
        ArrayList<String>[] similarWords = new ArrayList[k];
        for(int i = 0; i < k; i++) // initialize arraylist
        {
            similarWords[i] = new ArrayList<>();
        }

        Set<String> keySet = names.keySet();
        Object[] arr = keySet.toArray();
        Set<String> keySet2 = names.keySet();
        for(int i = 0; i < k; i++)
        {
            double sum = 0;

            String curr= namesList.get(i);
            
            for(String s : similarWords[i])
            {
                if(names.get(s) < names.get(curr))
                {
                    sum+= names.get(s);
                }
                
            }
            for(int j = i; j < k; j++)
            {
                String comp = namesList.get(j);
                if(curr.equals(comp) == false)
                {
                    if(is_similar(comp, curr))
                    {
                        similarWords[j].add(curr);
                        if(names.get(comp) < names.get(curr))
                            sum += names.get(comp); // add if less popular than current name
                    }
                }
            }
            // calc misspellability of this name
            int misspellability = (int)((sum / names.get(curr))*100 + 0.5);
            misspellabilityAll.put(curr, misspellability);
        }
       // System.out.println("HERE");
        
        // System.out.println("misspellabilityAll: \n" + misspellabilityAll);
        ArrayList<String> maxKeys = new ArrayList<>();

        Set<String> keyLoop = misspellabilityAll.keySet();
        while(num != 0)
        {
            String bestKey = "";
            int max = -1;
            for(String thisKey : keyLoop)
            {
                if(misspellabilityAll.get(thisKey) > max && maxKeys.contains(thisKey) == false)
                {
                    max = misspellabilityAll.get(thisKey);
                    bestKey = thisKey;
                }
            }
            maxKeys.add(bestKey); //add the best key to maxKeys
            //remove the best key
            //keyLoop.remove(bestKey);
            num--;
        }


        for(String s : maxKeys)
        {
            System.out.println(s + " " + misspellabilityAll.get(s));
        }

    } 
}
