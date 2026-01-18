/*    */ package com.hypixel.hytale.builtin.beds.sleep.components;
/*    */ 
/*    */ import java.time.Instant;
/*    */ 
/*    */ public final class Slumber extends Record implements PlayerSleep {
/*    */   private final Instant gameTimeStart;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #20	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$Slumber;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/* 20 */   public Slumber(Instant gameTimeStart) { this.gameTimeStart = gameTimeStart; } public Instant gameTimeStart() { return this.gameTimeStart; }
/*    */    public static PlayerSomnolence createComponent(WorldTimeResource worldTimeResource) {
/* 22 */     Instant now = worldTimeResource.getGameTime();
/* 23 */     Slumber state = new Slumber(now);
/* 24 */     return new PlayerSomnolence(state);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\components\PlayerSleep$Slumber.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */