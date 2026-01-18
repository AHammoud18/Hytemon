/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.cartas;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiCarta;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleElementCarta<R>
/*    */   extends BiCarta<R>
/*    */ {
/*    */   private R element;
/*    */   
/*    */   @Nonnull
/*    */   public static <R> SingleElementCarta<R> of(@Nonnull R element) {
/* 18 */     SingleElementCarta<R> c = new SingleElementCarta<>();
/* 19 */     c.element = element;
/* 20 */     return c;
/*    */   }
/*    */ 
/*    */   
/*    */   public R apply(int x, int z, @Nonnull WorkerIndexer.Id id) {
/* 25 */     return this.element;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<R> allPossibleValues() {
/* 31 */     return Collections.singletonList(this.element);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\cartas\SingleElementCarta.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */