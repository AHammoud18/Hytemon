/*    */ package com.hypixel.hytale.builtin.portals.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TimerFragmentCommand extends PortalWorldCommandBase {
/* 15 */   private final RequiredArg<Integer> remainingSecondsArg = withRequiredArg("seconds", "server.commands.fragment.timer.arg.seconds.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */   
/*    */   public TimerFragmentCommand() {
/* 18 */     super("timer", "server.commands.fragment.timer.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull PortalWorld portalWorld, @Nonnull Store<EntityStore> store) {
/* 23 */     int before = (int)portalWorld.getRemainingSeconds(world);
/*    */     
/* 25 */     int desired = ((Integer)this.remainingSecondsArg.get(context)).intValue();
/* 26 */     portalWorld.setRemainingSeconds(world, desired);
/*    */     
/* 28 */     context.sendMessage(Message.translation("server.commands.fragment.timer.success")
/* 29 */         .param("before", before)
/* 30 */         .param("after", desired));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\commands\TimerFragmentCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */