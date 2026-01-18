/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Objects
/*    */ {
/*    */   static void checkFromIndexSize(int paramInt1, int paramInt2, int paramInt3) {
/*  9 */     if ((paramInt3 | paramInt1 | paramInt2) < 0 || paramInt2 > paramInt3 - paramInt1)
/* 10 */       throw new IndexOutOfBoundsException(String.format("Range [%s, %<s + %s) out of bounds for length %s", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) })); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\Objects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */