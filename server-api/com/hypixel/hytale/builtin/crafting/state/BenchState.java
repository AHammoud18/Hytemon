/*     */ package com.hypixel.hytale.builtin.crafting.state;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.BenchUpgradeRequirement;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.DestroyableBlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BenchState
/*     */   extends BlockState
/*     */   implements DestroyableBlockState
/*     */ {
/*     */   public static BuilderCodec<BenchState> CODEC;
/*     */   
/*     */   static {
/*  41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchState.class, BenchState::new, BlockState.BASE_CODEC).appendInherited(new KeyedCodec("TierLevel", (Codec)Codec.INTEGER), (state, o) -> state.tierLevel = o.intValue(), state -> Integer.valueOf(state.tierLevel), (state, parent) -> state.tierLevel = parent.tierLevel).add()).appendInherited(new KeyedCodec("UpgradeItems", (Codec)new ArrayCodec((Codec)ItemStack.CODEC, x$0 -> new ItemStack[x$0])), (state, o) -> state.upgradeItems = o, state -> state.upgradeItems, (state, parent) -> state.upgradeItems = parent.upgradeItems).add()).build();
/*     */   }
/*  43 */   private int tierLevel = 1;
/*     */   
/*     */   public int getTierLevel() {
/*  46 */     return this.tierLevel;
/*     */   }
/*     */   
/*  49 */   protected ItemStack[] upgradeItems = ItemStack.EMPTY_ARRAY;
/*     */   
/*     */   protected Bench bench;
/*     */ 
/*     */   
/*     */   public boolean initialize(@Nonnull BlockType blockType) {
/*  55 */     if (!super.initialize(blockType)) return false;
/*     */     
/*  57 */     this.bench = blockType.getBench();
/*     */     
/*  59 */     if (this.bench == null) {
/*  60 */       if (this.upgradeItems.length > 0) {
/*  61 */         dropUpgradeItems();
/*     */       }
/*  63 */       return false;
/*     */     } 
/*     */     
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public void addUpgradeItems(List<ItemStack> consumed) {
/*  70 */     consumed.addAll(Arrays.asList(this.upgradeItems));
/*  71 */     this.upgradeItems = (ItemStack[])consumed.toArray(x$0 -> new ItemStack[x$0]);
/*  72 */     markNeedsSave();
/*     */   }
/*     */   
/*     */   private void dropUpgradeItems() {
/*  76 */     if (this.upgradeItems.length == 0)
/*     */       return; 
/*  78 */     World world = getChunk().getWorld();
/*  79 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*  80 */     Vector3d dropPosition = getBlockPosition().toVector3d().add(0.5D, 0.0D, 0.5D);
/*  81 */     Holder[] arrayOfHolder = ItemComponent.generateItemDrops((ComponentAccessor)entityStore, List.of(this.upgradeItems), dropPosition, Vector3f.ZERO);
/*  82 */     if (arrayOfHolder.length > 0) {
/*  83 */       world.execute(() -> entityStore.addEntities(itemEntityHolders, AddReason.SPAWN));
/*     */     }
/*     */     
/*  86 */     this.upgradeItems = ItemStack.EMPTY_ARRAY;
/*     */   }
/*     */   
/*     */   public Bench getBench() {
/*  90 */     return this.bench;
/*     */   }
/*     */   
/*     */   public void setTierLevel(int newTierLevel) {
/*  94 */     if (this.tierLevel != newTierLevel) {
/*  95 */       this.tierLevel = newTierLevel;
/*  96 */       onTierLevelChange();
/*  97 */       markNeedsSave();
/*     */     } 
/*     */   }
/*     */   
/*     */   public BenchUpgradeRequirement getNextLevelUpgradeMaterials() {
/* 102 */     return this.bench.getUpgradeRequirement(this.tierLevel);
/*     */   }
/*     */   
/*     */   protected void onTierLevelChange() {
/* 106 */     getChunk().setBlockInteractionState(getBlockPosition(), getBaseBlockType(), getTierStateName());
/*     */   }
/*     */   
/*     */   public BlockType getBaseBlockType() {
/* 110 */     BlockType currentBlockType = getBlockType();
/*     */     
/* 112 */     String baseBlockKey = currentBlockType.getDefaultStateKey();
/* 113 */     BlockType baseBlockType = (BlockType)BlockType.getAssetMap().getAsset(baseBlockKey);
/* 114 */     if (baseBlockType == null) baseBlockType = currentBlockType;
/*     */     
/* 116 */     return baseBlockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTierStateName() {
/* 124 */     return (this.tierLevel > 1) ? ("Tier" + this.tierLevel) : "default";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDestroy() {
/* 129 */     dropUpgradeItems();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\state\BenchState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */