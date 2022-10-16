# BB Version 0.0.1

## Variables
##### About
Variables can be used to store values, they can be increased, decreased and compared to other variables or immediate values
##### Definitions & assumption
- All variables are integers
- Hence, you cant to specify variable type since it's always int
- All variables are stored in global scope not matter what scope they were first used/defined in
- Negative values are allowed
- Variables do not need to be defined before use
- If not defined before use will be initialized to 0
- Variable names cant be numbers e.g. 12fe is allowed but 12 would not be allowed

##### Examples
clear X; --- X is initialized and set to 0
incr X; --- X is initialized by 1
decr X; --- X is initialized by 1
incr Y; --- Y is initialized to 1
decr Z; --- Z is initialized to -1

## Operand
##### About
Operand is a value that the interpreter will operate on, it can eight be given in direct addressing mode by passing a variable name, in this case if the variable was initialed the interpreter fetch this value otherwise it will initialize the variable to 0 and return 0. Other option is to used immediate addressing mode where instead of passing in a variable name an integer number is given instead. The interpreter will automatically detect weather or not it should use direct or immediate addressing mode.

## Immediate Values
##### About
Immediate values can be used in comparison against other immediate values or variables. Immediate values are used by typing the desired value in decimal number system where operand is expected operand instead of a variable name

## Comparison operators
##### About
Comparison operators can be used to compare two operands against each other returning a boolean value that is used in a control struct for making decision.

##### Format
All comparisons follow this format
<operandA> <comparionsOperator> <operandb>

##### Available comparison operators
not | !=
equal | ==
greater | >
greaterOrEqual | >=
less | <
lessOrEqual | <=
Both mathematical and english languages operates can be used interchangeably

##### Examples
clear a;
incr a;
incr a;
clear b;
incr b;

a > b | a greater b --- evaluates to true
a > 2 | a greater 2 --- evaluates to false
a >= 2 | a greaterOrEqual 2 --- evaluates to true
a != 2 | a not 2 --- evaluates to false
23 == 23 | 23 equals 23 --- evaluates to true
-3 < b | -3 less b --- evaluates to true
1 =< b | 1 lessOrEqual b --- evaluates to true

## Control structures
### While loops
##### About
While loop will execute code in their scope while the comparison condition evaluates to true.
NOTE: variables defined in a loop scope will still be saved in the global scope NOT the local loop scope!

##### Structure
while <operandA> <comparionsOperator> <operandb> do;
...
...
...
end;
Everything between "while <operandA> <comparionsOperator> <operandb> do;" and "end;" is considered loops scope

##### Example
clear a;
clear b;
...
while a > b do;
...loop scope...
end;