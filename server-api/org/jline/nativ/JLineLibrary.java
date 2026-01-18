/*    */ package org.jline.nativ;
/*    */ 
/*    */ import java.io.FileDescriptor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JLineLibrary
/*    */ {
/*    */   public static native FileDescriptor newFileDescriptor(int paramInt);
/*    */   
/*    */   public static native ProcessBuilder.Redirect newRedirectPipe(FileDescriptor paramFileDescriptor);
/*    */   
/*    */   static {
/* 36 */     JLineNativeLoader.initialize();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\nativ\JLineLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */