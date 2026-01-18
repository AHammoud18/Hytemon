/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.LegacyEntityTrackerSystems;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Default
/*    */   extends CommandBase
/*    */ {
/*    */   Default() {
/* 46 */     super("default", "server.commands.entity.lod.default.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 51 */     LegacyEntityTrackerSystems.LegacyLODCull.ENTITY_LOD_RATIO = 3.5E-5D;
/* 52 */     context.sendMessage(Message.translation("server.commands.entity.lod.ratioSet")
/* 53 */         .param("ratio", LegacyEntityTrackerSystems.LegacyLODCull.ENTITY_LOD_RATIO));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityLodCommand$Default.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */