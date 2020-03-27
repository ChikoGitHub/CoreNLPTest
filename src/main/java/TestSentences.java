import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class TestSentences {

    public static void main(String[] args) {

        BufferedReader readerNonVerbs;
        BufferedReader readerVerbs;

        int verbs = 0;
        int non_verbs = 0;

        StanfordCoreNLP pipeline;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        pipeline = new StanfordCoreNLP(props);

        final List<String> verbTags = Arrays.asList("VB", "VBG", "VBN");

        try {
            FileWriter writer = new FileWriter("src/main/java/results.txt");
            BufferedWriter bufferedWriter = new BufferedWriter((writer));

            testCase(1, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(1, "non_verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(2, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(2, "non_verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(3, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(3, "non_verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(4, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(4, "non_verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(5, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(5, "non_verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(6, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(6, "non_verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(7, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(7, "non_verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(8, "verbs.txt", pipeline, verbTags, bufferedWriter);
            testCase(8, "non_verbs.txt", pipeline, verbTags, bufferedWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testCase( int test, String file, StanfordCoreNLP pipeline, List<String> verbTags, BufferedWriter bufferedWriter ){

        String testTextBeforeWord;
        String testTextAfterWord;
        int wordPosition;
        int verbs = 0;
        int non_verbs = 0;

        if (test == 1 ){
            testTextBeforeWord = "";
            testTextAfterWord = "";
            wordPosition = 0;
        }
        else if (test == 2){
            testTextBeforeWord = "I will ";
            testTextAfterWord = " this.";
            wordPosition = 2;
        }
        else if (test == 3){
            testTextBeforeWord = "Do ";
            testTextAfterWord = " that.";
            wordPosition = 1;
        }
        else if (test == 4){
            testTextBeforeWord = "";
            testTextAfterWord = " that.";
            wordPosition = 0;
        }
        else if (test == 5){
            testTextBeforeWord = "I am thinking about ";
            testTextAfterWord = ".";
            wordPosition = 4;
        }
        else if (test == 6){
            testTextBeforeWord = "I am about to ";
            testTextAfterWord = ".";
            wordPosition = 4;
        }
        else if (test == 7){
            testTextBeforeWord = "I would like to ";
            testTextAfterWord = ".";
            wordPosition = 4;
        }
        else if (test == 8){
            testTextBeforeWord = "I ";
            testTextAfterWord = "";
            wordPosition = 1;
        }
        else {
            return;
        }

        try {

            //"C:\\Users\\Chiko\\Desktop\\CoreNLPTest\\src\\main\\java\\"

            BufferedReader reader = new BufferedReader( new FileReader("src/main/java/" + file));

            String line = reader.readLine();

            while (line != null){
                String[] numberAndWord = line.split(" ");

                String textToTest= testTextBeforeWord + numberAndWord[1] + testTextAfterWord;

                Annotation document = new Annotation(textToTest);

                pipeline.annotate(document);

                List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

                String partOfSpeech = sentences.get(0).get(CoreAnnotations.TokensAnnotation.class).get(wordPosition).get(CoreAnnotations.PartOfSpeechAnnotation.class);
                //String word = sentences.get(0).get(CoreAnnotations.TokensAnnotation.class).get(wordPosition).get(CoreAnnotations.TextAnnotation.class);

                if( verbTags.contains(partOfSpeech) ){
                    verbs++;
                }else {
                    non_verbs ++;
                }

                line = reader.readLine();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        try {
            bufferedWriter.write("Results for test " + test + " ( " + file + " dataset) ----> " + testTextBeforeWord + " _____ " + testTextAfterWord);
            bufferedWriter.newLine();

            int total = verbs + non_verbs;

            bufferedWriter.write("Total avaliations ----- " + total);
            bufferedWriter.newLine();

            bufferedWriter.write("verbs ----------------- " +  verbs );
            bufferedWriter.newLine();

            bufferedWriter.write("verbs ----------------- " +  non_verbs );
            bufferedWriter.newLine();

            bufferedWriter.write("-------------------------------------------------------------------------------------------------");
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
