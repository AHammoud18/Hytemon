/*    */ package com.hypixel.hytale.builtin.beds.sleep.components;
/*    */ 
/*    */ import java.time.Instant;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NoddingOff
/*    */   extends Record
/*    */   implements PlayerSleep
/*    */ {
/*    */   private final Instant realTimeStart;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/components/PlayerSleep$NoddingOff;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public NoddingOff(Instant realTimeStart) {
/* 28 */     this.realTimeStart = realTimeStart; } public Instant realTimeStart() { return this.realTimeStart; }
/*    */    public static PlayerSomnolence createComponent() {
/* 30 */     Instant now = Instant.now();
/* 31 */     NoddingOff state = new NoddingOff(now);
/* 32 */     return new PlayerSomnolence(state);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\components\PlayerSleep$NoddingOff.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */