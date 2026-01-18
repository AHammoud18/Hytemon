/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NPCRunTestsCommand extends AbstractPlayerCommand {
/*     */   @Nonnull
/*  38 */   private static final Message MESSAGE_COMMANDS_NPC_RUN_TESTS_SPECIFY_ROLES = Message.translation("server.commands.npc.runtests.specifyroles");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  44 */   private final OptionalArg<String> rolesArg = withOptionalArg("roles", "server.commands.npc.runtests.roles.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  50 */   private final FlagArg presetArg = withFlagArg("preset", "server.commands.npc.runtests.preset.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  56 */   private final FlagArg passArg = withFlagArg("pass", "server.commands.npc.runtests.pass.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  62 */   private final FlagArg failArg = withFlagArg("fail", "server.commands.npc.runtests.fail.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  68 */   private final FlagArg abortArg = withFlagArg("abort", "server.commands.npc.runtests.abort.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCRunTestsCommand() {
/*  74 */     super("runtests", "server.commands.npc.runtests.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*     */     String[] roles;
/*  84 */     NPCTestData testDataComponent = (NPCTestData)store.ensureAndGetComponent(ref, NPCTestData.getComponentType());
/*  85 */     TransformComponent playerTransformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  86 */     assert playerTransformComponent != null;
/*     */     
/*  88 */     if (!testDataComponent.npcRoles.isEmpty()) {
/*  89 */       if (((Boolean)this.passArg.get(context)).booleanValue()) {
/*  90 */         setNextRole(testDataComponent, ref, store, world); return;
/*     */       } 
/*  92 */       if (((Boolean)this.failArg.get(context)).booleanValue()) {
/*  93 */         testDataComponent.failedRoles.add(testDataComponent.npcRoles.getInt(testDataComponent.index));
/*  94 */         setNextRole(testDataComponent, ref, store, world); return;
/*     */       } 
/*  96 */       if (((Boolean)this.abortArg.get(context)).booleanValue()) {
/*  97 */         reportResults(ref, testDataComponent, store);
/*  98 */         Ref<EntityStore> npcRef = world.getEntityRef(testDataComponent.targetUUID);
/*  99 */         if (npcRef != null) {
/* 100 */           cleanupNPC(npcRef, store);
/*     */         }
/* 102 */         store.removeComponent(ref, NPCTestData.getComponentType());
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     if (((Boolean)this.presetArg.get(context)).booleanValue()) {
/* 109 */       roles = NPCPlugin.get().getPresetCoverageTestNPCs();
/*     */     } else {
/* 111 */       if (!this.rolesArg.provided(context)) {
/*     */         
/* 113 */         context.sendMessage(MESSAGE_COMMANDS_NPC_RUN_TESTS_SPECIFY_ROLES);
/* 114 */         store.removeComponent(ref, NPCTestData.getComponentType());
/*     */         return;
/*     */       } 
/* 117 */       String roleString = (String)this.rolesArg.get(context);
/* 118 */       if (roleString == null || roleString.isEmpty()) {
/* 119 */         context.sendMessage(MESSAGE_COMMANDS_NPC_RUN_TESTS_SPECIFY_ROLES);
/* 120 */         store.removeComponent(ref, NPCTestData.getComponentType());
/*     */         return;
/*     */       } 
/* 123 */       roles = roleString.split(",");
/*     */     } 
/* 125 */     for (String role : roles) {
/*     */       int flockSize;
/*     */       try {
/* 128 */         int idx = role.indexOf('#');
/* 129 */         flockSize = (idx < 0) ? 1 : Integer.parseInt(role.substring(idx + 1));
/* 130 */         if (idx > 0) {
/* 131 */           role = role.substring(0, idx);
/*     */         }
/* 133 */       } catch (NumberFormatException e) {
/* 134 */         context.sendMessage(Message.translation("server.commands.npc.runtests.invalidflocksize").param("role", role));
/*     */       } 
/*     */       
/* 137 */       int builderIndex = NPCPlugin.get().getIndex(role);
/* 138 */       if (builderIndex == Integer.MIN_VALUE) {
/* 139 */         context.sendMessage(Message.translation("server.commands.npc.spawn.templateNotFound").param("template", role));
/*     */       }
/*     */       else {
/*     */         
/* 143 */         testDataComponent.npcRoles.add(builderIndex);
/* 144 */         testDataComponent.flockSizes.add(flockSize);
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     if (testDataComponent.targetUUID == null) {
/* 149 */       spawnNPC(ref, testDataComponent, 0, playerTransformComponent.getPosition(), playerTransformComponent.getRotation(), store);
/*     */     }
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
/*     */ 
/*     */   
/*     */   private static void setNextRole(@Nonnull NPCTestData testData, @Nonnull Ref<EntityStore> reference, @Nonnull Store<EntityStore> store, @Nonnull World world) {
/*     */     Vector3d position;
/*     */     Vector3f rotation;
/* 166 */     Ref<EntityStore> npcReference = world.getEntityRef(testData.targetUUID);
/*     */     
/* 168 */     testData.index++;
/*     */     
/* 170 */     if (testData.index >= testData.npcRoles.size()) {
/*     */       
/* 172 */       reportResults(reference, testData, store);
/*     */       
/* 174 */       if (npcReference != null) {
/* 175 */         cleanupNPC(npcReference, store);
/*     */       }
/* 177 */       store.removeComponent(reference, NPCTestData.getComponentType());
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 183 */     if (npcReference != null) {
/* 184 */       TransformComponent npcTransformComponent = (TransformComponent)store.getComponent(npcReference, TransformComponent.getComponentType());
/* 185 */       assert npcTransformComponent != null;
/*     */       
/* 187 */       position = npcTransformComponent.getPosition();
/* 188 */       rotation = npcTransformComponent.getRotation();
/* 189 */       cleanupNPC(npcReference, store);
/*     */     } else {
/* 191 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(reference, TransformComponent.getComponentType());
/* 192 */       assert transformComponent != null;
/* 193 */       position = transformComponent.getPosition();
/* 194 */       rotation = transformComponent.getRotation();
/*     */     } 
/*     */     
/* 197 */     spawnNPC(reference, testData, testData.index, position, rotation, store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void cleanupNPC(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 207 */     Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(ref, (ComponentAccessor)store);
/* 208 */     if (flockReference != null) {
/* 209 */       ObjectArrayList<Ref<EntityStore>> members = new ObjectArrayList();
/*     */       
/* 211 */       EntityGroup entityGroupComponent = (EntityGroup)store.getComponent(flockReference, EntityGroup.getComponentType());
/* 212 */       assert entityGroupComponent != null;
/*     */       
/* 214 */       entityGroupComponent.forEachMember((index, member, list) -> list.add(member), members);
/*     */       
/* 216 */       for (ObjectListIterator<Ref<EntityStore>> objectListIterator = members.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> member = objectListIterator.next();
/* 217 */         store.removeEntity(member, RemoveReason.REMOVE); }
/*     */     
/*     */     } 
/* 220 */     store.removeEntity(ref, RemoveReason.REMOVE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void spawnNPC(@Nonnull Ref<EntityStore> playerReference, @Nonnull NPCTestData testData, int index, @Nonnull Vector3d position, @Nullable Vector3f rotation, @Nonnull Store<EntityStore> store) {
/* 236 */     Pair<Ref<EntityStore>, NPCEntity> npcPair = NPCPlugin.get().spawnEntity(store, testData.npcRoles.getInt(index), position, rotation, null, null);
/* 237 */     Ref<EntityStore> npcRef = (Ref<EntityStore>)npcPair.first();
/* 238 */     NPCEntity npcComponent = (NPCEntity)npcPair.second();
/*     */     
/* 240 */     int flockSize = testData.flockSizes.getInt(index);
/* 241 */     if (flockSize > 1) {
/* 242 */       TransformComponent npcTransformComponent = (TransformComponent)store.getComponent(npcRef, TransformComponent.getComponentType());
/* 243 */       assert npcTransformComponent != null;
/*     */       
/* 245 */       FlockPlugin.trySpawnFlock(npcRef, npcComponent, store, npcComponent.getRoleIndex(), npcTransformComponent.getPosition(), npcTransformComponent.getRotation(), flockSize, null);
/*     */     } 
/* 247 */     String roleName = npcComponent.getRoleName();
/*     */     
/* 249 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(playerReference, PlayerRef.getComponentType());
/* 250 */     assert playerRefComponent != null;
/* 251 */     playerRefComponent.sendMessage(Message.translation("server.commands.npc.runtests.testing").param("role", roleName).insert("\n")
/* 252 */         .insert(Message.translation("server.npc.tests." + roleName)));
/*     */     
/* 254 */     UUIDComponent npcUUIDComponent = (UUIDComponent)store.getComponent(npcRef, UUIDComponent.getComponentType());
/* 255 */     assert npcUUIDComponent != null;
/* 256 */     testData.targetUUID = npcUUIDComponent.getUuid();
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void reportResults(@Nonnull Ref<EntityStore> playerReference, @Nonnull NPCTestData testData, @Nonnull Store<EntityStore> store) {
/* 271 */     NPCPlugin npcPlugin = NPCPlugin.get();
/* 272 */     Message msg = Message.translation("server.commands.npc.runtests.results");
/* 273 */     for (int i = 0; i < testData.npcRoles.size(); i++) {
/* 274 */       int index = testData.npcRoles.getInt(i);
/* 275 */       msg.insert("  " + npcPlugin.getName(index) + ": ");
/* 276 */       String result = (i >= testData.index) ? "server.commands.npc.runtests.notrun" : (testData.failedRoles.contains(index) ? "server.commands.npc.runtests.fail" : "server.commands.npc.runtests.pass");
/* 277 */       msg.insert(Message.translation(result));
/* 278 */       msg.insert("\n");
/*     */     } 
/*     */     
/* 281 */     PlayerRef playerRef = (PlayerRef)store.getComponent(playerReference, PlayerRef.getComponentType());
/* 282 */     assert playerRef != null;
/* 283 */     playerRef.sendMessage(msg);
/* 284 */     npcPlugin.getLogger().at(Level.INFO).log(msg.getRawText());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NPCTestData
/*     */     implements Component<EntityStore>
/*     */   {
/*     */     public static ComponentType<EntityStore, NPCTestData> getComponentType() {
/* 296 */       return NPCPlugin.get().getNpcTestDataComponentType();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     private final IntList npcRoles = (IntList)new IntArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     private final IntList flockSizes = (IntList)new IntArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 312 */     private final IntSet failedRoles = (IntSet)new IntOpenHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int index;
/*     */ 
/*     */ 
/*     */     
/*     */     private UUID targetUUID;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<EntityStore> clone() {
/* 328 */       NPCTestData data = new NPCTestData();
/* 329 */       data.npcRoles.addAll(this.npcRoles);
/* 330 */       data.index = this.index;
/* 331 */       data.failedRoles.addAll((IntCollection)this.failedRoles);
/* 332 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCRunTestsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */