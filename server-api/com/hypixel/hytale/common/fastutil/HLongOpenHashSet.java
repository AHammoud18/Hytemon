/*    */ package com.hypixel.hytale.common.fastutil;
/*    */ 
/*    */ import com.hypixel.hytale.function.predicate.LongTriIntBiObjPredicate;
/*    */ import it.unimi.dsi.fastutil.HashCommon;
/*    */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HLongOpenHashSet
/*    */   extends LongOpenHashSet
/*    */   implements HLongSet
/*    */ {
/*    */   public <T, V> void removeIf(@Nonnull LongTriIntBiObjPredicate<T, V> predicate, int ia, int ib, int ic, T obj1, V obj2) {
/* 18 */     int pos = this.n;
/* 19 */     int last = -1;
/* 20 */     int c = this.size;
/* 21 */     boolean mustReturnNull = this.containsNull;
/* 22 */     LongArrayList wrapped = null;
/*    */     
/* 24 */     while (c != 0) {
/* 25 */       long value = 0L;
/*    */       
/* 27 */       c--;
/* 28 */       if (mustReturnNull) {
/* 29 */         mustReturnNull = false;
/* 30 */         last = this.n;
/* 31 */         value = this.key[this.n];
/*    */       } else {
/* 33 */         long[] key1 = this.key;
/*    */         
/* 35 */         while (--pos >= 0) {
/* 36 */           if (key1[pos] != 0L) {
/* 37 */             value = key1[last = pos];
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 42 */         if (pos < 0) {
/* 43 */           last = Integer.MIN_VALUE;
/* 44 */           value = wrapped.getLong(-pos - 1);
/*    */         } 
/*    */       } 
/*    */       
/* 48 */       if (predicate.test(value, ia, ib, ic, obj1, obj2)) {
/* 49 */         if (last == this.n) {
/* 50 */           this.containsNull = false;
/* 51 */           this.key[this.n] = 0L;
/*    */           
/* 53 */           this.size--;
/* 54 */           last = -1; continue;
/* 55 */         }  if (pos >= 0) {
/* 56 */           int pos1 = last;
/* 57 */           long[] key1 = this.key;
/*    */ 
/*    */           
/*    */           while (true) {
/* 61 */             int last1 = pos1;
/* 62 */             pos1 = pos1 + 1 & this.mask;
/*    */             
/*    */             label47: while (true) {
/*    */               long curr;
/* 66 */               if ((curr = key1[pos1]) == 0L) {
/* 67 */                 key1[last1] = 0L;
/*    */                 
/*    */                 break;
/*    */               } 
/* 71 */               int slot = (int)HashCommon.mix(curr) & this.mask;
/* 72 */               if ((last1 <= pos1) ? (
/* 73 */                 last1 >= slot || slot > pos1) : (
/*    */ 
/*    */                 
/* 76 */                 last1 >= slot && slot > pos1)) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */                 
/* 83 */                 if (pos1 < last1) {
/* 84 */                   if (wrapped == null) {
/* 85 */                     wrapped = new LongArrayList(2);
/*    */                   }
/*    */                   
/* 88 */                   wrapped.add(key1[pos1]);
/*    */                   break label47;
/*    */                 } 
/* 91 */                 key1[last1] = curr; continue;
/*    */               }  pos1 = pos1 + 1 & this.mask;
/*    */             }  break;
/* 94 */           }  this.size--;
/* 95 */           last = -1; continue;
/*    */         } 
/* 97 */         remove(wrapped.getLong(-pos - 1));
/* 98 */         last = -1;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\fastutil\HLongOpenHashSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */