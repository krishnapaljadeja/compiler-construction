%{
#include <stdio.h>
#include <string.h>

int line_no = 1;
int sym_index = 0;
char symtab[50][20];

int search(char *s) {
    for(int i = 0; i < sym_index; i++) {
        if(strcmp(symtab[i], s) == 0)
            return 1;
    }
    return 0;
}
%}

KEYWORD     int|char|float|double|long|void|return|struct
ID          [a-zA-Z_][a-zA-Z0-9_]*
NUM         [0-9]+(\.[0-9]+)?
CHAR        \'[^\']\'
STRING      \"[^\"]*\"
OP          \+|\-|\*|\/|=|==|<=|>=|<|>
PUNC        [\(\)\{\}\[\];,]
INVALID     [0-9]+[a-zA-Z]+

%%

\n              { line_no++; }

{KEYWORD}      { printf("Keyword: %s\n", yytext); }

{ID}           {
                    printf("Identifier: %s\n", yytext);
                    if(!search(yytext)) {
                        strcpy(symtab[sym_index++], yytext);
                    }
                }

{NUM}          { printf("Constant: %s\n", yytext); }

{CHAR}         { printf("Constant: %s\n", yytext); }

{STRING}       { printf("String: %s\n", yytext); }

{OP}           { printf("Operator: %s\n", yytext); }

{PUNC}         { printf("Punctuation: %s\n", yytext); }

"//".*         { /* single-line comment */ }

"/*"([^*]|\*+[^*/])*\*+"/"  { /* multi-line comment */ }

{INVALID}      {
                    printf("LEXICAL ERROR\n");
                    printf("Line %d : %s invalid lexeme\n", line_no, yytext);
                }

[ \t]+         { /* ignore whitespaces */ }

.              { /* ignore other characters */ }

%%

int main() {
    printf("TOKENS\n");
    yylex();

    printf("\nSYMBOL TABLE ENTRIES\n");
    for(int i = 0; i < sym_index; i++) {
        printf("%d) %s\n", i+1, symtab[i]);
    }
    return 0;
}

int yywrap() {
    return 1;
}
