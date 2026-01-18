/*    */ package com.hypixel.hytale.builtin.buildertools;
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
/*    */ public final class FluidChange
/*    */   extends Record
/*    */ {
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final int z;
/*    */   private final int fluidId;
/*    */   private final byte fluidLevel;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #55	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #55	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #55	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/PrototypePlayerBuilderToolSettings$FluidChange;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public FluidChange(int x, int y, int z, int fluidId, byte fluidLevel) {
/* 55 */     this.x = x; this.y = y; this.z = z; this.fluidId = fluidId; this.fluidLevel = fluidLevel; } public int x() { return this.x; } public int y() { return this.y; } public int z() { return this.z; } public int fluidId() { return this.fluidId; } public byte fluidLevel() { return this.fluidLevel; }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\PrototypePlayerBuilderToolSettings$FluidChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */