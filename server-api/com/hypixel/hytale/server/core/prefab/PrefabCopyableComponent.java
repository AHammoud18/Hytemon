/*    */ package com.hypixel.hytale.server.core.prefab;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ public class PrefabCopyableComponent implements Component<EntityStore> {
/*  9 */   public static final PrefabCopyableComponent INSTANCE = new PrefabCopyableComponent();
/*    */   
/*    */   public static ComponentType<EntityStore, PrefabCopyableComponent> getComponentType() {
/* 12 */     return EntityModule.get().getPrefabCopyableComponentType();
/*    */   }
/*    */   
/*    */   public static PrefabCopyableComponent get() {
/* 16 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 21 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\PrefabCopyableComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */