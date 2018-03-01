/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

import Example.Tokenizer.Token;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rob
 */
public class TokenizerTest {

  public TokenizerTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test that empty string returns EOF
   */
  @Test
  public void testEmptyString() {
    
    Tokenizer instance = new Tokenizer("");
    
    assertEquals(Token.EOF, instance.Next());
  }
  
  /**
   * Test of Next method, of class Tokenizer.
   */
  @Test
  public void testNext() {

    System.out.println("Next");
    Tokenizer instance = new Tokenizer("This 1234 &*( !");

    assertEquals(Token.CHAR, instance.Next());
    assertEquals(Token.CHAR, instance.Next());
    assertEquals(Token.CHAR, instance.Next());
    assertEquals(Token.CHAR, instance.Next());
    assertEquals(Token.WHITESPACE, instance.Next());
    assertEquals(Token.DIGIT, instance.Next());
    assertEquals(Token.DIGIT, instance.Next());
    assertEquals(Token.DIGIT, instance.Next());
    assertEquals(Token.DIGIT, instance.Next());
    assertEquals(Token.WHITESPACE, instance.Next());
    assertEquals(Token.AMPERSAND, instance.Next());
    assertEquals(Token.STAR, instance.Next());
    assertEquals(Token.LPAREN, instance.Next());
    assertEquals(Token.WHITESPACE, instance.Next());
    assertEquals(Token.BANG, instance.Next());
    assertEquals(Token.EOF, instance.Next());
  }
}
