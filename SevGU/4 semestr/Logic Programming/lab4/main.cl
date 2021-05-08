% worm2fed

class main
    open core

predicates
    classInfo : core::classInfo.
    calculationW : (integer N, integer I, integer W) procedure (i,i,o).

predicates
    run : core::runnable.

end class main