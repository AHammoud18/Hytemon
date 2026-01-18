/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.operation;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.InteractionRules;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OperationsBuilder
/*     */ {
/*     */   @Nonnull
/* 151 */   private final List<Operation> operationList = (List<Operation>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Label createLabel() {
/* 176 */     return new Label(this.operationList.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Label createUnresolvedLabel() {
/* 195 */     return new Label(-2147483648);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resolveLabel(@Nonnull Label label) {
/* 217 */     if (label.index != Integer.MIN_VALUE) throw new IllegalArgumentException("Label already resolved"); 
/* 218 */     label.index = this.operationList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jump(@Nonnull Label target) {
/* 228 */     this.operationList.add(new JumpOperation(target));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperation(@Nonnull Operation operation) {
/* 237 */     this.operationList.add(operation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperation(@Nonnull Operation operation, Label... labels) {
/* 251 */     this.operationList.add(new LabelOperation(operation, labels));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Operation[] build() {
/* 261 */     return (Operation[])this.operationList.toArray(x$0 -> new Operation[x$0]);
/*     */   } private static final class LabelOperation extends Record implements Operation, Operation.NestedOperation {
/*     */     private final Operation inner; private final Label[] labels;
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/interaction/interaction/operation/OperationsBuilder$LabelOperation;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #267	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/interaction/interaction/operation/OperationsBuilder$LabelOperation;
/*     */     }
/* 267 */     private LabelOperation(Operation inner, Label[] labels) { this.inner = inner; this.labels = labels; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/interaction/interaction/operation/OperationsBuilder$LabelOperation;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #267	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/interaction/interaction/operation/OperationsBuilder$LabelOperation;
/* 267 */       //   0	8	1	o	Ljava/lang/Object; } public Operation inner() { return this.inner; } public Label[] labels() { return this.labels; }
/*     */ 
/*     */     
/*     */     public void tick(@Nonnull Ref<EntityStore> ref, @Nonnull LivingEntity entity, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 271 */       context.setLabels(this.labels);
/* 272 */       this.inner.tick(ref, entity, firstRun, time, type, context, cooldownHandler);
/*     */     }
/*     */ 
/*     */     
/*     */     public void simulateTick(@Nonnull Ref<EntityStore> ref, @Nonnull LivingEntity entity, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 277 */       context.setLabels(this.labels);
/* 278 */       this.inner.simulateTick(ref, entity, firstRun, time, type, context, cooldownHandler);
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(@Nonnull Ref<EntityStore> ref, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context) {
/* 283 */       context.setLabels(this.labels);
/* 284 */       this.inner.handle(ref, firstRun, time, type, context);
/*     */     }
/*     */ 
/*     */     
/*     */     public WaitForDataFrom getWaitForDataFrom() {
/* 289 */       return this.inner.getWaitForDataFrom();
/*     */     }
/*     */ 
/*     */     
/*     */     public InteractionRules getRules() {
/* 294 */       return this.inner.getRules();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 300 */       return "LabelOperation{inner=" + String.valueOf(this.inner) + ", labels=" + 
/*     */         
/* 302 */         Arrays.toString((Object[])this.labels) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\operation\OperationsBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */