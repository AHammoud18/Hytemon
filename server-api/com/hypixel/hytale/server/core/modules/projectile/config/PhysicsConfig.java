/*    */ package com.hypixel.hytale.server.core.modules.projectile.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.PhysicsConfig;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PhysicsConfig
/*    */   extends NetworkSerializable<PhysicsConfig>
/*    */ {
/*    */   @Nonnull
/* 23 */   public static final CodecMapCodec<PhysicsConfig> CODEC = new CodecMapCodec("Type");
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
/*    */   void apply(@Nonnull Holder<EntityStore> paramHolder, @Nullable Ref<EntityStore> paramRef, @Nonnull Vector3d paramVector3d, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor, boolean paramBoolean);
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
/*    */   default double getGravity() {
/* 47 */     return 0.0D;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\config\PhysicsConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */