/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

/**
 *
 * @author Rob
 */
public class Token {

  private final int tokenStart;
  private final int tokenEnd;
  private final String stringValue;
  private final TokenType tokenType;

  public enum TokenType {
    IDENTIFIER,
    WHITESPACE,
    INTEGER,
    OPERATOR, UNKNOWN, RESERVEDWORD
  }

  Token(TokenType tokenType, int tokenStart, int tokenEnd, String stringValue) {
    this.tokenType = tokenType;
    this.tokenStart = tokenStart;
    this.tokenEnd = tokenEnd;
    this.stringValue = stringValue;
  }

  public int getTokenStart() {
    return tokenStart;
  }

  public int getTokenEnd() {
    return tokenEnd;
  }

  public String getStringValue() {
    return stringValue;
  }

  public TokenType getTokenType() {
    return tokenType;
  }
}
