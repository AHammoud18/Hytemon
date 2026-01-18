/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TagSetExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 13 */   private static final TagSetExistsValidator DEFAULT_INSTANCE = new TagSetExistsValidator();
/*    */ 
/*    */   
/*    */   private TagSetExistsValidator() {}
/*    */ 
/*    */   
/*    */   private TagSetExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 20 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 26 */     return "TagSet";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String value) {
/* 31 */     return (NPCGroup.getAssetMap().getIndex(value) != Integer.MIN_VALUE);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value, String attribute) {
/* 37 */     return "The NPC group tag set with the name \"" + value + "\" does not exist in attribute \"" + attribute + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 43 */     return NPCGroup.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static TagSetExistsValidator required() {
/* 51 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static TagSetExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 56 */     return new TagSetExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\TagSetExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */