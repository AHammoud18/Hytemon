/*    */ package com.hypixel.hytale.server.core.modules.projectile.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.projectile.ProjectileModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Projectile
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 20 */   public static Projectile INSTANCE = new Projectile();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 26 */   public static final BuilderCodec<Projectile> CODEC = BuilderCodec.builder(Projectile.class, () -> INSTANCE)
/* 27 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ComponentType<EntityStore, Projectile> getComponentType() {
/* 33 */     return ProjectileModule.get().getProjectileComponentType();
/*    */   }
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
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 48 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\projectile\component\Projectile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */