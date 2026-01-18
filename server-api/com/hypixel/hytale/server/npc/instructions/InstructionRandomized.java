/*    */ package com.hypixel.hytale.server.npc.instructions;
/*    */ 
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.common.map.WeightedMap;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.instructions.builders.BuilderInstruction;
/*    */ import com.hypixel.hytale.server.npc.instructions.builders.BuilderInstructionRandomized;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class InstructionRandomized
/*    */   extends Instruction {
/*    */   @Nonnull
/*    */   protected final IWeightedMap<InstructionHolder> weightedInstructionMap;
/*    */   protected final boolean resetOnStateChange;
/*    */   protected final double minExecuteTime;
/*    */   protected final double maxExecuteTime;
/*    */   protected double timeout;
/*    */   @Nullable
/*    */   protected InstructionHolder current;
/*    */   
/*    */   public InstructionRandomized(@Nonnull BuilderInstructionRandomized builder, Sensor sensor, @Nonnull Instruction[] instructionList, @Nonnull BuilderSupport support) {
/* 29 */     super((BuilderInstruction)builder, sensor, instructionList, support);
/* 30 */     WeightedMap.Builder<InstructionHolder> mapBuilder = WeightedMap.builder((Object[])InstructionHolder.EMPTY_ARRAY);
/* 31 */     for (Instruction instruction : instructionList) {
/* 32 */       mapBuilder.put(new InstructionHolder(instruction), instruction.getWeight());
/*    */     }
/* 34 */     this.weightedInstructionMap = mapBuilder.build();
/* 35 */     this.resetOnStateChange = builder.getResetOnStateChange(support);
/* 36 */     double[] executeTimeRange = builder.getExecuteFor(support);
/* 37 */     this.minExecuteTime = executeTimeRange[0];
/* 38 */     this.maxExecuteTime = executeTimeRange[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 43 */     if (this.instructionList.length == 0)
/*    */       return; 
/* 45 */     this.timeout -= dt;
/* 46 */     if (this.timeout <= 0.0D || this.current == null) {
/* 47 */       ThreadLocalRandom random = ThreadLocalRandom.current();
/* 48 */       this.current = (InstructionHolder)this.weightedInstructionMap.get(random.nextDouble());
/* 49 */       this.timeout = RandomExtra.randomRange(this.minExecuteTime, this.maxExecuteTime);
/*    */     } 
/*    */     
/* 52 */     Instruction instruction = this.current.instruction;
/* 53 */     if (instruction.matches(ref, role, dt, store)) {
/* 54 */       instruction.onMatched(role);
/* 55 */       instruction.execute(ref, role, dt, store);
/* 56 */       instruction.onCompleted(role);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearOnce() {
/* 62 */     super.clearOnce();
/* 63 */     if (this.resetOnStateChange) this.current = null;
/*    */   
/*    */   }
/*    */   
/*    */   public void reset() {
/* 68 */     super.clearOnce();
/* 69 */     this.current = null;
/*    */   }
/*    */   
/*    */   protected static class InstructionHolder {
/* 73 */     protected static final InstructionHolder[] EMPTY_ARRAY = new InstructionHolder[0];
/*    */     
/*    */     private final Instruction instruction;
/*    */     
/*    */     protected InstructionHolder(Instruction instruction) {
/* 78 */       this.instruction = instruction;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\instructions\InstructionRandomized.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */