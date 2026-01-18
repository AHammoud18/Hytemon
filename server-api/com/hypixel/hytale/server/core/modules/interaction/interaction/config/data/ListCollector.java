/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.data;
/*    */ 
/*    */ import com.hypixel.hytale.function.function.TriFunction;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListCollector<T>
/*    */   implements Collector
/*    */ {
/*    */   private final TriFunction<CollectorTag, InteractionContext, Interaction, T> function;
/*    */   private List<T> list;
/*    */   
/*    */   public ListCollector(TriFunction<CollectorTag, InteractionContext, Interaction, T> function) {
/* 24 */     this.function = function;
/*    */   }
/*    */   
/*    */   public List<T> getList() {
/* 28 */     return this.list;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 33 */     this.list = (List<T>)new ObjectArrayList();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void into(@Nonnull InteractionContext context, Interaction interaction) {}
/*    */ 
/*    */   
/*    */   public boolean collect(@Nonnull CollectorTag tag, @Nonnull InteractionContext context, @Nonnull Interaction interaction) {
/* 42 */     this.list.add((T)this.function.apply(tag, context, interaction));
/* 43 */     return false;
/*    */   }
/*    */   
/*    */   public void outof() {}
/*    */   
/*    */   public void finished() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\data\ListCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */