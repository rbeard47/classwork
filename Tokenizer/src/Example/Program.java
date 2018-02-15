/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

import Example.Tokenizer.Token;
import java.util.Iterator;

/**
 * This Program class is the is the main executable and contains the main function
 * @author Rob
 */
public class Program {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    // Create a new tokenizer
    Tokenizer tokenizer = new Tokenizer("Hello &56 World!");
    
    // Loop through the tokens until you find the EOF token
    for(Token t = tokenizer.Next(); t != Token.EOF; t = tokenizer.Next()){
      System.out.println(t);
    }
    
    // Create a new Lexer object
    Lexer lexer = new Lexer("This if 32434 \t is for a test 55 ^#$#$435#%%$");
    
    // Call the Lex() method which loops through the input string and tokenizes it
    lexer.Lex();
    
    // Get an iterator to use to loop through the tokens
    Iterator<Example.Token> i = lexer.iterator();
    
    // Use the iterator to loop through each token and dump info about it such as TokenType,
    // where in the string the token start and stops and the string value that was stored.
    while(i.hasNext()) {
      Example.Token t = i.next();
      System.out.format("type=%s begin=%d beyond=%d value=%s\n", t.getTokenType(),
              t.getTokenStart(), t.getTokenEnd(), t.getStringValue());
    }
  }
}
