/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.ScaledCurveCondition;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.PositionCache;
/*    */ import com.hypixel.hytale.server.npc.role.support.WorldSupport;
/*    */ import java.util.function.Supplier;
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
/*    */ public class NearbyCountCondition
/*    */   extends ScaledCurveCondition
/*    */ {
/*    */   public static final BuilderCodec<NearbyCountCondition> CODEC;
/*    */   protected double range;
/*    */   protected String npcGroup;
/*    */   protected int npcGroupIndex;
/*    */   protected boolean includePlayers;
/*    */   
/*    */   static {
/* 44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NearbyCountCondition.class, NearbyCountCondition::new, ScaledCurveCondition.ABSTRACT_CODEC).documentation("A scaled curve condition that returns a utility value based on the number of NPCs nearby belonging to a specific **NPCGroup**.")).append(new KeyedCodec("Range", (Codec)Codec.DOUBLE), (condition, d) -> condition.range = d.doubleValue(), condition -> Double.valueOf(condition.range)).documentation("The range within which to count NPCs.").addValidator(Validators.nonNull()).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("NPCGroup", (Codec)Codec.STRING), (condition, s) -> condition.npcGroup = s, condition -> condition.npcGroup).documentation("The NPCGroup to count NPCs from.").addValidator(Validators.nonNull()).addValidator(NPCGroup.VALIDATOR_CACHE.getValidator()).add()).afterDecode(condition -> condition.npcGroupIndex = NPCGroup.getAssetMap().getIndex(condition.npcGroup))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getRange() {
/* 55 */     return this.range;
/*    */   }
/*    */   
/*    */   public String getNpcGroup() {
/* 59 */     return this.npcGroup;
/*    */   }
/*    */   
/*    */   public int getNpcGroupIndex() {
/* 63 */     return this.npcGroupIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupNPC(@Nonnull Role role) {
/* 68 */     PositionCache positionCache = role.getPositionCache();
/* 69 */     positionCache.requireEntityDistanceSorted(this.range);
/* 70 */     this.includePlayers = WorldSupport.hasTagInGroup(this.npcGroupIndex, BuilderManager.getPlayerGroupID());
/* 71 */     if (this.includePlayers) positionCache.requirePlayerDistanceSorted(this.range);
/*    */   
/*    */   }
/*    */   
/*    */   protected double getInput(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 76 */     NPCEntity selfNpcComponent = (NPCEntity)archetypeChunk.getComponent(selfIndex, NPCEntity.getComponentType());
/* 77 */     assert selfNpcComponent != null;
/*    */     
/* 79 */     PositionCache positionCache = selfNpcComponent.getRole().getPositionCache();
/* 80 */     return positionCache.countEntitiesInRange(0.0D, this.range, this.includePlayers, NearbyCountCondition::filterNPC, selfNpcComponent.getRole(), this, (ComponentAccessor)commandBuffer);
/*    */   }
/*    */   
/*    */   protected static boolean filterNPC(@Nonnull Role role, Ref<EntityStore> ref, @Nonnull NearbyCountCondition _this, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 84 */     return WorldSupport.isGroupMember(role.getRoleIndex(), ref, _this.npcGroupIndex, componentAccessor);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 90 */     return "NearbyCountCondition{range=" + this.range + ", npcGroup=" + this.npcGroup + ", npcGroupIndex=" + this.npcGroupIndex + "} " + super
/*    */ 
/*    */ 
/*    */       
/* 94 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\NearbyCountCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */