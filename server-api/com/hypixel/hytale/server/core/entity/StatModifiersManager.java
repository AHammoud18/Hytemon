/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.BrokenPenalties;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemArmor;
/*     */ import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class StatModifiersManager
/*     */ {
/*     */   @Nonnull
/*  36 */   private final AtomicBoolean recalculate = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  42 */   private final IntSet statsToClear = (IntSet)new IntOpenHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRecalculate(boolean value) {
/*  51 */     this.recalculate.set(value);
/*     */   }
/*     */   
/*     */   public void queueEntityStatsToClear(@Nonnull int[] entityStatsToClear) {
/*  55 */     for (int i = 0; i < entityStatsToClear.length; i++) {
/*  56 */       this.statsToClear.add(entityStatsToClear[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void recalculateEntityStatModifiers(@Nonnull Ref<EntityStore> ref, @Nonnull EntityStatMap statMap, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     LivingEntity livingEntity;
/*  63 */     if (!this.recalculate.getAndSet(false))
/*     */       return; 
/*  65 */     if (!this.statsToClear.isEmpty()) {
/*     */       
/*  67 */       IntIterator iterator = this.statsToClear.iterator();
/*  68 */       while (iterator.hasNext()) {
/*  69 */         statMap.minimizeStatValue(EntityStatMap.Predictable.SELF, iterator.nextInt());
/*     */       }
/*     */       
/*  72 */       this.statsToClear.clear();
/*     */     } 
/*     */     
/*  75 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  76 */     Entity entity = EntityUtils.getEntity(ref, componentAccessor);
/*  77 */     if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; }
/*     */     else
/*     */     { return; }
/*  80 */      Inventory inventory = livingEntity.getInventory();
/*     */ 
/*     */     
/*  83 */     Int2ObjectOpenHashMap<Object2FloatMap<StaticModifier.CalculationType>> effectModifiers = calculateEffectStatModifiers(ref, componentAccessor);
/*  84 */     applyEffectModifiers(statMap, (Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>>)effectModifiers);
/*     */ 
/*     */     
/*  87 */     BrokenPenalties brokenPenalties = world.getGameplayConfig().getItemDurabilityConfig().getBrokenPenalties();
/*  88 */     Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>> statModifiers = computeStatModifiers(brokenPenalties, inventory);
/*  89 */     applyStatModifiers(statMap, statModifiers);
/*     */     
/*  91 */     ItemStack itemInHand = inventory.getItemInHand();
/*  92 */     addItemStatModifiers(itemInHand, statMap, "*Weapon_", v -> (v.getWeapon() != null) ? v.getWeapon().getStatModifiers() : null);
/*     */ 
/*     */     
/*  95 */     if (itemInHand == null || itemInHand.getItem().getUtility().isCompatible()) {
/*  96 */       addItemStatModifiers(inventory.getUtilityItem(), statMap, "*Utility_", v -> v.getUtility().getStatModifiers());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Int2ObjectOpenHashMap<Object2FloatMap<StaticModifier.CalculationType>> calculateEffectStatModifiers(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 103 */     Int2ObjectOpenHashMap<Object2FloatMap<StaticModifier.CalculationType>> statModifiers = new Int2ObjectOpenHashMap();
/* 104 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)componentAccessor.getComponent(ref, EffectControllerComponent.getComponentType());
/* 105 */     if (effectControllerComponent == null) return statModifiers;
/*     */     
/* 107 */     effectControllerComponent.getActiveEffects().forEach((k, v) -> {
/*     */           if (!v.isInfinite() && v.getRemainingDuration() <= 0.0F)
/*     */             return;  int index = v.getEntityEffectIndex(); EntityEffect effect = (EntityEffect)EntityEffect.getAssetMap().getAsset(index);
/*     */           if (effect == null || effect.getStatModifiers() == null)
/*     */             return; 
/*     */           ObjectIterator<Int2ObjectMap.Entry<StaticModifier[]>> objectIterator = effect.getStatModifiers().int2ObjectEntrySet().iterator();
/*     */           while (objectIterator.hasNext()) {
/*     */             Int2ObjectMap.Entry<StaticModifier[]> entry = objectIterator.next();
/*     */             int entityStatType = entry.getIntKey();
/*     */             for (StaticModifier modifier : (StaticModifier[])entry.getValue()) {
/*     */               float value = modifier.getAmount();
/*     */               Object2FloatMap<StaticModifier.CalculationType> statModifierToApply = (Object2FloatMap<StaticModifier.CalculationType>)statModifiers.computeIfAbsent(entityStatType, ());
/*     */               statModifierToApply.mergeFloat(modifier.getCalculationType(), value, Float::sum);
/*     */             } 
/*     */           } 
/*     */         });
/* 123 */     return statModifiers;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void applyEffectModifiers(@Nonnull EntityStatMap statMap, @Nonnull Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>> statModifiers) {
/* 128 */     for (int i = 0; i < statMap.size(); i++) {
/* 129 */       Object2FloatMap<StaticModifier.CalculationType> statModifiersForEntityStat = (Object2FloatMap<StaticModifier.CalculationType>)statModifiers.get(i);
/* 130 */       if (statModifiersForEntityStat == null) {
/*     */         
/* 132 */         for (StaticModifier.CalculationType calculationType : StaticModifier.CalculationType.values()) {
/* 133 */           statMap.removeModifier(i, calculationType.createKey("Effect"));
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 139 */         for (StaticModifier.CalculationType calculationType : StaticModifier.CalculationType.values()) {
/* 140 */           if (!statModifiersForEntityStat.containsKey(calculationType)) {
/* 141 */             statMap.removeModifier(i, calculationType.createKey("Effect"));
/*     */           }
/*     */         } 
/*     */         
/* 145 */         for (ObjectIterator<Object2FloatMap.Entry<StaticModifier.CalculationType>> objectIterator = statModifiersForEntityStat.object2FloatEntrySet().iterator(); objectIterator.hasNext(); ) { Object2FloatMap.Entry<StaticModifier.CalculationType> entry = objectIterator.next();
/* 146 */           StaticModifier.CalculationType calculationType = (StaticModifier.CalculationType)entry.getKey();
/* 147 */           StaticModifier modifier = new StaticModifier(Modifier.ModifierTarget.MAX, calculationType, entry.getFloatValue());
/* 148 */           statMap.putModifier(i, calculationType.createKey("Effect"), (Modifier)modifier); }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void computeStatModifiers(double brokenPenalty, @Nonnull Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>> statModifiers, @Nonnull ItemStack itemInHand, @Nonnull Int2ObjectMap<StaticModifier[]> itemStatModifiers) {
/* 157 */     boolean broken = itemInHand.isBroken();
/* 158 */     for (ObjectIterator<Int2ObjectMap.Entry<StaticModifier[]>> objectIterator = itemStatModifiers.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<StaticModifier[]> entry = objectIterator.next();
/* 159 */       int entityStatType = entry.getIntKey();
/* 160 */       for (StaticModifier modifier : (StaticModifier[])entry.getValue()) {
/* 161 */         float value = modifier.getAmount();
/*     */ 
/*     */         
/* 164 */         if (broken) value = (float)(value * brokenPenalty);
/*     */         
/* 166 */         Object2FloatMap<StaticModifier.CalculationType> statModifierToApply = (Object2FloatMap<StaticModifier.CalculationType>)statModifiers.computeIfAbsent(entityStatType, x -> new Object2FloatOpenHashMap());
/* 167 */         statModifierToApply.mergeFloat(modifier.getCalculationType(), value, Float::sum);
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>> computeStatModifiers(@Nonnull BrokenPenalties brokenPenalties, @Nonnull Inventory inventory) {
/* 176 */     Int2ObjectOpenHashMap<Object2FloatMap<StaticModifier.CalculationType>> statModifiers = new Int2ObjectOpenHashMap();
/*     */     
/* 178 */     double armorBrokenPenalty = brokenPenalties.getArmor(0.0D);
/* 179 */     ItemContainer armorContainer = inventory.getArmor(); short i;
/* 180 */     for (i = 0; i < armorContainer.getCapacity(); i = (short)(i + 1)) {
/* 181 */       ItemStack armorItemStack = armorContainer.getItemStack(i);
/* 182 */       if (armorItemStack != null) {
/* 183 */         addArmorStatModifiers(armorItemStack, armorBrokenPenalty, statModifiers);
/*     */       }
/*     */     } 
/* 186 */     return (Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>>)statModifiers;
/*     */   }
/*     */   
/*     */   private static void addArmorStatModifiers(@Nonnull ItemStack itemStack, double brokenPenalties, @Nonnull Int2ObjectOpenHashMap<Object2FloatMap<StaticModifier.CalculationType>> statModifiers) {
/* 190 */     if (ItemStack.isEmpty(itemStack))
/*     */       return; 
/* 192 */     ItemArmor armorItem = itemStack.getItem().getArmor();
/* 193 */     if (armorItem == null)
/*     */       return; 
/* 195 */     Int2ObjectMap<StaticModifier[]> itemStatModifiers = armorItem.getStatModifiers();
/* 196 */     if (itemStatModifiers == null)
/*     */       return; 
/* 198 */     computeStatModifiers(brokenPenalties, (Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>>)statModifiers, itemStack, itemStatModifiers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addItemStatModifiers(@Nullable ItemStack itemStack, @Nonnull EntityStatMap entityStatMap, @Nonnull String prefix, @Nonnull Function<Item, Int2ObjectMap<StaticModifier[]>> toStatModifiers) {
/* 205 */     if (ItemStack.isEmpty(itemStack)) {
/* 206 */       clearAllStatModifiers(EntityStatMap.Predictable.SELF, entityStatMap, prefix, null);
/*     */       
/*     */       return;
/*     */     } 
/* 210 */     Int2ObjectMap<StaticModifier[]> itemStatModifiers = toStatModifiers.apply(itemStack.getItem());
/* 211 */     if (itemStatModifiers == null) {
/* 212 */       clearAllStatModifiers(EntityStatMap.Predictable.SELF, entityStatMap, prefix, null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 217 */     for (ObjectIterator<Int2ObjectMap.Entry<StaticModifier[]>> objectIterator = itemStatModifiers.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<StaticModifier[]> entry = objectIterator.next();
/* 218 */       int offset = 0;
/* 219 */       int statIndex = entry.getIntKey();
/* 220 */       for (StaticModifier modifier : (StaticModifier[])entry.getValue()) {
/*     */         
/* 222 */         String key = prefix + prefix;
/* 223 */         offset++;
/*     */         
/* 225 */         Modifier existing = entityStatMap.getModifier(statIndex, key);
/* 226 */         if (existing instanceof StaticModifier) { StaticModifier existingStatic = (StaticModifier)existing;
/* 227 */           if (existingStatic.equals(modifier)) {
/*     */             continue;
/*     */           } }
/*     */ 
/*     */         
/* 232 */         entityStatMap.putModifier(EntityStatMap.Predictable.SELF, statIndex, key, (Modifier)modifier);
/*     */         continue;
/*     */       } 
/* 235 */       clearStatModifiers(EntityStatMap.Predictable.SELF, entityStatMap, statIndex, prefix, offset); }
/*     */ 
/*     */     
/* 238 */     clearAllStatModifiers(EntityStatMap.Predictable.SELF, entityStatMap, prefix, itemStatModifiers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void clearAllStatModifiers(@Nonnull EntityStatMap.Predictable predictable, @Nonnull EntityStatMap entityStatMap, @Nonnull String prefix, @Nullable Int2ObjectMap<StaticModifier[]> excluding) {
/* 245 */     for (int i = 0; i < entityStatMap.size(); i++) {
/* 246 */       if (excluding == null || !excluding.containsKey(i)) {
/* 247 */         clearStatModifiers(predictable, entityStatMap, i, prefix, 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void clearStatModifiers(@Nonnull EntityStatMap.Predictable predictable, @Nonnull EntityStatMap entityStatMap, int statIndex, @Nonnull String prefix, int offset) {
/*     */     String key;
/*     */     do {
/* 258 */       key = prefix + prefix;
/* 259 */       offset++;
/* 260 */     } while (entityStatMap.removeModifier(predictable, statIndex, key) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyStatModifiers(@Nonnull EntityStatMap statMap, @Nonnull Int2ObjectMap<Object2FloatMap<StaticModifier.CalculationType>> statModifiers) {
/* 268 */     for (int i = 0; i < statMap.size(); i++) {
/* 269 */       Object2FloatMap<StaticModifier.CalculationType> statModifiersForEntityStat = (Object2FloatMap<StaticModifier.CalculationType>)statModifiers.get(i);
/* 270 */       if (statModifiersForEntityStat == null) {
/*     */ 
/*     */         
/* 273 */         for (StaticModifier.CalculationType calculationType : StaticModifier.CalculationType.values()) {
/* 274 */           statMap.removeModifier(i, calculationType.createKey("Armor"));
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 280 */         for (StaticModifier.CalculationType calculationType : StaticModifier.CalculationType.values()) {
/* 281 */           if (!statModifiersForEntityStat.containsKey(calculationType)) {
/* 282 */             statMap.removeModifier(i, calculationType.createKey("Armor"));
/*     */           }
/*     */         } 
/*     */         
/* 286 */         for (ObjectIterator<Object2FloatMap.Entry<StaticModifier.CalculationType>> objectIterator = statModifiersForEntityStat.object2FloatEntrySet().iterator(); objectIterator.hasNext(); ) { Object2FloatMap.Entry<StaticModifier.CalculationType> entry = objectIterator.next();
/* 287 */           StaticModifier.CalculationType calculationType = (StaticModifier.CalculationType)entry.getKey();
/* 288 */           StaticModifier modifier = new StaticModifier(Modifier.ModifierTarget.MAX, calculationType, entry.getFloatValue());
/* 289 */           statMap.putModifier(i, calculationType.createKey("Armor"), (Modifier)modifier); }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\StatModifiersManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */