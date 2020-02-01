
package org.javacc.parser;

public interface ParserCodeGenerator {

  /**
   * Generate the code for the parser. Note that the code generator just
   * produces a buffer.
   */
  void generateCode(CodeGeneratorSettings settings, ParserData parserData);

  /**
   * Complete the code generation and save any output file(s).
   */
  void finish(CodeGeneratorSettings settings, ParserData parserData);
}
