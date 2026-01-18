/*    */ package com.hypixel.hytale.builtin.beds.sleep.components;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.beds.BedsPlugin;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*    */ 
/*    */ public class PlayerSomnolence implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, PlayerSomnolence> getComponentType() {
/* 11 */     return BedsPlugin.getInstance().getPlayerSomnolenceComponentType();
/*    */   }
/*    */   
/* 14 */   public static PlayerSomnolence AWAKE = new PlayerSomnolence(PlayerSleep.FullyAwake.INSTANCE);
/*    */   
/* 16 */   private PlayerSleep state = PlayerSleep.FullyAwake.INSTANCE;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerSomnolence(PlayerSleep state) {
/* 22 */     this.state = state;
/*    */   }
/*    */   
/*    */   public PlayerSleep getSleepState() {
/* 26 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   @NullableDecl
/*    */   public Component<EntityStore> clone() {
/* 32 */     PlayerSomnolence clone = new PlayerSomnolence();
/* 33 */     clone.state = this.state;
/* 34 */     return clone;
/*    */   }
/*    */   
/*    */   public PlayerSomnolence() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\components\PlayerSomnolence.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */