/*    */ package com.hypixel.hytale.server.npc.sensorinfo;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.universe.world.path.IPath;
/*    */ import com.hypixel.hytale.server.core.universe.world.path.IPathWaypoint;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathProvider
/*    */   implements IPathProvider
/*    */ {
/*    */   @Nullable
/*    */   private IPath<? extends IPathWaypoint> path;
/*    */   private boolean isValid;
/*    */   
/*    */   public void setPath(IPath<? extends IPathWaypoint> path) {
/* 19 */     this.path = path;
/* 20 */     this.isValid = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 25 */     this.path = null;
/* 26 */     this.isValid = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPath() {
/* 31 */     return this.isValid;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IPath<? extends IPathWaypoint> getPath() {
/* 37 */     return this.path;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\PathProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */