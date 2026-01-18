/*    */ package com.hypixel.hytale.codec.validation;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.exception.CodecValidationException;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ThrowingValidationResults
/*    */   extends ValidationResults {
/*    */   public ThrowingValidationResults(ExtraInfo extraInfo) {
/* 12 */     super(extraInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(@Nonnull ValidationResults.ValidationResult result) {
/* 17 */     StringBuilder sb = new StringBuilder("Failed to validate asset!\n");
/* 18 */     this.extraInfo.appendDetailsTo(sb);
/*    */     
/* 20 */     sb.append("Key: ").append(this.extraInfo.peekKey()).append("\n");
/*    */     
/* 22 */     sb.append("Results:\n");
/* 23 */     boolean failed = result.appendResult(sb);
/*    */     
/* 25 */     if (failed) throw new CodecValidationException(sb.toString()); 
/* 26 */     HytaleLogger.getLogger().at(Level.WARNING).log(sb.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 32 */     return "ThrowingValidationResults{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\validation\ThrowingValidationResults.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */