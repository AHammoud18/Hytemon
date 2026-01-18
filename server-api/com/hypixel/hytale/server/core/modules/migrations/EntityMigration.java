/*    */ package com.hypixel.hytale.server.core.modules.migrations;
/*    */ 
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import java.util.function.IntFunction;
/*    */ 
/*    */ public abstract class EntityMigration<T>
/*    */   implements Migration {
/*    */   private Class<T> tClass;
/*    */   private IntFunction<ExtraInfo> extraInfoSupplier;
/*    */   
/*    */   public EntityMigration(Class<T> tClass, IntFunction<ExtraInfo> extraInfoSupplier) {
/* 13 */     this.tClass = tClass;
/* 14 */     this.extraInfoSupplier = extraInfoSupplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void run(WorldChunk chunk) {
/* 19 */     throw new UnsupportedOperationException("Not implemented!");
/*    */   }
/*    */   
/*    */   protected abstract boolean migrate(T paramT);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\migrations\EntityMigration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */