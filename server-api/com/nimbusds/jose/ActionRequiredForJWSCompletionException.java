/*    */ package com.nimbusds.jose;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ public class ActionRequiredForJWSCompletionException
/*    */   extends JOSEException
/*    */ {
/*    */   private final JWSSignerOption option;
/*    */   private final CompletableJWSObjectSigning completableSigning;
/*    */   
/*    */   public ActionRequiredForJWSCompletionException(String message, JWSSignerOption option, CompletableJWSObjectSigning completableSigning) {
/* 52 */     super(message);
/* 53 */     this.option = Objects.<JWSSignerOption>requireNonNull(option);
/* 54 */     this.completableSigning = Objects.<CompletableJWSObjectSigning>requireNonNull(completableSigning);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWSSignerOption getTriggeringOption() {
/* 64 */     return this.option;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CompletableJWSObjectSigning getCompletableJWSObjectSigning() {
/* 75 */     return this.completableSigning;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\ActionRequiredForJWSCompletionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */