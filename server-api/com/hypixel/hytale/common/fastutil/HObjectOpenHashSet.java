/*    */ package com.hypixel.hytale.common.fastutil;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HObjectOpenHashSet<K>
/*    */   extends ObjectOpenHashSet<K>
/*    */ {
/*    */   @Nullable
/*    */   public K first() {
/* 19 */     if (this.containsNull) return (K)this.key[this.n]; 
/* 20 */     K[] key = (K[])this.key;
/* 21 */     for (int pos = this.n; pos-- != 0;) {
/* 22 */       if (key[pos] != null) {
/* 23 */         return key[pos];
/*    */       }
/*    */     } 
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void pushInto(@Nonnull Collection<K> c) {
/* 36 */     if (this.containsNull) c.add((K)this.key[this.n]); 
/* 37 */     K[] key = (K[])this.key;
/* 38 */     for (int pos = this.n; pos-- != 0;) {
/* 39 */       if (key[pos] != null)
/* 40 */         c.add(key[pos]); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\fastutil\HObjectOpenHashSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */