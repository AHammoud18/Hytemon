/*   */ package com.hypixel.hytale.server.core.util;
/*   */ 
/*   */ public class ProcessUtil {
/*   */   public static boolean isProcessRunning(int pid) {
/* 5 */     return ProcessHandle.of(pid).isPresent();
/*   */   }
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\ProcessUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */