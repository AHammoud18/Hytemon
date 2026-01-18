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
/*     */ public interface ObjectBooleanPair<K>
/*     */   extends Pair<K, Boolean>
/*     */ {
/*     */   @Deprecated
/*     */   default Boolean right() {
/*  38 */     return Boolean.valueOf(rightBoolean());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ObjectBooleanPair<K> right(boolean r) {
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
/*     */   default ObjectBooleanPair<K> right(Boolean l) {
/*  60 */     return right(l.booleanValue());
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
/*     */   default boolean secondBoolean() {
/*  72 */     return rightBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean second() {
/*  83 */     return Boolean.valueOf(secondBoolean());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ObjectBooleanPair<K> second(boolean r) {
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
/*     */   default ObjectBooleanPair<K> second(Boolean l) {
/* 105 */     return second(l.booleanValue());
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
/*     */   default boolean valueBoolean() {
/* 117 */     return rightBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean value() {
/* 128 */     return Boolean.valueOf(valueBoolean());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ObjectBooleanPair<K> value(boolean r) {
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
/*     */   default ObjectBooleanPair<K> value(Boolean l) {
/* 150 */     return value(l.booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K> ObjectBooleanPair<K> of(K left, boolean right) {
/* 161 */     return new ObjectBooleanImmutablePair<>(left, right);
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
/*     */   static <K> Comparator<ObjectBooleanPair<K>> lexComparator() {
/* 176 */     return (x, y) -> {
/*     */         int t = ((Comparable<Object>)x.left()).compareTo(y.left());
/*     */         return (t != 0) ? t : Boolean.compare(x.rightBoolean(), y.rightBoolean());
/*     */       };
/*     */   }
/*     */   
/*     */   boolean rightBoolean();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectBooleanPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */