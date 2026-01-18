/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.sequential;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RunCommandOperation
/*    */   extends SequenceBrushOperation
/*    */ {
/*    */   public static final BuilderCodec<RunCommandOperation> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RunCommandOperation.class, RunCommandOperation::new).append(new KeyedCodec("CommandToRun", (Codec)Codec.STRING), (op, val) -> op.commandArg = val, op -> op.commandArg).documentation("Runs a command, substituting the strings... \n'{x}', '{y}', and '{z}' for the origin coordinates\n'{radius}' with width/2, '{width}' with width, and '{height}' with height\n'{var:<persistent variable name>} with the value of the persistent variable'").add()).documentation("Runs a command, see help for argument replacements")).build();
/*    */   }
/* 33 */   private String commandArg = "";
/*    */   
/* 35 */   private static final Pattern regexBracketPattern = Pattern.compile("\\{var:(\\w*)}");
/*    */   
/*    */   public RunCommandOperation() {
/* 38 */     super("runCommand", "Runs a command, see help for argument replacements", false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void modifyBrushConfig(@Nonnull Ref<EntityStore> ref, @Nonnull BrushConfig brushConfig, @Nonnull BrushConfigCommandExecutor brushConfigCommandExecutor, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 43 */     String commandString = this.commandArg;
/* 44 */     if (commandString.startsWith("/")) commandString = commandString.substring(1);
/*    */     
/* 46 */     Vector3i origin = brushConfig.getOrigin();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     commandString = commandString.replace("{x}", String.valueOf(origin.x)).replace("{y}", String.valueOf(origin.y)).replace("{z}", String.valueOf(origin.z)).replace("{width}", String.valueOf(brushConfig.getShapeWidth())).replace("{height}", String.valueOf(brushConfig.getShapeHeight())).replace("{radius}", String.valueOf(brushConfig.getShapeWidth() / 2.0D));
/*    */     
/* 54 */     Matcher matcher = regexBracketPattern.matcher(commandString);
/* 55 */     while (matcher.find()) {
/* 56 */       String variableName = commandString.substring(matcher.start(1), matcher.end(1));
/* 57 */       String replacementValue = String.valueOf(brushConfigCommandExecutor.getPersistentVariableOrDefault(variableName, -2147483648));
/* 58 */       commandString = commandString.replaceFirst(regexBracketPattern.pattern(), replacementValue);
/*    */     } 
/*    */     
/* 61 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 62 */     assert playerComponent != null;
/*    */     
/* 64 */     CommandManager.get().handleCommand((CommandSender)playerComponent, commandString);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\sequential\RunCommandOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */