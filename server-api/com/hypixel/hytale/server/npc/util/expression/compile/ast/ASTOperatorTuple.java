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
/*    */ public class ASTOperatorTuple
/*    */   extends ASTOperator
/*    */ {
/*    */   public ASTOperatorTuple(@Nonnull ValueType arrayType, @Nonnull Token token, int tokenPosition) {
/* 19 */     super(arrayType, token, tokenPosition);
/* 20 */     this.codeGen = (scope -> ExecutionContext.genPACK(getValueType(), getArguments().size()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   public static void fromParsedTuple(@Nonnull Parser.ParsedToken openingToken, int argumentCount, @Nonnull CompileContext compileContext) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield token : Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;
/*    */     //   4: astore_3
/*    */     //   5: aload_3
/*    */     //   6: getstatic com/hypixel/hytale/server/npc/util/expression/compile/Token.OPEN_SQUARE_BRACKET : Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;
/*    */     //   9: if_acmpeq -> 29
/*    */     //   12: new java/lang/IllegalStateException
/*    */     //   15: dup
/*    */     //   16: aload_3
/*    */     //   17: invokevirtual get : ()Ljava/lang/String;
/*    */     //   20: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   25: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   28: athrow
/*    */     //   29: aload_0
/*    */     //   30: getfield tokenPosition : I
/*    */     //   33: istore #4
/*    */     //   35: aload_2
/*    */     //   36: invokevirtual getOperandStack : ()Ljava/util/Stack;
/*    */     //   39: astore #5
/*    */     //   41: iload_1
/*    */     //   42: ifne -> 62
/*    */     //   45: aload #5
/*    */     //   47: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandEmptyArray
/*    */     //   50: dup
/*    */     //   51: aload_3
/*    */     //   52: iload #4
/*    */     //   54: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;I)V
/*    */     //   57: invokevirtual push : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   60: pop
/*    */     //   61: return
/*    */     //   62: aload #5
/*    */     //   64: invokevirtual size : ()I
/*    */     //   67: istore #6
/*    */     //   69: iload #6
/*    */     //   71: iload_1
/*    */     //   72: isub
/*    */     //   73: istore #7
/*    */     //   75: aload #5
/*    */     //   77: iload #7
/*    */     //   79: invokevirtual get : (I)Ljava/lang/Object;
/*    */     //   82: checkcast com/hypixel/hytale/server/npc/util/expression/compile/ast/AST
/*    */     //   85: invokevirtual getValueType : ()Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   88: astore #8
/*    */     //   90: getstatic com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperatorTuple$1.$SwitchMap$com$hypixel$hytale$server$npc$util$expression$ValueType : [I
/*    */     //   93: aload #8
/*    */     //   95: invokevirtual ordinal : ()I
/*    */     //   98: iaload
/*    */     //   99: tableswitch default -> 142, 1 -> 124, 2 -> 130, 3 -> 136
/*    */     //   124: getstatic com/hypixel/hytale/server/npc/util/expression/ValueType.NUMBER_ARRAY : Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   127: goto -> 160
/*    */     //   130: getstatic com/hypixel/hytale/server/npc/util/expression/ValueType.STRING_ARRAY : Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   133: goto -> 160
/*    */     //   136: getstatic com/hypixel/hytale/server/npc/util/expression/ValueType.BOOLEAN_ARRAY : Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   139: goto -> 160
/*    */     //   142: new java/lang/IllegalStateException
/*    */     //   145: dup
/*    */     //   146: aload #8
/*    */     //   148: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   151: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   156: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   159: athrow
/*    */     //   160: astore #9
/*    */     //   162: iconst_1
/*    */     //   163: istore #10
/*    */     //   165: iload #7
/*    */     //   167: istore #11
/*    */     //   169: iload #11
/*    */     //   171: iload #6
/*    */     //   173: if_icmpge -> 240
/*    */     //   176: aload #5
/*    */     //   178: iload #11
/*    */     //   180: invokevirtual get : (I)Ljava/lang/Object;
/*    */     //   183: checkcast com/hypixel/hytale/server/npc/util/expression/compile/ast/AST
/*    */     //   186: astore #12
/*    */     //   188: iload #10
/*    */     //   190: aload #12
/*    */     //   192: invokevirtual isConstant : ()Z
/*    */     //   195: iand
/*    */     //   196: istore #10
/*    */     //   198: aload #12
/*    */     //   200: invokevirtual getValueType : ()Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   203: aload #8
/*    */     //   205: if_acmpeq -> 234
/*    */     //   208: new java/lang/IllegalStateException
/*    */     //   211: dup
/*    */     //   212: aload #8
/*    */     //   214: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   217: aload #12
/*    */     //   219: invokevirtual getValueType : ()Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   222: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   225: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*    */     //   230: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   233: athrow
/*    */     //   234: iinc #11, 1
/*    */     //   237: goto -> 169
/*    */     //   240: iload #10
/*    */     //   242: ifeq -> 370
/*    */     //   245: getstatic com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperatorTuple$1.$SwitchMap$com$hypixel$hytale$server$npc$util$expression$ValueType : [I
/*    */     //   248: aload #9
/*    */     //   250: invokevirtual ordinal : ()I
/*    */     //   253: iaload
/*    */     //   254: tableswitch default -> 334, 4 -> 280, 5 -> 298, 6 -> 316
/*    */     //   280: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandNumberArray
/*    */     //   283: dup
/*    */     //   284: aload_3
/*    */     //   285: iload #4
/*    */     //   287: aload #5
/*    */     //   289: iload #7
/*    */     //   291: iload_1
/*    */     //   292: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILjava/util/Stack;II)V
/*    */     //   295: goto -> 352
/*    */     //   298: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandStringArray
/*    */     //   301: dup
/*    */     //   302: aload_3
/*    */     //   303: iload #4
/*    */     //   305: aload #5
/*    */     //   307: iload #7
/*    */     //   309: iload_1
/*    */     //   310: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILjava/util/Stack;II)V
/*    */     //   313: goto -> 352
/*    */     //   316: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperandBooleanArray
/*    */     //   319: dup
/*    */     //   320: aload_3
/*    */     //   321: iload #4
/*    */     //   323: aload #5
/*    */     //   325: iload #7
/*    */     //   327: iload_1
/*    */     //   328: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;ILjava/util/Stack;II)V
/*    */     //   331: goto -> 352
/*    */     //   334: new java/lang/IllegalStateException
/*    */     //   337: dup
/*    */     //   338: aload #9
/*    */     //   340: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   343: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   348: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   351: athrow
/*    */     //   352: astore #11
/*    */     //   354: aload #5
/*    */     //   356: iload #7
/*    */     //   358: invokevirtual setSize : (I)V
/*    */     //   361: aload #5
/*    */     //   363: aload #11
/*    */     //   365: invokevirtual push : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   368: pop
/*    */     //   369: return
/*    */     //   370: new com/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperatorTuple
/*    */     //   373: dup
/*    */     //   374: aload #9
/*    */     //   376: aload_3
/*    */     //   377: iload #4
/*    */     //   379: invokespecial <init> : (Lcom/hypixel/hytale/server/npc/util/expression/ValueType;Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;I)V
/*    */     //   382: astore #11
/*    */     //   384: iload #7
/*    */     //   386: istore #12
/*    */     //   388: iload #12
/*    */     //   390: iload #6
/*    */     //   392: if_icmpge -> 416
/*    */     //   395: aload #11
/*    */     //   397: aload #5
/*    */     //   399: iload #12
/*    */     //   401: invokevirtual get : (I)Ljava/lang/Object;
/*    */     //   404: checkcast com/hypixel/hytale/server/npc/util/expression/compile/ast/AST
/*    */     //   407: invokevirtual addArgument : (Lcom/hypixel/hytale/server/npc/util/expression/compile/ast/AST;)V
/*    */     //   410: iinc #12, 1
/*    */     //   413: goto -> 388
/*    */     //   416: aload #5
/*    */     //   418: iload #7
/*    */     //   420: invokevirtual setSize : (I)V
/*    */     //   423: aload #5
/*    */     //   425: aload #11
/*    */     //   427: invokevirtual push : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   430: pop
/*    */     //   431: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     //   #31	-> 5
/*    */     //   #32	-> 12
/*    */     //   #35	-> 29
/*    */     //   #38	-> 35
/*    */     //   #39	-> 41
/*    */     //   #40	-> 45
/*    */     //   #41	-> 61
/*    */     //   #44	-> 62
/*    */     //   #45	-> 69
/*    */     //   #46	-> 75
/*    */     //   #47	-> 90
/*    */     //   #48	-> 124
/*    */     //   #49	-> 130
/*    */     //   #50	-> 136
/*    */     //   #51	-> 142
/*    */     //   #47	-> 160
/*    */     //   #56	-> 162
/*    */     //   #57	-> 165
/*    */     //   #58	-> 176
/*    */     //   #59	-> 188
/*    */     //   #61	-> 198
/*    */     //   #62	-> 208
/*    */     //   #57	-> 234
/*    */     //   #67	-> 240
/*    */     //   #69	-> 245
/*    */     //   #70	-> 280
/*    */     //   #71	-> 298
/*    */     //   #72	-> 316
/*    */     //   #73	-> 334
/*    */     //   #69	-> 352
/*    */     //   #76	-> 354
/*    */     //   #77	-> 361
/*    */     //   #78	-> 369
/*    */     //   #82	-> 370
/*    */     //   #84	-> 384
/*    */     //   #85	-> 395
/*    */     //   #84	-> 410
/*    */     //   #87	-> 416
/*    */     //   #88	-> 423
/*    */     //   #89	-> 431
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   188	46	12	ast	Lcom/hypixel/hytale/server/npc/util/expression/compile/ast/AST;
/*    */     //   169	71	11	i	I
/*    */     //   354	16	11	item	Lcom/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperand;
/*    */     //   388	28	12	i	I
/*    */     //   0	432	0	openingToken	Lcom/hypixel/hytale/server/npc/util/expression/compile/Parser$ParsedToken;
/*    */     //   0	432	1	argumentCount	I
/*    */     //   0	432	2	compileContext	Lcom/hypixel/hytale/server/npc/util/expression/compile/CompileContext;
/*    */     //   5	427	3	token	Lcom/hypixel/hytale/server/npc/util/expression/compile/Token;
/*    */     //   35	397	4	tokenPosition	I
/*    */     //   41	391	5	operandStack	Ljava/util/Stack;
/*    */     //   69	363	6	len	I
/*    */     //   75	357	7	firstArgument	I
/*    */     //   90	342	8	argumentType	Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   162	270	9	arrayType	Lcom/hypixel/hytale/server/npc/util/expression/ValueType;
/*    */     //   165	267	10	isConstant	Z
/*    */     //   384	48	11	ast	Lcom/hypixel/hytale/server/npc/util/expression/compile/ast/ASTOperatorTuple;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   41	391	5	operandStack	Ljava/util/Stack<Lcom/hypixel/hytale/server/npc/util/expression/compile/ast/AST;>;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperatorTuple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */