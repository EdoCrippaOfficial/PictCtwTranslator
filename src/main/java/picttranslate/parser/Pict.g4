grammar Pict;

start : (parameter)+ (constraint)* ;

parameter : value (':'|',') value (',' value)* ;

constraint : ((iftext predicate thentext predicate) | predicate)';';

predicate : clause | (clause logicalOperator predicate);

clause : term | '(' predicate ')';

term : value relation value
     | value inClause '{' value (',' value)* '}'
     | value notInClause '{' value (',' value)* '}';

value : TESTO | '"' TESTO '"';

relation : '=' | '<>' | '>' | '>=' | '<' | '<=';

logicalOperator : 'and' | 'or';

inClause : ('IN'|'in');
notInClause : ('NOT IN'|'not in');

iftext : ('if'|'IF');
thentext : ('then'|'THEN');

TESTO
    :  [a-zA-Z0-9\-!@$%^&*~]+
    ;


LINE_COMMENT
    : '#' (~('\n'|'\r'))* -> skip;

WS: [ \n\t\r]+ -> skip;
