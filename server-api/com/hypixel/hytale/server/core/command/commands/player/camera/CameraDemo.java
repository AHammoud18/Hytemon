/*     */ package com.hypixel.hytale.server.core.command.commands.player.camera;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.math.iterator.BlockIterator;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.MouseButtonType;
/*     */ import com.hypixel.hytale.protocol.PositionDistanceOffsetType;
/*     */ import com.hypixel.hytale.protocol.ServerCameraSettings;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.CameraManager;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerMouseButtonEvent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CameraDemo {
/*  23 */   public static final CameraDemo INSTANCE = new CameraDemo();
/*     */   
/*  25 */   private final EventRegistry eventRegistry = new EventRegistry(new CopyOnWriteArrayList(), () -> this.isActive, "CameraDemo is not active!", (IEventRegistry)HytaleServer.get().getEventBus());
/*  26 */   private final ServerCameraSettings cameraSettings = createServerCameraSettings();
/*     */   
/*     */   private boolean isActive;
/*     */   
/*     */   public void activate() {
/*  31 */     if (this.isActive) {
/*     */       return;
/*     */     }
/*  34 */     this.eventRegistry.enable();
/*  35 */     this.isActive = true;
/*     */     
/*  37 */     this.eventRegistry.register(PlayerConnectEvent.class, event -> onAddNewPlayer(event.getPlayerRef()));
/*  38 */     this.eventRegistry.register(PlayerMouseButtonEvent.class, this::onPlayerMouseButton);
/*  39 */     this.eventRegistry.registerGlobal(PlayerInteractEvent.class, event -> event.setCancelled(true));
/*  40 */     Universe.get().getPlayers().forEach(this::onAddNewPlayer);
/*     */   }
/*     */   
/*     */   public void deactivate() {
/*  44 */     if (!this.isActive)
/*     */       return; 
/*  46 */     this.eventRegistry.shutdown();
/*     */ 
/*     */     
/*  49 */     Universe.get().getPlayers().forEach(p -> {
/*     */           CameraManager cameraManager = (CameraManager)p.getComponent(CameraManager.getComponentType());
/*     */           if (cameraManager != null)
/*     */             cameraManager.resetCamera(p); 
/*     */         });
/*  54 */     this.isActive = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void onAddNewPlayer(@Nonnull PlayerRef player) {
/*  59 */     player.getPacketHandler().writeNoCache((Packet)new SetServerCamera(ClientCameraView.Custom, true, this.cameraSettings));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onPlayerMouseButton(@Nonnull PlayerMouseButtonEvent event) {
/*  65 */     if ((event.getMouseButton()).state != MouseButtonState.Released) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     Ref<EntityStore> ref = event.getPlayerRef();
/*  70 */     if (!ref.isValid())
/*     */       return; 
/*  72 */     Store<EntityStore> store = ref.getStore();
/*  73 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  75 */     Vector3i targetBlock = event.getTargetBlock();
/*     */     
/*  77 */     CameraManager cameraManagerComponent = (CameraManager)store.getComponent(ref, CameraManager.getComponentType());
/*  78 */     assert cameraManagerComponent != null;
/*     */     
/*  80 */     Vector3i lastTargetBlock = cameraManagerComponent.getLastMouseButtonPressedPosition((event.getMouseButton()).mouseButtonType);
/*     */     
/*  82 */     if ((event.getMouseButton()).mouseButtonType == MouseButtonType.Middle) {
/*  83 */       if (event.getItemInHand() != null && event.getItemInHand().hasBlockType() && targetBlock != null) {
/*  84 */         String key = event.getItemInHand().getId();
/*  85 */         int blockId = BlockType.getAssetMap().getIndex(key);
/*  86 */         if (blockId == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key);
/*     */         
/*  88 */         if (!lastTargetBlock.equals(targetBlock)) {
/*  89 */           BlockIterator.iterateFromTo(lastTargetBlock, targetBlock, (x, y, z, px, py, pz, qx, qy, qz) -> {
/*     */                 world.getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setBlock(x, y, z, blockId);
/*     */                 return true;
/*     */               });
/*     */         } else {
/*  94 */           int x = targetBlock.getX();
/*  95 */           int z = targetBlock.getZ();
/*  96 */           world.getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setBlock(x, targetBlock.getY(), z, blockId);
/*     */         } 
/*     */       } 
/*  99 */     } else if ((event.getMouseButton()).mouseButtonType == MouseButtonType.Right) {
/* 100 */       if (!lastTargetBlock.equals(targetBlock)) {
/* 101 */         BlockIterator.iterateFromTo(lastTargetBlock, targetBlock, (x, y, z, px, py, pz, qx, qy, qz) -> {
/*     */               world.getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setBlock(x, y, z, 0);
/*     */               return true;
/*     */             });
/*     */       } else {
/* 106 */         int x = targetBlock.getX();
/* 107 */         int z = targetBlock.getZ();
/* 108 */         world.getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setBlock(x, targetBlock.getY(), z, 0);
/*     */       } 
/* 110 */     } else if ((event.getMouseButton()).mouseButtonType == MouseButtonType.Left && 
/* 111 */       event.getItemInHand() != null && event.getItemInHand().hasBlockType() && targetBlock != null) {
/* 112 */       String key = event.getItemInHand().getId();
/* 113 */       int blockId = BlockType.getAssetMap().getIndex(key);
/* 114 */       if (blockId == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key);
/*     */       
/* 116 */       if (!lastTargetBlock.equals(targetBlock)) {
/* 117 */         BlockIterator.iterateFromTo(lastTargetBlock
/* 118 */             .getX(), (lastTargetBlock.getY() + 1), lastTargetBlock.getZ(), targetBlock
/* 119 */             .getX(), (targetBlock.getY() + 1), targetBlock.getZ(), (x, y, z, px, py, pz, qx, qy, qz) -> {
/*     */               world.getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setBlock(x, y, z, blockId);
/*     */ 
/*     */               
/*     */               return true;
/*     */             });
/*     */       } else {
/* 126 */         int x = targetBlock.getX();
/* 127 */         int z = targetBlock.getZ();
/* 128 */         world.getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setBlock(x, targetBlock.getY() + 1, z, blockId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static ServerCameraSettings createServerCameraSettings() {
/* 136 */     ServerCameraSettings cameraSettings = new ServerCameraSettings();
/*     */     
/* 138 */     cameraSettings.positionLerpSpeed = 0.2F;
/* 139 */     cameraSettings.rotationLerpSpeed = 0.2F;
/* 140 */     cameraSettings.distance = 20.0F;
/* 141 */     cameraSettings.displayCursor = true;
/* 142 */     cameraSettings.sendMouseMotion = true;
/* 143 */     cameraSettings.isFirstPerson = false;
/* 144 */     cameraSettings.movementForceRotationType = MovementForceRotationType.Custom;
/* 145 */     cameraSettings.eyeOffset = true;
/* 146 */     cameraSettings.positionDistanceOffsetType = PositionDistanceOffsetType.DistanceOffset;
/* 147 */     cameraSettings.rotationType = RotationType.Custom;
/* 148 */     cameraSettings.rotation = new Direction(0.0F, -1.5707964F, 0.0F);
/* 149 */     cameraSettings.mouseInputType = MouseInputType.LookAtPlane;
/* 150 */     cameraSettings.planeNormal = new Vector3f(0.0F, 1.0F, 0.0F);
/*     */     
/* 152 */     return cameraSettings;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\camera\CameraDemo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */