/*     */ package com.hypixel.hytale.server.core.entity.entities.player.windows;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.protocol.packets.window.WindowType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public abstract class BlockWindow
/*     */   extends Window
/*     */   implements ValidatedWindow
/*     */ {
/*     */   private static final float MAX_DISTANCE = 7.0F;
/*     */   protected final int x;
/*     */   protected final int y;
/*     */   protected final int z;
/*     */   @Nonnull
/*     */   protected BlockType blockType;
/*     */   protected final int rotationIndex;
/*  54 */   private double maxDistance = 7.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private double maxDistanceSqr = this.maxDistance * this.maxDistance;
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
/*     */   public BlockWindow(@Nonnull WindowType windowType, int x, int y, int z, int rotationIndex, @Nonnull BlockType blockType) {
/*  72 */     super(windowType);
/*  73 */     this.x = x;
/*  74 */     this.y = y;
/*  75 */     this.z = z;
/*  76 */     this.rotationIndex = rotationIndex;
/*  77 */     this.blockType = blockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  84 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  91 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  98 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRotationIndex() {
/* 105 */     return this.rotationIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockType getBlockType() {
/* 113 */     return this.blockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxDistance(double maxDistance) {
/* 122 */     this.maxDistance = maxDistance;
/* 123 */     this.maxDistanceSqr = maxDistance * maxDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxDistance() {
/* 130 */     return this.maxDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate() {
/* 135 */     PlayerRef playerRef = getPlayerRef();
/* 136 */     if (playerRef == null) {
/* 137 */       return false;
/*     */     }
/*     */     
/* 140 */     Ref<EntityStore> ref = playerRef.getReference();
/* 141 */     if (ref == null) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     Store<EntityStore> store = ref.getStore();
/* 146 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 147 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*     */ 
/*     */     
/* 150 */     if (transformComponent.getPosition().distanceSquaredTo(this.x, this.y, this.z) > this.maxDistanceSqr) return false;
/*     */ 
/*     */     
/* 153 */     WorldChunk worldChunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(this.x, this.z));
/* 154 */     if (worldChunk == null) return false; 
/* 155 */     BlockType currentBlockType = worldChunk.getBlockType(this.x, this.y, this.z);
/* 156 */     if (currentBlockType == null) return false; 
/* 157 */     Item currentItem = currentBlockType.getItem();
/* 158 */     if (currentItem == null) return false; 
/* 159 */     return currentItem.equals(this.blockType.getItem());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\windows\BlockWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */