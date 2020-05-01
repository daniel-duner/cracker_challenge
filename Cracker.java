import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class Cracker {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Validator.argumentCheck(args, 2);
        Cracker cracker = new Cracker();
        List<User> users = new CopyOnWriteArrayList<>();
        try {
            Mangler mangler = new Mangler();
            Dictionary dictionary = new Dictionary(8);
            File wordList = Validator.createFile(args[0], true);
            File commonPasswords = Validator.createFile("commonPasswords.txt",true);
            File userAccountInformation = Validator.createFile(args[1], true);
            Scanner fileReader = new Scanner(userAccountInformation);
            
            //Stores users and their passwords and add the users names to the dicitonary
            while (fileReader.hasNext()) {
                String[] user = fileReader.nextLine().split(":");
                Validator.validateUserArguments(user.length);
                Validator.validateHash(user[1]);
                user[4] = user[4].replace(".", "");
                String[] fullName = user[4].split(" ");
                dictionary.addWords(fullName);
                users.add(new User(user[4], user[1]));
            }
            fileReader.close();

            //Test the passwords against common passwords
            fileReader = new Scanner(commonPasswords);
            while(fileReader.hasNext()){
                mangler.passwordTest(fileReader.nextLine(),users);
            }
            fileReader.close();

            dictionary.addWords(wordList);

            //executes the normal mangle, which is all mangles except append and prepend
            CountDownLatch latch = new CountDownLatch(dictionary.numberOfSections());
            cracker.executeMangle(dictionary, users, "normal", latch);
            latch.await();

            //executes dual mangle, which mangles a word with all mangles exept append 
            //and prepend and then mangles each of these again with the mangles another time
            // this time with append and prepend
            latch = new CountDownLatch(dictionary.numberOfSections());
            cracker.executeMangle(dictionary, users, "dual", latch);
            latch.await();

            //tests append and prepend
            latch = new CountDownLatch(dictionary.numberOfSections());
            cracker.executeMangle(dictionary, users, "heavy", latch);
            latch.await();
            
            //tests append and prepend and mangles again with all the other mangles
            latch = new CountDownLatch(dictionary.numberOfSections());
            cracker.executeMangle(dictionary, users, "ultimate", latch);
            latch.await();

            System.out.println("Runtime: " + ((System.currentTimeMillis() - startTime) / 1000));
        } catch (InterruptedException e) {
            Terminator.terminate("Thread waiting was interrupted");
        } catch(FileNotFoundException e){
            Terminator.terminate("No file exists on the path: " + args[1]);
        }
    }

    private void executeMangle(Dictionary dictionary,List<User> users, String method, CountDownLatch latch){
        int beginIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < dictionary.numberOfSections(); i++) {
            if (i == dictionary.numberOfSections() - 1) {
                endIndex = endIndex + dictionary.lastSectionLength();
            } else {
                endIndex = endIndex + dictionary.sectionLength();
            }
            new Mangle(latch, dictionary.words(), beginIndex, endIndex, users, method).start();
            beginIndex = beginIndex + dictionary.sectionLength();
        }
    }
}
