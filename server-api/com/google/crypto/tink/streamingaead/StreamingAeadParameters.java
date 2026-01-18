/*    */ package com.google.crypto.tink.streamingaead;
/*    */ 
/*    */ import com.google.crypto.tink.Parameters;
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
/*    */ public abstract class StreamingAeadParameters
/*    */   extends Parameters
/*    */ {
/*    */   public final boolean hasIdRequirement() {
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\StreamingAeadParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */