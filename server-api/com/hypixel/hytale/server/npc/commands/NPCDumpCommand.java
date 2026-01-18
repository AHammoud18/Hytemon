/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.util.ComponentInfo;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCDumpCommand
/*     */   extends NPCWorldCommandBase
/*     */ {
/*     */   @Nonnull
/*  34 */   private final FlagArg jsonArg = withFlagArg("json", "server.commands.npc.dump.json");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCDumpCommand() {
/*  40 */     super("dump", "server.commands.npc.dump.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull NPCEntity npc, @Nonnull World world, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/*  50 */     StringBuilder sb = new StringBuilder(npc.getRoleName());
/*  51 */     sb.append(":\n");
/*     */     
/*  53 */     Role role = npc.getRole();
/*  54 */     if (role != null) {
/*  55 */       if (!((Boolean)this.jsonArg.get(context)).booleanValue()) {
/*  56 */         ObjectArrayList objectArrayList = new ObjectArrayList();
/*  57 */         dumpComponent(role, (IAnnotatedComponent)role, -1, 0, (List<ComponentInfo>)objectArrayList);
/*  58 */         for (ComponentInfo info : objectArrayList) {
/*  59 */           sb.append(info).append('\n');
/*     */         }
/*     */       } else {
/*  62 */         JsonObject obj = new JsonObject();
/*  63 */         dumpComponentsAsJson(role, (IAnnotatedComponent)role, -1, 0, (JsonElement)obj);
/*  64 */         sb.append(obj);
/*     */       } 
/*     */     }
/*     */     
/*  68 */     NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
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
/*     */   
/*     */   private static void dumpComponent(@Nonnull Role role, @Nonnull IAnnotatedComponent component, int index, int nestingDepth, @Nonnull List<ComponentInfo> infoList) {
/*  85 */     ComponentInfo componentInfo = new ComponentInfo(component.getClass().getSimpleName(), index, nestingDepth);
/*  86 */     infoList.add(componentInfo);
/*     */     
/*  88 */     if (component instanceof IAnnotatedComponentCollection) { IAnnotatedComponentCollection aggregate = (IAnnotatedComponentCollection)component;
/*  89 */       int nestedComponentCount = aggregate.componentCount();
/*  90 */       for (int i = 0; i < nestedComponentCount; i++) {
/*  91 */         IAnnotatedComponent nestedComponent = aggregate.getComponent(i);
/*  92 */         if (nestedComponent != null)
/*     */         {
/*     */           
/*  95 */           dumpComponent(role, nestedComponent, i, nestingDepth + 1, infoList);
/*     */         }
/*     */       }  }
/*     */     
/*  99 */     component.getInfo(role, componentInfo);
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
/*     */   
/*     */   private static void dumpComponentsAsJson(@Nonnull Role role, @Nonnull IAnnotatedComponent component, int index, int nestingDepth, @Nonnull JsonElement parent) {
/* 116 */     ComponentInfo componentInfo = new ComponentInfo(component.getClass().getSimpleName(), index, nestingDepth);
/* 117 */     JsonObject object = parent.isJsonObject() ? parent.getAsJsonObject() : new JsonObject();
/* 118 */     object.add("name", (JsonElement)new JsonPrimitive(componentInfo.getName()));
/* 119 */     if (componentInfo.getIndex() >= 0) {
/* 120 */       object.add("index", (JsonElement)new JsonPrimitive(Integer.valueOf(componentInfo.getIndex())));
/*     */     }
/*     */     
/* 123 */     if (component instanceof IAnnotatedComponentCollection) { IAnnotatedComponentCollection aggregate = (IAnnotatedComponentCollection)component;
/* 124 */       JsonArray array = new JsonArray();
/* 125 */       object.add("children", (JsonElement)array);
/* 126 */       int nestedComponentCount = aggregate.componentCount();
/* 127 */       for (int i = 0; i < nestedComponentCount; i++) {
/* 128 */         IAnnotatedComponent nestedComponent = aggregate.getComponent(i);
/* 129 */         if (nestedComponent != null)
/*     */         {
/*     */           
/* 132 */           dumpComponentsAsJson(role, nestedComponent, i, nestingDepth + 1, (JsonElement)array);
/*     */         }
/*     */       }  }
/*     */     
/* 136 */     component.getInfo(role, componentInfo);
/* 137 */     List<String> fields = componentInfo.getFields();
/* 138 */     if (!fields.isEmpty()) {
/* 139 */       JsonArray array = new JsonArray();
/* 140 */       for (String field : fields) {
/* 141 */         array.add(field);
/*     */       }
/* 143 */       object.add("parameters", (JsonElement)array);
/*     */     } 
/*     */     
/* 146 */     if (parent.isJsonArray())
/* 147 */       parent.getAsJsonArray().add((JsonElement)object); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCDumpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */