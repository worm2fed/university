% worm2fed
% main.pro
implement main
    open core

constants
    className = "main".
    classVersion = "".

clauses
    classInfo(className, classVersion).
    q() :-
        stdio::write("A = "),
        A = stdio::read(),

       if (A = 0), ! then
            stdio::write("Not a quadratic equation."), stdio::nl
        else
            stdio::write("B = "),
            B = stdio::read(),
            stdio::write("C = "),
            C = stdio::read(),

           if (B * B = 4 * A * C), ! then
                stdio::writef("x = %f", -B / 2.0 /A )
            elseif (B * B > 4 * A * C), ! then
                D = B * B - 4 * A * C,
                stdio::writef("x1 = %f\n", (-B + math::sqrt(D)) / 2.0 / A),
                stdio::writef("x2 = %f", (-B - math::sqrt(D)) / 2.0 / A)
            else
                D = -B * B + 4 * A * C,
                stdio::writef("x1 = (%f, %f)\n", -B / 2.0 / A, math::sqrt(D) / 2.0 / A),
                stdio::writef("x2 = (%f, %f)", -B/ 2.0 / A, -math::sqrt(D) / 2.0 / A)
            end if
        end if.

clauses
    run():-
        console::init(),
        q(),
        succeed().
end implement main

goal
    console::runUtf8(main::run).
