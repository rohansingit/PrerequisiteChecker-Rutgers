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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
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
    HashMap<String, Integer> prereqs = new HashMap<String, Integer>();
    String target = StdIn.readString();
    Queue<String> q = new LinkedList<String>();
    prereqs.put(target, 0);
    q.add(target);
    while (!q.isEmpty()){
        String course = q.remove();
        for (String reqTaken:adjMap.get(course)){
            if(!prereqs.containsKey(reqTaken)){
                q.add(reqTaken);
                prereqs.put(reqTaken, prereqs.get(course)+1);
            }
        }
    }
    prereqs.remove(target);


    HashSet<String> taken = new HashSet<String>();
    int numTaken = StdIn.readInt();
    for (int i = 0; i<numTaken; i++){
        String takenC = StdIn.readString();
        Queue<String> searched = new LinkedList<String>();
        taken.add(takenC);
        searched.add(takenC);
        while (!searched.isEmpty()){
            String courses = searched.remove();
            for (String reqTaken:adjMap.get(courses)){
                if(!taken.contains(reqTaken)){
                    searched.add(reqTaken);
                    taken.add(reqTaken);
                }
            }
        }
    }
    HashMap<String, Integer> yetToTake = new HashMap<String, Integer>();
    int sems = 0;
    for (String iterator: prereqs.keySet()){ 
        if (!taken.contains(iterator)) {
            yetToTake.put(iterator, prereqs.get(iterator));
            if (prereqs.get(iterator)>sems) sems=prereqs.get(iterator);
        }
    }
    StdOut.println(sems);
    while (yetToTake.size()>0){
        HashSet<String> thisSemCourses = new HashSet<String>();
        for (String ite: adjMap.keySet()){
            if (taken.contains(ite)) continue;
            boolean good = true;
            for (String pre:adjMap.get(ite)){
                if (!taken.contains(pre)) good = false; 
            }
            if (good && yetToTake.containsKey(ite)) { 
                StdOut.print(ite + " ");
                yetToTake.remove(ite);
                thisSemCourses.add(ite);
            }
        }
        for (String x: thisSemCourses) taken.add(x);
    StdOut.println();
    }
    }
}
