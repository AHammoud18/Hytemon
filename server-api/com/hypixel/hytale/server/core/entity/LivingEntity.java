/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.event.EventRegistration;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ListTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entity.BlockMigrationExtraInfo;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LivingEntity
/*     */   extends Entity
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<LivingEntity> CODEC;
/*     */   public static final int DEFAULT_ITEM_THROW_SPEED = 6;
/*     */   
/*     */   static {
/*  53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(LivingEntity.class, Entity.CODEC).append(new KeyedCodec("Inventory", (Codec)Inventory.CODEC), (livingEntity, inventory, extraInfo) -> { livingEntity.setInventory(inventory); if (extraInfo instanceof BlockMigrationExtraInfo) livingEntity.inventory.doMigration(((BlockMigrationExtraInfo)extraInfo).getBlockMigration());  }(livingEntity, extraInfo) -> livingEntity.inventory).add()).afterDecode(livingEntity -> { if (livingEntity.inventory == null) livingEntity.setInventory(livingEntity.createDefaultInventory());  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   private final StatModifiersManager statModifiersManager = new StatModifiersManager();
/*     */ 
/*     */   
/*     */   private Inventory inventory;
/*     */ 
/*     */   
/*     */   protected double currentFallDistance;
/*     */ 
/*     */   
/*     */   private EventRegistration armorInventoryChangeEventRegistration;
/*     */ 
/*     */   
/*     */   private boolean isEquipmentNetworkOutdated;
/*     */ 
/*     */ 
/*     */   
/*     */   public LivingEntity() {
/*  77 */     setInventory(createDefaultInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LivingEntity(@Nonnull World world) {
/*  86 */     super(world);
/*     */     
/*  88 */     setInventory(createDefaultInventory());
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
/*     */   public boolean canBreathe(@Nonnull Ref<EntityStore> ref, @Nonnull BlockMaterial breathingMaterial, int fluidId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 106 */     boolean invulnerable = componentAccessor.getArchetype(ref).contains(Invulnerable.getComponentType());
/* 107 */     return (invulnerable || (breathingMaterial == BlockMaterial.Empty && fluidId == 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getPackedMaterialAndFluidAtBreathingHeight(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 118 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 120 */     Transform lookVec = TargetUtil.getLook(ref, componentAccessor);
/* 121 */     Vector3d position = lookVec.getPosition();
/*     */     
/* 123 */     ChunkStore chunkStore = world.getChunkStore();
/* 124 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(position.x, position.z);
/* 125 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*     */ 
/*     */     
/* 128 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 129 */       return MathUtil.packLong(BlockMaterial.Empty.ordinal(), 0);
/*     */     }
/*     */     
/* 132 */     return WorldUtil.getPackedMaterialAndFluidAtPosition(chunkRef, (ComponentAccessor)chunkStore.getStore(), position.x, position.y, position.z);
/*     */   }
/*     */   
/*     */   public Inventory getInventory() {
/* 136 */     return this.inventory;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Inventory setInventory(Inventory inventory) {
/* 141 */     return setInventory(inventory, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Inventory setInventory(Inventory inventory, boolean ensureCapacity) {
/* 146 */     ObjectArrayList objectArrayList = ensureCapacity ? new ObjectArrayList() : null;
/*     */     
/* 148 */     inventory = setInventory(inventory, ensureCapacity, (List<ItemStack>)objectArrayList);
/*     */ 
/*     */     
/* 151 */     if (objectArrayList != null && !objectArrayList.isEmpty()) {
/* 152 */       ListTransaction<ItemStackTransaction> transactionList = inventory.getCombinedHotbarFirst().addItemStacks((List)objectArrayList);
/* 153 */       for (ItemStackTransaction itemStackTransaction : transactionList.getList());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 158 */     return inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Inventory setInventory(Inventory inventory, boolean ensureCapacity, List<ItemStack> remainder) {
/* 164 */     if (this.inventory != null) this.inventory.unregister(); 
/* 165 */     if (this.armorInventoryChangeEventRegistration != null) this.armorInventoryChangeEventRegistration.unregister();
/*     */     
/* 167 */     if (ensureCapacity) {
/* 168 */       inventory = Inventory.ensureCapacity(inventory, remainder);
/*     */     }
/*     */ 
/*     */     
/* 172 */     inventory.setEntity(this);
/*     */     
/* 174 */     this.armorInventoryChangeEventRegistration = inventory.getArmor().registerChangeEvent(event -> this.statModifiersManager.setRecalculate(true));
/* 175 */     this.inventory = inventory;
/* 176 */     return inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveTo(@Nonnull Ref<EntityStore> ref, double locX, double locY, double locZ, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 181 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 182 */     assert transformComponent != null;
/*     */     
/* 184 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)componentAccessor.getComponent(ref, MovementStatesComponent.getComponentType());
/* 185 */     assert movementStatesComponent != null;
/*     */     
/* 187 */     MovementStates movementStates = movementStatesComponent.getMovementStates();
/*     */ 
/*     */     
/* 190 */     boolean fallDamageActive = (!movementStates.inFluid && !movementStates.climbing && !movementStates.flying && !movementStates.gliding);
/*     */     
/* 192 */     if (fallDamageActive) {
/* 193 */       Vector3d position = transformComponent.getPosition();
/* 194 */       if (!movementStates.onGround) {
/* 195 */         if (position.getY() > locY) {
/* 196 */           this.currentFallDistance += position.getY() - locY;
/*     */         }
/*     */       } else {
/* 199 */         this.currentFallDistance = 0.0D;
/*     */       } 
/*     */     } else {
/* 202 */       this.currentFallDistance = 0.0D;
/*     */     } 
/*     */     
/* 205 */     super.moveTo(ref, locX, locY, locZ, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDecreaseItemStackDurability(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canApplyItemStackPenalties(Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStackSlotTransaction decreaseItemStackDurability(@Nonnull Ref<EntityStore> ref, @Nullable ItemStack itemStack, int inventoryId, int slotId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 224 */     if (!canDecreaseItemStackDurability(ref, componentAccessor)) return null; 
/* 225 */     if (itemStack == null || itemStack.isEmpty() || itemStack.getItem() == null) return null; 
/* 226 */     if (itemStack.isBroken()) return null;
/*     */     
/* 228 */     Item item = itemStack.getItem();
/*     */     
/* 230 */     ItemContainer section = this.inventory.getSectionById(inventoryId);
/* 231 */     if (section == null) return null;
/*     */     
/* 233 */     if (item.getArmor() != null) {
/* 234 */       ItemStackSlotTransaction transaction = updateItemStackDurability(ref, itemStack, section, slotId, -item.getDurabilityLossOnHit(), componentAccessor);
/* 235 */       if (transaction.getSlotAfter().isBroken()) {
/* 236 */         this.statModifiersManager.setRecalculate(true);
/*     */       }
/* 238 */       return transaction;
/* 239 */     }  if (item.getWeapon() != null) {
/* 240 */       return updateItemStackDurability(ref, itemStack, section, slotId, -item.getDurabilityLossOnHit(), componentAccessor);
/*     */     }
/*     */     
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStackSlotTransaction updateItemStackDurability(@Nonnull Ref<EntityStore> ref, @Nonnull ItemStack itemStack, ItemContainer container, int slotId, double durabilityChange, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 253 */     ItemStack updatedItemStack = itemStack.withIncreasedDurability(durabilityChange);
/* 254 */     return container.replaceItemStackInSlot((short)slotId, itemStack, updatedItemStack);
/*     */   }
/*     */   
/*     */   public void invalidateEquipmentNetwork() {
/* 258 */     this.isEquipmentNetworkOutdated = true;
/*     */   }
/*     */   
/*     */   public boolean consumeEquipmentNetworkOutdated() {
/* 262 */     boolean temp = this.isEquipmentNetworkOutdated;
/* 263 */     this.isEquipmentNetworkOutdated = false;
/* 264 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public StatModifiersManager getStatModifiersManager() {
/* 272 */     return this.statModifiersManager;
/*     */   }
/*     */   
/*     */   public double getCurrentFallDistance() {
/* 276 */     return this.currentFallDistance;
/*     */   }
/*     */   
/*     */   public void setCurrentFallDistance(double currentFallDistance) {
/* 280 */     this.currentFallDistance = currentFallDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 286 */     return "LivingEntity{, " + super
/* 287 */       .toString() + "}";
/*     */   }
/*     */   
/*     */   protected abstract Inventory createDefaultInventory();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\LivingEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */