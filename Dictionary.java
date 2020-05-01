import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    private List<String> dictionary = new ArrayList<>();
    private int size = dictionary.size();
    private int numberOfSections;
    private int remainder;
    private int sectionLength;

    public Dictionary(int numberOfSections) {
        this.numberOfSections = numberOfSections;
    }

    public void addWords(File wordList) {
        Scanner reader;
        try {
            reader = new Scanner(wordList);
            while(reader.hasNext()){
                dictionary.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setSections();
    }
    public void addWords(String[] wordList) {
        for(String word : wordList){
            dictionary.add(word);
        }
        setSections();
    }
    private void setSections(){
        size = dictionary.size();
        remainder = size % numberOfSections;
        sectionLength = (size - remainder) / numberOfSections;
    }
    public List<String> words(){
        return dictionary;
    }
    public int sectionLength(){
        return sectionLength;
    }
    public int lastSectionLength(){
        return sectionLength+remainder;
    }
    public int numberOfSections(){
        return numberOfSections;
    }
    public int size(){
        return size;
    }

    
}