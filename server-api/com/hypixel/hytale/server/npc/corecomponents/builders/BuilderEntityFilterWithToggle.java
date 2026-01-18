/*    */ package com.hypixel.hytale.server.npc.corecomponents.builders;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BuilderEntityFilterWithToggle
/*    */   extends BuilderBase<IEntityFilter>
/*    */ {
/* 18 */   protected final BooleanHolder enabled = new BooleanHolder();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Builder<IEntityFilter> readCommonConfig(@Nonnull JsonElement data) {
/* 23 */     super.readCommonConfig(data);
/* 24 */     getBoolean(data, "Enabled", this.enabled, true, BuilderDescriptorState.Stable, "Whether this entity filter should be enabled", null);
/* 25 */     return (Builder<IEntityFilter>)this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Class<IEntityFilter> category() {
/* 31 */     return IEntityFilter.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled(ExecutionContext context) {
/* 36 */     return this.enabled.get(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\builders\BuilderEntityFilterWithToggle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */