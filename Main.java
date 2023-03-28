import java.util.Collections;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Insert values one at a time and finish with an empty line.");
        System.out.println("Type 'end' or 'eof' to close the program early.");
        System.out.println();
        List<Integer> numbers = new ArrayList<>(Arrays.asList());

        // create infinite for loop to gather inputs (infinite until 32bit int limit
        // ¯\_(ツ)_/¯)
        for (int i = 1; true; i++) {
            System.out.println("Please enter number " + i + " in your sequence or a new line to finish.");
            // wait until user input...
            String input = in.nextLine();

            // if line is empty:
            if (input.equals("")) {
                break;
            }

            // check for a request to terminate
            if (input.equals("end") || input.equals("eof")) {
                System.out.println("Goodbye world.");
                // close with exit code 0 (no error)
                System.exit(0);
            }

            // use a try-catch to parse the input as a integer
            try {
                numbers.add(Integer.valueOf(input));

                // if not a valid integer or above integer limit, raise clean print
            } catch (Exception e) {
                // do *not* increment counter if not a valid integer
                i--;
                System.out.println("There was an issue converting your input to an Integer, please try again.");

                // then, add a new line
            } finally {
                System.out.println("");
            }

        }
        in.close();

        // create stem arraylist that will store leave lists
        List<List<Integer>> stems = new ArrayList<List<Integer>>();

        // Begin parsing each value in array via loop:
        for (int i = 0; i < numbers.size(); i++) {
            // create leave list which whill store stem + leave values per number
            List<Integer> leaves = new ArrayList<Integer>();

            // Pull individual entry from numbers arrayList
            int number = numbers.get(i);
            // Convert entry to String to use .length and .substring (slower but shorter)
            String numStr = String.valueOf(number);
            // Create an always-positive cutting point
            Integer stemStop = Math.max(0, numStr.length() - 1);
            // Initialize output integers
            Integer leavePart;
            Integer stemPart = 0;

            leavePart = Integer.valueOf(numStr.substring(stemStop));
            // if given number has more than 1 digit
            if (numStr.length() > 1) {
                // cut a stem for the part
                stemPart = Integer.valueOf(numStr.substring(0, stemStop));
            }

            // check if stems already contains the stemPart
            int index = -1;
            // loop through each stem
            for (int j = 0; j < stems.size(); j++) {
                // get the stemPart of each stem
                Integer leavestem = stems.get(j).get(0);
                // if the pulled stemPart is the current stemPart
                if (leavestem == stemPart) {
                    index = j;
                    break;
                }
            }
            // add current leavePart to this stemPart if ^ is triggered
            if (index != -1) {
                // get array entry in question
                List<Integer> entry = stems.get(index);
                entry.add(leavePart);
                stems.set(index, entry);

            } else {
                // add values to this leaf
                leaves.add(stemPart);
                leaves.add(leavePart);
                // add leaf to the stem
                stems.add(leaves);
            }
        }
        // sorts stems, no clue how i stole it from stackoverflow
        Collections.sort(stems, (l1, l2) -> l1.get(0).compareTo(l2.get(0)));

        // begin final print
        System.out.println("  Stems   |  Leaves");
        System.out.println("__________|_________");
        for (int i = 0; i < stems.size(); i++) {
            // get the current stem
            int stem = stems.get(i).get(0);
            // create arrayList only containing leaves from the stem
            List<Integer> leaves = new ArrayList<Integer>();
            for (int j = 1; j < stems.get(i).size(); j++) {
                leaves.add(stems.get(i).get(j));
            }
            // sort leaves
            Collections.sort(leaves);
            // format leaves array by converting it to string and using .substring
            String leavesFormatted = leaves.toString().substring(1, leaves.toString().length() - 1);
            // calculate number of prefix space padding is needed for the stem,
            String prefixSpaces = "         ".substring(Math.min(String.valueOf(stem).length(), 9));
            // print formatted stem
            System.out.println(prefixSpaces + stem + " | " + leavesFormatted);
        }
    }
}
