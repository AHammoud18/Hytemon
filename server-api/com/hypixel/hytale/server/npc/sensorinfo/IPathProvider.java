/*    */ package com.hypixel.hytale.server.npc.sensorinfo;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.universe.world.path.IPath;
/*    */ import com.hypixel.hytale.server.core.universe.world.path.IPathWaypoint;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public interface IPathProvider
/*    */   extends ExtraInfoProvider
/*    */ {
/*    */   boolean hasPath();
/*    */   
/*    */   @Nullable
/*    */   IPath<? extends IPathWaypoint> getPath();
/*    */   
/*    */   void clear();
/*    */   
/*    */   @Nonnull
/*    */   default Class<IPathProvider> getType() {
/* 35 */     return IPathProvider.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\IPathProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */