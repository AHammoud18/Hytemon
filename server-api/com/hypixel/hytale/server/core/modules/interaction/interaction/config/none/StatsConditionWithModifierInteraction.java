/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.ValueType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemArmor;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class StatsConditionWithModifierInteraction
/*     */   extends StatsConditionBaseInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<StatsConditionWithModifierInteraction> CODEC;
/*     */   protected ItemArmor.InteractionModifierId interactionModifierId;
/*     */   
/*     */   static {
/*  43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StatsConditionWithModifierInteraction.class, StatsConditionWithModifierInteraction::new, StatsConditionBaseInteraction.CODEC).documentation("Interaction that is successful if the given stat conditions match.")).append(new KeyedCodec("InteractionModifierId", (Codec)new EnumCodec(ItemArmor.InteractionModifierId.class)), (changeStatWithModifierInteraction, s) -> changeStatWithModifierInteraction.interactionModifierId = s, changeStatWithModifierInteraction -> changeStatWithModifierInteraction.interactionModifierId).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  49 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  50 */     assert commandBuffer != null;
/*     */     
/*  52 */     Ref<EntityStore> ref = context.getEntity();
/*  53 */     if (!canAfford(ref, (ComponentAccessor<EntityStore>)commandBuffer)) {
/*  54 */       (context.getState()).state = InteractionState.Failed;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canAfford(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  60 */     EntityStatMap entityStatMapComponent = (EntityStatMap)componentAccessor.getComponent(ref, EntityStatMap.getComponentType());
/*  61 */     if (entityStatMapComponent == null) return false;
/*     */     
/*  63 */     if (this.costs == null) return false; 
/*  64 */     for (ObjectIterator<Int2FloatMap.Entry> objectIterator = this.costs.int2FloatEntrySet().iterator(); objectIterator.hasNext(); ) { Int2FloatMap.Entry cost = objectIterator.next();
/*  65 */       EntityStatValue stat = entityStatMapComponent.get(cost.getIntKey());
/*  66 */       if (stat == null) return false;
/*     */       
/*  68 */       float statValue = (this.valueType == ValueType.Absolute) ? stat.get() : (stat.asPercentage() * 100.0F);
/*     */       
/*  70 */       Inventory inventory = null;
/*  71 */       Entity entity = EntityUtils.getEntity(ref, componentAccessor); if (entity instanceof LivingEntity) { LivingEntity livingEntity = (LivingEntity)entity;
/*  72 */         inventory = livingEntity.getInventory(); }
/*     */ 
/*     */       
/*  75 */       float modifiedCost = calculateDiscount(inventory, cost.getIntKey(), cost.getFloatValue());
/*     */       
/*  77 */       if (this.lessThan) {
/*  78 */         if (statValue >= modifiedCost)
/*  79 */           return false; 
/*     */         continue;
/*     */       } 
/*  82 */       if (statValue < modifiedCost && !canOverdraw(statValue, stat.getMin())) {
/*  83 */         return false;
/*     */       } }
/*     */ 
/*     */ 
/*     */     
/*  88 */     return true;
/*     */   }
/*     */   
/*     */   private float calculateDiscount(@Nullable Inventory inventory, int statIndex, float baseCost) {
/*  92 */     float modifiedCost = baseCost;
/*  93 */     float flatModifier = 0.0F;
/*  94 */     float multiplierModifier = 0.0F;
/*  95 */     ItemContainer armorContainer = null;
/*  96 */     if (inventory != null) armorContainer = inventory.getArmor();
/*     */     
/*  98 */     if (armorContainer != null) {
/*  99 */       short i; for (i = 0; i < armorContainer.getCapacity(); i = (short)(i + 1)) {
/* 100 */         ItemStack itemStack = armorContainer.getItemStack(i);
/* 101 */         if (itemStack != null && !itemStack.isEmpty()) {
/*     */           
/* 103 */           Item item = itemStack.getItem();
/* 104 */           if (item != null && item.getArmor() != null) {
/*     */             
/* 106 */             Int2ObjectMap<StaticModifier> statModifierMap = item.getArmor().getInteractionModifier(this.interactionModifierId.toString());
/* 107 */             if (statModifierMap != null)
/*     */             
/* 109 */             { StaticModifier statModifier = (StaticModifier)statModifierMap.get(statIndex);
/*     */               
/* 111 */               if (statModifier.getCalculationType() == StaticModifier.CalculationType.ADDITIVE)
/* 112 */               { flatModifier += statModifier.getAmount(); }
/*     */               else
/*     */               
/* 115 */               { multiplierModifier = statModifier.getAmount(); }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 119 */     }  modifiedCost += flatModifier;
/* 120 */     modifiedCost *= Math.max(0.0F, 1.0F - multiplierModifier);
/* 121 */     return modifiedCost;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 127 */     return "StatsConditionWithModifierInteraction{interactionModifierId=" + String.valueOf(this.interactionModifierId) + "}" + super
/*     */       
/* 129 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\StatsConditionWithModifierInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */