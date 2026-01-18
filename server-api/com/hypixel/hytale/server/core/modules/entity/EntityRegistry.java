/*    */ package com.hypixel.hytale.server.core.modules.entity;
/*    */ 
/*    */ import com.hypixel.hytale.codec.DirectDecodeCodec;
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import com.hypixel.hytale.registry.Registry;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.List;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class EntityRegistry
/*    */   extends Registry<EntityRegistration>
/*    */ {
/*    */   public EntityRegistry(@Nonnull List<BooleanConsumer> registrations, BooleanSupplier precondition, String preconditionMessage) {
/* 17 */     super(registrations, precondition, preconditionMessage, EntityRegistration::new);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T extends com.hypixel.hytale.server.core.entity.Entity> EntityRegistration registerEntity(@Nonnull String key, @Nonnull Class<T> clazz, Function<World, T> constructor, DirectDecodeCodec<T> codec) {
/* 22 */     checkPrecondition();
/* 23 */     return (EntityRegistration)register(EntityModule.get().registerEntity(key, clazz, constructor, codec));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\EntityRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */