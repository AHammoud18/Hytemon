/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HollowCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 31 */   private final DefaultArg<String> blockTypeArg = withDefaultArg("blockType", "server.commands.hollow.blockType.desc", (ArgumentType)ArgTypes.BLOCK_TYPE_KEY, "Empty", "Air");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   private final DefaultArg<Integer> thicknessArg = (DefaultArg<Integer>)
/* 38 */     withDefaultArg("thickness", "server.commands.hollow.thickness.desc", (ArgumentType)ArgTypes.INTEGER, 
/* 39 */       Integer.valueOf(1), "Thickness of 1")
/* 40 */     .addValidator(Validators.range(Integer.valueOf(1), Integer.valueOf(128)));
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 45 */   private final FlagArg floorArg = (FlagArg)
/* 46 */     withFlagArg("floor", "server.commands.hollow.floor.desc")
/* 47 */     .addAliases(new String[] { "bottom" });
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 52 */   private final FlagArg roofArg = (FlagArg)
/* 53 */     withFlagArg("roof", "server.commands.hollow.roof.desc")
/* 54 */     .addAliases(new String[] { "ceiling", "top" });
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 59 */   private final FlagArg perimeterArg = (FlagArg)
/* 60 */     withFlagArg("perimeter", "server.commands.hollow.perimeter.desc")
/* 61 */     .addAliases(new String[] { "all" });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HollowCommand() {
/* 67 */     super("hollow", "server.commands.hollow.desc");
/* 68 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 73 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 74 */     assert playerComponent != null;
/*    */     
/* 76 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 78 */     int blockTypeIndex = BlockType.getAssetMap().getIndex(this.blockTypeArg.get(context));
/*    */     
/* 80 */     Boolean floor = (Boolean)this.floorArg.get(context);
/* 81 */     Boolean roof = (Boolean)this.roofArg.get(context);
/* 82 */     Boolean perimeter = (Boolean)this.perimeterArg.get(context);
/*    */     
/* 84 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.hollow(r, blockTypeIndex, ((Integer)this.thicknessArg.get(context)).intValue(), 
/* 85 */           (roof.booleanValue() || perimeter.booleanValue()), (floor.booleanValue() || perimeter.booleanValue()), componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\HollowCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */