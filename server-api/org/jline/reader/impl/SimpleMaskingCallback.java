/*    */ package org.jline.reader.impl;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import org.jline.reader.MaskingCallback;
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
/*    */ public final class SimpleMaskingCallback
/*    */   implements MaskingCallback
/*    */ {
/*    */   private final Character mask;
/*    */   
/*    */   public SimpleMaskingCallback(Character mask) {
/* 23 */     this.mask = Objects.<Character>requireNonNull(mask, "mask must be a non null character");
/*    */   }
/*    */ 
/*    */   
/*    */   public String display(String line) {
/* 28 */     if (this.mask.equals(Character.valueOf(false))) {
/* 29 */       return "";
/*    */     }
/* 31 */     StringBuilder sb = new StringBuilder(line.length());
/* 32 */     for (int i = line.length(); i-- > 0;) {
/* 33 */       sb.append(this.mask.charValue());
/*    */     }
/* 35 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String history(String line) {
/* 41 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\SimpleMaskingCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */