/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Pos2Command
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 33 */   private final OptionalArg<Integer> xArg = withOptionalArg("x", "server.commands.pos2.x.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final OptionalArg<Integer> yArg = withOptionalArg("y", "server.commands.pos2.y.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 45 */   private final OptionalArg<Integer> zArg = withOptionalArg("z", "server.commands.pos2.z.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Pos2Command() {
/* 51 */     super("pos2", "server.commands.pos2.desc");
/* 52 */     setPermissionGroup(GameMode.Creative);
/* 53 */     requirePermission("hytale.editor.selection.use");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*    */     Vector3i intTriple;
/* 62 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 63 */     assert playerComponent != null;
/*    */     
/* 65 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store)) {
/*    */       return;
/*    */     }
/* 68 */     if (this.xArg.provided(context) && this.yArg.provided(context) && this.zArg.provided(context)) {
/* 69 */       intTriple = new Vector3i(((Integer)this.xArg.get(context)).intValue(), ((Integer)this.yArg.get(context)).intValue(), ((Integer)this.zArg.get(context)).intValue());
/*    */     } else {
/* 71 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 72 */       assert transformComponent != null;
/*    */       
/* 74 */       Vector3d position = transformComponent.getPosition();
/*    */ 
/*    */ 
/*    */       
/* 78 */       intTriple = new Vector3i(MathUtil.floor(position.getX()), MathUtil.floor(position.getY()), MathUtil.floor(position.getZ()));
/*    */     } 
/*    */     
/* 81 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.pos2(intTriple, componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\Pos2Command.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */