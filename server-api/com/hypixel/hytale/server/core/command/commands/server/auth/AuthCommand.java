/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
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
/*    */ public class AuthCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public AuthCommand() {
/* 22 */     super("auth", "server.commands.auth.desc");
/* 23 */     addSubCommand((AbstractCommand)new AuthStatusCommand());
/* 24 */     addSubCommand((AbstractCommand)new AuthLoginCommand());
/* 25 */     addSubCommand((AbstractCommand)new AuthSelectCommand());
/* 26 */     addSubCommand((AbstractCommand)new AuthLogoutCommand());
/* 27 */     addSubCommand((AbstractCommand)new AuthCancelCommand());
/* 28 */     addSubCommand((AbstractCommand)new AuthPersistenceCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */