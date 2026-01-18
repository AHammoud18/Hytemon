/*    */ package com.hypixel.hytale.server.core.modules.debug.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugShapeSubCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public DebugShapeSubCommand() {
/* 14 */     super("shape", "server.commands.debug.shape.desc");
/*    */     
/* 16 */     addSubCommand((AbstractCommand)new DebugShapeSphereCommand());
/* 17 */     addSubCommand((AbstractCommand)new DebugShapeCubeCommand());
/* 18 */     addSubCommand((AbstractCommand)new DebugShapeCylinderCommand());
/* 19 */     addSubCommand((AbstractCommand)new DebugShapeConeCommand());
/* 20 */     addSubCommand((AbstractCommand)new DebugShapeArrowCommand());
/* 21 */     addSubCommand((AbstractCommand)new DebugShapeShowForceCommand());
/*    */     
/* 23 */     addSubCommand((AbstractCommand)new DebugShapeClearCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\debug\commands\DebugShapeSubCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */