/*    */ package com.hypixel.hytale.common.collection;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Flags<T extends Flag>
/*    */ {
/*    */   private int flags;
/*    */   
/*    */   public Flags(@Nonnull T flag) {
/* 16 */     set(flag, true);
/*    */   }
/*    */   
/*    */   @SafeVarargs
/*    */   public Flags(@Nonnull T... flags) {
/* 21 */     for (T flag : flags) {
/* 22 */       set(flag, true);
/*    */     }
/*    */   }
/*    */   
/*    */   public Flags(int flags) {
/* 27 */     this.flags = flags;
/*    */   }
/*    */   
/*    */   public int getFlags() {
/* 31 */     return this.flags;
/*    */   }
/*    */   
/*    */   public boolean is(@Nonnull T flag) {
/* 35 */     return ((this.flags & flag.mask()) != 0);
/*    */   }
/*    */   
/*    */   public boolean not(@Nonnull T flag) {
/* 39 */     return ((this.flags & flag.mask()) == 0);
/*    */   }
/*    */   
/*    */   public boolean set(@Nonnull T flag, boolean value) {
/* 43 */     if (value) {
/* 44 */       return (this.flags != (this.flags |= flag.mask()));
/*    */     }
/* 46 */     return (this.flags != (this.flags &= flag.mask() ^ 0xFFFFFFFF));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean toggle(@Nonnull T flag) {
/* 51 */     return (((this.flags ^= flag.mask()) & flag.mask()) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 58 */     return StringUtil.toPaddedBinaryString(this.flags);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\collection\Flags.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */