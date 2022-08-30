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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
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
        HashSet<String> prereqs = new HashSet<String>();
        String target = StdIn.readString();
        Queue<String> q = new LinkedList<String>();
        q.add(target);
        while (!q.isEmpty()){
            String course = q.remove();
            for (String reqTaken:adjMap.get(course)){
                if(!prereqs.contains(reqTaken)){
                    q.add(reqTaken);
                    prereqs.add(reqTaken);
                }
            }
        }


        HashSet<String> taken = new HashSet<String>();
        int numTaken = StdIn.readInt();
        for (int i = 0; i<numTaken; i++){
            String takenCourse = StdIn.readString();
            Queue<String> searched = new LinkedList<String>();
            taken.add(takenCourse);
            searched.add(takenCourse);
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
        
        for (String iterator: prereqs) if (!taken.contains(iterator)) StdOut.println(iterator);

    }
    }

