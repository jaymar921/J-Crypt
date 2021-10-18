import javax.swing.*;
import java.util.*;

public class Encryptor {
    private Key key;
    private StringBuilder data;
    private Map<String,String> key_value;
    private Map<String, String> key_value_inv;
    private Map<Integer, String> key_location;
    private Map<String, String> swapper;
    private Map<String, String> assigned_char;
    private char[] l;

    public Encryptor(int size){
        key = new Key(size);
        check();
    }
    public Encryptor(int size, boolean s){
        key = new Key(size);
        if(!s)
            check();
        else
            checks();
    }

    public Encryptor(String key){
        this.key = new Key().fromString(key);
        check();

    }
    public Encryptor(String key,boolean s){
        this.key = new Key().fromString(key);
        if(!s)
            check();
        else
            checks();
    }
    private void checks(){
        String message = "this Will serve as a test, program will recommend a good key";
        String e_msg = getEncryption(message);
        if(!getDecryption(e_msg).contains(message)) {
            key = new Key();
            checks();
        }
    }
    private void check(){
        String message = "this will serve as a test, program will recommend a good key";
        String e_msg = getEncryption(message);
        if(getDecryption(e_msg).contains(message)) {
            System.out.println("Good Key "+key.key);
        }else {
            key = new Key();
            System.out.println("Generating key "+key.key);
            check();
        }
    }

    private String encrypt(String information){
        generateKeyValues();
        data = new StringBuilder();
        char[] info = information.toCharArray();
        for(int i =0 ; i <info.length ;i++){
            if(info[i]==' ')
                data.append("§").append(" ");
            else if(key_value.containsKey(String.valueOf(info[i])))
                data.append(key_value.get(String.valueOf(info[i]))).append(" ");
            else
                data.append(String.valueOf(info[i])).append(" ");
        }
        info = data.toString().toCharArray();
        data = new StringBuilder();
        for(int i =0 ; i <info.length ;i++){
            if(swapper.containsKey(String.valueOf(info[i])))
                data.append(swapper.get(String.valueOf(info[i]))).append("");
            else
                data.append(String.valueOf(info[i])).append("");
        }
        return data.toString();
    }
    public String getEncryption(String information){
        String[] a = encrypt(information).split(" ");
        a = data.toString().split(" ");
        //data = new StringBuilder();
        //for(String b : a){
       //     data.append(key_value_inv.getOrDefault(b, b)).append("");
        //}

        return data.toString();
    }

    public String getDecryption(String information){
        String[] a = decrypt(information).split(" ");

        data = new StringBuilder();
        boolean s  = false;

        for(String b : a){
            s=false;
            for(String c : key_value_inv.keySet()) {
                if (key_value_inv.get(c).equals(b)) {
                    data.append(key_value.getOrDefault(c,c)).append(" ");
                    s = true;
                }
            }
            if(!s)
            data.append(b).append(" ");

        }

        a = data.toString().split(" ");
        data =new StringBuilder();
        for(String b : a){
            try {
                data.append(key_location.getOrDefault(Integer.parseInt(b)-getNumericEntry(), b)).append(" ");
            }catch (Exception e){
                data.append(b).append(" ");
            }
        }
        a = data.toString().split(" ");
        data = new StringBuilder();
        s = false;
        for(String b : a){
            s = false;
            for(String z : key_value.keySet()){
                if(key_value.get(z).equals(b)) {
                    data.append(z);
                    s = true;
                }
            }
            if(!s)
                data.append(b);
        }
        //data = new StringBuilder();
        //for(int z : key_location.keySet())System.out.print(z+" "+key_location.get(z)+", ");
        String[] z = data.toString().split("§");
        data = new StringBuilder();
        for(String x : z)
            data.append(x).append(" ");
        return data.toString();
    }

    private String decrypt(String information){
        generateKeyValues();
        data = new StringBuilder();
        String[] info = information.split(" ");
        boolean s;

        for (String c : info) {
            if (key_value_inv.containsKey(c))
                data.append(key_value_inv.get(String.valueOf(c))).append(" ");
            else
                data.append(c).append(" ");
        }
        //for(String z : swapper.keySet())System.out.println(z + " - "+ swapper.get(z));
        info = data.toString().split(" ");
        data = new StringBuilder();

        for(int i =0 ; i <info.length ;i++){
            s = false;
            for(String k : swapper.keySet()){
                if(swapper.get(k).equals(info[i])) {
                    data.append(k).append(" ");
                    s = true;
                    break;
                }
            }
            if(!s)
                data.append(info[i]).append(" ");
        }




        return data.toString();
    }



