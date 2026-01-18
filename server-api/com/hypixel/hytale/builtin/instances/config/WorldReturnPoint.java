/*    */ package com.hypixel.hytale.builtin.instances.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldReturnPoint
/*    */ {
/*    */   public static final BuilderCodec<WorldReturnPoint> CODEC;
/*    */   private UUID world;
/*    */   private Transform returnPoint;
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WorldReturnPoint.class, WorldReturnPoint::new).documentation("A world/location pair that is used as a place to return players to.")).append(new KeyedCodec("World", (Codec)Codec.UUID_BINARY), (o, i) -> o.world = i, o -> o.world).documentation("The UUID of the world to return the player to.").addValidator(Validators.nonNull()).add()).append(new KeyedCodec("ReturnPoint", (Codec)Transform.CODEC), (o, i) -> o.returnPoint = i, o -> o.returnPoint).documentation("The location to send the player to.").addValidator(Validators.nonNull()).add()).append(new KeyedCodec("ReturnOnReconnect", (Codec)Codec.BOOLEAN), (o, i) -> o.returnOnReconnect = i.booleanValue(), o -> Boolean.valueOf(o.returnOnReconnect)).documentation("Whether this point should be triggered when a player reconnects into a world.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean returnOnReconnect = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldReturnPoint(UUID world, Transform returnPoint, boolean returnOnReconnect) {
/* 52 */     this.world = world;
/* 53 */     this.returnPoint = returnPoint;
/* 54 */     this.returnOnReconnect = returnOnReconnect;
/*    */   }
/*    */   
/*    */   public UUID getWorld() {
/* 58 */     return this.world;
/*    */   }
/*    */   
/*    */   public void setWorld(UUID world) {
/* 62 */     this.world = world;
/*    */   }
/*    */   
/*    */   public Transform getReturnPoint() {
/* 66 */     return this.returnPoint;
/*    */   }
/*    */   
/*    */   public void setReturnPoint(Transform returnPoint) {
/* 70 */     this.returnPoint = returnPoint;
/*    */   }
/*    */   
/*    */   public boolean isReturnOnReconnect() {
/* 74 */     return this.returnOnReconnect;
/*    */   }
/*    */   
/*    */   public void setReturnOnReconnect(boolean returnOnReconnect) {
/* 78 */     this.returnOnReconnect = returnOnReconnect;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WorldReturnPoint clone() {
/* 84 */     return new WorldReturnPoint(this.world, this.returnPoint, this.returnOnReconnect);
/*    */   }
/*    */   
/*    */   public WorldReturnPoint() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\config\WorldReturnPoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */