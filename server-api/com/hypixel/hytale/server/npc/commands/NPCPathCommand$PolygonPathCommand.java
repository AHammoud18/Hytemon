/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.path.TransientPath;
/*     */ import com.hypixel.hytale.builtin.path.waypoint.RelativeWaypointDefinition;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import java.util.ArrayDeque;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolygonPathCommand
/*     */   extends NPCWorldCommandBase
/*     */ {
/*     */   @Nonnull
/* 109 */   private final RequiredArg<Integer> sidesArg = (RequiredArg<Integer>)
/* 110 */     withRequiredArg("sides", "server.commands.npc.path.polygon.sides.desc", (ArgumentType)ArgTypes.INTEGER)
/* 111 */     .addValidator(Validators.greaterThan(Integer.valueOf(0)));
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 116 */   private final OptionalArg<Double> lengthArg = (OptionalArg<Double>)
/* 117 */     withOptionalArg("length", "server.commands.npc.path.length.desc", (ArgumentType)ArgTypes.DOUBLE)
/* 118 */     .addValidator(Validators.greaterThan(Double.valueOf(0.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolygonPathCommand() {
/* 124 */     super("polygon", "server.commands.npc.path.polygon.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 136 */     ArrayDeque<RelativeWaypointDefinition> instructions = new ArrayDeque<>();
/* 137 */     Integer sides = (Integer)this.sidesArg.get(context);
/* 138 */     float angle = 6.2831855F / sides.intValue();
/* 139 */     double length = this.lengthArg.provided(context) ? ((Double)this.lengthArg.get(context)).doubleValue() : 5.0D;
/* 140 */     for (int i = 0; i < sides.intValue(); i++) {
/* 141 */       instructions.add(new RelativeWaypointDefinition(angle, length));
/*     */     }
/*     */     
/* 144 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 145 */     assert transformComponent != null;
/*     */     
/* 147 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 148 */     assert headRotationComponent != null;
/*     */     
/* 150 */     Vector3d position = transformComponent.getPosition();
/* 151 */     Vector3f headRotation = headRotationComponent.getRotation();
/* 152 */     npc.getPathManager().setTransientPath(TransientPath.buildPath(position, headRotation, instructions, 1.0D));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCPathCommand$PolygonPathCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */