/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Spliterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface CharBigList
/*     */   extends BigList<Character>, CharCollection, Comparable<BigList<? extends Character>>
/*     */ {
/*     */   default CharSpliterator spliterator() {
/* 110 */     return CharSpliterators.asSpliterator(iterator(), size64(), 16720);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void getElements(long from, char[] a, int offset, int length) {
/* 143 */     getElements(from, new char[][] { a }, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void setElements(char[][] a) {
/* 179 */     setElements(0L, a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void setElements(long index, char[][] a) {
/* 190 */     setElements(index, a, 0L, BigArrays.length(a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void setElements(long index, char[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: lload_1
/*     */     //   1: lconst_0
/*     */     //   2: lcmp
/*     */     //   3: ifge -> 38
/*     */     //   6: new java/lang/IndexOutOfBoundsException
/*     */     //   9: dup
/*     */     //   10: new java/lang/StringBuilder
/*     */     //   13: dup
/*     */     //   14: invokespecial <init> : ()V
/*     */     //   17: ldc 'Index ('
/*     */     //   19: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   22: lload_1
/*     */     //   23: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   26: ldc ') is negative'
/*     */     //   28: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   34: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   37: athrow
/*     */     //   38: lload_1
/*     */     //   39: aload_0
/*     */     //   40: invokeinterface size64 : ()J
/*     */     //   45: lcmp
/*     */     //   46: ifle -> 95
/*     */     //   49: new java/lang/IndexOutOfBoundsException
/*     */     //   52: dup
/*     */     //   53: new java/lang/StringBuilder
/*     */     //   56: dup
/*     */     //   57: invokespecial <init> : ()V
/*     */     //   60: ldc 'Index ('
/*     */     //   62: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   65: lload_1
/*     */     //   66: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   69: ldc ') is greater than list size ('
/*     */     //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   74: aload_0
/*     */     //   75: invokeinterface size64 : ()J
/*     */     //   80: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   83: ldc ')'
/*     */     //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   88: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   91: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   94: athrow
/*     */     //   95: aload_3
/*     */     //   96: lload #4
/*     */     //   98: lload #6
/*     */     //   100: invokestatic ensureOffsetLength : ([[CJJ)V
/*     */     //   103: lload_1
/*     */     //   104: lload #6
/*     */     //   106: ladd
/*     */     //   107: aload_0
/*     */     //   108: invokeinterface size64 : ()J
/*     */     //   113: lcmp
/*     */     //   114: ifle -> 166
/*     */     //   117: new java/lang/IndexOutOfBoundsException
/*     */     //   120: dup
/*     */     //   121: new java/lang/StringBuilder
/*     */     //   124: dup
/*     */     //   125: invokespecial <init> : ()V
/*     */     //   128: ldc 'End index ('
/*     */     //   130: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   133: lload_1
/*     */     //   134: lload #6
/*     */     //   136: ladd
/*     */     //   137: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   140: ldc ') is greater than list size ('
/*     */     //   142: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   145: aload_0
/*     */     //   146: invokeinterface size64 : ()J
/*     */     //   151: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   154: ldc ')'
/*     */     //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   159: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   162: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   165: athrow
/*     */     //   166: aload_0
/*     */     //   167: lload_1
/*     */     //   168: invokeinterface listIterator : (J)Lit/unimi/dsi/fastutil/chars/CharBigListIterator;
/*     */     //   173: astore #8
/*     */     //   175: lconst_0
/*     */     //   176: lstore #9
/*     */     //   178: lload #9
/*     */     //   180: lload #6
/*     */     //   182: lcmp
/*     */     //   183: ifge -> 218
/*     */     //   186: aload #8
/*     */     //   188: invokeinterface nextChar : ()C
/*     */     //   193: pop
/*     */     //   194: aload #8
/*     */     //   196: aload_3
/*     */     //   197: lload #4
/*     */     //   199: lload #9
/*     */     //   201: dup2
/*     */     //   202: lconst_1
/*     */     //   203: ladd
/*     */     //   204: lstore #9
/*     */     //   206: ladd
/*     */     //   207: invokestatic get : ([[CJ)C
/*     */     //   210: invokeinterface set : (C)V
/*     */     //   215: goto -> 178
/*     */     //   218: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #218	-> 0
/*     */     //   #219	-> 38
/*     */     //   #220	-> 95
/*     */     //   #221	-> 103
/*     */     //   #222	-> 166
/*     */     //   #223	-> 175
/*     */     //   #224	-> 178
/*     */     //   #225	-> 186
/*     */     //   #226	-> 194
/*     */     //   #228	-> 218
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	219	0	this	Lit/unimi/dsi/fastutil/chars/CharBigList;
/*     */     //   0	219	1	index	J
/*     */     //   0	219	3	a	[[C
/*     */     //   0	219	4	offset	J
/*     */     //   0	219	6	length	J
/*     */     //   175	44	8	iter	Lit/unimi/dsi/fastutil/chars/CharBigListIterator;
/*     */     //   178	41	9	i	J
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean addAll(long index, CharBigList l) {
/* 349 */     return addAll(index, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean addAll(CharBigList l) {
/* 361 */     return addAll(size64(), l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean addAll(long index, CharList l) {
/* 375 */     return addAll(index, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean addAll(CharList l) {
/* 387 */     return addAll(size64(), l);
/*     */   }
/*     */   
/*     */   CharBigListIterator iterator();
/*     */   
/*     */   CharBigListIterator listIterator();
/*     */   
/*     */   CharBigListIterator listIterator(long paramLong);
/*     */   
/*     */   CharBigList subList(long paramLong1, long paramLong2);
/*     */   
/*     */   void getElements(long paramLong1, char[][] paramArrayOfchar, long paramLong2, long paramLong3);
/*     */   
/*     */   void removeElements(long paramLong1, long paramLong2);
/*     */   
/*     */   void addElements(long paramLong, char[][] paramArrayOfchar);
/*     */   
/*     */   void addElements(long paramLong1, char[][] paramArrayOfchar, long paramLong2, long paramLong3);
/*     */   
/*     */   void add(long paramLong, char paramChar);
/*     */   
/*     */   boolean addAll(long paramLong, CharCollection paramCharCollection);
/*     */   
/*     */   char getChar(long paramLong);
/*     */   
/*     */   char removeChar(long paramLong);
/*     */   
/*     */   char set(long paramLong, char paramChar);
/*     */   
/*     */   long indexOf(char paramChar);
/*     */   
/*     */   long lastIndexOf(char paramChar);
/*     */   
/*     */   @Deprecated
/*     */   void add(long paramLong, Character paramCharacter);
/*     */   
/*     */   @Deprecated
/*     */   Character get(long paramLong);
/*     */   
/*     */   @Deprecated
/*     */   long indexOf(Object paramObject);
/*     */   
/*     */   @Deprecated
/*     */   long lastIndexOf(Object paramObject);
/*     */   
/*     */   @Deprecated
/*     */   Character remove(long paramLong);
/*     */   
/*     */   @Deprecated
/*     */   Character set(long paramLong, Character paramCharacter);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */