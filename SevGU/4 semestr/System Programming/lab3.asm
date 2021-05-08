LAB SEGMENT 							; Имя сегмента
	ASSUME CS:LAB,DS:LAB 				; Имя сегмента кода и данных
		ORG 100H 						; Для COM-файлов программа
										; начинается с адреса 100H
		START: JMP main
			PACK db 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789' 		; исходная таблица
			UNPACK db '3LX6QKBJ8EWCUZ7SF1MGN4DO25AP9HRIVY0T' 	; таблица кодировки
			CADRDATA db 'BNDS3IEND04DVVVV' 						; данные для расшифровки
			PP dd 41H 											; преамбула
			DATA db 16 dup(?) 									; данные после расшифровки
			SUMMA dw ? 											; контрольная сумма
			KK db 0FFH 											; конец кадра
			
			main:
				LEA SI, CADRDATA  		; адреса данных для распаковки в индекс источник SI
				MOV CX, 16  			; 16 символов, в регистр CX число 16, повторений цикла.
				MOV DX, 0 				; обнуление для вычисления суммы.
				MOV AH, 0
			
			M1:
				LEA DI, PACK 			; в индекс приёмник значения из таблицы pack.
				MOV BX, CX 				; bx <- cx
				MOV CX, 36 				; cx <- 36
				LODSB  					; загрузить из памяти байт в регистр Al
				REPNE SCASB 			; сравнить текущий символ в AL c D (UNPACK) повторять пока z!=0
				MOV CX, BX 				; cx <- bx возвращаем значение 16.
				MOV BX, 16 				; bx <- 16
				SUB BX, CX 				; куда записывать значение, bx-cx
				MOV AL, [DI+35] 		; в Al значение для перекодировки.
				LEA DI, DATA+BX 		; в индекс приемник загрузить адреса данных+значение.
				STOSB 					; записать значение регистра al  в память.
				ADD DX, AX 				; Dx+Ax арифметическая сумма всех байт поля
				MOV [SUMMA], DX 		; значение из dx отправить в поле памяти SUMMA.
				LOOP M1 				; CX-1, if CX !=0  то переход по M1, иначе к следующей команде.
				INT 20H
	
	LAB ENDS
END START