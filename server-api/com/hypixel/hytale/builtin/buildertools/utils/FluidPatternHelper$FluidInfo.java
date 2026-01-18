/*    */ package com.hypixel.hytale.builtin.buildertools.utils;
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
/*    */ public final class FluidInfo
/*    */   extends Record
/*    */ {
/*    */   private final int fluidId;
/*    */   private final byte fluidLevel;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/utils/FluidPatternHelper$FluidInfo;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public FluidInfo(int fluidId, byte fluidLevel) {
/* 31 */     this.fluidId = fluidId; this.fluidLevel = fluidLevel; } public int fluidId() { return this.fluidId; } public byte fluidLevel() { return this.fluidLevel; }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertool\\utils\FluidPatternHelper$FluidInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */