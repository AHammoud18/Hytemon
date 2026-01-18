/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoleExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 13 */   private static final RoleExistsValidator DEFAULT_INSTANCE = new RoleExistsValidator();
/*    */ 
/*    */   
/*    */   private RoleExistsValidator() {}
/*    */ 
/*    */   
/*    */   private RoleExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 20 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 26 */     return "Role";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String role) {
/* 31 */     return NPCPlugin.get().hasRoleName(role);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String role, String attributeName) {
/* 37 */     return "The Role with the name \"" + role + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 43 */     return "NPCRole";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RoleExistsValidator required() {
/* 51 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RoleExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 56 */     return new RoleExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\RoleExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */