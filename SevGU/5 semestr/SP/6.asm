Program   SEGMENT 
    ORG 100H
    MOV      CH,CL
    MOV      AX, 12345
    MOV      CL,0FFH
    MOV      SI,AX
    MOV      [BX+SI+0FH],DL
    MOV      BP,OFFSET A
    MOV      BX,OFFSET B
    MOV      DL,[BP+DI+56]
    MOV      AX,[BX+SI+123]
    MOV      [BP+SI+4AH],BL
     XCHG      BX,AX
    LOOP       Q
    XCHG      CL,DL
    XCHG      [BP+DI+3EH],CL
Q:  IMUL      AX  
	XCHG      SI,[BX+DI+0CH]
    INT 20H
A   DB       80 , 88h
B   DW       0A38H, 0FFH
Program   ENDS
END