/*     */ package com.hypixel.hytale.builtin.path.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.PathPlugin;
/*     */ import com.hypixel.hytale.builtin.path.WorldPathData;
/*     */ import com.hypixel.hytale.builtin.path.path.IPrefabPath;
/*     */ import com.hypixel.hytale.builtin.path.waypoint.IPrefabPathWaypoint;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabPathNodesCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   @Nonnull
/*  30 */   private final RequiredArg<Integer> worldgenIdArg = withRequiredArg("worldgenId", "server.commands.npcpath.nodes.worldgenId.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  36 */   private final RequiredArg<UUID> pathArg = withRequiredArg("path", "server.commands.npcpath.nodes.path.desc", (ArgumentType)ArgTypes.UUID);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabPathNodesCommand() {
/*  42 */     super("nodes", "server.commands.npcpath.nodes.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  47 */     Integer worldgenId = (Integer)this.worldgenIdArg.get(context);
/*  48 */     UUID uuid = (UUID)this.pathArg.get(context);
/*  49 */     WorldPathData worldPathData = (WorldPathData)store.getResource(WorldPathData.getResourceType());
/*     */     
/*  51 */     IPrefabPath path = worldPathData.getPrefabPath(worldgenId.intValue(), uuid, true);
/*  52 */     if (path == null) {
/*  53 */       context.sendMessage(Message.translation("server.npc.npcpath.noSuchPath")
/*  54 */           .param("path", uuid.toString())
/*  55 */           .param("worldgenId", worldgenId.intValue()));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  60 */     StringBuilder sb = new StringBuilder("Path [ ");
/*  61 */     sb.append(path.getName()).append(" ]:");
/*  62 */     sb.append("\n Length: ").append(path.length());
/*  63 */     sb.append("\n Fully loaded: ").append(path.isFullyLoaded());
/*  64 */     sb.append("\n Waypoints: ");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     Message msg = Message.translation("server.npc.npcpath.nodes.pathDesc").param("name", path.getName()).param("length", path.length()).param("isLoaded", path.isFullyLoaded());
/*     */     
/*  71 */     List<IPrefabPathWaypoint> waypoints = path.getPathWaypoints();
/*  72 */     int[] order = { 0 };
/*  73 */     waypoints.forEach(waypoint -> {
/*     */           if (waypoint == null) {
/*     */             sb.append("\n  ").append('#').append(order[0]).append(" (Not loaded)");
/*     */ 
/*     */             
/*     */             msg.insert(Message.translation("server.npc.npcpath.nodes.waypointNotLoaded").param("index", order[0]));
/*     */             
/*     */             order[0] = order[0] + 1;
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           Vector3d pos = waypoint.getWaypointPosition((ComponentAccessor)store);
/*     */           
/*     */           Vector3f rotation = waypoint.getWaypointRotation((ComponentAccessor)store);
/*     */           
/*     */           sb.append("\n  ").append('#').append(waypoint.getOrder());
/*     */           
/*     */           sb.append(" (").append(pos.x).append(", ").append(pos.y).append(", ").append(pos.z).append(')');
/*     */           
/*     */           sb.append("\n   ").append("Rotation: (").append(rotation.x).append(", ").append(rotation.y).append(", ").append(rotation.z).append(')');
/*     */           
/*     */           sb.append("\n   ").append("Pause time: ").append(waypoint.getPauseTime()).append('s');
/*     */           
/*     */           sb.append("\n   ").append(String.format("Observation angle: %.2f", new Object[] { Float.valueOf(waypoint.getObservationAngle() * 57.295776F) }));
/*     */           
/*     */           msg.insert(Message.translation("server.npc.npcpath.nodes.node").param("index", waypoint.getOrder()).param("posX", pos.x).param("posY", pos.y).param("posZ", pos.z).param("rotX", rotation.x).param("rotY", rotation.y).param("rotZ", rotation.z).param("time", waypoint.getPauseTime()).param("angle", String.format("%.2f", new Object[] { Float.valueOf(waypoint.getObservationAngle() * 57.295776F) })));
/*     */           
/*     */           order[0] = order[0] + 1;
/*     */         });
/*     */     
/* 104 */     PathPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/* 105 */     context.sendMessage(msg);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathNodesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */