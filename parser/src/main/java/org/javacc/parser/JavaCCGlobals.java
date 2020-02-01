/*
 * Copyright (c) 2006, Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. * Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. * Neither the name of the Sun Microsystems, Inc. nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.javacc.parser;

import org.javacc.Version;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * This package contains data created as a result of parsing and semanticizing a
 * JavaCC input file. This data is what is used by the back-ends of JavaCC as
 * well as any other back-end of JavaCC related tools such as JJTree.
 */
public class JavaCCGlobals {

  /**
   * String that identifies the JavaCC generated files.
   */
  public static final String toolName = "JavaCC";

  /**
   * Set to true if this file has been processed by JJTree.
   */
  public static boolean      jjtreeGenerated;

  /**
   * The list of tools that have participated in generating the input grammar
   * file.
   */
  public static List<String> toolNames;

  /**
   * This prints the banner line when the various tools are invoked. This takes
   * as argument the tool's full name and its version.
   */
  public static void bannerLine(String fullName, String ver) {
    System.out.print("Java Compiler Compiler Version " + Version.fullVersion + " (" + fullName);
    if (!ver.equals("")) {
      System.out.print(" Version " + ver);
    }
    System.out.println(")");
  }

  /**
   * The name of the parser class (what appears in PARSER_BEGIN and PARSER_END).
   */
  public static String                                                              cu_name;

  /**
   * This is a list of tokens that appear after "PARSER_BEGIN(name)" all the way
   * until (but not including) the opening brace "{" of the class "name".
   */
  public static List<Token>                                                         cu_to_insertion_point_1   =
      new ArrayList<>();

  /**
   * This is the list of all tokens that appear after the tokens in
   * "cu_to_insertion_point_1" and until (but not including) the closing brace
   * "}" of the class "name".
   */
  public static List<Token>                                                         cu_to_insertion_point_2   =
      new ArrayList<>();

  /**
   * This is the list of all tokens that appear after the tokens in
   * "cu_to_insertion_point_2" and until "PARSER_END(name)".
   */
  public static List<Token>                                                         cu_from_insertion_point_2 =
      new ArrayList<>();

  /**
   * A list of all grammar productions - normal and JAVACODE - in the order they
   * appear in the input file. Each entry here will be a subclass of
   * "NormalProduction".
   */
  public static List<NormalProduction>                                              bnfproductions            =
      new ArrayList<>();

  /**
   * A symbol table of all grammar productions - normal and JAVACODE. The symbol
   * table is indexed by the name of the left hand side non-terminal. Its
   * contents are of type "NormalProduction".
   */
  public static Map<String, NormalProduction>                                       production_table          =
      new HashMap<>();

  /**
   * A mapping of lexical state strings to their integer internal
   * representation. Integers are stored as java.lang.Integer's.
   */
  static Hashtable<String, Integer>                                                 lexstate_S2I              =
      new Hashtable<>();

  /**
   * A mapping of the internal integer representations of lexical states to
   * their strings. Integers are stored as java.lang.Integer's.
   */
  public static Hashtable<Integer, String>                                          lexstate_I2S              =
      new Hashtable<>();

  /**
   * The declarations to be inserted into the TokenManager class.
   */
  public static List<Token>                                                         token_mgr_decls;

  /**
   * The list of all TokenProductions from the input file. This list includes
   * implicit TokenProductions that are created for uses of regular expressions
   * within BNF productions.
   */
  public static List<TokenProduction>                                               rexprlist                 =
      new ArrayList<>();

  /**
   * The total number of distinct tokens. This is therefore one more than the
   * largest assigned token ordinal.
   */
  public static int                                                                 tokenCount;

  /**
   * This is a symbol table that contains all named tokens (those that are
   * defined with a label). The index to the table is the image of the label and
   * the contents of the table are of type "RegularExpression".
   */
  static Map<String, RegularExpression>                                             named_tokens_table        =
      new HashMap<>();

  /**
   * Contains the same entries as "named_tokens_table", but this is an ordered
   * list which is ordered by the order of appearance in the input file.
   */
  public static List<RegularExpression>                                             ordered_named_tokens      =
      new ArrayList<>();

  /**
   * A mapping of ordinal values (represented as objects of type "Integer") to
   * the corresponding labels (of type "String"). An entry exists for an ordinal
   * value only if there is a labeled token corresponding to this entry. If
   * there are multiple labels representing the same ordinal value, then only
   * one label is stored.
   */
  public static Map<Integer, String>                                                names_of_tokens           =
      new HashMap<>();

  /**
   * A mapping of ordinal values (represented as objects of type "Integer") to
   * the corresponding RegularExpression's.
   */
  public static Map<Integer, RegularExpression>                                     rexps_of_tokens           =
      new HashMap<>();

