/*    */ package org.fusesource.jansi;
/*    */ 
/*    */ import org.fusesource.jansi.internal.Kernel32;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class WindowsSupport
/*    */ {
/*    */   @Deprecated
/*    */   public static String getLastErrorMessage() {
/* 28 */     return Kernel32.getLastErrorMessage();
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static String getErrorMessage(int errorCode) {
/* 33 */     return Kernel32.getErrorMessage(errorCode);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\WindowsSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */