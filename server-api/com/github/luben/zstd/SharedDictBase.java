/*   */ package com.github.luben.zstd;
/*   */ 
/*   */ abstract class SharedDictBase
/*   */   extends AutoCloseBase
/*   */ {
/*   */   protected void finalize() {
/* 7 */     close();
/*   */   }
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\SharedDictBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */