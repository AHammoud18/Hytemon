/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity.snapshot;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.entity.system.SnapshotSystems;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySnapshotLengthCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 23 */   private final RequiredArg<Integer> lengthArg = withRequiredArg("length", "server.commands.entity.snapshot.length.length.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntitySnapshotLengthCommand() {
/* 29 */     super("length", "server.commands.entity.snapshot.length.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 34 */     int lengthMs = ((Integer)this.lengthArg.get(context)).intValue();
/* 35 */     SnapshotSystems.HISTORY_LENGTH_NS = TimeUnit.MILLISECONDS.toNanos(lengthMs);
/* 36 */     long millis = TimeUnit.NANOSECONDS.toMillis(SnapshotSystems.HISTORY_LENGTH_NS);
/* 37 */     context.sendMessage(Message.translation("server.commands.entity.snapshot.lengthSet").param("millis", millis));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\snapshot\EntitySnapshotLengthCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */