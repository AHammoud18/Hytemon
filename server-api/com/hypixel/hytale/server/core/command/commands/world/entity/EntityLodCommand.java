/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.LegacyEntityTrackerSystems;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityLodCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 21 */   private final RequiredArg<Double> ratioArg = withRequiredArg("ratio", "server.commands.entity.lod.ratio.desc", (ArgumentType)ArgTypes.DOUBLE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityLodCommand() {
/* 27 */     super("lod", "server.commands.entity.lod.desc");
/* 28 */     addSubCommand((AbstractCommand)new Default());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     LegacyEntityTrackerSystems.LegacyLODCull.ENTITY_LOD_RATIO = ((Double)this.ratioArg.get(context)).doubleValue();
/* 34 */     context.sendMessage(Message.translation("server.commands.entity.lod.ratioSet")
/* 35 */         .param("ratio", LegacyEntityTrackerSystems.LegacyLODCull.ENTITY_LOD_RATIO));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static class Default
/*    */     extends CommandBase
/*    */   {
/*    */     Default() {
/* 46 */       super("default", "server.commands.entity.lod.default.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void executeSync(@Nonnull CommandContext context) {
/* 51 */       LegacyEntityTrackerSystems.LegacyLODCull.ENTITY_LOD_RATIO = 3.5E-5D;
/* 52 */       context.sendMessage(Message.translation("server.commands.entity.lod.ratioSet")
/* 53 */           .param("ratio", LegacyEntityTrackerSystems.LegacyLODCull.ENTITY_LOD_RATIO));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityLodCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */