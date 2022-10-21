# BB Version 0.0.2

## Variables
### About
Variables can be used to store values, they can be increased, decreased and compared to other variables or immediate values
### Definitions & assumption
- All variables are integers
- Hence, you cant to specify variable type since it's always int
- All variables are stored in global scope not matter what scope they were first used/defined in
- Negative values are allowed
- Variables do not need to be defined before use
- If not defined before use will be initialized to 0
- Each line has to end with ;
- Variable names cant be numbers e.g. 12fe is allowed but 12 would not be allowed
- New in BB version 0.0.2
  - Varibale name can no longer contain " in their name
  - Function name follow the same rules as variable names
  - User defined function name cant be the same as build in function
  - Function and variable can have the same name with no conflict
### Examples
<pre>
# X is initialized and set to 0  
clear X;
# X is initialized by 1  
incr X;
# X is decreased by 1  
decr X; 
# Y is initialized to 1  
incr Y;
# Z is initialized to -1  
decr Z;

# Logs values of the variables
log X;
log Y;
log Z;
</pre>
## Operand
### About
Operand is a value that the interpreter will operate on, it can eight be given in direct addressing mode by passing a variable name, in this case if the variable was initialed the interpreter fetch this value otherwise it will initialize the variable to 0 and return 0. Other option is to used immediate addressing mode where instead of passing in a variable name an integer number is given instead. The interpreter will automatically detect weather or not it should use direct or immediate addressing mode.

## Immediate Values
### About
Immediate values can be used in comparison against other immediate values or variables. Immediate values are used by typing the desired value in decimal number system where operand is expected operand instead of a variable name

## Comparison operators
### About
Comparison operators can be used to compare two operands against each other returning a boolean value that is used in a control struct for making decision.

### Format
All comparisons follow the following format:  
<pre>
operandA comparisonsOperator operandB
</pre>

### Available comparison operators
not | !=  
equal | ==  
greater | >  
greaterOrEqual | >=  
less | <  
lessOrEqual | <=  
Both mathematical and english languages operates can be used interchangeably.

### Examples
<pre>
# Initializes variables
clear a;  
incr a;  
incr a;  
clear b;  
incr b;  

# a greater b --- evaluates to true  
if a > b do; ... end;
# a greater 2 --- evaluates to false  
if a > 2 do; ... end;
# a greaterOrEqual 2 --- evaluates to true  
if a >= 2 do; ... end;
# a not 2 --- evaluates to false
if a != 2 do; ... end;
# 23 equals 23 --- evaluates to true  
if 23 == 23 do; ... end;
# -3 less b --- evaluates to true  
if -3 < b do; ... end;
# 1 lessOrEqual b --- evaluates to true  
if 1 =< b do; ... end;
</pre>
## Control structures
### While loops
#### About
While loop will execute code in their scope while the comparison comparisonCondition evaluates to true.
NOTE: variables defined in a loop scope will still be saved in the global scope NOT the local loop scope!

#### Structure
<pre>
while operandA comparisonsOperator operandB do;  
  ...
end;  
</pre>
Everything between "while operandA comparisonsOperator operandB do;" and "end;" is considered loops scope

#### Example
<pre>
# Logs numbers from 10 to 1
clear a;  
clear b; 
a = 10 + 0;
while a > b do;  
    log a;
    decr a;
end;  
</pre>
### If, elseif & else statements
#### About
If statements can be used to create branches, the program will only execute the contests of if statement if its condition evaluates to true. The if statement can also be chained together with any number of elseif and can optionally end with an else statement.  
#### Structure
##### Simple if
<pre>
if operandA comparisonsOperator operandB do;  
  ....  
end;  
</pre>

##### If elseif
<pre>
if operandA comparisonsOperator operandB do;  
  ....  
else if operandA comparisonsOperator operandB do;  
  ...  
else if operandA comparisonsOperator operandB do;  
  ...  
end;  
</pre>

##### If elseif elseif
<pre>
if operandA comparisonsOperator operandB do;  
  ...
else if operandA comparisonsOperator operandB do;  
  ...  
else if operandA comparisonsOperator operandB do;  
  ...  
else do;  
  ...  
end;  
</pre>

#### Example
<pre>
# Creates a loop that uses if control structure to count dawn
a = 0 + 32;  
while b == 0 do;
    if a > 0 do;  
        a = a - 1;  
    elseif a == 0 do;  
        clear d;  
        decr a;  
    else do;  
        decr b;  
    end;  
end;

# Logs values of variables after the loop has finished running
log a;
log b;
log d;
</pre>
## Comments
Any line that begins with # will be treated as a comment and will be completely ignored when compiling code.

## Build in function
### Log
#### About
log function can be used to log string or value of variable to the console.  
NOTE: In the current version string have to be contained using "" and cant contain spaces.
#### Structure
<pre>
log operand;
</pre>
#### Example
<pre>
a = 34 * 34;
a = a + 23;
log a;
a = a % 34;
log a;
</pre>

### Input
#### About
input function can be used to read an integer value from the console to a specified variable, if input value is not en integer the specified variable will be loaded with 0.
#### Structure
<pre>
input varibaleName;
</pre>
#### Example
<pre>
# gets a positive non-zero value from the user
log "Enter-a-positive-non-zero-value:";
input userInput;
while userInput <= 0 do;
  lof "Invalid-input";
  log "Enter-a-positive-non-zero-value:";
  input userInput;
end;
log "userInput-is";
log userInput;
</pre>

## Arithmetic operators
### About
Arithmetic operators can be used to perform arithmetic operation. BB version 0.0.2 supports +, -, *, / and % operators.
### Structure
<pre>
saveLocation = leftSide operator rightSide;
</pre>
### Examples
<pre>
a = 10 + 0;
log a;
b = 34 + 0;
log b;
c = a + b;
log c;
d = a - b;
log d;
e = a * b;
log e;
f = b / a;
log f;
g = b % a;
log g;

h = 234 + -34;
log h;
i = 34 - 13;
log i;
j = 12 * 10;
log j;
k = 121 / 10;
log k;
l = 1235 % 5;
log l;
</pre>

## Functions
### About
Function can be used to define a callable scopes (all variable are till global) this BB version 0.0.2 only supports function with no parameters and no return values. All function are complied before the rest of the code codes, recursion is not supported, and you can only use a function A within a function B if A was defined before B.
### Structure
<pre>
function functionName do;
  ...
end;
</pre>
### Example
<pre>
# Defines function A;
function funA do;
    a = a + 1;
    b = a + 2;
end;

# Runs a loop that will execute funA and funB 10 times;
loopCounter = 10 + 0;
while loopCounter > 0 do;
    funA;
    funB;
    decr loopCounter;
    
    log "--------------";
    log a;
    log b;
    log c;
    log d;
    log loopCounter;
    log "--------------";
end;

# Defines function B;
function funB do;
    c = b % a;
    d = c * 10;
end;


</pre>

## Large example of language
### About
This program asks the user for a number greater than or equal to 1, it will keep asking for a number until it receives a valid input. Then it will find the first n (the specified number) prime numbers.
### Code
<pre>
# get the number of prime numbers to find from console
log "num_of_primes_to_find:";
input num_of_primes_to_find;

# check if the user input is valid, if not we ask the user again
while num_of_primes_to_find < 1 do;
    log "invalid_input";
    log "pleas_enter_a_new_number:";
    input num_of_primes_to_find;
end;

# logs a message to separate user input from results
log "this_are_the_first_n_prime_number";

# defines variable used in the loop (for clarity)
clear found_primes;
current_number = 2 + 0;

# we know that 2 is a prime, lets start the search at 3
log 2;
found_primes = found_primes + 1;
current_number = current_number + 1;

# keeps checking numbers until it finds the specified number of primes
while found_primes < num_of_primes_to_find do;
    is_prime;
    current_number = current_number + 1;
end;

# defines a function used to check if the current_number is prime
function is_prime do;
    divider = 2 + 0;
    clear flag;

    while divider < current_number do;
        res = current_number % divider;
        if res == 0 do;
            incr flag;
            divider = current_number + 0;
        else do;
            divider = divider + 1;
        end;
    end;

    if flag == 0 do;
        log current_number;
        incr found_primes;
    end;
end;
</pre>