    private void generateKeyValues(){
        generateAssignedChars();
        key_location = new HashMap<>();
        key_value = new HashMap<>();
        key_value_inv = new HashMap<>();
        swapper = new HashMap<>();
        int j = 0;
        int c_i = 0;
        for(int i = getNumericEntry(); i < getNumericEntry()+key.getChars().length; c_i = i+1, i++) {
            key_location.put(j,String.valueOf(i));
            key_value.put(String.valueOf(key.getChars()[j++]), String.valueOf(i));
        }
        for(char c : l)
            for(String e : key_value.keySet())
                if(e.equals(String.valueOf(c))) {
                    for(int a : key_location.keySet())
                        if(key_value.get(e).equals(key_location.get(a))) {
                            key_location.replace(a, String.valueOf(c_i));
                            break;
                        }
                    key_value.replace(e, String.valueOf(c_i++));

                }
        for(int i = 0; i < invChar().length; i++){
            key_value_inv.put(key_location.get(i), String.valueOf(invChar()[i]));
        }

        int x = key_value.size();
        Map<Integer, String> m_kv = new HashMap<>();
        Map<Integer, String> m_kvi = new HashMap<>();
        int z = 0;
        for (String a : key_value.keySet()){
            m_kv.put(z++,key_value.get(a));
        }
        z = 0;
        for(String a : key_value_inv.keySet()) {
            m_kvi.put(z++, a);
        }
        z=m_kv.size()-1;
        for(int i = 0; i < key_value.size(); i++){
            swapper.put(m_kv.get(i),m_kvi.get(z--));
        }
        //for(String a : swapper.keySet())
        //    System.out.print(a+" "+swapper.get(a)+", ");

        //for(String c:key_value.keySet())System.out.print(c+" "+key_value.get(c)+", ");
        //System.out.println();
        //for(String c : key_value_inv.keySet())System.out.print(c+" "+key_value_inv.get(c)+", ");
        //System.out.println();
    }

    private void generateAssignedChars(){
        assigned_char = new HashMap<>();
        int c = 0;
        for (int i = 0; i<getNumericEntry()+getNumericEntry(); i++) {
            if(c>=key.getChars().length)
                c = 0;
            assigned_char.put(String.valueOf(i), String.valueOf(key.getChars()[c++]));
        }
    }

    private int getNumericEntry(){
        String k = key.getKey();
        l = new char[k.length()];
        int m = 0;
        int j = 1;
        for(int i = 0; i < k.length() ; i++){
            if(isNum(String.valueOf(k.charAt(i))))
                j*=Integer.parseInt(String.valueOf(k.charAt(i)));
            else
                l[m++] = k.charAt(i);
        }
        char[] n = l;
        l = new char[m];
        for(int i = 0; i < m;  i++)l[i] = n[i];

        return j;
    }

    public boolean isNum(String a){
        try{
            int i = Integer.parseInt(a);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    public Key getKey(){
        return key;
    }

    public char[] getChars(){
        return new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','@','-','.',',','=','*','+','/','?','~','\n','`','!',';',':','(',')','<','>','&','%','$','#','\\','_','\t'};
        //return new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    }

    private char[] invChar(){
        return new char[]{'b','d','f','h','j','l','n','p','r','t','v','x','z','y','w','u','s','q','o','m','k','i','g','e','c','a','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','9','7','5','3','1','0','2','4','6','8','*','.','-','=',',','@','/','?','~','+','\n','`','!',';',':','(',')','<','>','&','%','$','#','\\','_','\t'};
        //return new char[]{'B','D','F','H','J','L','N','P','R','T','V','X','Z','Y','W','U','S','Q','O','M','K','I','G','E','C','A'};
    }

    private char[] getDelimChar(){
        return new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    }

    public List<String> wordDictionary(){
        //Modify this dictionary
        List<String> words = new ArrayList<>();
        String[] s = new String[]{"the", "is", "this", "hi", "hello", "why", "what", "when", "I", "am", "name", "address", "test", "yes", "no", "world","find","quick","slow","hit","don't","not","and","or","a","is","we","at","data","your","our","in","who","course","of","bit","try"};
        for(String w : s)
            words.add(w);
        return words;
    }



    public class Key{
        private String key;
        int size;

        public Key(int size){
            this.size = size;
            generateKey();
        }
        public Key(){
            this(8);
        }

        private void generateKey(){
            char[] a = getChars();
            int[] b = {1,2,3,4,5,6,7,8,9};
            StringBuilder sb= new StringBuilder();
            for(int i =0;i < this.size; i++){
                if(Math.random()<=0.7) {
                    char c = a[new Random().nextInt(a.length-20)];
                    if(!sb.toString().contains(String.valueOf(c)))
                        sb.append(c);
                }else
                    sb.append(b[new Random().nextInt(b.length)]);
            }
            key = sb.toString();
        }

        public char[] getChars(){
            return new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','@','-','.',',','=','*','+','/','?','~','\n','`','!',';',':','(',')','<','>','&','%','$','#','\\','_','\t'};
        }

        public String getKey(){
            return key;
        }

        public Key fromString(String key){
            this.key = key;
            return this;
        }

        public String toString(){
            return key;
        }
    }
}
