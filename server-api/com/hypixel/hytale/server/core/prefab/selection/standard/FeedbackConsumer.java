/*    */ package com.hypixel.hytale.server.core.prefab.selection.standard;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface FeedbackConsumer {
/*    */   public static final FeedbackConsumer DEFAULT = (key, total, count, target, componentAccessor) -> {
/*    */     
/*    */     };
/*    */   
/*    */   void accept(@Nonnull String paramString, int paramInt1, int paramInt2, @Nonnull CommandSender paramCommandSender, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\standard\FeedbackConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */