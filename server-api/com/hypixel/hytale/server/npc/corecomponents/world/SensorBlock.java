/*     */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.blockpositions.IBlockPositionData;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.resource.ResourceView;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.BlockTarget;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorBlock;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.PositionProvider;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SensorBlock extends SensorBase {
/*     */   protected final double range;
/*     */   protected final double yRange;
/*  31 */   protected final PositionProvider positionProvider = new PositionProvider(); protected final int blockSet; protected final boolean pickRandom; protected final boolean reserveBlock;
/*     */   
/*     */   public SensorBlock(@Nonnull BuilderSensorBlock builder, @Nonnull BuilderSupport support) {
/*  34 */     super((BuilderSensorBase)builder);
/*  35 */     this.range = builder.getRange(support);
/*  36 */     this.yRange = builder.getYRange(support);
/*  37 */     this.blockSet = builder.getBlockSet(support);
/*  38 */     this.pickRandom = builder.isPickRandom(support);
/*  39 */     this.reserveBlock = builder.isReserveBlock(support);
/*  40 */     support.requireBlockTypeBlackboard(this.blockSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  45 */     if (!super.matches(ref, role, dt, store)) {
/*  46 */       this.positionProvider.clear();
/*  47 */       return false;
/*     */     } 
/*     */     
/*  50 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  51 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  52 */     assert transformComponent != null;
/*     */     
/*  54 */     Vector3d entityPos = transformComponent.getPosition();
/*  55 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/*  56 */     assert npcComponent != null;
/*     */ 
/*     */     
/*  59 */     BlockTarget target = role.getWorldSupport().getCachedBlockTarget(this.blockSet);
/*  60 */     Vector3d position = target.getPosition();
/*  61 */     if (!position.equals(Vector3d.MIN)) {
/*     */ 
/*     */ 
/*     */       
/*  65 */       WorldChunk targetChunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(position.x, position.z));
/*  66 */       if (targetChunk != null) {
/*     */         
/*  68 */         BlockSection section = targetChunk.getBlockChunk().getSectionAtBlockY(MathUtil.floor(position.y));
/*  69 */         if (section.getLocalChangeCounter() == target.getChunkChangeRevision() || section
/*  70 */           .get(MathUtil.floor(position.x), MathUtil.floor(position.y), MathUtil.floor(position.z)) == target.getFoundBlockType()) {
/*  71 */           if (Math.abs(entityPos.y - position.y) > this.yRange || entityPos.distanceSquaredTo(position) > this.range * this.range) {
/*  72 */             this.positionProvider.clear();
/*  73 */             return false;
/*     */           } 
/*  75 */           this.positionProvider.setTarget(position);
/*  76 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     if (target.isActive()) target.reset(npcComponent);
/*     */     
/*  83 */     BlockTypeView blackboard = npcComponent.getBlockTypeBlackboardView(ref, store);
/*  84 */     IBlockPositionData blockData = blackboard.findBlock(this.blockSet, this.range, this.yRange, this.pickRandom, ref, (ComponentAccessor)store);
/*     */     
/*  86 */     if (blockData == null) {
/*  87 */       this.positionProvider.clear();
/*  88 */       return false;
/*     */     } 
/*     */     
/*  91 */     position.assign(blockData.getXCentre(), blockData.getYCentre(), blockData.getZCentre());
/*     */     
/*  93 */     int blockTypeId = blockData.getBlockType();
/*  94 */     target.setFoundBlockType(blockTypeId);
/*  95 */     target.setChunkChangeRevision(blockData.getChunkSection().getLocalChangeCounter());
/*     */     
/*  97 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockTypeId);
/*     */     
/*  99 */     if (this.reserveBlock || !blockType.isAllowsMultipleUsers()) {
/*     */ 
/*     */       
/* 102 */       ResourceView resourceView = (ResourceView)((Blackboard)store.getResource(Blackboard.getResourceType())).getView(ResourceView.class, ResourceView.indexViewFromWorldPosition(position));
/* 103 */       resourceView.reserveBlock(npcComponent, blockData.getX(), blockData.getY(), blockData.getZ());
/* 104 */       target.setReservationHolder(resourceView);
/* 105 */       Blackboard.LOGGER.at(Level.FINE).log("Entity %s reserved block from set %s at %s", npcComponent.getRoleName(), Integer.valueOf(this.blockSet), position);
/*     */     } 
/*     */     
/* 108 */     Blackboard.LOGGER.at(Level.FINE).log("Entity %s found block from set %s at %s", npcComponent.getRoleName(), Integer.valueOf(this.blockSet), position);
/* 109 */     this.positionProvider.setTarget(position);
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/* 115 */     return (InfoProvider)this.positionProvider;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */