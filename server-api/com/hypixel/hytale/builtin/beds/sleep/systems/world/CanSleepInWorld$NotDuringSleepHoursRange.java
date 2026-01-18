/*    */ package com.hypixel.hytale.builtin.beds.sleep.systems.world;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.SleepConfig;
/*    */ import java.time.LocalDateTime;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NotDuringSleepHoursRange
/*    */   extends Record
/*    */   implements CanSleepInWorld.Result
/*    */ {
/*    */   private final LocalDateTime worldTime;
/*    */   private final SleepConfig sleepConfig;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/beds/sleep/systems/world/CanSleepInWorld$NotDuringSleepHoursRange;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/systems/world/CanSleepInWorld$NotDuringSleepHoursRange;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/beds/sleep/systems/world/CanSleepInWorld$NotDuringSleepHoursRange;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/systems/world/CanSleepInWorld$NotDuringSleepHoursRange;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/beds/sleep/systems/world/CanSleepInWorld$NotDuringSleepHoursRange;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/beds/sleep/systems/world/CanSleepInWorld$NotDuringSleepHoursRange;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public NotDuringSleepHoursRange(LocalDateTime worldTime, SleepConfig sleepConfig) {
/* 30 */     this.worldTime = worldTime; this.sleepConfig = sleepConfig; } public LocalDateTime worldTime() { return this.worldTime; } public SleepConfig sleepConfig() { return this.sleepConfig; }
/*    */   
/*    */   public boolean isNegative() {
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\systems\world\CanSleepInWorld$NotDuringSleepHoursRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */