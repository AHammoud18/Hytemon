/*    */ package com.hypixel.hytale.builtin.beds.sleep.components;
/*    */ public final class MorningWakeUp extends Record implements PlayerSleep { private final Instant gameTimeStart;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp;
/*    */   }
/*    */   
/* 12 */   public MorningWakeUp(Instant gameTimeStart) { this.gameTimeStart = gameTimeStart; } public Instant gameTimeStart() { return this.gameTimeStart; }
/*    */   public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$MorningWakeUp;
/*    */     //   0	8	1	o	Ljava/lang/Object; } public static PlayerSomnolence createComponent(WorldTimeResource worldTimeResource) {
/* 14 */     Instant now = worldTimeResource.getGameTime();
/* 15 */     MorningWakeUp state = new MorningWakeUp(now);
/* 16 */     return new PlayerSomnolence(state);
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\components\PlayerSleep$MorningWakeUp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */