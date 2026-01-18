/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemArmor;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChangeStatWithModifierInteraction extends ChangeStatBaseInteraction {
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChangeStatWithModifierInteraction.class, ChangeStatWithModifierInteraction::new, ChangeStatBaseInteraction.CODEC).documentation("Changes the given stats.")).append(new KeyedCodec("InteractionModifierId", (Codec)new EnumCodec(ItemArmor.InteractionModifierId.class)), (changeStatWithModifierInteraction, s) -> changeStatWithModifierInteraction.interactionModifierId = s, changeStatWithModifierInteraction -> changeStatWithModifierInteraction.interactionModifierId).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */   public static final BuilderCodec<ChangeStatWithModifierInteraction> CODEC;
/*    */   protected ItemArmor.InteractionModifierId interactionModifierId;
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 39 */     Ref<EntityStore> ref = context.getEntity();
/* 40 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*    */     
/* 42 */     EntityStatMap entityStatMapComponent = (EntityStatMap)commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
/* 43 */     assert entityStatMapComponent != null;
/*    */     
/* 45 */     Int2FloatOpenHashMap int2FloatOpenHashMap = new Int2FloatOpenHashMap(this.entityStats);
/*    */     
/* 47 */     Inventory inventory = null;
/* 48 */     Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer); if (entity instanceof LivingEntity) { LivingEntity livingEntity = (LivingEntity)entity;
/* 49 */       inventory = livingEntity.getInventory(); }
/*    */ 
/*    */     
/* 52 */     for (IntIterator<Integer> intIterator = int2FloatOpenHashMap.keySet().iterator(); intIterator.hasNext(); ) { int index = ((Integer)intIterator.next()).intValue();
/* 53 */       if (inventory == null)
/* 54 */         continue;  ItemContainer armorContainer = inventory.getArmor();
/* 55 */       if (armorContainer == null)
/* 56 */         continue;  float flatModifier = 0.0F;
/* 57 */       float multiplierModifier = 0.0F;
/*    */       short i;
/* 59 */       for (i = 0; i < armorContainer.getCapacity(); i = (short)(i + 1)) {
/* 60 */         ItemStack itemStack = armorContainer.getItemStack(i);
/* 61 */         if (itemStack != null && !itemStack.isEmpty()) {
/*    */           
/* 63 */           Item item = itemStack.getItem();
/* 64 */           if (item != null && item.getArmor() != null) {
/*    */             
/* 66 */             Int2ObjectMap<StaticModifier> statModifierMap = item.getArmor().getInteractionModifier(this.interactionModifierId.toString());
/* 67 */             if (statModifierMap != null)
/*    */             
/* 69 */             { StaticModifier statModifier = (StaticModifier)statModifierMap.get(index);
/* 70 */               if (statModifier != null)
/*    */               {
/* 72 */                 if (statModifier.getCalculationType() == StaticModifier.CalculationType.ADDITIVE)
/* 73 */                 { flatModifier += statModifier.getAmount(); }
/*    */                 else
/*    */                 
/* 76 */                 { multiplierModifier = statModifier.getAmount(); }  }  } 
/*    */           } 
/*    */         } 
/* 79 */       }  float cost = this.entityStats.get(index);
/* 80 */       cost += flatModifier;
/* 81 */       cost *= Math.max(0.0F, 1.0F - multiplierModifier);
/* 82 */       int2FloatOpenHashMap.replace(index, cost); }
/*    */     
/* 84 */     entityStatMapComponent.processStatChanges(EntityStatMap.Predictable.NONE, (Int2FloatMap)int2FloatOpenHashMap, this.valueType, this.changeStatBehaviour);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 90 */     return "ChangeStatWithModifierInteraction{interactionModifierId=" + String.valueOf(this.interactionModifierId) + "}" + super
/*    */       
/* 92 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\ChangeStatWithModifierInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */