# algo_proj2
Project 2 for Computer Algorithms class (Wrong name)
Joey Press

References:
I consulted the java API a lot for Single.java, particularly with regards to hashtables:
https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html
Most notably was when I used the keySet() method to loop through the hashTable object.


How the program works:
My is_similar() method is fairly straightforward. It handles 3 cases. If the words are greater than 1 difference in length, return false. If they are the same length, loop through and count the amount of different characters, return false if more than 2. Finally, if they are 1 letter off in terms of length, loop through until you find a difference, then add the previous character at that index to the shorter word and check for equality (return true if so). I have rigorously tested this method and I am confident it handles all test cases. 

For Single.java, I read in the file and store the names in a hashtable, ignoring duplicates. The key to making this fast is checking if the name is similar while reading in, that way my hashtable only has names that are similar to input and their popularity (length = k). Then, it just comes down to looping through the k length list and calculating the misspellability. 

Finally, for Most.java, I took the same approach to reading in the file as part 1, but this time we need to store all the names (not just similar ones). Then, I loop through the list of names, comparing them to each other name in the list, doing the calculation, and then storing the results in a separate hashtable. I was able to optimize it by starting the inner for loop at the current index and storing the list of similar names in an an array of arraylists. After that, we need to find the names with the most misspellability, so loop through the k-list again and find the top number of names in accordance with what the user input.


How I came up with the idea for the algorithms:
I took the outline phase of this assignment seriously, and outlined the way that I was going to handle the is_similar function and the basic logic Single.java. I then wrote the program to just work, and once I got it working, I optimized it as much as possible (by calculating the popularity while reading in the file and using a Hashtable).
Also, writing out a sample list and performing my algorithm on the list proved to be really useful for part 2. This was particularly evident in my EI with LT Pfau, where I recognized that making the program fast comes down to limiting the amount of times that I call is_similar(). 


Analysis of worst-case run time of algorithm in terms of n, k, and m:
n = # of names in a given list
k = # of unique names
m = maximum length of each name

is_similar()
Worst case: O(m), this is because in the worst case it will have to go to the end of the name to find the defect while looping through letters. 

Single.java()
Worst case: O(n*m + k)
This is because it is going to call is_similar() n times, then loop through the resultant k-length list once. It looks ugly, but this is actually really good because k is usually << n. Also, in practice, the list of names produced by the first loop is going to be significantly smaller than k because it only contains unique names that are similar to the input.

Most.java()
Worst case: O(n + k + k^2 + k)
This is because we loop through n times to extract the data, k times to initialize an array of arraylists, k^2 times in the for loop (worst case only), and then k to find the maximum names. This may look bad, but I was able to optimize this by cleaning up the k^2 term, specifically by using the array of arraylists to not check terms before the current index, making it closer to k*lgk in practice.


