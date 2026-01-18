/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.data;
/*    */ 
/*    */ import com.hypixel.hytale.function.function.TriFunction;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleCollector<T>
/*    */   implements Collector
/*    */ {
/*    */   private final TriFunction<CollectorTag, InteractionContext, Interaction, T> function;
/*    */   @Nullable
/*    */   private T result;
/*    */   
/*    */   public SingleCollector(TriFunction<CollectorTag, InteractionContext, Interaction, T> function) {
/* 23 */     this.function = function;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T getResult() {
/* 28 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 33 */     this.result = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void into(@Nonnull InteractionContext context, Interaction interaction) {}
/*    */ 
/*    */   
/*    */   public boolean collect(@Nonnull CollectorTag tag, @Nonnull InteractionContext context, @Nonnull Interaction interaction) {
/* 42 */     this.result = (T)this.function.apply(tag, context, interaction);
/* 43 */     return (this.result != null);
/*    */   }
/*    */   
/*    */   public void outof() {}
/*    */   
/*    */   public void finished() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\data\SingleCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */