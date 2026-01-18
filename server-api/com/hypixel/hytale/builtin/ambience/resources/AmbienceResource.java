/*    */ package com.hypixel.hytale.builtin.ambience.resources;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.AmbiencePlugin;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.asset.type.ambiencefx.config.AmbienceFX;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AmbienceResource
/*    */   implements Resource<EntityStore> {
/*    */   public static ResourceType<EntityStore, AmbienceResource> getResourceType() {
/* 13 */     return AmbiencePlugin.get().getAmbienceResourceType();
/*    */   }
/*    */   
/*    */   private int forcedMusicIndex;
/*    */   
/*    */   public void setForcedMusicAmbience(@Nullable String musicAmbienceId) {
/* 19 */     if (musicAmbienceId == null) {
/* 20 */       this.forcedMusicIndex = 0;
/*    */       
/*    */       return;
/*    */     } 
/* 24 */     this.forcedMusicIndex = AmbienceFX.getAssetMap().getIndex(musicAmbienceId);
/*    */   }
/*    */   
/*    */   public int getForcedMusicIndex() {
/* 28 */     return this.forcedMusicIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public Resource<EntityStore> clone() {
/* 33 */     AmbienceResource clone = new AmbienceResource();
/* 34 */     clone.forcedMusicIndex = this.forcedMusicIndex;
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\resources\AmbienceResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */