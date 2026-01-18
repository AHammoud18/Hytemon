/*    */ package com.hypixel.hytale.protocol.io;
/*    */ public final class ValidationResult extends Record { private final boolean isValid; @Nullable
/*    */   private final String error;
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/protocol/io/ValidationResult;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/protocol/io/ValidationResult;
/*    */   }
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/protocol/io/ValidationResult;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/protocol/io/ValidationResult;
/*    */   }
/* 10 */   public ValidationResult(boolean isValid, @Nullable String error) { this.isValid = isValid; this.error = error; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/protocol/io/ValidationResult;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/protocol/io/ValidationResult;
/* 10 */     //   0	8	1	o	Ljava/lang/Object; } public boolean isValid() { return this.isValid; } @Nullable public String error() { return this.error; }
/*    */ 
/*    */   
/* 13 */   public static final ValidationResult OK = new ValidationResult(true, null);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static ValidationResult error(@Nonnull String message) {
/* 20 */     return new ValidationResult(false, message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void throwIfInvalid() {
/* 27 */     if (!this.isValid)
/* 28 */       throw new ProtocolException((this.error != null) ? this.error : "Validation failed"); 
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\ValidationResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */