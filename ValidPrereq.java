package prereqchecker;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
	// WRITE YOUR CODE HERE
        StdIn.setFile(args[0]);
        
        HashMap<String, ArrayList<String>> adjMap = new HashMap<String, ArrayList<String>>();
        
        int numCourses = StdIn.readInt();
        for (int i=0; i<numCourses; i++) adjMap.put(StdIn.readString(), new ArrayList<String>());
        
        int numprereqs = StdIn.readInt();
        for (int i=0; i<numprereqs; i++) adjMap.get(StdIn.readString()).add(StdIn.readString());
        
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        String courseAddTo = StdIn.readString();
        String addedCourse = StdIn.readString();
        adjMap.get(courseAddTo).add(addedCourse);

        HashSet<String> current = new HashSet<String>();

        if (isValid(courseAddTo, current, adjMap)) StdOut.print("YES");
        else StdOut.print("NO");
    }
    
    private static boolean isValid(String course, HashSet<String> current, HashMap<String, ArrayList<String>> adjMap){
        ArrayList<String> iterator = adjMap.get(course);
        if (current.contains(course)) return false;
        else current.add(course);
        boolean x=true;
        for (String next:iterator){
            x = isValid(next, current, adjMap);
            if (!x) break;
        }
        current.remove(course);
        
        return x;
    }

}
