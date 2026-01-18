/*    */ package com.hypixel.hytale.server.core.asset.type.blocktick;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated(forRemoval = true)
/*    */ public final class BlockTickManager
/*    */ {
/* 13 */   private static final AtomicReference<IBlockTickProvider> BLOCK_TICK_PROVIDER = new AtomicReference<>(IBlockTickProvider.NONE);
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setBlockTickProvider(@Nonnull IBlockTickProvider provider) {
/* 18 */     BLOCK_TICK_PROVIDER.set(provider);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static IBlockTickProvider getBlockTickProvider() {
/* 23 */     return BLOCK_TICK_PROVIDER.get();
/*    */   }
/*    */   
/*    */   public static boolean hasBlockTickProvider() {
/* 27 */     return (getBlockTickProvider() != IBlockTickProvider.NONE);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktick\BlockTickManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */