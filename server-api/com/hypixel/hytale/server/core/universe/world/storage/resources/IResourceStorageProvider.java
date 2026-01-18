/*    */ package com.hypixel.hytale.server.core.universe.world.storage.resources;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.BuilderCodecMapCodec;
/*    */ import com.hypixel.hytale.component.IResourceStorage;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
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
/*    */ public interface IResourceStorageProvider
/*    */ {
/*    */   @Nonnull
/* 19 */   public static final BuilderCodecMapCodec<IResourceStorageProvider> CODEC = new BuilderCodecMapCodec("Type", true);
/*    */   
/*    */   <T extends com.hypixel.hytale.server.core.universe.world.WorldProvider> IResourceStorage getResourceStorage(@Nonnull World paramWorld);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\resources\IResourceStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */