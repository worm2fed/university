% worm2fed

implement main
    open core

domains
    list = integer*.

class predicates
    unite : (list, list, list, list) procedure (i, i, i, o).
    add : (list, integer, list) procedure (i, i, o).
    delete_all : (integer, list, list) procedure (i, i, o).
    delete : (list, list) procedure (i,  o).

clauses
    % получить 1 список из 2-х равных по длине
    add([G | Tail], El, [G | Res]) :- add(Tail, El, Res).
    add([], El, [El]).

    unite([], [], ListIn, ListIn) :- !.
    unite([X | L1], [Y | L2], ListIn, ListOut) :- add(ListIn, X, Temp),
            add(Temp, Y, Temp2),
            unite(L1, L2, Temp2, ListOut ), !.
    unite(_, _, _, []).

    %удалить повторяющиеся элементы %
    delete_all(_, [], []) :- !.
    delete_all(X, [X | L], L1) :- delete_all(X, L, L1), !.
    delete_all(X, [Y | L], [Y | L1]) :-X <> Y, delete_all(X, L, L1), !.
    delete_all(_, _, []).

    delete([], []) :- !.
    delete([H | T], Res) :-
            delete_all(H, T, Res1),
            delete(Res1, Res2),
            add(Res2, H, Res), !.

   delete(_, []).

clauses
    run():-
        console::init(),
            L1 = [1, 3, 5, 7, 9],
            L2 = [2, 4, 6, 8, 10],
            unite(L1, L2, [], Result),
            stdio::write(Result),
            L3 = [1, 2, 3, 4, 3, 4, 3, 2, 5],
            delete(L3, Res),
            stdio::write(Res).

end implement main

goal
    console::runUtf8(main::run).