/*    */ package com.hypixel.hytale.server.spawning;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoadedNPCEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   private BuilderInfo builderInfo;
/*    */   
/*    */   public LoadedNPCEvent(@Nonnull BuilderInfo builderInfo) {
/* 17 */     Objects.requireNonNull(builderInfo, "builderInfo can't be null for event");
/* 18 */     if (!(builderInfo.getBuilder() instanceof ISpawnableWithModel)) {
/* 19 */       throw new IllegalArgumentException("BuilderInfo builder must be spawnable for event");
/*    */     }
/* 21 */     this.builderInfo = builderInfo;
/*    */   }
/*    */   
/*    */   public BuilderInfo getBuilderInfo() {
/* 25 */     return this.builderInfo;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "LoadedNPCEvent{builderInfo=" + String.valueOf(this.builderInfo) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\LoadedNPCEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */