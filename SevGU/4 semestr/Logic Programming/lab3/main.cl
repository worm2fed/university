% worm2fed
% main.cl
class main
    open core

predicates
    classInfo : core::classInfo.
    q : () procedure(). % вычисление всегда будет успешным и будет найдено ровно одно решение

predicates
    run : core::runnable.

end class main