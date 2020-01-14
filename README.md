# SLRAnalyser-
将文法保存在D盘根目录下的Grammar.txt中  
文法（文法左部右部分隔符必须是->，如果是其他的符号会出问题的）
E->E+T  
E->E-T  
E->T  
T->T*F  
T->T/F  
T->F  
F->(E)  
F->id  
F->num  
