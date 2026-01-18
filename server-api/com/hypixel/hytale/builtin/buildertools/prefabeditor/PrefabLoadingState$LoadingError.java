/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public final class LoadingError
/*    */   extends Record
/*    */ {
/*    */   @Nonnull
/*    */   private final String translationKey;
/*    */   @Nullable
/*    */   private final String details;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/prefabeditor/PrefabLoadingState$LoadingError;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #50	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/prefabeditor/PrefabLoadingState$LoadingError;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/prefabeditor/PrefabLoadingState$LoadingError;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #50	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/prefabeditor/PrefabLoadingState$LoadingError;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/prefabeditor/PrefabLoadingState$LoadingError;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #50	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/prefabeditor/PrefabLoadingState$LoadingError;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public LoadingError(@Nonnull String translationKey, @Nullable String details) {
/* 50 */     this.translationKey = translationKey; this.details = details; } @Nonnull public String translationKey() { return this.translationKey; } @Nullable public String details() { return this.details; }
/*    */    public LoadingError(@Nonnull String translationKey) {
/* 52 */     this(translationKey, null);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Message toMessage() {
/* 57 */     Message message = Message.translation(this.translationKey);
/* 58 */     if (this.details != null) {
/* 59 */       message = message.param("details", this.details);
/*    */     }
/* 61 */     return message;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabLoadingState$LoadingError.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */