/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ 
/*    */ 
/*    */ public abstract class DefaultEntityStatTypes
/*    */ {
/*    */   private static int HEALTH;
/*    */   private static int OXYGEN;
/*    */   private static int STAMINA;
/*    */   
/*    */   public static int getHealth() {
/* 13 */     return HEALTH;
/*    */   }
/*    */   private static int MANA; private static int SIGNATURE_ENERGY; private static int AMMO;
/*    */   public static int getOxygen() {
/* 17 */     return OXYGEN;
/*    */   }
/*    */   
/*    */   public static int getStamina() {
/* 21 */     return STAMINA;
/*    */   }
/*    */   
/*    */   public static int getMana() {
/* 25 */     return MANA;
/*    */   }
/*    */   
/*    */   public static int getSignatureEnergy() {
/* 29 */     return SIGNATURE_ENERGY;
/*    */   }
/*    */   
/*    */   public static int getAmmo() {
/* 33 */     return AMMO;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void update() {
/* 40 */     IndexedLookupTableAssetMap<String, EntityStatType> assetMap = EntityStatType.getAssetMap();
/*    */     
/* 42 */     HEALTH = assetMap.getIndex("Health");
/* 43 */     OXYGEN = assetMap.getIndex("Oxygen");
/* 44 */     STAMINA = assetMap.getIndex("Stamina");
/* 45 */     MANA = assetMap.getIndex("Mana");
/* 46 */     SIGNATURE_ENERGY = assetMap.getIndex("SignatureEnergy");
/* 47 */     AMMO = assetMap.getIndex("Ammo");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\DefaultEntityStatTypes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */