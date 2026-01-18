/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.path.TransientPath;
/*     */ import com.hypixel.hytale.builtin.path.waypoint.RelativeWaypointDefinition;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.ArrayDeque;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCPathCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public NPCPathCommand() {
/*  34 */     super("path", "server.commands.npc.path.desc");
/*  35 */     addSubCommand((AbstractCommand)new SetPathCommand());
/*  36 */     addSubCommand((AbstractCommand)new PolygonPathCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SetPathCommand
/*     */     extends NPCWorldCommandBase
/*     */   {
/*     */     @Nonnull
/*  47 */     private final RequiredArg<String> instructionsArg = withRequiredArg("instructions", "server.commands.npc.path.instructions.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SetPathCommand() {
/*  53 */       super("", "server.commands.npc.path.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/*  63 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  64 */       assert transformComponent != null;
/*     */       
/*  66 */       HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  67 */       assert headRotationComponent != null;
/*     */       
/*  69 */       String instructionsString = (String)this.instructionsArg.get(context);
/*  70 */       ArrayDeque<RelativeWaypointDefinition> instructions = parseInstructions(context, instructionsString);
/*  71 */       if (instructions == null) {
/*     */         return;
/*     */       }
/*     */       
/*  75 */       npc.getPathManager().setTransientPath(
/*  76 */           TransientPath.buildPath(transformComponent.getPosition(), headRotationComponent.getRotation(), instructions, 1.0D));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private ArrayDeque<RelativeWaypointDefinition> parseInstructions(@Nonnull CommandContext context, @Nonnull String str) {
/*  82 */       ArrayDeque<RelativeWaypointDefinition> instructions = new ArrayDeque<>();
/*  83 */       String[] parts = str.split(",");
/*  84 */       int index = 0;
/*     */       try {
/*  86 */         while (index < parts.length) {
/*  87 */           float rotation = Float.parseFloat(parts[index++]) * 0.017453292F;
/*  88 */           double distance = Double.parseDouble(parts[index++]);
/*  89 */           instructions.add(new RelativeWaypointDefinition(rotation, distance));
/*     */         } 
/*  91 */       } catch (NumberFormatException e) {
/*  92 */         context.sendMessage(Message.raw("Invalid number format: " + e.getMessage()));
/*  93 */         return null;
/*  94 */       } catch (IndexOutOfBoundsException e) {
/*  95 */         context.sendMessage(Message.raw("Instructions must be defined in pairs! Missing distance value."));
/*  96 */         return null;
/*     */       } 
/*  98 */       return instructions;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PolygonPathCommand
/*     */     extends NPCWorldCommandBase
/*     */   {
/*     */     @Nonnull
/* 109 */     private final RequiredArg<Integer> sidesArg = (RequiredArg<Integer>)
/* 110 */       withRequiredArg("sides", "server.commands.npc.path.polygon.sides.desc", (ArgumentType)ArgTypes.INTEGER)
/* 111 */       .addValidator(Validators.greaterThan(Integer.valueOf(0)));
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 116 */     private final OptionalArg<Double> lengthArg = (OptionalArg<Double>)
/* 117 */       withOptionalArg("length", "server.commands.npc.path.length.desc", (ArgumentType)ArgTypes.DOUBLE)
/* 118 */       .addValidator(Validators.greaterThan(Double.valueOf(0.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PolygonPathCommand() {
/* 124 */       super("polygon", "server.commands.npc.path.polygon.desc");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 136 */       ArrayDeque<RelativeWaypointDefinition> instructions = new ArrayDeque<>();
/* 137 */       Integer sides = (Integer)this.sidesArg.get(context);
/* 138 */       float angle = 6.2831855F / sides.intValue();
/* 139 */       double length = this.lengthArg.provided(context) ? ((Double)this.lengthArg.get(context)).doubleValue() : 5.0D;
/* 140 */       for (int i = 0; i < sides.intValue(); i++) {
/* 141 */         instructions.add(new RelativeWaypointDefinition(angle, length));
/*     */       }
/*     */       
/* 144 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 145 */       assert transformComponent != null;
/*     */       
/* 147 */       HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 148 */       assert headRotationComponent != null;
/*     */       
/* 150 */       Vector3d position = transformComponent.getPosition();
/* 151 */       Vector3f headRotation = headRotationComponent.getRotation();
/* 152 */       npc.getPathManager().setTransientPath(TransientPath.buildPath(position, headRotation, instructions, 1.0D));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCPathCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */