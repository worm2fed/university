% worm2fed

implement main
    open core

domains
       fio = fio(string,string,string).

class facts - biblioDB
    student : (fio, string, string, string).

class predicates
    add : () procedure.
    out : () procedure.
    del : () procedure.
    search : () procedure.
    toFIO : (string, fio) determ (i, o).
    fio : (string*, fio) determ (i, o).

clauses
    toFIO(In, FIO) :- L = string::split(In, " "), fio(L, FIO).
    fio([X | [Y | [Z | []]]], FIO) :- FIO = fio(X, Y, Z).

    add() :- stdio::write("-----------------------Adding new record-----------------------\n"),
        stdio::write("Введите ФИО студента: "), toFIO(stdio::readLine(), FIO),
        stdio::write("Введите номер читательского билета: "), ChB = stdio::readLine(),
        stdio::write("Введите номер группы: "), Group = stdio::readLine(),
        stdio::write("Есть ли у стеднета задолжность?: "), Dolg = stdio::readLine(),
        asserta(student(FIO, ChB, Group, Dolg)),
        stdio::write("Добавлен студент: ", student(FIO, ChB, Group, Dolg)), stdio::nl(),
        succeed(), !; succeed().

    search() :- stdio::write("----------------------Поиск------------------\n"),
        stdio::write("Введите ФИО студента: "), toFIO(stdio::readLine(), FIO),
        student(FIO, Y, Z, W), stdio::write(FIO, " ", Y, " ", Z, " ", W, "\n"), fail.
    search().

    out() :- student(X, Y, Z, W), stdio::write(X, " ", Y, " ", Z, " ", W, " \n"), fail.
    out().

    del() :- stdio::write("-----------------------Удаление записи-----------------------\n"),
        stdio::write("Введите ФИО студента: "), toFIO(stdio::readLine(), FIO),
        retract (student(FIO, _, _, _)),
        stdio::write("Удален студент: ", student(FIO, ChB, Group, Dolg)), stdio::nl(),
        succeed(), !; succeed().

class predicates
    reconsult : (string FileName).
    saveFile : (string FileName).

clauses
    reconsult(FileName) :- retractFactDB(biblioDB),
        file::consult(FileName, biblioDB).
    saveFile(FileName) :- file::save(FileName, biblioDB).

    run() :- console::init(), stdIO::write("Load data\n"),
        reconsult("..\\DB.txt"),
        add(),
        search(),
        del(),
        stdio::write("-----------------------Вся база-----------------------\n"),
        out(),
        saveFile("..\\DB.txt"),
        succeed().

end implement main

goal
    %console::runUtf8(main::run).
    mainExe::run(main::run).