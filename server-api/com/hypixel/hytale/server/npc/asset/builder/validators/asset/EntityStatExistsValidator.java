/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityStatExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final EntityStatExistsValidator DEFAULT_INSTANCE = new EntityStatExistsValidator();
/*    */ 
/*    */   
/*    */   private EntityStatExistsValidator() {}
/*    */ 
/*    */   
/*    */   private EntityStatExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "EntityStat";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String entityStat) {
/* 32 */     return (EntityStatType.getAssetMap().getAsset(entityStat) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String entityStat, String attributeName) {
/* 38 */     return "The entity stat with the name \"" + entityStat + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return EntityStatType.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EntityStatExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static EntityStatExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new EntityStatExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\EntityStatExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */