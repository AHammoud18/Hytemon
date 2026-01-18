/*    */ package com.hypixel.hytale.builtin.beds.sleep.resources;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.beds.BedsPlugin;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*    */ 
/*    */ public class WorldSomnolence implements Resource<EntityStore> {
/*    */   public static ResourceType<EntityStore, WorldSomnolence> getResourceType() {
/* 11 */     return BedsPlugin.getInstance().getWorldSomnolenceResourceType();
/*    */   }
/*    */   
/* 14 */   private WorldSleep state = WorldSleep.Awake.INSTANCE;
/*    */   
/*    */   public WorldSleep getState() {
/* 17 */     return this.state;
/*    */   }
/*    */   
/*    */   public void setState(WorldSleep state) {
/* 21 */     this.state = state;
/*    */   }
/*    */ 
/*    */   
/*    */   @NullableDecl
/*    */   public Resource<EntityStore> clone() {
/* 27 */     WorldSomnolence clone = new WorldSomnolence();
/* 28 */     clone.state = this.state;
/* 29 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\resources\WorldSomnolence.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */