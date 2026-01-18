/*    */ package com.hypixel.hytale.builtin.fluid;
/*    */ public final class FluidState extends Record { private final int fluidLevel; private final byte verticalFill;
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/fluid/FluidState;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/fluid/FluidState;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/fluid/FluidState;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/fluid/FluidState;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/* 12 */   public FluidState(int fluidLevel, byte verticalFill) { this.fluidLevel = fluidLevel; this.verticalFill = verticalFill; } public int fluidLevel() { return this.fluidLevel; } public byte verticalFill() { return this.verticalFill; }
/*    */   
/* 14 */   public static int SOURCE_LEVEL = 0;
/*    */   public static final int FULL_LEVEL = 8;
/* 16 */   public static final FluidState[] FLUID_STATES = generateFluidStates(8);
/*    */   
/*    */   public FluidState(int fluidLevel, int verticalFill) {
/* 19 */     this(fluidLevel, (byte)verticalFill);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static FluidState[] generateFluidStates(int maxLevel) {
/* 24 */     ObjectArrayList<FluidState> objectArrayList = new ObjectArrayList();
/* 25 */     objectArrayList.add(new FluidState(SOURCE_LEVEL, maxLevel));
/*    */     
/* 27 */     for (int i = 1; i <= maxLevel; i++) {
/* 28 */       objectArrayList.add(new FluidState(i, i));
/*    */     }
/*    */     
/* 31 */     return (FluidState[])objectArrayList.toArray(x$0 -> new FluidState[x$0]);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 37 */     return "FluidState{fluidLevel=" + this.fluidLevel + ", verticalFill=" + this.verticalFill + "}";
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */