/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

import Example.Token.TokenType;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A lexer is a more complex tokenizer and usually tries to keep some state information about the
 * tokens returned (like source code line, position, etc). This is usually part of writing a
 * compiler or other language parsing program
 *
 * @author Rob
 */
public class Lexer {

  // List of lexemes we find during the "lexing" of the inputString
  private final List<Token> tokens = new ArrayList<>();
  private final List<String> reservedWords = Arrays.asList("for", "if", "then");

  // Input string to lex
  private final String inputString;

  public Lexer(String s) {
    inputString = s;
  }

  @SuppressWarnings("empty-statement")
  public void Lex() {

    int currentIndex = 0;
    int begin = 0;
    int limit = inputString.length();

    while (true) {

      if (begin >= limit) {
        break;
      } else if (isWhitespace(inputString.charAt(currentIndex))) {
        // Loop until no more whitespace characters
        while (++currentIndex < limit && isWhitespace(inputString.charAt(currentIndex)));
        tokens.add(new Token(TokenType.WHITESPACE, begin, currentIndex, inputString.substring(begin, currentIndex)));
        begin = currentIndex;
      } else if (isDigit(inputString.charAt(currentIndex))) {
        while (++currentIndex < limit && isDigit(inputString.charAt(currentIndex)));
        tokens.add(new Token(TokenType.INTEGER, begin, currentIndex, inputString.substring(begin, currentIndex)));
        begin = currentIndex;
      } else if (isLetter(inputString.charAt(currentIndex))) {
        while (++currentIndex < limit && isLetter(inputString.charAt(currentIndex)));
        if (reservedWords.contains(inputString.substring(begin, currentIndex))) {
          tokens.add(new Token(TokenType.RESERVEDWORD, begin, currentIndex, inputString.substring(begin, currentIndex)));
          begin = currentIndex;
        } else {
          tokens.add(new Token(TokenType.IDENTIFIER, begin, currentIndex, inputString.substring(begin, currentIndex)));
          begin = currentIndex;
        }
      } else {
        tokens.add(new Token(TokenType.UNKNOWN, begin, currentIndex, inputString.substring(begin, currentIndex)));
        begin = ++currentIndex;
      }
    }
  }

  /**
   * Returns each Token found after calling Lex()
   *
   * @return
   */
  public Iterator<Token> iterator() {
    return tokens.iterator();
  }
}
