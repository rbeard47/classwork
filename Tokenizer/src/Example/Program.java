/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

import Example.Tokenizer.Token;
import java.util.Iterator;

/**
 *
 * @author Rob
 */
public class Program {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    Tokenizer tokenizer = new Tokenizer("Hello &56 World!");
    
    for(Token t = tokenizer.Next(); t != Token.EOF; t = tokenizer.Next()){
      System.out.println(t);
    }
    
    Lexer lexer = new Lexer("This if 32434 \t is for a test 55 ^#$#$435#%%$");
    
    lexer.Lex();
    
    Iterator<Example.Token> i = lexer.iterator();
    
    while(i.hasNext()) {
      Example.Token t = i.next();
      System.out.format("type=%s begin=%d beyond=%d value=%s\n", t.getTokenType(),
              t.getTokenStart(), t.getTokenEnd(), t.getStringValue());
    }
  }
}
