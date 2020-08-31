grammar Pict;

start : (parameter )+ (constraint)* ;

parameter : value (':') paramValue (',' paramValue)* | submodel;

paramValue : value | '<' reusedValue '>' | value '|' aliasValue;

submodel : '{' value (',' value)* '}' '@' value;

constraint : ((iftext predicate thentext predicate) | predicate) ';';

predicate : clause | (clause logicalOperator predicate);

clause : term | '(' predicate ')';

term : value relation value
     | value inClause '{' value (',' value)* '}'
     | value notInClause '{' value (',' value)* '}';

value : valueText+;
valueText: TESTO | '"' TESTO+ '"' | '(' TESTO+ ')';
reusedValue : value;
aliasValue : value;

relation : '=' | '<>' | '>' | '>=' | '<' | '<=';

logicalOperator : 'and' | 'AND' | 'or' | 'OR';

inClause : ('IN'|'in');
notInClause : ('NOT IN'|'not in');

iftext : ('if'|'IF');
thentext : ('then'|'THEN');

TESTO
    :  [a-zA-Z0-9!@$%^&*]+
    ;


LINE_COMMENT
    : '#' (~('\n'|'\r'))* -> skip;

WS: [ \n\t\r]+ -> skip;
