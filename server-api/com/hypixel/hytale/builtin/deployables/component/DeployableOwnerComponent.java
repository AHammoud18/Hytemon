/*     */ package com.hypixel.hytale.builtin.deployables.component;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.deployables.DeployablesPlugin;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableConfig;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class DeployableOwnerComponent implements Component<EntityStore> {
/*     */   @Nonnull
/*  26 */   private final List<Pair<String, Ref<EntityStore>>> deployables = (List<Pair<String, Ref<EntityStore>>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  32 */   private final Object2IntMap<String> deployableCountPerId = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  38 */   private final List<Ref<EntityStore>> deployablesForDestruction = (List<Ref<EntityStore>>)new ObjectArrayList();
/*     */ 
/*     */   
/*  41 */   private final List<Pair<String, Ref<EntityStore>>> tempDestructionList = (List<Pair<String, Ref<EntityStore>>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, DeployableOwnerComponent> getComponentType() {
/*  48 */     return DeployablesPlugin.get().getDeployableOwnerComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getMaxDeployablesForId(@Nonnull DeployableComponent comp) {
/*  58 */     return comp.getConfig().getMaxLiveCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getMaxDeployablesGlobal(@Nonnull Store<EntityStore> store) {
/*  68 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  69 */     GameplayConfig gameplayConfig = world.getGameplayConfig();
/*  70 */     return gameplayConfig.getPlayerConfig().getMaxDeployableEntities();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(@Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  79 */     handleOverMaxDeployableDestruction(commandBuffer);
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
/*     */   public void registerDeployable(@Nonnull Ref<EntityStore> owner, @Nonnull DeployableComponent deployableComp, @Nonnull String id, @Nonnull Ref<EntityStore> deployable, @Nonnull Store<EntityStore> store) {
/*  96 */     this.deployables.add(Pair.of(id, deployable));
/*  97 */     incrementId(id);
/*     */     
/*  99 */     handlePerDeployableLimit(id, deployableComp);
/* 100 */     handleGlobalDeployableLimit(store, owner);
/*     */   }
/*     */   
/*     */   public void deRegisterDeployable(@Nonnull String id, @Nonnull Ref<EntityStore> deployable) {
/* 104 */     this.deployables.remove(Pair.of(id, deployable));
/* 105 */     decrementId(id);
/*     */   }
/*     */   
/*     */   private void incrementId(@Nonnull String id) {
/* 109 */     if (!this.deployableCountPerId.containsKey(id)) {
/* 110 */       this.deployableCountPerId.put(id, 1);
/*     */       
/*     */       return;
/*     */     } 
/* 114 */     this.deployableCountPerId.put(id, this.deployableCountPerId.getInt(id) + 1);
/*     */   }
/*     */   
/*     */   private void decrementId(@Nonnull String id) {
/* 118 */     if (!this.deployableCountPerId.containsKey(id)) {
/* 119 */       this.deployableCountPerId.put(id, 0);
/*     */       
/*     */       return;
/*     */     } 
/* 123 */     this.deployableCountPerId.put(id, this.deployableCountPerId.getInt(id) - 1);
/*     */   }
/*     */   
/*     */   private int getCurrentDeployablesById(@Nonnull String id) {
/* 127 */     return this.deployableCountPerId.getOrDefault(id, 0);
/*     */   }
/*     */   
/*     */   private void handlePerDeployableLimit(@Nonnull String id, @Nonnull DeployableComponent deployableComponent) {
/* 131 */     int limit = getMaxDeployablesForId(deployableComponent);
/* 132 */     int current = getCurrentDeployablesById(id);
/* 133 */     if (current <= limit)
/*     */       return; 
/* 135 */     int diff = current - limit;
/* 136 */     this.tempDestructionList.clear();
/*     */     
/* 138 */     for (Pair<String, Ref<EntityStore>> deployablePair : this.deployables) {
/* 139 */       if (((String)deployablePair.key()).equals(id)) {
/* 140 */         this.deployablesForDestruction.add((Ref<EntityStore>)deployablePair.value());
/* 141 */         this.tempDestructionList.add(deployablePair);
/* 142 */         diff--;
/*     */       } 
/*     */       
/* 145 */       if (diff <= 0)
/*     */         break; 
/*     */     } 
/* 148 */     this.deployables.removeAll(this.tempDestructionList);
/* 149 */     this.tempDestructionList.clear();
/*     */   }
/*     */   
/*     */   private void handleGlobalDeployableLimit(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> owner) {
/* 153 */     int limit = 1;
/* 154 */     int current = 0;
/*     */     
/* 156 */     for (Pair<String, Ref<EntityStore>> deployablePair : this.deployables) {
/* 157 */       DeployableComponent deployableComponent = (DeployableComponent)store.getComponent((Ref)deployablePair.value(), DeployableComponent.getComponentType());
/* 158 */       assert deployableComponent != null;
/*     */       
/* 160 */       DeployableConfig deployableConfig = deployableComponent.getConfig();
/* 161 */       if (deployableConfig.getCountTowardsGlobalLimit()) {
/* 162 */         current++;
/*     */       }
/*     */     } 
/*     */     
/* 166 */     if (current <= 1)
/*     */       return; 
/* 168 */     int diff = current - 1;
/* 169 */     this.tempDestructionList.clear();
/*     */     
/* 171 */     for (Pair<String, Ref<EntityStore>> deployablePair : this.deployables) {
/* 172 */       Ref<EntityStore> deployableRef = (Ref<EntityStore>)deployablePair.value();
/*     */       
/* 174 */       DeployableComponent deployableComponent = (DeployableComponent)store.getComponent(deployableRef, DeployableComponent.getComponentType());
/* 175 */       assert deployableComponent != null;
/*     */       
/* 177 */       DeployableConfig deployableConfig = deployableComponent.getConfig();
/* 178 */       if (!deployableConfig.getCountTowardsGlobalLimit())
/*     */         continue; 
/* 180 */       this.deployablesForDestruction.add(deployableRef);
/* 181 */       this.tempDestructionList.add(deployablePair);
/* 182 */       diff--;
/*     */       
/* 184 */       if (diff <= 0)
/*     */         break; 
/*     */     } 
/* 187 */     this.deployables.removeAll(this.tempDestructionList);
/* 188 */     this.tempDestructionList.clear();
/*     */   }
/*     */   
/*     */   private void handleOverMaxDeployableDestruction(@Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 192 */     if (this.deployablesForDestruction.isEmpty())
/*     */       return; 
/* 194 */     for (Ref<EntityStore> deployableEntityRef : this.deployablesForDestruction) {
/* 195 */       DeathComponent.tryAddComponent(commandBuffer, deployableEntityRef, new Damage(Damage.NULL_SOURCE, DamageCause.COMMAND, 0.0F));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.deployablesForDestruction.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Component<EntityStore> clone() {
/* 208 */     return (Component<EntityStore>)new KnockbackComponent();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\component\DeployableOwnerComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */