% worm2fed
% W = (3+ i)+(6+i)+(9+i)+….+(m+i).

implement main
    open core

constants
    className = "main".
    classVersion = "".

clauses
    classInfo(className, classVersion).

    calculationW(N, 0, W) :- !, W = N,
        stdio::write("0 W = 3"),
        stdio::nl.

    calculationW(N, I, W) :-
        calculationW(N+3, I-1, W1),
        W = I + N + W1,
        stdio::write(I, " W = ", W),
        stdio::nl.

clauses
    run():-
        console::init(),
        calculationW(3, 5, W),
        programControl::sleep(5000),
        succeed().
end implement main

goal
    console::runUtf8(main::run).