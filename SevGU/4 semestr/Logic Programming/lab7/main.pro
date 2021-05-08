% worm2fed

implement main
open core, console

domains
    binary_tree = tree(integer, binary_tree, binary_tree); nil().

class predicates
    isotree : (binary_tree, binary_tree) nondeterm.
    tree_member : (integer, binary_tree) determ.
    max : (integer, integer, integer) determ (i, i, o).
    depth : (binary_tree, integer) nondeterm (i, o).
    substitute : (integer, integer, binary_tree, binary_tree) nondeterm.

clauses
    max(A, B, X) :- A >= B, X = A, !.
    max(A, B, X) :- A < B, X = B, !.

    depth(nil, 0).
    depth(tree(X, Left, Right), Dh) :- depth(Left, LDh),
            depth(Right, RDh),
            max(LDh, RDh, C),
            Dh = (C + 1).
    isotree(nil, nil).
    isotree(tree(X, Left1, Right1),
        tree(X, Left2, Right2)) :- isotree(Left1, Left2),
            isotree(Right1, Right2);
            isotree(Left1, Right2),
            isotree(Right1, Left2).
    tree_member(X, tree(X,_,_)) :- !.
    tree_member(X, tree(_,L,_)) :- tree_member(X, L), !.
    tree_member(X, tree(_,_,R)) :- tree_member(X, R).

    substitute(X, Y, nil, nil).
    substitute(X, Y, tree(X, Left, Right),
        tree(Z, Left1, Right1)) :- Z = Y,
            substitute(X, Y, Left, Left1),
            substitute(X, Y, Right, Right1).

    substitute(X, Y, tree(Z, Left, Right),
        tree(C, Left1, Right1)) :- substitute(X, Y, Left, Left1),
            substitute(X, Y, Right, Right1).

    run() :- console::init(),
            Tree1 = tree(10, tree(50, nil, nil), tree(70, tree(70, nil, nil), nil)),
            Tree2 = tree(11, tree(55, nil, nil), tree(111, tree(77, tree(66, tree(55, nil, nil), nil), nil), nil)),
            Tree3 = tree(10, tree(50, nil, nil), tree(99, tree(99, nil, nil), nil)),
            write("\n Проверка на принадлежность элемемнта к дереву Tree1 \n"),
            write("Введите число: > "),
            X = read(),
            if tree_member(X, Tree1) then write("....True")
            else write("....False") end if,
            write("\nПроверка на изотермизм Tree1 и Tree2 "),
            if isotree(Tree1, Tree2), ! then write(".....True")
            else write(".....False") end if,
            write("\nПроверка на изотермизм Tree1 и Tree1 "),
            if isotree(Tree1, Tree1), ! then write(".....True")
            else write(".....False") end if,
            A = 70, B = 99,
            write("\n Подстановка ", B, " вместо ", A, " ?"),

            if substitute(A, B, Tree1, Tree3), ! then write(".....True")
            else write(".....False") end if,
            write("\nГлубина дерева Tree 1: "),
            depth(Tree1, Y),write(Y),
            write("\nГлубина дерева Tree 2: "),
            depth(Tree2, Z),write(Z), !;
            succeed().

    end implement main

    goal
        mainExe::run(main::run).
