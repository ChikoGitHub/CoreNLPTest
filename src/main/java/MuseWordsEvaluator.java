import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.*;
import java.util.*;

public class MuseWordsEvaluator {

    public static void main(String[] args) {

        BufferedReader reader;

        StanfordCoreNLP pipeline;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        pipeline = new StanfordCoreNLP(props);

        final List<String> verbTags = Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ");

        Map<String, Integer> map1 = new HashMap<>(); // just the word
        Map<String, Integer> map2 = new HashMap<>(); // I will ___ this.
        Map<String, Integer> map3 = new HashMap<>(); // Do not ___.
        Map<String, Integer> map4 = new HashMap<>(); // ___ that.

        for (String tag : verbTags){
            map1.put(tag, 0);
            map2.put(tag, 0);
            map3.put(tag, 0);
            map4.put(tag, 0);
        }

        map1.put("NotVerb", 0);
        map2.put("NotVerb", 0);
        map3.put("NotVerb", 0);
        map4.put("NotVerb", 0);

        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\Chiko\\Desktop\\CoreNLPTest\\src\\main\\resources\\muse_just_words.txt"));

            String line = reader.readLine();

            while(line != null){

                //test for case 1, results in map 1

                Annotation document1 = new Annotation((line));

                pipeline.annotate(document1);

                List<CoreMap> sentences1 = document1.get(CoreAnnotations.SentencesAnnotation.class);
                String partOfSpeech1 = sentences1.get(0).get(CoreAnnotations.TokensAnnotation.class).get(0).get(CoreAnnotations.PartOfSpeechAnnotation.class);

                if( verbTags.contains(partOfSpeech1)) {
                    map1.put(partOfSpeech1, (map1.get(partOfSpeech1) + 1));
                } else {
                    map1.put("NotVerb", (map1.get("NotVerb") + 1));
                }


                //test for case 2, results in map 2

                String testCase2 = "Do not " + line + ".";

                Annotation document2 = new Annotation((testCase2));

                pipeline.annotate(document2);

                List<CoreMap> sentences2 = document2.get(CoreAnnotations.SentencesAnnotation.class);
                String partOfSpeech2 = sentences2.get(0).get(CoreAnnotations.TokensAnnotation.class).get(2).get(CoreAnnotations.PartOfSpeechAnnotation.class);

                if( verbTags.contains(partOfSpeech2)) {
                    map2.put(partOfSpeech2, (map2.get(partOfSpeech2) + 1));
                } else {
                    map2.put("NotVerb", (map2.get("NotVerb") + 1));
                }


                //test for case 3, results in map 3

                String testCase3 = "I will " + line + " this.";

                Annotation document3 = new Annotation((testCase3));

                pipeline.annotate(document3);

                List<CoreMap> sentences3 = document3.get(CoreAnnotations.SentencesAnnotation.class);
                String partOfSpeech3 = sentences3.get(0).get(CoreAnnotations.TokensAnnotation.class).get(2).get(CoreAnnotations.PartOfSpeechAnnotation.class);

                if( verbTags.contains(partOfSpeech1)) {
                    map3.put(partOfSpeech3, (map3.get(partOfSpeech3) + 1));
                } else {
                    map3.put("NotVerb", (map3.get("NotVerb") + 1));
                }


                //test for case 4, results in map 4

                String testCase4 = line + " that.";

                Annotation document4 = new Annotation((testCase4));

                pipeline.annotate(document4);

                List<CoreMap> sentences4 = document4.get(CoreAnnotations.SentencesAnnotation.class);
                String partOfSpeech4 = sentences4.get(0).get(CoreAnnotations.TokensAnnotation.class).get(0).get(CoreAnnotations.PartOfSpeechAnnotation.class);

                if( verbTags.contains(partOfSpeech4)) {
                    map4.put(partOfSpeech4, (map4.get(partOfSpeech4) + 1));
                } else {
                    map4.put("NotVerb", (map4.get("NotVerb") + 1));
                }

                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            System.err.println("Problem reading a line.");
        }

        try{

            FileWriter writer1 = new FileWriter("src/main/resources/muse_case_1_results.txt");
            BufferedWriter bufferedWriter1 = new BufferedWriter((writer1));

            FileWriter writer2 = new FileWriter("src/main/resources/muse_case_2_results.txt");
            BufferedWriter bufferedWriter2 = new BufferedWriter((writer2));

            FileWriter writer3 = new FileWriter("src/main/resources/muse_case_3_results.txt");
            BufferedWriter bufferedWriter3 = new BufferedWriter((writer3));

            FileWriter writer4 = new FileWriter("src/main/resources/muse_case_4_results.txt");
            BufferedWriter bufferedWriter4 = new BufferedWriter((writer4));

//----------------------------------------------------------------------------------------------------------------------

            int verbs = 0;

            for( Map.Entry<String, Integer> entry : map1.entrySet()){
                if(!entry.getKey().equals("NotVerb")){
                    bufferedWriter1.append(entry.getKey());
                    bufferedWriter1.append(" ");
                    bufferedWriter1.append(entry.getValue().toString());
                    bufferedWriter1.newLine();
                    bufferedWriter1.flush();
                    verbs = verbs + entry.getValue();
                }
            }

            bufferedWriter1.newLine();
            bufferedWriter1.append("Verbs: " + verbs);
            bufferedWriter1.newLine();
            bufferedWriter1.append("NotVerbs: " + map1.get("NotVerb"));
            bufferedWriter1.flush();
            verbs = 0;

//----------------------------------------------------------------------------------------------------------------------

            for( Map.Entry<String, Integer> entry : map2.entrySet()){
                if(!entry.getKey().equals("NotVerb")){
                    bufferedWriter2.append(entry.getKey());
                    bufferedWriter2.append(" ");
                    bufferedWriter2.append(entry.getValue().toString());
                    bufferedWriter2.newLine();
                    bufferedWriter2.flush();
                    verbs = verbs + entry.getValue();
                }
            }

            bufferedWriter2.newLine();
            bufferedWriter2.append("Verbs: " + verbs);
            bufferedWriter2.newLine();
            bufferedWriter2.append("NotVerbs: " + map2.get("NotVerb"));
            bufferedWriter2.flush();
            verbs = 0;

//----------------------------------------------------------------------------------------------------------------------

            for( Map.Entry<String, Integer> entry : map3.entrySet()){
                if(!entry.getKey().equals("NotVerb")){
                    bufferedWriter3.append(entry.getKey());
                    bufferedWriter3.append(" ");
                    bufferedWriter3.append(entry.getValue().toString());
                    bufferedWriter3.newLine();
                    bufferedWriter3.flush();
                    verbs = verbs + entry.getValue();
                }
            }

            bufferedWriter3.newLine();
            bufferedWriter3.append("Verbs: " + verbs);
            bufferedWriter3.newLine();
            bufferedWriter3.append("NotVerbs: " + map3.get("NotVerb"));
            bufferedWriter3.flush();
            verbs = 0;

//----------------------------------------------------------------------------------------------------------------------

            for( Map.Entry<String, Integer> entry : map4.entrySet()){
                if(!entry.getKey().equals("NotVerb")){
                    bufferedWriter4.append(entry.getKey());
                    bufferedWriter4.append(" ");
                    bufferedWriter4.append(entry.getValue().toString());
                    bufferedWriter4.newLine();
                    bufferedWriter4.flush();
                    verbs = verbs + entry.getValue();
                }
            }

            bufferedWriter4.newLine();
            bufferedWriter4.append("Verbs: " + verbs);
            bufferedWriter4.newLine();
            bufferedWriter4.append("NotVerbs: " + map4.get("NotVerb"));
            bufferedWriter4.flush();

//----------------------------------------------------------------------------------------------------------------------

        } catch (IOException e){
            System.err.println("Error");
            e.printStackTrace();
        }



    }

}
