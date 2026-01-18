/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public ChunkCommand() {
/* 14 */     super("chunk", "server.commands.chunk.desc");
/* 15 */     addAliases(new String[] { "chunks" });
/* 16 */     addSubCommand((AbstractCommand)new ChunkFixHeightMapCommand());
/* 17 */     addSubCommand((AbstractCommand)new ChunkForceTickCommand());
/* 18 */     addSubCommand((AbstractCommand)new ChunkInfoCommand());
/* 19 */     addSubCommand((AbstractCommand)new ChunkLightingCommand());
/* 20 */     addSubCommand((AbstractCommand)new ChunkLoadCommand());
/* 21 */     addSubCommand((AbstractCommand)new ChunkLoadedCommand());
/* 22 */     addSubCommand((AbstractCommand)new ChunkMarkSaveCommand());
/* 23 */     addSubCommand((AbstractCommand)new ChunkMaxSendRateCommand());
/* 24 */     addSubCommand((AbstractCommand)new ChunkRegenerateCommand());
/* 25 */     addSubCommand((AbstractCommand)new ChunkResendCommand());
/* 26 */     addSubCommand((AbstractCommand)new ChunkTintCommand());
/* 27 */     addSubCommand((AbstractCommand)new ChunkTrackerCommand());
/* 28 */     addSubCommand((AbstractCommand)new ChunkUnloadCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */