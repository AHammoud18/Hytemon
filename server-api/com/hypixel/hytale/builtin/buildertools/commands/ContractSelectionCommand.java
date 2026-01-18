/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.Axis;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContractSelectionCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 34 */   private final RequiredArg<Integer> distanceArg = withRequiredArg("distance", "server.commands.contract.arg.distance.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final OptionalArg<List<Axis>> axisArg = withListOptionalArg("axis", "command.contract.arg.axis.desc", (ArgumentType)ArgTypes.forEnum("Axis", Axis.class));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContractSelectionCommand() {
/* 46 */     super("contractSelection", "server.commands.contract.desc");
/* 47 */     setPermissionGroup(GameMode.Creative);
/* 48 */     addAliases(new String[] { "contract" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 53 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 54 */     assert playerComponent != null;
/*    */     
/* 56 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 58 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 59 */     assert headRotationComponent != null;
/*    */     
/* 61 */     int distance = ((Integer)this.distanceArg.get(context)).intValue();
/*    */     
/* 63 */     ObjectArrayList<Vector3i> objectArrayList = new ObjectArrayList();
/* 64 */     if (this.axisArg.provided(context)) {
/* 65 */       for (Axis axis : this.axisArg.get(context)) {
/* 66 */         objectArrayList.add(axis.getDirection().scale(distance));
/*    */       }
/*    */     } else {
/* 69 */       objectArrayList.add(headRotationComponent.getAxisDirection().scale(distance));
/*    */     } 
/*    */     
/* 72 */     for (Vector3i direction : objectArrayList)
/* 73 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.contract(r, direction, componentAccessor)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\ContractSelectionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */