/*    */ package com.hypixel.hytale.server.core.universe.world.meta;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import com.hypixel.hytale.registry.Registry;
/*    */ import java.util.List;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BlockStateRegistry
/*    */   extends Registry<BlockStateRegistration>
/*    */ {
/*    */   public BlockStateRegistry(@Nonnull List<BooleanConsumer> registrations, BooleanSupplier precondition, String preconditionMessage) {
/* 15 */     super(registrations, precondition, preconditionMessage, BlockStateRegistration::new);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockState> BlockStateRegistration registerBlockState(@Nonnull Class<T> clazz, @Nonnull String key, Codec<T> codec) {
/* 20 */     checkPrecondition();
/* 21 */     return (BlockStateRegistration)register(BlockStateModule.get().registerBlockState(clazz, key, codec));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockState, D extends com.hypixel.hytale.server.core.asset.type.blocktype.config.StateData> BlockStateRegistration registerBlockState(@Nonnull Class<T> clazz, @Nonnull String key, Codec<T> codec, Class<D> dataClass, Codec<D> dataCodec) {
/* 26 */     checkPrecondition();
/* 27 */     return (BlockStateRegistration)register(BlockStateModule.get().registerBlockState(clazz, key, codec, dataClass, dataCodec));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\BlockStateRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */