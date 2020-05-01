import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validator {
    public static File createFile(String path, boolean inputFile){
        File file = new File(path);
    try{
        Path realPath = Paths.get(path);
        file.getCanonicalPath();
        if((!file.exists() || file.isDirectory()) && inputFile){
            throw new IOException();
        }
        if((!file.canRead() && inputFile) || (!file.canWrite() && !inputFile)){
            Terminator.terminate("Could not access file on path: "+path);
        }
    } catch (InvalidPathException | IOException e) {
        Terminator.terminate("No file exists on the path: " + path);
    }
    return file;
    }

    //Checks the amount of arguments
    public static void argumentCheck(String[] arguments, int numberOfArguments){
        int inputLength = arguments.length;
        if (inputLength != numberOfArguments) {
            Terminator.terminate("WrongNumberOfArguments expected: "+ numberOfArguments + " received "+ inputLength);
        }
    }

    public static void validateUserArguments(int size){
        if (size != 7) {
            Terminator.terminate("Expected words: 7 received: "+ size);
        }
    }

	public static void validateHash(String hash) {
        if (hash.length() != 13) {
            Terminator.terminate("Expected words: 7 received: "+ hash.length());
        }
	}

}