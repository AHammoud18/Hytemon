/*    */ package com.hypixel.hytale.server.core.modules.entity.damage.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DesyncDamageCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public DesyncDamageCommand() {
/* 18 */     super("desyncdamage", "server.commands.damage.desyncdamage.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 23 */     DamageSystems.FilterUnkillable.CAUSE_DESYNC = !DamageSystems.FilterUnkillable.CAUSE_DESYNC;
/* 24 */     context.sendMessage(Message.translation("server.commands.damage.desyncDamageEnabled")
/* 25 */         .param("enabled", DamageSystems.FilterUnkillable.CAUSE_DESYNC));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\commands\DesyncDamageCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */