/*    */ package com.hypixel.hytale.server.core.command.system;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandValidationResults
/*    */   extends ValidationResults
/*    */ {
/*    */   public CommandValidationResults(@Nonnull ExtraInfo extraInfo) {
/* 17 */     super(extraInfo);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processResults(@Nonnull ParseResult parseResult) {
/* 25 */     _processValidationResults();
/*    */     
/* 27 */     if (this.validatorExceptions == null || this.validatorExceptions.isEmpty())
/*    */       return; 
/* 29 */     StringBuilder sb = new StringBuilder();
/* 30 */     boolean failed = false;
/* 31 */     for (ValidationResults.ValidatorResultsHolder holder : this.validatorExceptions) {
/* 32 */       for (ValidationResults.ValidationResult result : holder.results()) {
/* 33 */         failed |= result.appendResult(sb);
/*    */       }
/*    */     } 
/*    */     
/* 37 */     if (failed) {
/*    */ 
/*    */       
/* 40 */       parseResult.fail(Message.raw(sb.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     this.validatorExceptions.clear();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\CommandValidationResults.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */