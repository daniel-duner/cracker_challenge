import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Mangle extends Thread {
    private Mangler mangler = new Mangler();
    private List<User> users;
    private List<String> dictionary;
    private int beginIndex;
    private int endIndex;
    private CountDownLatch latch;
    private String method;

    public Mangle(CountDownLatch latch, List<String> dictionary, int beginIndex, int endIndex, List<User> users, String method){
        this.dictionary = dictionary;
        this.users = users;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.latch = latch;
        this.method = method;
    }
    @Override
    public void run() {
        switch (method) {
            case "normal":
                normalMangle();
                break;
            case "dual":
                dualMangle();
                break;
            case "ultimate":
                ultimateMangle();
                break;
            case "heavy":
                heavyMangle();
                break;
            default:
                break;
        }
    }
    
    public void normalMangle(){
        for(int i = beginIndex; i < endIndex; i++ ){
            String word = dictionary.get(i);
            mangleTester(word, users);
        }
        latch.countDown();
    }

    private void dualMangle(){
        for(int i = beginIndex; i < endIndex; i++ ){
            String word = dictionary.get(i);
            for(int j = 1 ; j < 13; j++ ){
                String mangledWord = mangler.mangle(word, j);
                mangleTester(mangledWord, users);
            }
        }
        latch.countDown();
    }
    private void heavyMangle(){
        for(int i = beginIndex; i < endIndex; i++ ){
            String word = dictionary.get(i);
            mangleTesterHeavy(word, users);
        }
        latch.countDown();
    }

    private void ultimateMangle(){
        for(int i = beginIndex; i < endIndex; i++ ){
            String word = dictionary.get(i);
            List<String> appendedWords = mangler.append(word);
            for(String appendedWord: appendedWords){
                mangleTester(appendedWord, users);
            }
            List<String> prependedWords = mangler.prepend(word);
            for(String prependedWord: prependedWords){
                mangleTester(prependedWord, users);
            }
        }
        latch.countDown();
    }
    private void mangleTester(String word, List<User> users){
        Mangler mangler = new Mangler();
        mangler.passwordTest(mangler.deleteF(word), users);
        mangler.passwordTest(mangler.deleteL(word), users);
        mangler.passwordTest(mangler.reverse(word), users);
        mangler.passwordTest(mangler.duplicate(word), users);
        mangler.passwordTest(mangler.reflect(word, 0), users);
        mangler.passwordTest(mangler.reflect(word, 1), users);
        mangler.passwordTest(mangler.uppercase(word), users);
        mangler.passwordTest(mangler.lowercase(word), users);
        mangler.passwordTest(mangler.capitalize(word), users);
        mangler.passwordTest(mangler.ncapitalize(word), users);
        mangler.passwordTest(mangler.toggle(word, 0), users);
        mangler.passwordTest(mangler.toggle(word, 1), users);
    }
    private void mangleTesterHeavy(String word, List<User> users){
        Mangler mangler = new Mangler();
        mangler.prepend(word, users);
        mangler.append(word, users);
    }
}