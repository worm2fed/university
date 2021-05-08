;	Программа вычисления выражения A*X*X+B*X+C, где
;	X – некоторая величина от 0 до 255 длиной в 1байт,
;   A,B,C – некоторые константы от 0 до 65535 длиной 2байта
;	или слово каждая

EXAMPLE SEGMENT						;	Имя сегмента
	ASSUME CS:EXAMPLE, DS:EXAMPLE	;	Имя сегмента кода и данных
	    ORG			100H 			;	Для COM–файлов программа
									;	начинается с адреса 100H
		;	Определение констант
		A	EQU		1000H
		B	EQU		2000H
		C	EQU		3000H

		;	Исполняемые команды
		START:
			;	Вычисление A * X * X
			XOR		AX, AX			;	AX	0
			MOV		AL, X			;	A 	L 	X
			MUL		X				;	AX 	X * X
			MOV		BX, A			;	BX	A
			MUL		BX				;	DX, AX	A * X * X
			MOV		RLOW, AX		;	RLOW	AX
			MOV		RHIGHT, DX  	;	RHIGHT	DX

			;	Вычисление B * X
			MOV		AL, X 			;	AL 	Х
			XOR		AH, AH      	;	AH	0
			MOV		BX, B 			;	BX	B
			MUL		BX 	        	;	DX, AX	B * X

			;	Вычисление A * X * X + B * X
			ADD		AX, RLOW		;	младшая часть
			ADC		DX, RHIGHT		;	старшая часть

			;	Вычисление A * X * X + B * X + C
			ADD		AX, C
			ADC		DX, 0
			MOV		RLOW, AX		;	сохранение результата в памяти
			MOV		RHIGHT, DX       

			;	Выход в DOS
			INT		20H

			X	DB	31H				;	присвоение значения переменной Х
			;   Резервирование памяти для результата
			RLOW	DW	?
			RHIGHT	DW	?
EXAMPLE	ENDS						;	конец описания сегмента
END 								;	конец исходного модуля,
START				    			;	START – точка входа (метка первой исполняемой команды)

