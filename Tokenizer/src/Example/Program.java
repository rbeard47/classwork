/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

import Example.Tokenizer.Token;

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
  }
}
