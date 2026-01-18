/*    */ package com.hypixel.hytale.builtin.adventure.npcshop;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCShopPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   public NPCShopPlugin(@Nonnull JavaPluginInit init) {
/* 18 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 23 */     NPCPlugin.get().registerCoreComponentType("OpenShop", com.hypixel.hytale.builtin.adventure.npcshop.npc.builders.BuilderActionOpenShop::new);
/* 24 */     NPCPlugin.get().registerCoreComponentType("OpenBarterShop", com.hypixel.hytale.builtin.adventure.npcshop.npc.builders.BuilderActionOpenBarterShop::new);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcshop\NPCShopPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */