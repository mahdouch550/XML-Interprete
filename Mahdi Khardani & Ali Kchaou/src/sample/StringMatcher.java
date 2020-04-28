package sample;

public class StringMatcher {

    private String [] resultDivision(String result){
        if(!result.startsWith("<") && !result.endsWith(">"))
            return result.split(" ");
        else
            return null;
    }

    private String[] translate(String words[], String sourceLanguage, String destinationLanguage) throws Exception {
        String translatedWords[]=new String [words.length];
        for(int i=0; i<words.length; i++){
            translatedWords[i]= Translator.translate(sourceLanguage, destinationLanguage, words[i]);
        }
        return translatedWords;
    }

    private boolean mistypedWord(String word1, String word2){
        int counter=0;
        for(int i=0; i<minimumLength(word1,word2); i++){
            if(word1.charAt(i)==word2.charAt(i))
                counter++;
        }
        return counter<=2;
    }

    private int minimumLength(String word1, String word2){
        return (word1.length()>word2.length())? word1.length():word2.length();
    }
}
