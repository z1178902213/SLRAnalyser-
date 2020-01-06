# SLRAnalyser-
# 先把文法保存在D盘根目录下的Grammar.txt的文本文档中，程序会自动识别文法
# 文法：
 E->E+T
 E->E-T
 E->T
 T->T*F
 T->T/F
 T->F
 F->(E) 
 F->id
 F->num
