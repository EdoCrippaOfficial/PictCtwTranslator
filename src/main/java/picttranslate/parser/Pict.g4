grammar Pict;

start : (parameter)+ (constraint)* ;

parameter : value (':'|',') value (',' value)* ;

constraint : iftext predicate thentext predicate ';';

predicate : clause | (clause logicalOperator predicate);

clause : term | '(' predicate ')';

term : value relation value | value ('IN'|'in') '{' value (',' value)* '}';

value : TESTO | '"' TESTO '"';

relation : '=' | '<>' | '>' | '>=' | '<' | '<=';

logicalOperator : 'and' | 'or';

iftext : 'if';
thentext : 'then';

TESTO
    :  [a-zA-Z0-9-]+
    ;


LINE_COMMENT
    : '#' (~('\n'|'\r'))* -> skip;

WS: [ \n\t\r]+ -> skip;
