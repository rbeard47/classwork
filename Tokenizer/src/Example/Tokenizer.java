/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;

/**
 *
 * @author Rob
 */
public class Tokenizer {

  private final String inputString;
  private int currentIndex;

  Tokenizer(String inputString) {
    this.inputString = inputString;
  }

  /**
   * Define enums for all the known token types
   */
  public static enum Token {
    CHAR, NUMBER, STRING, SPECIAL_CHAR, WS, BACKSLASH, BANG, AT, HASH, DOLLAR, PERCENT, CARET,
    AMPERSAND, STAR, LPAREN, RPAREN, MINUS, UNKNOWN, DIGIT, EOF, WHITESPACE, PLUS, LBRACE, RBRACE,
    LBRACKET, RBRACKET, TILDE, EQUALS
  };

  /**
   *
   * @return the next Token from the input string;
   */
  public Token Next() {
    // Are we at the end of the string...
    if (currentIndex >= inputString.length()) {
      return Token.EOF;
    }

    char c = inputString.charAt(currentIndex++);

    if (isLetter(c)) {
      return Token.CHAR;
    }

    if (isWhitespace(c)) {
      return Token.WHITESPACE;
    }

    // Handle digits
    if (isDigit(c)) {
      return Token.DIGIT;
    }

    // Handle other special characters
    switch (c) {
      case ' ':
        return Token.WHITESPACE;
      case '\\':
        return Token.BACKSLASH;
      case '!':
        return Token.BANG;
      case '@':
        return Token.AT;
      case '#':
        return Token.HASH;
      case '$':
        return Token.DOLLAR;
      case '%':
        return Token.PERCENT;
      case '^':
        return Token.CARET;
      case '&':
        return Token.AMPERSAND;
      case '*':
        return Token.STAR;
      case '(':
        return Token.LPAREN;
      case ')':
        return Token.RPAREN;
      case '-':
        return Token.MINUS;
      case '+':
        return Token.PLUS;
      case '=':
        return Token.EQUALS;
      case '~':
        return Token.TILDE;
      case '[': 
        return Token.LBRACKET;
      case ']': 
        return Token.RBRACKET;
      case '{':
        return Token.LBRACE;
      case '}':
        return Token.RBRACE;
      default:
        return Token.UNKNOWN;
    }
  }
}
