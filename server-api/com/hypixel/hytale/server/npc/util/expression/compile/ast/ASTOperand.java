/*    */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Parser;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ASTOperand
/*    */   extends AST
/*    */ {
/*    */   public ASTOperand(@Nonnull ValueType valueType, @Nonnull Token token, int tokenPosition) {
/* 20 */     super(valueType, token, tokenPosition);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ASTOperand createFromParsedToken(@Nonnull Parser.ParsedToken operand, @Nonnull CompileContext compileContext) {
/*    */     Scope scope;
/* 26 */     Token token = operand.token;
/* 27 */     int tokenPosition = operand.tokenPosition;
/* 28 */     String tokenString = operand.tokenString;
/*    */     
/* 30 */     switch (token) {
/*    */       case STRING:
/* 32 */         return new ASTOperandString(token, tokenPosition, tokenString);
/*    */       case NUMBER:
/* 34 */         return new ASTOperandNumber(token, tokenPosition, operand.tokenNumber);
/*    */       case IDENTIFIER:
/* 36 */         scope = compileContext.getScope();
/* 37 */         if (scope.isConstant(tokenString))
/*    */         {
/* 39 */           return createFromScopeConstant(token, tokenPosition, scope, tokenString);
/*    */         }
/* 41 */         return new ASTOperandIdentifier(scope.getType(tokenString), token, tokenPosition, tokenString);
/*    */     } 
/* 43 */     throw new IllegalStateException("Unknown parser operand type in AST" + String.valueOf(operand.token));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private static ASTOperand createFromScopeConstant(@Nonnull Token token, int tokenPosition, @Nonnull Scope scope, String identifier) {
/*    */     // Byte code:
/*    */     //   0: aload_2
/*    */     //   1: aload_3
/*    */     //   2: invokeinterface getType : (Ljava/lang/String;)Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   7: astore #4
/*    */     //   9: aload #4
/*    */     //   11: astore #5
/*    */     //   13: iconst_0
/*    */     //   14: istore #6
/*    */     //   16: aload #5
/*    */     //   18: iload #6
/*    */     //   20: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*    */     //   25: tableswitch default -> 168, -1 -> 168, 0 -> 72, 1 -> 86, 2 -> 100, 3 -> 114, 4 -> 128, 5 -> 142, 6 -> 156
/*    */     //   72: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandNumber
/*    */     //   75: dup
/*    */     //   76: aload_0
/*    */     //   77: iload_1
/*    */     //   78: aload_2
/*    */     //   79: aload_3
/*    */     //   80: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILcom/hypixel/hytale/server/npc/util/expression/Scope;Ljava/lang/String;)V
/*    */     //   83: goto -> 186
/*    */     //   86: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandString
/*    */     //   89: dup
/*    */     //   90: aload_0
/*    */     //   91: iload_1
/*    */     //   92: aload_2
/*    */     //   93: aload_3
/*    */     //   94: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILcom/hypixel/hytale/server/npc/util/expression/Scope;Ljava/lang/String;)V
/*    */     //   97: goto -> 186
/*    */     //   100: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandBoolean
/*    */     //   103: dup
/*    */     //   104: aload_0
/*    */     //   105: iload_1
/*    */     //   106: aload_2
/*    */     //   107: aload_3
/*    */     //   108: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILcom/hypixel/hytale/server/npc/util/expression/Scope;Ljava/lang/String;)V
/*    */     //   111: goto -> 186
/*    */     //   114: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandNumberArray
/*    */     //   117: dup
/*    */     //   118: aload_0
/*    */     //   119: iload_1
/*    */     //   120: aload_2
/*    */     //   121: aload_3
/*    */     //   122: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILcom/hypixel/hytale/server/npc/util/expression/Scope;Ljava/lang/String;)V
/*    */     //   125: goto -> 186
/*    */     //   128: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandStringArray
/*    */     //   131: dup
/*    */     //   132: aload_0
/*    */     //   133: iload_1
/*    */     //   134: aload_2
/*    */     //   135: aload_3
/*    */     //   136: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILcom/hypixel/hytale/server/npc/util/expression/Scope;Ljava/lang/String;)V
/*    */     //   139: goto -> 186
/*    */     //   142: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandBooleanArray
/*    */     //   145: dup
/*    */     //   146: aload_0
/*    */     //   147: iload_1
/*    */     //   148: aload_2
/*    */     //   149: aload_3
/*    */     //   150: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILcom/hypixel/hytale/server/npc/util/expression/Scope;Ljava/lang/String;)V
/*    */     //   153: goto -> 186
/*    */     //   156: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandEmptyArray
/*    */     //   159: dup
/*    */     //   160: aload_0
/*    */     //   161: iload_1
/*    */     //   162: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;I)V
/*    */     //   165: goto -> 186
/*    */     //   168: new java/lang/IllegalStateException
/*    */     //   171: dup
/*    */     //   172: aload #4
/*    */     //   174: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   177: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   182: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   185: athrow
/*    */     //   186: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #50	-> 0
/*    */     //   #51	-> 9
/*    */     //   #52	-> 72
/*    */     //   #53	-> 86
/*    */     //   #54	-> 100
/*    */     //   #55	-> 114
/*    */     //   #56	-> 128
/*    */     //   #57	-> 142
/*    */     //   #58	-> 156
/*    */     //   #59	-> 168
/*    */     //   #51	-> 186
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	187	0	token	Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;
/*    */     //   0	187	1	tokenPosition	I
/*    */     //   0	187	2	scope	Lcom/hypixel/hytale/server/npc/util/expression/Scope;
/*    */     //   0	187	3	identifier	Ljava/lang/String;
/*    */     //   9	178	4	type	Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ASTOperand createFromOperand(@Nonnull Token token, int tokenPosition, @Nonnull ExecutionContext.Operand operand) {
/*    */     // Byte code:
/*    */     //   0: aload_2
/*    */     //   1: getfield type : Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   4: astore_3
/*    */     //   5: aload_3
/*    */     //   6: astore #4
/*    */     //   8: iconst_0
/*    */     //   9: istore #5
/*    */     //   11: aload #4
/*    */     //   13: iload #5
/*    */     //   15: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*    */     //   20: tableswitch default -> 176, -1 -> 176, 0 -> 68, 1 -> 84, 2 -> 100, 3 -> 116, 4 -> 132, 5 -> 148, 6 -> 164
/*    */     //   68: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandNumber
/*    */     //   71: dup
/*    */     //   72: aload_0
/*    */     //   73: iload_1
/*    */     //   74: aload_2
/*    */     //   75: getfield number : D
/*    */     //   78: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ID)V
/*    */     //   81: goto -> 193
/*    */     //   84: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandString
/*    */     //   87: dup
/*    */     //   88: aload_0
/*    */     //   89: iload_1
/*    */     //   90: aload_2
/*    */     //   91: getfield string : Ljava/lang/String;
/*    */     //   94: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILjava/lang/String;)V
/*    */     //   97: goto -> 193
/*    */     //   100: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandBoolean
/*    */     //   103: dup
/*    */     //   104: aload_0
/*    */     //   105: iload_1
/*    */     //   106: aload_2
/*    */     //   107: getfield bool : Z
/*    */     //   110: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;IZ)V
/*    */     //   113: goto -> 193
/*    */     //   116: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandNumberArray
/*    */     //   119: dup
/*    */     //   120: aload_0
/*    */     //   121: iload_1
/*    */     //   122: aload_2
/*    */     //   123: getfield numberArray : [D
/*    */     //   126: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;I[D)V
/*    */     //   129: goto -> 193
/*    */     //   132: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandStringArray
/*    */     //   135: dup
/*    */     //   136: aload_0
/*    */     //   137: iload_1
/*    */     //   138: aload_2
/*    */     //   139: getfield stringArray : [Ljava/lang/String;
/*    */     //   142: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;I[Ljava/lang/String;)V
/*    */     //   145: goto -> 193
/*    */     //   148: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandBooleanArray
/*    */     //   151: dup
/*    */     //   152: aload_0
/*    */     //   153: iload_1
/*    */     //   154: aload_2
/*    */     //   155: getfield boolArray : [Z
/*    */     //   158: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;I[Z)V
/*    */     //   161: goto -> 193
/*    */     //   164: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandEmptyArray
/*    */     //   167: dup
/*    */     //   168: aload_0
/*    */     //   169: iload_1
/*    */     //   170: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;I)V
/*    */     //   173: goto -> 193
/*    */     //   176: new java/lang/IllegalStateException
/*    */     //   179: dup
/*    */     //   180: aload_3
/*    */     //   181: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   184: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   189: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   192: athrow
/*    */     //   193: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #66	-> 0
/*    */     //   #67	-> 5
/*    */     //   #68	-> 68
/*    */     //   #69	-> 84
/*    */     //   #70	-> 100
/*    */     //   #71	-> 116
/*    */     //   #72	-> 132
/*    */     //   #73	-> 148
/*    */     //   #74	-> 164
/*    */     //   #75	-> 176
/*    */     //   #67	-> 193
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	194	0	token	Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;
/*    */     //   0	194	1	tokenPosition	I
/*    */     //   0	194	2	operand	Lcom/hypixel/hytale/server/npc/util/expression/ExecutionContext$Operand;
/*    */     //   5	189	3	type	Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */