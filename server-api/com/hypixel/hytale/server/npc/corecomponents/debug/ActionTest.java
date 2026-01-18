/*    */ package com.hypixel.hytale.server.npc.corecomponents.debug;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.debug.builders.BuilderActionTest;
/*    */ import java.util.Arrays;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionTest
/*    */   extends ActionBase
/*    */ {
/*    */   public ActionTest(@Nonnull BuilderActionTest builder, @Nonnull BuilderSupport support) {
/* 19 */     super((BuilderActionBase)builder);
/* 20 */     HytaleLogger logger = NPCPlugin.get().getLogger();
/* 21 */     logger.at(Level.INFO).log("==== Test Action Build Start ===");
/* 22 */     logger.at(Level.INFO).log("Boolean %s", Boolean.valueOf(builder.getBoolean(support)));
/* 23 */     logger.at(Level.INFO).log("Double %s", Double.valueOf(builder.getDouble(support)));
/* 24 */     logger.at(Level.INFO).log("Float %s", Float.valueOf(builder.getFloat(support)));
/* 25 */     logger.at(Level.INFO).log("Int %s", builder.getInt(support));
/* 26 */     logger.at(Level.INFO).log("String %s", builder.getString(support));
/*    */     
/* 28 */     logger.at(Level.INFO).log("Enum %s", builder.getEnum(support));
/* 29 */     logger.at(Level.INFO).log("EnumSet %s", builder.getEnumSet(support));
/*    */     
/* 31 */     logger.at(Level.INFO).log("Asset %s", builder.getAsset(support));
/*    */     
/* 33 */     logger.at(Level.INFO).log("DoubleArray %s", Arrays.toString(builder.getNumberArray(support)));
/* 34 */     logger.at(Level.INFO).log("StringArray %s", Arrays.toString((Object[])builder.getStringArray(support)));
/* 35 */     logger.at(Level.INFO).log("===== Test Action Build End ====");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\debug\ActionTest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */