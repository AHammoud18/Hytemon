/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.combatactions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluator;
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluatorConfig;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.Option;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*    */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class BasicAttackTargetCombatAction
/*    */   extends CombatActionOption
/*    */ {
/*    */   private static final double BASIC_ATTACK_DISTANCE_OFFSET = 0.1D;
/*    */   public static final BuilderCodec<BasicAttackTargetCombatAction> CODEC;
/*    */   protected int weaponSlot;
/*    */   protected int offhandSlot;
/*    */   
/*    */   static {
/* 53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BasicAttackTargetCombatAction.class, BasicAttackTargetCombatAction::new, Option.ABSTRACT_CODEC).documentation("A combat action which simply selects a target and sets up distances for use with substates that only contain basic attacks.")).appendInherited(new KeyedCodec("WeaponSlot", (Codec)Codec.INTEGER), (option, i) -> option.weaponSlot = i.intValue(), option -> Integer.valueOf(option.weaponSlot), (option, parent) -> option.weaponSlot = parent.weaponSlot).documentation("The weapon (hotbar) slot to switch to for basic attacks.").add()).appendInherited(new KeyedCodec("OffhandSlot", (Codec)Codec.INTEGER), (option, i) -> option.offhandSlot = i.intValue(), option -> Integer.valueOf(option.offhandSlot), (option, parent) -> option.offhandSlot = parent.offhandSlot).documentation("The off-hand slot to switch to for basic attacks. -1 set to no off-hand equipped.").add()).afterDecode(option -> option.actionTarget = CombatActionOption.Target.Hostile)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, CommandBuffer<EntityStore> commandBuffer, Role role, @Nonnull CombatActionEvaluator evaluator, @Nonnull ValueStore valueStore) {
/* 61 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, NPCEntity.getComponentType());
/* 62 */     assert npcComponent != null;
/*    */     
/* 64 */     HytaleLogger.Api ctx = CombatActionEvaluator.LOGGER.at(Level.FINEST);
/* 65 */     if (ctx.isEnabled()) {
/* 66 */       ctx.log("%s: Executing option %s", archetypeChunk.getReferenceTo(index), getId());
/*    */     }
/*    */ 
/*    */     
/* 70 */     InventoryHelper.setHotbarSlot(npcComponent.getInventory(), (byte)this.weaponSlot);
/* 71 */     InventoryHelper.setOffHandSlot(npcComponent.getInventory(), (byte)this.offhandSlot);
/*    */     
/* 73 */     CombatActionEvaluatorConfig.BasicAttacks basicAttacks = evaluator.getCurrentBasicAttackSet();
/* 74 */     if (basicAttacks != null) {
/*    */ 
/*    */       
/* 77 */       double range = basicAttacks.getMaxRange() - 0.1D;
/* 78 */       valueStore.storeDouble(evaluator.getMinRangeSlot(), range);
/* 79 */       valueStore.storeDouble(evaluator.getMaxRangeSlot(), range);
/*    */     } 
/*    */     
/* 82 */     evaluator.completeCurrentAction(true, false);
/* 83 */     evaluator.clearTimeout();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBasicAttackAllowed(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, CommandBuffer<EntityStore> commandBuffer, CombatActionEvaluator evaluator) {
/* 89 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean cancelBasicAttackOnSelect() {
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\evaluator\combatactions\BasicAttackTargetCombatAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */