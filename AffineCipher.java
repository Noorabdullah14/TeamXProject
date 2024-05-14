
import java.util.Scanner;

public class AffineCipher
{

    public static void main(String[] args)
    {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter the message: ");
        String message = inputScanner.next();
         // Input the keys
        System.out.print("Enter key 1 (a): ");
        int key1 = inputScanner.nextInt();
        System.out.print("Enter key 2 (b): ");
        int key2 = inputScanner.nextInt();
        
        if (GCD(key1, 26) != 1) {
            System.out.println("Key 1 (a) should be coprime with 26");
            return;
        }
        String encrypted = encryption(message,key1,key2);
        String decrypted = decryption(encrypted, key1, key2);
        System.out.println("Encrypted Message is : "+ encrypted);
        System.out.println("Decrypted Message is: "+ decrypted);
        inputScanner.close();
    }
    
        public static String encryption(String plaintext,int key1,int key2)
    {
        plaintext = plaintext.toUpperCase();
        String encrypted = "";
        int a = key1;
        int b = key2;
        for (int i = 0; i < plaintext.length(); i++)
        {
            encrypted = encrypted + (char) ((((a * plaintext.charAt(i)) + b) % 26) + 65);
        }
        return encrypted;
    }
 
    public static String decryption(String encrypted,int key1,int key2)
    {
        String decrypted = "";
        int a = key1;
        int b = key2;
        int a_inv = 0;
        int flag = 0;
        for (int i = 0; i < 26; i++)
        {
            flag = (a * i) % 26;
            if (flag == 1)
            {
                a_inv = i;
            }
        }
        for (int i = 0; i < encrypted.length(); i++)
        {
            decrypted = decrypted + (char) (((a_inv * ((encrypted.charAt(i) - b)) % 26)) + 65);
        }
        return decrypted;
    }
    
   public static int GCD(int a, int b) {
        if (b == 0)
            return a;
        return GCD(b, a % b);
    }
 
}