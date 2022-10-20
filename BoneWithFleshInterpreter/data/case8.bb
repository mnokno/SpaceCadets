
log "num_of_primes_to_find:";
input num_of_primes_to_find;


clear found_primes;
current_number = 2 + 0;


log 2;
found_primes = found_primes + 1;
current_number = current_number + 1;


while found_primes < num_of_primes_to_find do;
    is_prime;
    current_number = current_number + 1;
end;


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