  /**
   * This is a three-level symbol table that contains all simple tokens (those
   * that are defined using a single string (with or without a label). The index
   * to the first level table is a lexical state which maps to a second level
   * hashtable. The index to the second level hashtable is the string of the
   * simple token converted to upper case, and this maps to a third level
   * hashtable. This third level hashtable contains the actual string of the
   * simple token and maps it to its RegularExpression.
   */
  static Hashtable<String, Hashtable<String, Hashtable<String, RegularExpression>>> simple_tokens_table       =
      new Hashtable<>();

  /**
   * maskindex, jj2index, maskVals are variables that are shared between
   * ParseEngine and ParseGen.
   */
  public static int                                                                 maskindex                 = 0;
  public static int                                                                 jj2index                  = 0;
  public static boolean                                                             lookaheadNeeded;
  public static List<int[]>                                                         maskVals                  =
      new ArrayList<>();

  public static Action                                                              actForEof;
  public static String                                                              nextStateForEof;
  public static Token                                                               otherLanguageDeclTokenBeg;
  public static Token                                                               otherLanguageDeclTokenEnd;


  // Some general purpose utilities follow.

  /**
   * Returns the identifying string for the file name, given a toolname used to
   * generate it.
   *
   * @param toolName
   * @param fileName
   */
  public static String getIdString(String toolName, String fileName) {
    return JavaCCGlobals.getIdString(Collections.singletonList(toolName), fileName);
  }

  /**
   * Returns the identifying string for the file name, given a set of tool names
   * that are used to generate it.
   */
  private static String getIdString(List<String> toolNames, String fileName) {
    String id = String.format("Generated By:%s: Do not edit this line. %s", String.join("&", toolNames), fileName);
    if (id.length() <= 200) {
      return id;
    }
    System.out.println("Tool names too long.");
    throw new Error();
  }

  /**
   * Returns true if tool name passed is one of the tool names returned by
   * getToolNames(fileName).
   */
  public static boolean isGeneratedBy(String toolName, String fileName) {
    List<String> v = JavaCCGlobals.getToolNames(fileName);
    for (int i = 0; i < v.size(); i++) {
      if (toolName.equals(v.get(i))) {
        return true;
      }
    }
    return false;
  }

  private static List<String> makeToolNameList(String str) {
    List<String> retVal = new ArrayList<>();

    int limit1 = str.indexOf('\n');
    if (limit1 == -1) {
      limit1 = 1000;
    }
    int limit2 = str.indexOf('\r');
    if (limit2 == -1) {
      limit2 = 1000;
    }
    int limit = (limit1 < limit2) ? limit1 : limit2;

    String tmp;
    if (limit == 1000) {
      tmp = str;
    } else {
      tmp = str.substring(0, limit);
    }

    if (tmp.indexOf(':') == -1) {
      return retVal;
    }

    tmp = tmp.substring(tmp.indexOf(':') + 1);

    if (tmp.indexOf(':') == -1) {
      return retVal;
    }

    tmp = tmp.substring(0, tmp.indexOf(':'));

    int i = 0, j = 0;

    while (j < tmp.length() && (i = tmp.indexOf('&', j)) != -1) {
      retVal.add(tmp.substring(j, i));
      j = i + 1;
    }

    if (j < tmp.length()) {
      retVal.add(tmp.substring(j));
    }

    return retVal;
  }

  /**
   * Returns a List of names of the tools that have been used to generate the
   * given file.
   */
  public static List<String> getToolNames(String fileName) {
    char[] buf = new char[256];
    java.io.FileReader stream = null;
    int read, total = 0;

    try {
      stream = new java.io.FileReader(fileName);

      for (;;) {
        if ((read = stream.read(buf, total, buf.length - total)) != -1) {
          if ((total += read) == buf.length) {
            break;
          }
        } else {
          break;
        }
      }

      return JavaCCGlobals.makeToolNameList(new String(buf, 0, total));
    } catch (java.io.FileNotFoundException e1) {} catch (java.io.IOException e2) {
      if (total > 0) {
        return JavaCCGlobals.makeToolNameList(new String(buf, 0, total));
      }
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (Exception e3) {}
      }
    }

    return new ArrayList<>();
  }

  public static void createOutputDir(File outputDir) {
    if (!outputDir.exists()) {
      JavaCCErrors.warning("Output directory \"" + outputDir + "\" does not exist. Creating the directory.");

      if (!outputDir.mkdirs()) {
        JavaCCErrors.semantic_error("Cannot create the output directory : " + outputDir);
        return;
      }
    }

    if (!outputDir.isDirectory()) {
      JavaCCErrors.semantic_error("\"" + outputDir + " is not a valid output directory.");
      return;
    }

    if (!outputDir.canWrite()) {
      JavaCCErrors.semantic_error("Cannot write to the output output directory : \"" + outputDir + "\"");
      return;
    }
  }

  private static CodeGenerator codeGenerator = null;

  public static CodeGenerator getCodeGenerator() {
    if (JavaCCGlobals.codeGenerator != null) {
      return JavaCCGlobals.codeGenerator;
    }

    String name = Options.getCodeGenerator();
    if (name == null) {
      return null;
    }

    ServiceLoader<CodeGenerator> serviceLoader = ServiceLoader.load(CodeGenerator.class);
    for (CodeGenerator generator : serviceLoader) {
      if (generator.getName().equalsIgnoreCase(name)) {
        JavaCCGlobals.codeGenerator = generator;
        return JavaCCGlobals.codeGenerator;
      }
    }

    JavaCCErrors.semantic_error("Could not load the CodeGenerator class: \"" + name + "\"");
    return JavaCCGlobals.codeGenerator;
  }

  public static String staticOpt() {
    if (Options.getStatic()) {
      return "static ";
    } else {
      return "";
    }
  }

  public static String add_escapes(String str) {
    String retval = "";
    char ch;
    for (int i = 0; i < str.length(); i++) {
      ch = str.charAt(i);
      if (ch == '\b') {
        retval += "\\b";
      } else if (ch == '\t') {
        retval += "\\t";
      } else if (ch == '\n') {
        retval += "\\n";
      } else if (ch == '\f') {
        retval += "\\f";
      } else if (ch == '\r') {
        retval += "\\r";
      } else if (ch == '\"') {
        retval += "\\\"";
      } else if (ch == '\'') {
        retval += "\\\'";
      } else if (ch == '\\') {
        retval += "\\\\";
      } else if (ch < 0x20 || ch > 0x7e) {
        String s = "0000" + Integer.toString(ch, 16);
        retval += "\\u" + s.substring(s.length() - 4, s.length());
      } else {
        retval += ch;
      }
    }
    return retval;
  }

  public static String addUnicodeEscapes(String str) {
    String retval = "";
    char ch;
    for (int i = 0; i < str.length(); i++) {
      ch = str.charAt(i);
      if (ch < 0x20
          || ch > 0x7e /* || ch == '\\' -- cba commented out 20140305 */ ) {
        String s = "0000" + Integer.toString(ch, 16);
        retval += "\\u" + s.substring(s.length() - 4, s.length());
      } else {
        retval += ch;
      }
    }
    return retval;
  }

  public static int cline, ccol;


  public static String printTokenOnly(Token t, boolean escape) {
    String retval = "";
    for (; JavaCCGlobals.cline < t.beginLine; JavaCCGlobals.cline++) {
      retval += "\n";
      JavaCCGlobals.ccol = 1;
    }
    for (; JavaCCGlobals.ccol < t.beginColumn; JavaCCGlobals.ccol++) {
      retval += " ";
    }
    if (t.kind == JavaCCParserConstants.STRING_LITERAL || t.kind == JavaCCParserConstants.CHARACTER_LITERAL) {
      retval += escape ? JavaCCGlobals.addUnicodeEscapes(t.image) : t.image;
    } else {
      retval += t.image;
    }
    JavaCCGlobals.cline = t.endLine;
    JavaCCGlobals.ccol = t.endColumn + 1;
    char last = t.image.charAt(t.image.length() - 1);
    if (last == '\n' || last == '\r') {
      JavaCCGlobals.cline++;
      JavaCCGlobals.ccol = 1;
    }
    return retval;
  }

  static void reInit() {
    JavaCCGlobals.jjtreeGenerated = false;
    JavaCCGlobals.toolNames = null;
    JavaCCGlobals.codeGenerator = null;
    JavaCCGlobals.cu_name = null;
    JavaCCGlobals.cu_to_insertion_point_1 = new ArrayList<>();
    JavaCCGlobals.cu_to_insertion_point_2 = new ArrayList<>();
    JavaCCGlobals.cu_from_insertion_point_2 = new ArrayList<>();
    JavaCCGlobals.bnfproductions = new ArrayList<>();
    JavaCCGlobals.production_table = new HashMap<>();
    JavaCCGlobals.lexstate_S2I = new Hashtable<>();
    JavaCCGlobals.lexstate_I2S = new Hashtable<>();
    JavaCCGlobals.token_mgr_decls = null;
    JavaCCGlobals.rexprlist = new ArrayList<>();
    JavaCCGlobals.tokenCount = 0;
    JavaCCGlobals.named_tokens_table = new HashMap<>();
    JavaCCGlobals.ordered_named_tokens = new ArrayList<>();
    JavaCCGlobals.otherLanguageDeclTokenBeg = null;
    JavaCCGlobals.otherLanguageDeclTokenEnd = null;
    JavaCCGlobals.names_of_tokens = new HashMap<>();
    JavaCCGlobals.rexps_of_tokens = new HashMap<>();
    JavaCCGlobals.simple_tokens_table = new Hashtable<>();
    JavaCCGlobals.maskindex = 0;
    JavaCCGlobals.jj2index = 0;
    JavaCCGlobals.maskVals = new ArrayList<>();
    JavaCCGlobals.cline = 0;
    JavaCCGlobals.ccol = 0;
    JavaCCGlobals.actForEof = null;
    JavaCCGlobals.nextStateForEof = null;
  }
}
