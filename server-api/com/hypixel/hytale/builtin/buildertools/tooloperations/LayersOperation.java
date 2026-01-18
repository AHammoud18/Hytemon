/*     */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayersOperation
/*     */   extends ToolOperation
/*     */ {
/*     */   private final Vector3i depthDirection;
/*     */   private final int layerOneLength;
/*     */   private final int layerTwoLength;
/*     */   private final boolean enableLayerTwo;
/*     */   private final int layerThreeLength;
/*     */   private final boolean enableLayerThree;
/*     */   private final BlockPattern layerOneBlockPattern;
/*     */   private final BlockPattern layerTwoBlockPattern;
/*     */   private final BlockPattern layerThreeBlockPattern;
/*     */   private final int brushDensity;
/*     */   private final int maxDepthNecessary;
/*     */   private boolean failed;
/*     */   private final int layerTwoDepthEnd;
/*     */   private final int layerThreeDepthEnd;
/*     */   
/*     */   public LayersOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  37 */     super(ref, packet, componentAccessor);
/*     */     
/*  39 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/*  40 */     assert headRotationComponent != null;
/*     */     
/*  42 */     switch ((String)this.args.tool().get("aDirection")) { case "Up":
/*  43 */         this.depthDirection = Vector3i.UP; break;
/*  44 */       case "Down": this.depthDirection = Vector3i.DOWN; break;
/*  45 */       case "North": this.depthDirection = Vector3i.NORTH; break;
/*  46 */       case "South": this.depthDirection = Vector3i.SOUTH; break;
/*  47 */       case "East": this.depthDirection = Vector3i.EAST; break;
/*  48 */       case "West": this.depthDirection = Vector3i.WEST; break;
/*  49 */       case "Camera": this.depthDirection = headRotationComponent.getAxisDirection(); break;
/*  50 */       default: this.depthDirection = Vector3i.DOWN;
/*     */         break; }
/*     */     
/*  53 */     this.brushDensity = ((Integer)this.args.tool().get("jBrushDensity")).intValue();
/*     */     
/*  55 */     this.layerOneLength = ((Integer)this.args.tool().get("bLayerOneLength")).intValue();
/*  56 */     this.layerTwoLength = ((Integer)this.args.tool().get("eLayerTwoLength")).intValue();
/*  57 */     this.layerThreeLength = ((Integer)this.args.tool().get("hLayerThreeLength")).intValue();
/*     */     
/*  59 */     this.layerOneBlockPattern = (BlockPattern)this.args.tool().get("cLayerOneMaterial");
/*  60 */     this.layerTwoBlockPattern = (BlockPattern)this.args.tool().get("fLayerTwoMaterial");
/*  61 */     this.layerThreeBlockPattern = (BlockPattern)this.args.tool().get("iLayerThreeMaterial");
/*     */     
/*  63 */     this.enableLayerTwo = ((Boolean)this.args.tool().get("dEnableLayerTwo")).booleanValue();
/*  64 */     this.enableLayerThree = ((Boolean)this.args.tool().get("gEnableLayerThree")).booleanValue();
/*     */     
/*  66 */     this.maxDepthNecessary = this.layerOneLength + (this.enableLayerTwo ? this.layerTwoLength : 0) + (this.enableLayerThree ? this.layerThreeLength : 0);
/*     */     
/*  68 */     this.layerTwoDepthEnd = this.layerOneLength + this.layerTwoLength;
/*  69 */     this.layerThreeDepthEnd = this.layerTwoDepthEnd + this.layerThreeLength;
/*     */     
/*  71 */     if (this.enableLayerThree && !this.enableLayerTwo) {
/*  72 */       player.sendMessage(Message.translation("server.builderTools.layerOperation.layerTwoRequired"));
/*  73 */       this.failed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean execute0(int x, int y, int z) {
/*  80 */     if (this.failed) {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     if (this.random.nextInt(100) > this.brushDensity) {
/*  85 */       return true;
/*     */     }
/*     */     
/*  88 */     int currentBlock = this.edit.getBlock(x, y, z);
/*     */     
/*  90 */     if (currentBlock <= 0) {
/*  91 */       return true;
/*     */     }
/*     */     
/*  94 */     if (this.depthDirection.x == 1) {
/*  95 */       for (int i = 0; i < this.maxDepthNecessary; i++) {
/*  96 */         if (this.edit.getBlock(x - i - 1, y, z) <= 0 && attemptSetBlock(x, y, z, i)) return true; 
/*     */       } 
/*  98 */     } else if (this.depthDirection.x == -1) {
/*  99 */       for (int i = 0; i < this.maxDepthNecessary; i++) {
/* 100 */         if (this.edit.getBlock(x + i + 1, y, z) <= 0 && attemptSetBlock(x, y, z, i)) return true; 
/*     */       } 
/* 102 */     } else if (this.depthDirection.y == 1) {
/* 103 */       for (int i = 0; i < this.maxDepthNecessary; i++) {
/* 104 */         if (this.edit.getBlock(x, y - i - 1, z) <= 0 && attemptSetBlock(x, y, z, i)) return true; 
/*     */       } 
/* 106 */     } else if (this.depthDirection.y == -1) {
/* 107 */       for (int i = 0; i < this.maxDepthNecessary; i++) {
/* 108 */         if (this.edit.getBlock(x, y + i + 1, z) <= 0 && attemptSetBlock(x, y, z, i)) return true; 
/*     */       } 
/* 110 */     } else if (this.depthDirection.z == 1) {
/* 111 */       for (int i = 0; i < this.maxDepthNecessary; i++) {
/* 112 */         if (this.edit.getBlock(x, y, z - i - 1) <= 0 && attemptSetBlock(x, y, z, i)) return true; 
/*     */       } 
/* 114 */     } else if (this.depthDirection.z == -1) {
/* 115 */       for (int i = 0; i < this.maxDepthNecessary; i++) {
/* 116 */         if (this.edit.getBlock(x, y, z + i + 1) <= 0 && attemptSetBlock(x, y, z, i)) return true;
/*     */       
/*     */       } 
/*     */     } 
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public boolean attemptSetBlock(int x, int y, int z, int depth) {
/* 124 */     if (depth < this.layerOneLength) {
/* 125 */       this.edit.setBlock(x, y, z, this.layerOneBlockPattern.nextBlock(this.random));
/* 126 */       return true;
/* 127 */     }  if (this.enableLayerTwo && depth < this.layerTwoDepthEnd && !this.layerThreeBlockPattern.isEmpty()) {
/* 128 */       this.edit.setBlock(x, y, z, this.layerTwoBlockPattern.nextBlock(this.random));
/* 129 */       return true;
/* 130 */     }  if (this.enableLayerThree && depth < this.layerThreeDepthEnd && !this.layerThreeBlockPattern.isEmpty()) {
/* 131 */       this.edit.setBlock(x, y, z, this.layerThreeBlockPattern.nextBlock(this.random));
/* 132 */       return true;
/*     */     } 
/* 134 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\LayersOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */