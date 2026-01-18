/*    */ package com.hypixel.hytale.server.core.util;
/*    */ 
/*    */ import java.util.UUID;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PlayerTextData
/*    */   extends Record
/*    */ {
/*    */   @Nonnull
/*    */   private final UUID uuid;
/*    */   @Nullable
/*    */   private final String movementStates;
/*    */   @Nullable
/*    */   private final String movementManager;
/*    */   @Nullable
/*    */   private final String cameraManager;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #60	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #60	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #60	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public PlayerTextData(@Nonnull UUID uuid, @Nullable String movementStates, @Nullable String movementManager, @Nullable String cameraManager) {
/* 60 */     this.uuid = uuid; this.movementStates = movementStates; this.movementManager = movementManager; this.cameraManager = cameraManager; } @Nonnull public UUID uuid() { return this.uuid; } @Nullable public String movementStates() { return this.movementStates; } @Nullable public String movementManager() { return this.movementManager; } @Nullable public String cameraManager() { return this.cameraManager; }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\DumpUtil$PlayerTextData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */