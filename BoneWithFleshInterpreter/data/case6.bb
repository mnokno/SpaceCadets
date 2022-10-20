# Defines function A;
function funA do;
    a = a + 1;
    b = a + 1;
end;

# Runs a loop that will execute funA and funB 10 times;
loopCounter = 10 + 0;
while loopCounter > 0 do;
    funA;
    funB;
    decr loopCounter;
end;

# Defines function B;
function funB do;
    c = b % a;
    d = c * 10;
end;