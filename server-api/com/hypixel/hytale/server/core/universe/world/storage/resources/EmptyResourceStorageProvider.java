/*    */ package com.hypixel.hytale.server.core.universe.world.storage.resources;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.EmptyResourceStorage;
/*    */ import com.hypixel.hytale.component.IResourceStorage;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EmptyResourceStorageProvider
/*    */   implements IResourceStorageProvider
/*    */ {
/* 12 */   public static final EmptyResourceStorageProvider INSTANCE = new EmptyResourceStorageProvider();
/*    */   
/*    */   public static final String ID = "Empty";
/* 15 */   public static final BuilderCodec<EmptyResourceStorageProvider> CODEC = BuilderCodec.builder(EmptyResourceStorageProvider.class, () -> INSTANCE)
/* 16 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <T extends com.hypixel.hytale.server.core.universe.world.WorldProvider> IResourceStorage getResourceStorage(@Nonnull World world) {
/* 21 */     return (IResourceStorage)EmptyResourceStorage.get();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 27 */     return "EmptyResourceStorageProvider{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\resources\EmptyResourceStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */