package com.github.sean111.wowtoc.lexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.lexer.FlexLexer;
import com.github.sean111.wowtoc.psi.TocTypes;
import com.intellij.psi.TokenType;

%%

%class TocLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=[\r\n]|\r\n
WHITE_SPACE=[ ]
// A comment begins with "#". When it has two or more characters, the second character cannot be "#".
END_OF_LINE_COMMENT=#([^#\n\f][^\n\f]*)?
TAG_PREFIX=##
// Tag names cannot contain ":". They can contain internal spaces but not leading or trailing spaces.
TAG_NAME=[^: \n\f]+|[^: \n\f][^:\n\f]*[^: \n\f]
SEPARATOR=:
// Tag values can contain internal spaces but not leading or trailing spaces.
TAG_VALUE=[^ \n\f]+|[^ \n\f][^\n\f]*[^ \n\f]
// File names cannot begin with "#" and cannot include trailing spaces.
FILE_NAME=[ ]+|[^#\n\f]([^ \n\f]*|[^\n\f]*[^ \n\f])

%state WAITING_CRLF
%state WAITING_KEY
%state WAITING_VALUE

%%

// In the initial state, match line breaks, comments, tag prefixes, or file names.
<YYINITIAL> {CRLF}+ {yybegin(YYINITIAL); return TokenType.WHITE_SPACE;}
<YYINITIAL> {END_OF_LINE_COMMENT} {yybegin(WAITING_CRLF); return TocTypes.COMMENT;}
<YYINITIAL> {TAG_PREFIX} {yybegin(WAITING_KEY); return TocTypes.TAG_PREFIX;}
<YYINITIAL> {FILE_NAME} {yybegin(WAITING_CRLF); return TocTypes.FILE_NAME;}
// While waiting for a tag name or separator, match whitespace, a tag name, a separator, or a line break.
<WAITING_KEY> {WHITE_SPACE}+ {yybegin(WAITING_KEY); return TokenType.WHITE_SPACE;}
<WAITING_KEY> {TAG_NAME} {yybegin(WAITING_KEY); return TocTypes.TAG_NAME;}
<WAITING_KEY> {WHITE_SPACE}*{CRLF}+ {yybegin(YYINITIAL); return TokenType.WHITE_SPACE;}
<WAITING_KEY> {SEPARATOR} {yybegin(WAITING_VALUE); return TocTypes.SEPARATOR;}
// While waiting for a tag value, match whitespace, a tag value, or a line break.
<WAITING_VALUE> {WHITE_SPACE}+ {yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE;}
<WAITING_VALUE> {TAG_VALUE} {yybegin(WAITING_CRLF); return TocTypes.TAG_VALUE;}
<WAITING_VALUE> {WHITE_SPACE}*{CRLF}+ {yybegin(YYINITIAL); return TokenType.WHITE_SPACE;}
// While waiting for a line break, match whitespace followed by a line break.
<WAITING_CRLF> {WHITE_SPACE}*{CRLF}+ {yybegin(YYINITIAL); return TokenType.WHITE_SPACE;}
[^] {return TokenType.BAD_CHARACTER;}
