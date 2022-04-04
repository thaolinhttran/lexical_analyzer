//Linh Thi Thao Tran
package lexeranalysis;

import java.util.*;
import java.io.*;
import static java.util.Collections.list;

enum Keyword{
    IF, FOR, WHILE, FUNCTION, RETURN, INT, ELSE, DO, BREAK, END;
}
public class Lexer {
    
    public static ArrayList<String> Tokens;
    static boolean match = false;
    static boolean error = false;
    
    public static void Tokenize(String fileName) throws FileNotFoundException{
        Scanner scan = new Scanner(new File (fileName));
        Tokens = new ArrayList<String>();
        
        while (scan.hasNext()){
            String temp = scan.next();
            char[] str = temp.toCharArray();
            
            String holder = "";
            int index = 0;
            String lexeme = "";
            String not = "";
            while(index < str.length){
                if(Character.isAlphabetic(str[index])){
                    while(index <= str.length - 1){
                        if(Character.isLetterOrDigit(str[index])){
                            lexeme += str[index];
                            index++;
                        }
                        else{
                            break;
                        }
                    }
                    
                    if(enumContains(lexeme)){
                        Tokens.add(lexeme.toUpperCase());
                    }
                    else{
                        Tokens.add("IDENT:" + lexeme);
                    }
                    
                    lexeme = "";
                }
                else if(Character.isDigit(str[index])){
                    while(index <= str.length - 1){
                        if(Character.isLetterOrDigit(str[index])){
                            lexeme += str[index];
                            index++;
                        }
                        else
                            break;
                    }
                    
                    if(onlyDigits(lexeme) == true){
                        Tokens.add("INT_LIT:" + lexeme);
                    }
                    else{
                        Tokens.add("SYNTAX ERROR: INVALID IDENTIFIER NAME");
                    }
                   
                    lexeme = "";
                }
                else if(!Character.isLetter(str[index])){
                    while(index <= str.length - 1){
                        if(!Character.isLetter(str[index])){
                            lexeme += str[index];
                            index++;
                        }
                        else
                            break;
                    }
                    
                    breakDown(lexeme);
                    

                    lexeme = "";
                }
                else if(Character.isWhitespace(str[index])){
                    index++;
                }
            }
        }
        for(int a = 0; a < Tokens.size(); a++){
            System.out.println(Tokens.get(a));
            if(Tokens.get(a).equalsIgnoreCase("SYNTAX ERROR: INVALID IDENTIFIER NAME")){
                break;
            }
        }
    }
   
    private static boolean enumContains(String lexeme){
        for (Keyword key : Keyword.values()){
            if(lexeme.equalsIgnoreCase(key.name()))
                return true;
        }
        
        return false;
    }
    
    private static int breakDown(String lexeme){
        String not = "";
        char[] special = lexeme.toCharArray();
        int x = special.length;
        LookUp(lexeme);
        if(x > 1 && match == false){
            not += special[x-1];
            lexeme ="";
            for(int z = 0; z <=x - 2; z++){
                lexeme += special[z];
            }
                        
            LookUp(lexeme);
            if(match == false){
                breakDown(lexeme);
            }
            x--;
        }
                    
        if(x < 3){
            Tokens.add(LookUp(lexeme));
        }
        if(!not.isEmpty()){
            breakDown(not);
        }
        return 0;
                    
     }
     
    private static boolean onlyDigits(String lexeme){
        boolean digit = false;
        for(int j = 0; j < lexeme.length(); j++){
            if(Character.isDigit(lexeme.charAt(j))){
                digit = true;
            }
            else
                digit = false;
        }
        return digit;
    }

    private static String LookUp(String lexeme){
        TreeMap<String, String> Tokens = new TreeMap<>();
        
        Tokens.put("=", "ASSIGN");
        Tokens.put("+", "ADD");
        Tokens.put("-", "SUB");
        Tokens.put("*", "MUL");
        Tokens.put("/", "DIV");
        Tokens.put("%", "MOD");
        Tokens.put(">", "GT");
        Tokens.put("<", "LT");
        Tokens.put(">=", "GE");
        Tokens.put("<=", "LE");
        
        Tokens.put("++", "INC");
        Tokens.put("(", "LP");
        Tokens.put(")", "RP");
        Tokens.put("{", "LB");
        Tokens.put("}", "RB");
        Tokens.put("|", "OR");
        Tokens.put("&", "AND");
        Tokens.put("==", "EE");
        Tokens.put("!", "NEG");
        Tokens.put(",", "COMMA");
        Tokens.put(";", "SEMI");        
        

        if(Tokens.containsKey(lexeme)){
            match = true;
            return Tokens.get(lexeme);
        }
        else{

            match = false;
            return "SYNTAX ERROR: INVALID IDENTIFIER NAME";
        }
    }

}
