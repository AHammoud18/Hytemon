/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ObjectFloatPair<K>
/*     */   extends Pair<K, Float>
/*     */ {
/*     */   @Deprecated
/*     */   default Float right() {
/*  38 */     return Float.valueOf(rightFloat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ObjectFloatPair<K> right(float r) {
/*  49 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ObjectFloatPair<K> right(Float l) {
/*  60 */     return right(l.floatValue());
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
/*     */   default float secondFloat() {
/*  72 */     return rightFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float second() {
/*  83 */     return Float.valueOf(secondFloat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ObjectFloatPair<K> second(float r) {
/*  94 */     return right(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ObjectFloatPair<K> second(Float l) {
/* 105 */     return second(l.floatValue());
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
/*     */   default float valueFloat() {
/* 117 */     return rightFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float value() {
/* 128 */     return Float.valueOf(valueFloat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ObjectFloatPair<K> value(float r) {
/* 139 */     return right(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ObjectFloatPair<K> value(Float l) {
/* 150 */     return value(l.floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K> ObjectFloatPair<K> of(K left, float right) {
/* 161 */     return new ObjectFloatImmutablePair<>(left, right);
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
/*     */   static <K> Comparator<ObjectFloatPair<K>> lexComparator() {
/* 176 */     return (x, y) -> {
/*     */         int t = ((Comparable<Object>)x.left()).compareTo(y.left());
/*     */         return (t != 0) ? t : Float.compare(x.rightFloat(), y.rightFloat());
/*     */       };
/*     */   }
/*     */   
/*     */   float rightFloat();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectFloatPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */