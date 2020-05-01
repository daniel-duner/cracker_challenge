import java.util.ArrayList;
import java.util.List;

public class Mangler{
    private String[] alphabet = {"1","2","3","4","5","6","7","8","9","0","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public List<String> prepend(String word){
        List<String> words = new ArrayList<>();
        for(String character : alphabet){
            words.add(character+word);
        }
        return words;
    }
    public List<String> append(String word){
        List<String> words = new ArrayList<>();
        for(String character : alphabet){
            words.add(word+character);
        }
        return words;
    }
    public void prepend(String word, List<User> users){
        for(String character : alphabet){
            String decryption = character+word;
            passwordTest(decryption, users);
        }
    }
    public void append(String word, List<User> users){
        for(String character : alphabet){
            String decryption = word+character;
            passwordTest(decryption, users);
        }
    }
    public String deleteF(String word){
        if(word.length() > 1){
            return word.substring(1);
        } else {
            return word;
        }

    }
    public String deleteL(String word){
        if(word.length() > 1){
            return word.substring(0, word.length()-1);
        } else {
            return word;
        }
    }
    public String reverse(String word){
        return new StringBuilder(word).reverse().toString();
    }
    public String duplicate(String word){
        return""+word+word;
        
    }
    public String reflect(String word, int placing){
        if(placing == 0){
            return ""+new StringBuilder(word).reverse().toString()+word;        
        } else {
            return ""+word+new StringBuilder(word).reverse().toString();
        }   
    }
    public String uppercase(String word){
        return word.toUpperCase();
        
    }
    public String lowercase(String word){
        return word.toLowerCase();
        
    }
    public String capitalize(String word){
        if(word.length() > 1){
            return word.substring(0,1).toUpperCase()+word.substring(1);
        } else {
            return word.toUpperCase();  
        }
        
    }
    public String ncapitalize(String word){
        if(word.length() > 1){
            return word.substring(0,1)+word.substring(1).toUpperCase();
        } else {
            return word.toUpperCase();  
        }
    }
    public String toggle(String word, int parity){
        String even = "";
        String odd = "";
        for(int i = 0; i < word.length(); i++){
            char letter = word.charAt(i);
            if(i % 2 == 0){
                if(parity == 0){
                    even = even + String.valueOf(letter).toUpperCase();
                } else {
                    odd = odd + String.valueOf(letter).toLowerCase();
                }
            } else {
                if(parity == 0){
                    even = even + String.valueOf(letter).toLowerCase();
                } else {
                    odd = odd + String.valueOf(letter).toUpperCase();
                }
            }
        }
        if(parity == 0){
            return even;
        } else {
            return odd;
        }
    }
    public String mangle(String word, int mangle){
        switch (mangle) {
            case 1:
                return reverse(word);
            case 2:
                return duplicate(word);
            case 3:
                return deleteF(word);
            case 4:
                return deleteL(word);
            case 5:
                return reflect(word,0);
            case 6:
                return reflect(word,1);
            case 7:
                return uppercase(word);
            case 8:
                return lowercase(word);
            case 9:
                return capitalize(word);
            case 10:
                return ncapitalize(word);
            case 11:
                return toggle(word,0);
            case 12:
                return toggle(word,1);
            default:
                return "";
        }
    }
    public void passwordTest(String word, List<User> users){
        for(User user : users){
            String encrypted = jcrypt.crypt(user.salt, word);
            if(user.password.equals(encrypted)){
                System.out.println(word);
                int index = users.indexOf(user);
                if(index != -1){
                    users.remove(index);
                }
            }
        }
    }
}