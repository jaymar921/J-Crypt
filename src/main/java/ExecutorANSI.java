
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ExecutorANSI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private Encryptor encryptor = null;
    private List<String> keys_dic = new ArrayList<>();
    private List<String> word_list = new ArrayList<>();
    private boolean usingWL;
    private boolean key_dict = false;
    private String key = "";
    private void Display(){

        System.out.println(
                        ANSI_CYAN+"_____________________________________________\n" +
                        ANSI_CYAN+"|        __      ______                 __  |\n" +
                        ANSI_CYAN+"|       / /     / ____/______  ______  / /_ |\n" +
                        ANSI_CYAN+"|  __  / /_____/ /   / ___/ / / / __ \\/ __/ |\n" +
                        ANSI_CYAN+"| / /_/ /_____/ /___/ /  / /_/ / /_/ / /_   |\n" +
                        ANSI_CYAN+"| \\____/      \\____/_/   \\__, / .___/\\__/   |\n" +
                        ANSI_CYAN+"|                       /____/_/            |\n" +
                        ANSI_CYAN+"|___________________________________________|\n" +
                        ANSI_BLUE+" Github: "+ANSI_WHITE+"https://github.com/jaymar921"+ANSI_CYAN
        );
    }

    private void options(){
        String[] op = {
                ANSI_CYAN + "[01] - " + ANSI_GREEN + "Encrypt Text" + "  " + ANSI_CYAN + "[05] - " + ANSI_GREEN + "Try Decrypt Text",
                ANSI_CYAN + "[02] - " + ANSI_GREEN + "Decrypt Text" + "  " + ANSI_CYAN + "[06] - " + ANSI_GREEN + "Try Decrypt File",
                ANSI_CYAN + "[03] - " + ANSI_GREEN + "Encrypt File" + "  " + ANSI_CYAN + "[07] - " + ANSI_GREEN + "-",
                ANSI_CYAN + "[04] - " + ANSI_GREEN + "Decrypt File" + "  " + ANSI_CYAN + "[08] - " + ANSI_GREEN + "-",
                ANSI_CYAN + "[00] - " + ANSI_RED + "Exit" + ANSI_RESET,
        };
        System.out.println(ANSI_WHITE+"Options:");
        for (String option:
             op) {
            System.out.println(option);
        }
    }

    public void excecuteCLI(){
        //Encryptor encryptor = new Encryptor("a64z2bx5y");
        Display();

        System.out.println(":: Generate a new Key? ");
        if(confirmation()){
            encryptor = new Encryptor(10, true);
            System.out.println("Generated Encryption Key -> "+encryptor.getKey());
            key = encryptor.getKey().getKey();
        }else{
            System.out.print(":: Enter key: ");
            String input_key = new Scanner(System.in).next();
            encryptor = new Encryptor(input_key, true);
            key = encryptor.getKey().getKey();
        }
        int option = -1;
        while (option!=0){
            try {
                options();
                System.out.print(ANSI_YELLOW+":: Enter Option: "+ANSI_GREEN);
                option = new Scanner(System.in).nextInt();
            }catch (Exception e){
                option = -1;
            }
            if(option==1)
                encrypt_text();
            if(option==2)
                decrypt_text();
            if(option==3)
                encrypt_file();
            if(option==4)
                decrypt_file();
            if(option==5)
                try_decrypt_text();
            if(option==6)
                try_decrypt_file();
        }
        System.out.println(ANSI_RED+"Program Terminated..."+ANSI_RESET);
    }

    public void encrypt_text(){
        System.out.println(ANSI_CYAN+"_____________________________________________");
        System.out.println("[ENCRYPT TEXT]");
        System.out.println("Enter String to Encrypt:");
        String str = new Scanner(System.in).nextLine();
        String str_en = encryptor.getEncryption(str);
        System.out.println("Encrypted Text: \n" + ANSI_WHITE + str_en + ANSI_CYAN);
        pressAnyKey();
    }

    public void decrypt_text(){
        System.out.println(ANSI_CYAN+"_____________________________________________");
        System.out.println("[DECRYPT TEXT]");
        System.out.println("Enter String to Decrypt:");
        String str = new Scanner(System.in).nextLine();
        String str_en = encryptor.getDecryption(str);
        System.out.println("Decrypted Text: \n" + ANSI_WHITE + str_en + ANSI_CYAN);
        pressAnyKey();
    }

    public void encrypt_file(){
        ConsoleHelper consoleHelper = new ConsoleHelper();
        System.out.println(ANSI_CYAN+"_____________________________________________");
        System.out.println("[ENCRYPT FILE]");
        System.out.println("Enter File name:");
        String filename = new Scanner(System.in).nextLine();
        try {
            File file = new File(filename);
            FileReader reader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while ((i= reader.read())!= -1){
                stringBuilder.append((char) i);
                consoleHelper.animate("Encrypting [" + Integer.toBinaryString(i)+"]");
                //simulate a piece of task
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            consoleHelper.animate("Encryption Complete\n" );
            String encrypted = encryptor.getEncryption(stringBuilder.toString());
            System.out.print("Output file name: "+ ANSI_PURPLE);
            String out_file = new Scanner(System.in).nextLine();

            File out = new File(out_file);
            FileWriter writer = new FileWriter(out_file);
            writer.write(encrypted);
            writer.close();
            reader.close();
            System.out.println(ANSI_CYAN+"Encrypted File: "+ANSI_WHITE+out_file+ANSI_CYAN);
            pressAnyKey();
        }catch (Exception e){
            System.out.println(ANSI_RED+"Failed to load ["+ANSI_CYAN+filename+ANSI_RED+"]"+ANSI_CYAN);
        }
        System.out.println(ANSI_CYAN+"_____________________________________________");
    }
    public void decrypt_file(){
        ConsoleHelper consoleHelper = new ConsoleHelper();
        System.out.println(ANSI_CYAN+"_____________________________________________");
        System.out.println("[DECRYPT TEXT]");
        System.out.println("Enter File name:");
        String filename = new Scanner(System.in).nextLine();
        try {
            File file = new File(filename);
            FileReader reader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while ((i= reader.read())!= -1){
                stringBuilder.append((char) i);
                consoleHelper.animate("Decrypting [" + Integer.toBinaryString(i)+"]");
                //simulate a piece of task
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            consoleHelper.animate("Decryption Complete\n" );
            String decryption = encryptor.getDecryption(stringBuilder.toString());
            System.out.print("Output file name: "+ ANSI_PURPLE);
            String out_file = new Scanner(System.in).nextLine();
            decryption = decryption.substring(0, decryption.length()-1);
            File out = new File(out_file);
            FileWriter writer = new FileWriter(out_file);
            writer.write(decryption);
            writer.close();
            reader.close();
            System.out.println(ANSI_CYAN+"Decrypted File: "+ANSI_WHITE+out_file+ANSI_CYAN);
            pressAnyKey();
        }catch (Exception e){
            System.out.println(ANSI_RED+"Failed to load ["+ANSI_CYAN+filename+ANSI_RED+"]"+ANSI_CYAN);
        }
        System.out.println(ANSI_CYAN+"_____________________________________________");
    }

    public void try_decrypt_file(){
        key_dict = false;
        usingWL = false;
        keys_dic = new ArrayList<>();
        DecimalFormat fmt = new DecimalFormat("###.##");
        List<String> keys = new ArrayList<>();
        ConsoleHelper consoleHelper = new ConsoleHelper();
        System.out.println(ANSI_CYAN+"_____________________________________________");
        System.out.println("[TRY DECRYPT FILE]");
        System.out.println("Enter File name:");
        String filename = new Scanner(System.in).nextLine();
        try {
            File file = new File(filename);
            FileReader reader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            int count = 1;
            while ((i= reader.read())!= -1){
                stringBuilder.append((char) i);
                consoleHelper.animate("READING [" + Integer.toBinaryString(i)+"] size: "+count++);
                //simulate a piece of task
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("\nUse a key dictionary?");
            if(confirmation())
                useKeyList();

            System.out.println("Use a word dictionary?");
            if(confirmation())
                useWordList();

            System.out.print("Set match percentage [5-100]: ");
            int expected_match = 0;
            try {
                expected_match= new Scanner(System.in).nextInt();
            }catch (Exception e){
                System.out.println("Invalid input, match percent set to 10%");
                expected_match=10;
            }
            System.out.println("Match percentage set to "+expected_match+"%");
            System.out.print(ANSI_GREEN+"");
            String str_en = encryptor.getDecryption(stringBuilder.toString());
            double match = 0;

            int dictionary_size = 0;
            if(!usingWL)
                dictionary_size = encryptor.wordDictionary().size();
            else
                dictionary_size = word_list.size();
            List<String> listed_word = new ArrayList<>();
            int j = 0;
            int tries = 1;
            while (true){
                if(keys.contains(encryptor.getKey().toString()))
                    continue;
                keys.add(encryptor.getKey().toString());
                String msg = "Trying Key -> ["+encryptor.getKey().toString() + "] -- ["+(tries++)+" tries]";
                consoleHelper.animate(msg);

                int number_match = 0;
                listed_word.clear();
                for(String word : str_en.split(" ")){

                    if(!usingWL){
                        if(encryptor.wordDictionary().contains(word) && !listed_word.contains(word) ) {
                            number_match++;
                            listed_word.add(word);
                        }
                    }else{
                        if(word_list.contains(word) && !listed_word.contains(word) ) {
                            number_match++;
                            listed_word.add(word);
                        }
                    }
                }


                match = (number_match/(double)dictionary_size)*100;
                int matched =0;
                for(String word : str_en.split(" ")){
                    if(listed_word.contains(word))
                        matched++;
                }
                if(match>=expected_match || ((double)(matched/str_en.split(" ").length))*100 > expected_match){


                    System.out.println("\nKey: "+encryptor.getKey().toString()+" has "+fmt.format(match)+"% word match in dictionary."+"("+matched+"/"+(str_en.split(" ").length)+") words are decrypted");
                    System.out.println("Would you like to stop searching?");
                    if(confirmation())
                        break;
                }

                if(key_dict) {
                    if(j>=keys_dic.size()){
                        System.out.println("\nAll keys are used");
                        str_en = "[J-CRYPT: TEXT NOT DECRYPTED]";
                        break;
                    }
                    encryptor = new Encryptor(keys_dic.get(j), true);
                    j++;
                }else
                    encryptor = new Encryptor(10, true);


                str_en = encryptor.getDecryption(stringBuilder.toString());
                str_en = str_en.substring(0, str_en.length()-1);

            }


            System.out.print("Output file name: "+ ANSI_PURPLE);
            String out_file = new Scanner(System.in).nextLine();
            File out = new File(out_file);
            FileWriter writer = new FileWriter(out_file);
            writer.write(str_en);
            writer.close();
            reader.close();
            System.out.println(ANSI_CYAN+"Decrypted File: "+ANSI_WHITE+out_file+ANSI_CYAN);

        }catch (Exception e){
            System.out.println(ANSI_RED+"Failed to load ["+ANSI_CYAN+filename+ANSI_RED+"]"+ANSI_CYAN);
        }
        System.out.println(ANSI_CYAN+"_____________________________________________");
        pressAnyKey();
    }

    public void try_decrypt_text(){
        key_dict = false;
        keys_dic = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        ConsoleHelper consoleHelper = new ConsoleHelper();
        System.out.println(ANSI_CYAN+"_____________________________________________");
        System.out.println("[TRY DECRYPT TEXT]");
        System.out.println("Enter String to Decrypt:");
        String str = new Scanner(System.in).nextLine();
        System.out.println("Use a key dictionary?");
        if(confirmation())
            useKeyList();
        System.out.print(ANSI_GREEN+"");
        boolean decrypted = false;
        String str_en = encryptor.getDecryption(str);
        int i = 0;
        while (true){
            if(keys.contains(encryptor.getKey().toString()))
                continue;
            keys.add(encryptor.getKey().toString());
            String msg = "Trying Key -> ["+encryptor.getKey().toString() + "] ["+str_en+"]";
            consoleHelper.animate(msg);
            for(String word : str_en.split(" ")){
                if(encryptor.wordDictionary().contains(word)) {
                    decrypted = true;
                    System.out.println("\nKey: "+encryptor.getKey());
                    break;
                }
            }
            if(decrypted)
                break;

            if(key_dict) {
                if(i>=keys_dic.size()-1){
                    System.out.println("\nAll keys are used");
                    str_en = "[J-CRYPT: TEXT NOT DECRYPTED]";
                    break;
                }
                encryptor = new Encryptor(keys_dic.get(i), true);
                i++;
            }else
                encryptor = new Encryptor(10, true);


            str_en = encryptor.getDecryption(str);
            str_en = str_en.substring(0, str_en.length()-1);
        }

        System.out.println(ANSI_CYAN+"Decrypted Text: \n"+ANSI_WHITE +  str_en +ANSI_CYAN);
        pressAnyKey();
    }

    private void useKeyList(){
        try {
            System.out.print("Enter [Key Dictionary] Filename: ");
            File file = new File(new Scanner(System.in).nextLine());
            FileReader reader = new FileReader(file);
            int i = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((i=reader.read())!=-1){
                stringBuilder.append((char)i);
            }
            keys_dic.addAll(Arrays.asList(stringBuilder.toString().split("\n")));
            System.out.println("Loaded Keys: "+file.getName());
            key_dict = true;
        }catch (Exception e){
            System.out.println(ANSI_RED+"Failed to load file"+ANSI_CYAN);
        }
    }

    private void useWordList(){
        try {
            System.out.print("Enter [Word Dictionary] Filename: ");
            File file = new File(new Scanner(System.in).nextLine());
            FileReader reader = new FileReader(file);
            int i = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((i=reader.read())!=-1){
                stringBuilder.append((char)i);
            }
            word_list.addAll(Arrays.asList(stringBuilder.toString().split(" ")));
            System.out.println("Loaded Keys: "+file.getName());
            usingWL = true;
        }catch (Exception e){
            System.out.println(ANSI_RED+"Failed to load file"+ANSI_CYAN);
        }
    }



    private void pressAnyKey(){
        try {
            System.out.println("Press 'ENTER' to continue");
            System.in.read();
        }catch (Exception ignore){

        }
    }



    private boolean confirmation(){
        try{
            System.out.print("Confirm[Y/n]: ");
            char in = new Scanner(System.in).next().toLowerCase().charAt(0);
            if(in == 'y')
                return true;
        }catch (Exception e){
            System.out.println("Invalid input, 'No' option was selected");
        }
        return false;
    }
}
