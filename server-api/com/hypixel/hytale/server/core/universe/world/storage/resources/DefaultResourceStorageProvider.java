/*    */ package com.hypixel.hytale.server.core.universe.world.storage.resources;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.IResourceStorage;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DefaultResourceStorageProvider
/*    */   implements IResourceStorageProvider
/*    */ {
/* 11 */   public static final DefaultResourceStorageProvider INSTANCE = new DefaultResourceStorageProvider();
/*    */   
/*    */   public static final String ID = "Hytale";
/* 14 */   public static final BuilderCodec<DefaultResourceStorageProvider> CODEC = BuilderCodec.builder(DefaultResourceStorageProvider.class, () -> INSTANCE)
/* 15 */     .build();
/*    */   
/* 17 */   public static final DiskResourceStorageProvider DEFAULT = new DiskResourceStorageProvider();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <T extends com.hypixel.hytale.server.core.universe.world.WorldProvider> IResourceStorage getResourceStorage(@Nonnull World world) {
/* 22 */     return DEFAULT.getResourceStorage(world);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 28 */     return "DefaultResourceStorageProvider{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\resources\DefaultResourceStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */