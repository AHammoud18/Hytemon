/*     */ package com.hypixel.hytale.builtin.buildertools.tooloperations;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolLaserPointer;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.world.PlayerUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
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
/*     */ public class LaserPointerOperation
/*     */   extends ToolOperation
/*     */ {
/*     */   private static final double MAX_DISTANCE = 128.0D;
/*     */   
/*     */   public LaserPointerOperation(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull BuilderToolOnUseInteraction packet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  34 */     super(ref, packet, componentAccessor);
/*     */     
/*     */     int laserColor, duration;
/*  37 */     String colorText = (String)this.args.tool().get("LaserColor");
/*     */     
/*     */     try {
/*  40 */       laserColor = ColorParseUtil.hexStringToRGBInt(colorText);
/*  41 */     } catch (NumberFormatException e) {
/*  42 */       player.sendMessage(Message.translation("server.builderTools.laserPointer.colorParseError").param("value", colorText));
/*  43 */       throw e;
/*     */     } 
/*     */ 
/*     */     
/*  47 */     Object durationObj = this.args.tool().get("Duration");
/*     */     
/*  49 */     if (durationObj instanceof Integer) {
/*  50 */       duration = ((Integer)durationObj).intValue();
/*  51 */     } else if (durationObj instanceof String) {
/*     */       try {
/*  53 */         duration = Integer.parseInt((String)durationObj);
/*  54 */       } catch (NumberFormatException e) {
/*  55 */         player.sendMessage(Message.translation("server.builderTools.laserPointer.durationParseError").param("value", String.valueOf(durationObj)));
/*  56 */         throw e;
/*     */       } 
/*     */     } else {
/*  59 */       duration = 300;
/*     */     } 
/*     */     
/*  62 */     NetworkId networkIdComponent = (NetworkId)componentAccessor.getComponent(ref, NetworkId.getComponentType());
/*  63 */     assert networkIdComponent != null;
/*     */ 
/*     */     
/*  66 */     int playerNetworkId = networkIdComponent.getId();
/*     */ 
/*     */     
/*  69 */     Transform lookVec = TargetUtil.getLook(ref, componentAccessor);
/*  70 */     Vector3d lookVecPosition = lookVec.getPosition();
/*  71 */     Vector3d lookVecDirection = lookVec.getDirection();
/*     */ 
/*     */     
/*  74 */     Vector3d hitLocation = TargetUtil.getTargetLocation(ref, blockId -> (blockId != 0), 128.0D, componentAccessor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     Vector3d endLocation = (hitLocation != null) ? hitLocation : lookVecPosition.add(lookVecDirection.scale(128.0D));
/*     */ 
/*     */     
/*  85 */     BuilderToolLaserPointer laserPacket = new BuilderToolLaserPointer();
/*  86 */     laserPacket.playerNetworkId = playerNetworkId;
/*  87 */     laserPacket.startX = (float)lookVecPosition.x;
/*  88 */     laserPacket.startY = (float)lookVecPosition.y;
/*  89 */     laserPacket.startZ = (float)lookVecPosition.z;
/*  90 */     laserPacket.endX = (float)endLocation.x;
/*  91 */     laserPacket.endY = (float)endLocation.y;
/*  92 */     laserPacket.endZ = (float)endLocation.z;
/*  93 */     laserPacket.color = laserColor;
/*  94 */     laserPacket.durationMs = duration;
/*     */ 
/*     */     
/*  97 */     PlayerUtil.broadcastPacketToPlayers(componentAccessor, (Packet)laserPacket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(ComponentAccessor<EntityStore> componentAccessor) {}
/*     */ 
/*     */ 
/*     */   
/*     */   boolean execute0(int x, int y, int z) {
/* 108 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\LaserPointerOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */