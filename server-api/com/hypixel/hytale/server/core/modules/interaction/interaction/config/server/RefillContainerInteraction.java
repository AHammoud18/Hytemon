/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RefillContainerInteraction extends SimpleBlockInteraction {
/*     */   public static final BuilderCodec<RefillContainerInteraction> CODEC;
/*     */   protected Map<String, RefillState> refillStateMap;
/*     */   @Nullable
/*     */   protected int[] allowedFluidIds;
/*     */   @Nullable
/*     */   protected Int2ObjectMap<String> fluidToState;
/*     */   
/*     */   static {
/*  51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RefillContainerInteraction.class, RefillContainerInteraction::new, SimpleBlockInteraction.CODEC).documentation("Refills a container item that is currently held.")).appendInherited(new KeyedCodec("States", (Codec)new MapCodec((Codec)RefillState.CODEC, java.util.HashMap::new)), (interaction, value) -> interaction.refillStateMap = value, interaction -> interaction.refillStateMap, (o, p) -> o.refillStateMap = p.refillStateMap).addValidator(Validators.nonNull()).add()).afterDecode(refillContainerInteraction -> { refillContainerInteraction.allowedFluidIds = null; refillContainerInteraction.fluidToState = null; })).build();
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
/*     */   protected int[] getAllowedFluidIds() {
/*  63 */     if (this.allowedFluidIds != null) return this.allowedFluidIds;
/*     */     
/*  65 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       .allowedFluidIds = this.refillStateMap.values().stream().map(RefillState::getAllowedFluids).flatMap(Arrays::stream).mapToInt(key -> Fluid.getAssetMap().getIndex(key)).sorted().toArray();
/*     */     
/*  72 */     return this.allowedFluidIds;
/*     */   }
/*     */   
/*     */   protected Int2ObjectMap<String> getFluidToState() {
/*  76 */     if (this.fluidToState != null) return this.fluidToState;
/*     */     
/*  78 */     this.fluidToState = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/*  79 */     this.refillStateMap.forEach((s, refillState) -> {
/*     */           for (String key : refillState.getAllowedFluids()) {
/*     */             this.fluidToState.put(Fluid.getAssetMap().getIndex(key), s);
/*     */           }
/*     */         });
/*     */     
/*  85 */     return this.fluidToState;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*     */     LivingEntity livingEntity;
/*  91 */     Ref<EntityStore> ref = context.getEntity();
/*     */ 
/*     */     
/*  94 */     Entity entity = EntityUtils.getEntity(ref, (ComponentAccessor)commandBuffer); if (entity instanceof LivingEntity) { livingEntity = (LivingEntity)entity; }
/*     */     else { return; }
/*  96 */      BlockPosition blockPosition_ = (context.getClientState()).blockPosition;
/*  97 */     InteractionSyncData state = context.getState();
/*     */     
/*  99 */     if (blockPosition_ == null) {
/* 100 */       state.state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     Ref<ChunkStore> section = world.getChunkStore().getChunkSectionReference(ChunkUtil.chunkCoordinate(blockPosition_.x), ChunkUtil.chunkCoordinate(blockPosition_.y), ChunkUtil.chunkCoordinate(blockPosition_.z));
/* 105 */     if (section == null)
/* 106 */       return;  FluidSection fluidSection = (FluidSection)section.getStore().getComponent(section, FluidSection.getComponentType());
/* 107 */     if (fluidSection == null)
/*     */       return; 
/* 109 */     int fluidId = fluidSection.getFluidId(blockPosition_.x, blockPosition_.y, blockPosition_.z);
/* 110 */     int[] allowedBlockIds = getAllowedFluidIds();
/* 111 */     if (allowedBlockIds != null && Arrays.binarySearch(allowedBlockIds, fluidId) < 0) {
/* 112 */       state.state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     String newState = (String)getFluidToState().get(fluidId);
/* 117 */     if (newState == null) {
/* 118 */       state.state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 122 */     ItemStack current = context.getHeldItem();
/* 123 */     Item newItemAsset = current.getItem().getItemForState(newState);
/* 124 */     if (newItemAsset == null) {
/* 125 */       state.state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 129 */     RefillState refillState = this.refillStateMap.get(newState);
/*     */     
/* 131 */     if (newItemAsset.getId().equals(current.getItemId())) {
/*     */       
/* 133 */       if (refillState != null) {
/* 134 */         double newDurability = MathUtil.maxValue(refillState.durability, current.getMaxDurability());
/*     */ 
/*     */         
/* 137 */         if (newDurability <= current.getDurability()) {
/* 138 */           state.state = InteractionState.Failed;
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 143 */         ItemStack newItem = current.withIncreasedDurability(newDurability);
/* 144 */         ItemStackSlotTransaction transaction = context.getHeldItemContainer().setItemStackForSlot((short)context.getHeldItemSlot(), newItem);
/* 145 */         if (!transaction.succeeded()) {
/* 146 */           state.state = InteractionState.Failed;
/*     */           return;
/*     */         } 
/* 149 */         context.setHeldItem(newItem);
/*     */       } 
/*     */     } else {
/*     */       
/* 153 */       ItemStackSlotTransaction removeEmptyTransaction = context.getHeldItemContainer().removeItemStackFromSlot((short)context.getHeldItemSlot(), current, 1);
/* 154 */       if (!removeEmptyTransaction.succeeded()) {
/* 155 */         state.state = InteractionState.Failed;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 160 */       ItemStack refilledContainer = new ItemStack(newItemAsset.getId(), 1);
/* 161 */       if (refillState != null && refillState.durability > 0.0D)
/*     */       {
/* 163 */         refilledContainer = refilledContainer.withDurability(refillState.durability);
/*     */       }
/*     */       
/* 166 */       if (current.getQuantity() == 1) {
/*     */         
/* 168 */         ItemStackSlotTransaction addFilledTransaction = context.getHeldItemContainer().setItemStackForSlot((short)context.getHeldItemSlot(), refilledContainer);
/*     */         
/* 170 */         if (!addFilledTransaction.succeeded()) {
/* 171 */           state.state = InteractionState.Failed;
/*     */           return;
/*     */         } 
/* 174 */         context.setHeldItem(refilledContainer);
/*     */       } else {
/* 176 */         SimpleItemContainer.addOrDropItemStack((ComponentAccessor)commandBuffer, ref, (ItemContainer)livingEntity.getInventory().getCombinedHotbarFirst(), refilledContainer);
/*     */         
/* 178 */         context.setHeldItem(context.getHeldItemContainer().getItemStack((short)context.getHeldItemSlot()));
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     if (refillState != null && refillState.getTransformFluid() != null) {
/* 183 */       int transformedFluid = Fluid.getFluidIdOrUnknown(refillState.getTransformFluid(), "Unknown fluid %s", new Object[] { refillState.getTransformFluid() });
/* 184 */       boolean placed = fluidSection.setFluid(blockPosition_.x, blockPosition_.y, blockPosition_.z, transformedFluid, (byte)((Fluid)Fluid.getAssetMap().getAsset(transformedFluid)).getMaxFluidLevel());
/* 185 */       if (!placed) {
/* 186 */         state.state = InteractionState.Failed;
/*     */       }
/*     */       
/* 189 */       world.performBlockUpdate(blockPosition_.x, blockPosition_.y, blockPosition_.z);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 202 */     return (Interaction)new com.hypixel.hytale.protocol.RefillContainerInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 207 */     super.configurePacket(packet);
/* 208 */     com.hypixel.hytale.protocol.RefillContainerInteraction p = (com.hypixel.hytale.protocol.RefillContainerInteraction)packet;
/* 209 */     p.refillFluids = getAllowedFluidIds();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 215 */     return "RefillContainerInteraction{refillStateMap=" + String.valueOf(this.refillStateMap) + ", allowedBlockIds=" + 
/*     */       
/* 217 */       Arrays.toString(this.allowedFluidIds) + ", blockToState=" + String.valueOf(this.fluidToState) + "} " + super
/*     */       
/* 219 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class RefillState
/*     */   {
/*     */     public static final BuilderCodec<RefillState> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String[] allowedFluids;
/*     */ 
/*     */     
/*     */     protected String transformFluid;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 239 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RefillState.class, RefillState::new).append(new KeyedCodec("AllowedFluids", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (interaction, value) -> interaction.allowedFluids = value, interaction -> interaction.allowedFluids).addValidator(Validators.nonNull()).add()).addField(new KeyedCodec("TransformFluid", (Codec)Codec.STRING), (interaction, value) -> interaction.transformFluid = value, interaction -> interaction.transformFluid)).addField(new KeyedCodec("Durability", (Codec)Codec.DOUBLE), (interaction, value) -> interaction.durability = value.doubleValue(), interaction -> Double.valueOf(interaction.durability))).build();
/*     */     }
/*     */ 
/*     */     
/* 243 */     protected double durability = -1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String[] getAllowedFluids() {
/* 249 */       return this.allowedFluids;
/*     */     }
/*     */     
/*     */     public String getTransformFluid() {
/* 253 */       return this.transformFluid;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getDurability() {
/* 260 */       return this.durability;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 267 */       return "RefillState{allowedFluids=" + Arrays.toString((Object[])this.allowedFluids) + ", transformFluid='" + this.transformFluid + "', durability=" + this.durability + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\RefillContainerInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */