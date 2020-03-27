import com.sun.corba.se.impl.logging.InterceptorsSystemException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;


import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;


public class WordCounter {

    public static void main(String[] args) {

        BufferedReader words = null;
        try {
            words = new BufferedReader(new FileReader("C:\\Users\\Chiko\\Desktop\\CoreNLPTest\\src\\main\\resources\\muse_words.txt"));
            System.out.println("Success in loading the test words");
        } catch (FileNotFoundException e) {
            System.err.println("Words file not found");
        }

        String word;

        int totalWords = 0;
        int totalEntries = 0;

        Map<String, Integer> map = new HashMap<>();

        try {

            while ((word = words.readLine())!= null){

                Integer wordCount = (Integer) map.get(word);

                if (wordCount != (null)){

                    wordCount ++;
                    map.put(word, wordCount);

                } else {

                    map.put(word, 1);

                }
            }

            FileWriter writer = new FileWriter("src/main/resources/muse_counted_words.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            FileWriter writer2 = new FileWriter("src/main/resources/muse_just_words.txt");
            BufferedWriter bufferedWriter2 = new BufferedWriter(writer2);

            Map<String, Integer> sortedMap = sortByComparator(map, false);

            for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                totalWords = totalWords + entry.getValue();
                totalEntries++;
                bufferedWriter.append(entry.getKey());
                bufferedWriter.append(" ");
                bufferedWriter.append(entry.getValue().toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();

                bufferedWriter2.append(entry.getKey());
                bufferedWriter2.newLine();
                bufferedWriter2.flush();
            }

        }catch (IOException e) {
            System.err.println("Error");
            e.printStackTrace();
        }

        System.out.println("Total Words: " +  totalWords);
        System.out.println("Total Entries: " +  totalEntries);

    }

    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
