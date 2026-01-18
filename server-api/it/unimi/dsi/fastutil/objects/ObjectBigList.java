/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ public interface ObjectBigList<K>
/*     */   extends BigList<K>, ObjectCollection<K>, Comparable<BigList<? extends K>>
/*     */ {
/*     */   default ObjectSpliterator<K> spliterator() {
/* 110 */     return ObjectSpliterators.asSpliterator(iterator(), size64(), 16464);
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
/*     */   default void getElements(long from, Object[] a, int offset, int length) {
/* 143 */     getElements(from, new Object[][] { a }, offset, length);
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
/*     */   default void setElements(K[][] a) {
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
/*     */   default void setElements(long index, K[][] a) {
/* 190 */     setElements(index, a, 0L, BigArrays.length((Object[][])a));
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
/*     */   default void setElements(long index, K[][] a, long offset, long length) {
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
/*     */     //   100: invokestatic ensureOffsetLength : ([[Ljava/lang/Object;JJ)V
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
/*     */     //   168: invokeinterface listIterator : (J)Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
/*     */     //   173: astore #8
/*     */     //   175: lconst_0
/*     */     //   176: lstore #9
/*     */     //   178: lload #9
/*     */     //   180: lload #6
/*     */     //   182: lcmp
/*     */     //   183: ifge -> 218
/*     */     //   186: aload #8
/*     */     //   188: invokeinterface next : ()Ljava/lang/Object;
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
/*     */     //   207: invokestatic get : ([[Ljava/lang/Object;J)Ljava/lang/Object;
/*     */     //   210: invokeinterface set : (Ljava/lang/Object;)V
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
/*     */     //   0	219	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigList;
/*     */     //   0	219	1	index	J
/*     */     //   0	219	3	a	[[Ljava/lang/Object;
/*     */     //   0	219	4	offset	J
/*     */     //   0	219	6	length	J
/*     */     //   175	44	8	iter	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
/*     */     //   178	41	9	i	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	219	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigList<TK;>;
/*     */     //   0	219	3	a	[[TK;
/*     */     //   175	44	8	iter	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator<TK;>;
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
/*     */   default boolean addAll(long index, ObjectBigList<? extends K> l) {
/* 241 */     return addAll(index, l);
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
/*     */   default boolean addAll(ObjectBigList<? extends K> l) {
/* 253 */     return addAll(size64(), l);
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
/*     */   default boolean addAll(long index, ObjectList<? extends K> l) {
/* 267 */     return addAll(index, l);
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
/*     */   default boolean addAll(ObjectList<? extends K> l) {
/* 279 */     return addAll(size64(), l);
/*     */   }
/*     */   
/*     */   ObjectBigListIterator<K> iterator();
/*     */   
/*     */   ObjectBigListIterator<K> listIterator();
/*     */   
/*     */   ObjectBigListIterator<K> listIterator(long paramLong);
/*     */   
/*     */   ObjectBigList<K> subList(long paramLong1, long paramLong2);
/*     */   
/*     */   void getElements(long paramLong1, Object[][] paramArrayOfObject, long paramLong2, long paramLong3);
/*     */   
/*     */   void removeElements(long paramLong1, long paramLong2);
/*     */   
/*     */   void addElements(long paramLong, K[][] paramArrayOfK);
/*     */   
/*     */   void addElements(long paramLong1, K[][] paramArrayOfK, long paramLong2, long paramLong3);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */