/*    */ package com.google.crypto.tink.keyderivation;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class KeyDerivationParameters
/*    */   extends Parameters
/*    */ {
/*    */   public abstract Parameters getDerivedKeyParameters();
/*    */   
/*    */   public boolean hasIdRequirement() {
/* 34 */     return getDerivedKeyParameters().hasIdRequirement();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\KeyDerivationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */