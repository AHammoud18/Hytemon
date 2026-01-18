/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BuilderTemplateInteractionVars
/*    */   extends BuilderCodecObjectHelper<Map<String, String>>
/*    */ {
/*    */   public BuilderTemplateInteractionVars() {
/* 16 */     super(RootInteraction.class, (Codec<Map<String, String>>)RootInteraction.CHILD_ASSET_CODEC_MAP, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, String> build() {
/* 21 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void readConfig(@Nonnull JsonElement data, @Nonnull ExtraInfo extraInfo) {
/* 26 */     super.readConfig(data, extraInfo);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Map<String, String> build(@Nonnull ExecutionContext context) {
/* 31 */     Map<String, String> override = context.getInteractionVars();
/* 32 */     if (override != null) return override; 
/* 33 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderTemplateInteractionVars.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */