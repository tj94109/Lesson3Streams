package com.terrance.Lesson3Streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This program reads from the JobResult_124432.txt file, counts the number pattern 00000000nnnnnnnn occurs, compares the time it takes in milliseconds.
 * @author TJ
 *
 */

public class Lesson3Streams {

    private static final String regex = ".*0{8}[0-9A-fa-f]{8}.*";
    private static List<String> words;
    private static long streamBefore,parallelStreamBefore, streamAfter, parallelStreamAfter, streamDifference, parallelStreamDifference, streamCount, parallelStreamCount;

    public static void main(String args[]) throws IOException {
        String filename = null;
        if(args.length > 0){
            filename = args[0];
            System.out.println(filename);
        }

        String contents = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        words = List.of(contents.split(","));
        int tryCount = 1;
        long difference = 1;

        while(streamDifference <= parallelStreamDifference){

            System.out.println("\nTry "+tryCount + ": " +"\nString Size: " + contents.length());

            //Stream time measurement
            streamBefore = System.currentTimeMillis();
            streamCount = getStreamCount();
            streamAfter = System.currentTimeMillis();

            streamDifference = streamAfter - streamBefore;
            System.out.println("Milliseconds using stream: " + streamDifference);

            //Parallel time measurement
            parallelStreamBefore = System.currentTimeMillis();
            parallelStreamCount = getParallelStreamCount();
            parallelStreamAfter = System.currentTimeMillis();

            parallelStreamDifference = parallelStreamAfter - parallelStreamBefore;
            System.out.println("Milliseconds using parallelStream: " + parallelStreamDifference);

            if(streamDifference <= parallelStreamDifference) {
                difference = parallelStreamDifference - streamDifference;
                System.out.println("Results: stream was " + difference + " milliseconds faster than parallel stream \nDoubling string size and trying again.");
            }else{
                difference = streamDifference - parallelStreamDifference;
                System.out.println("Results: parallel stream was "+ difference + " milliseconds faster than stream.\nAll Done!");

            }
            tryCount++;

            contents = contents+contents;

        }
    }

    //helper method to count regex matches using stream
    private static long getStreamCount(){
        return words.stream().filter(s -> s.matches(regex)).count();
    }

    //helper method to count regex matches using parallel stream
    private static long getParallelStreamCount(){
        return words.parallelStream().filter(s -> s.matches(regex)).count();
    }
}
