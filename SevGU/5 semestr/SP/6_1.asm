   SEGMENT 
    ORG 100H
    MOV      CH,CL
    MOV      AX, 12345
    MOD      CL,0FFH
    MOV      SI,AX
    MOV      [BX+SI+0FH],DL
    MOV      BP,OFFSET A
    MOV      BX,OFFSET
    MOV      DL
    MOV      AX,[BX+SI+123]
    MOV      [BP+SI+4AH],BL
    ADD      AL,58
 S:    ADD      BX,AX
    Js       Q
    ADD      CL,DL
    ADD      AL,[BP+SI+0EFFH]
    ADD      [BP+DI+3EAFH],CL
S:    ADD      SI,[BX+DI+0CH]
    SHR      AX
    INT 20H
A   DB       80 , 88h
B   DW       0A38H, 0FFH
Program   ENDS
END