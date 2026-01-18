/*     */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorSearchRay;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.PositionProvider;
/*     */ import com.hypixel.hytale.server.npc.util.RayBlockHitTest;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SensorSearchRay extends SensorBase {
/*     */   protected final int id;
/*     */   protected final float angle;
/*     */   protected final double range;
/*  30 */   protected final PositionProvider positionProvider = new PositionProvider(); protected final int blockSet;
/*     */   protected final float minRetestAngle;
/*  32 */   protected final Vector3d lastCheckedPosition = new Vector3d(); protected final double minRetestMoveSquared; protected final double throttleTime;
/*  33 */   protected float lastCheckedYaw = Float.MAX_VALUE;
/*     */   protected short lastBlockRevision;
/*     */   protected double throttleTimeRemaining;
/*     */   
/*     */   public SensorSearchRay(@Nonnull BuilderSensorSearchRay builder, @Nonnull BuilderSupport support) {
/*  38 */     super((BuilderSensorBase)builder);
/*  39 */     this.id = builder.getId(support);
/*  40 */     this.angle = -builder.getAngle(support);
/*  41 */     this.range = builder.getRange(support);
/*  42 */     this.blockSet = builder.getBlockSet(support);
/*  43 */     this.minRetestAngle = builder.getMinRetestAngle(support);
/*  44 */     double minRetestMove = builder.getMinRetestMove(support);
/*  45 */     this.minRetestMoveSquared = minRetestMove * minRetestMove;
/*  46 */     this.throttleTime = builder.getThrottleTime(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  51 */     if (!super.matches(ref, role, dt, store)) {
/*  52 */       this.positionProvider.clear();
/*  53 */       return false;
/*     */     } 
/*     */     
/*  56 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  58 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  59 */     assert transformComponent != null;
/*     */     
/*  61 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  62 */     assert headRotationComponent != null;
/*     */     
/*  64 */     Vector3d position = transformComponent.getPosition();
/*  65 */     Vector3f headRotation = headRotationComponent.getRotation();
/*     */     
/*  67 */     Vector3d cachedPosition = role.getWorldSupport().getCachedSearchRayPosition(this.id);
/*  68 */     if (!cachedPosition.equals(Vector3d.MIN)) {
/*     */ 
/*     */       
/*  71 */       WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(cachedPosition.x, cachedPosition.z));
/*  72 */       if (chunk != null) {
/*     */         
/*  74 */         BlockSection section = chunk.getBlockChunk().getSectionAtBlockY(MathUtil.floor(cachedPosition.y));
/*  75 */         if (section.getLocalChangeCounter() == this.lastBlockRevision) {
/*  76 */           this.positionProvider.setTarget(cachedPosition);
/*  77 */           return true;
/*     */         } 
/*     */ 
/*     */         
/*  81 */         cachedPosition.assign(Vector3d.MIN);
/*  82 */         this.positionProvider.clear();
/*     */       } 
/*  84 */     } else if ((this.throttleTimeRemaining -= dt) > 0.0D && Math.abs(headRotation.getYaw() - this.lastCheckedYaw) <= this.minRetestAngle && position
/*  85 */       .distanceSquaredTo(this.lastCheckedPosition) <= this.minRetestMoveSquared) {
/*     */       
/*  87 */       this.positionProvider.clear();
/*  88 */       return false;
/*     */     } 
/*     */     
/*  91 */     RayBlockHitTest blockRaySearch = RayBlockHitTest.THREAD_LOCAL.get();
/*  92 */     if (!blockRaySearch.init(ref, this.blockSet, this.angle, (ComponentAccessor)store)) {
/*  93 */       cachedPosition.assign(Vector3d.MIN);
/*  94 */       this.positionProvider.clear();
/*  95 */       blockRaySearch.clear();
/*  96 */       return false;
/*     */     } 
/*     */     
/*  99 */     this.lastCheckedPosition.assign(position);
/* 100 */     this.lastCheckedYaw = headRotation.getYaw();
/* 101 */     this.throttleTimeRemaining = this.throttleTime;
/*     */     
/* 103 */     boolean result = blockRaySearch.run(this.range);
/* 104 */     if (result) {
/* 105 */       this.lastBlockRevision = blockRaySearch.getLastBlockRevision();
/* 106 */       Vector3d targetPosition = blockRaySearch.getHitPosition();
/* 107 */       cachedPosition.assign(targetPosition.x + 0.5D, targetPosition.y + 0.5D, targetPosition.z + 0.5D);
/* 108 */       this.positionProvider.setTarget(cachedPosition);
/*     */     } else {
/* 110 */       cachedPosition.assign(Vector3d.MIN);
/* 111 */       this.positionProvider.clear();
/*     */     } 
/* 113 */     blockRaySearch.clear();
/* 114 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/* 119 */     return (InfoProvider)this.positionProvider;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorSearchRay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */