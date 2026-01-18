/*    */ package com.hypixel.hytale.server.core.modules.entity;
/*    */ 
/*    */ import com.hypixel.hytale.registry.Registration;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityRegistration
/*    */   extends Registration
/*    */ {
/*    */   private final Class<? extends Entity> entityClass;
/*    */   
/*    */   public EntityRegistration(Class<? extends Entity> entityClass, BooleanSupplier isEnabled, Runnable unregister) {
/* 18 */     super(isEnabled, unregister);
/* 19 */     this.entityClass = entityClass;
/*    */   }
/*    */   
/*    */   public EntityRegistration(@Nonnull EntityRegistration registration, BooleanSupplier isEnabled, Runnable unregister) {
/* 23 */     super(isEnabled, unregister);
/* 24 */     this.entityClass = registration.entityClass;
/*    */   }
/*    */   
/*    */   public Class<? extends Entity> getEntityClass() {
/* 28 */     return this.entityClass;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 34 */     return "EntityRegistration{entityClass=" + String.valueOf(this.entityClass) + ", " + super
/*    */       
/* 36 */       .toString() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\EntityRegistration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */