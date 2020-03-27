import java.io.*;


public class NonWordsRemover {

    public static void main(String[] args) {

        BufferedReader dictionary = null;


        BufferedReader words = null;
        try {
            words = new BufferedReader(new FileReader("C:\\Users\\Chiko\\Desktop\\CoreNLPTest\\src\\main\\resources\\words.txt"));
            System.out.println("Success in loading the test words");
        } catch (FileNotFoundException e) {
            System.err.println("Words file not found");
        }

        String word;
        String dictionaryWord;

        try {

            FileWriter writer = new FileWriter("src/main/resources/muse_words.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            try {
                while ((word = words.readLine()) != null) {

                    try {
                        dictionary = new BufferedReader(new FileReader("C:\\Users\\Chiko\\Desktop\\CoreNLPTest\\src\\main\\resources\\dicitonary.txt"));
                    } catch (FileNotFoundException e) {
                        System.err.println("Dictionary not found");
                    }

                    while ((dictionaryWord = dictionary.readLine()) != null) {
                        if (dictionaryWord.equals(word)) {
                            bufferedWriter.append(word);
                            bufferedWriter.newLine();
                        }
                    }
                }
            } catch (IOException | NullPointerException e) {
                System.err.println("Error");
                e.printStackTrace();
            }

            bufferedWriter.close();

        }catch (IOException e){
            System.err.println("Error");
            e.printStackTrace();
        }

    }
}
