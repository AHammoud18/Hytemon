/*     */ package com.hypixel.hytale.server.core.plugin.pending;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PendingLoadPlugin
/*     */ {
/*     */   @Nonnull
/*     */   private final PluginIdentifier identifier;
/*     */   
/*     */   PendingLoadPlugin(@Nullable Path path, @Nonnull PluginManifest manifest) {
/*  27 */     this.path = path;
/*  28 */     this.identifier = new PluginIdentifier(manifest);
/*  29 */     this.manifest = manifest;
/*     */   } @Nonnull
/*     */   private final PluginManifest manifest; @Nullable
/*     */   private final Path path; @Nonnull
/*     */   public PluginIdentifier getIdentifier() {
/*  34 */     return this.identifier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PluginManifest getManifest() {
/*  39 */     return this.manifest;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path getPath() {
/*  44 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<PendingLoadPlugin> createSubPendingLoadPlugins() {
/*  54 */     List<PluginManifest> subPlugins = this.manifest.getSubPlugins();
/*  55 */     if (subPlugins.isEmpty()) return Collections.emptyList();
/*     */     
/*  57 */     ObjectArrayList<PendingLoadPlugin> plugins = new ObjectArrayList(subPlugins.size());
/*  58 */     for (PluginManifest subManifest : subPlugins) {
/*  59 */       subManifest.inherit(this.manifest);
/*  60 */       plugins.add(createSubPendingLoadPlugin(subManifest));
/*     */     } 
/*  62 */     return (List<PendingLoadPlugin>)plugins;
/*     */   }
/*     */   
/*     */   public boolean dependsOn(PluginIdentifier identifier) {
/*  66 */     return (this.manifest.getDependencies().containsKey(identifier) || this.manifest.getOptionalDependencies().containsKey(identifier));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  77 */     if (this == o) return true; 
/*  78 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  80 */     PendingLoadPlugin that = (PendingLoadPlugin)o;
/*     */     
/*  82 */     if (!this.identifier.equals(that.identifier)) return false; 
/*  83 */     if (!this.manifest.equals(that.manifest)) return false; 
/*  84 */     return Objects.equals(this.path, that.path);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  89 */     int result = this.identifier.hashCode();
/*  90 */     result = 31 * result + this.manifest.hashCode();
/*  91 */     result = 31 * result + ((this.path != null) ? this.path.hashCode() : 0);
/*  92 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  98 */     return "PendingLoadPlugin{identifier=" + String.valueOf(this.identifier) + ", manifest=" + String.valueOf(this.manifest) + ", path=" + String.valueOf(this.path) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract PendingLoadPlugin createSubPendingLoadPlugin(PluginManifest paramPluginManifest);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract PluginBase load();
/*     */   
/*     */   public abstract boolean isInServerClassPath();
/*     */   
/*     */   @Nonnull
/*     */   public static List<PendingLoadPlugin> calculateLoadOrder(@Nonnull Map<PluginIdentifier, PendingLoadPlugin> pending) {
/* 112 */     HashMap<PluginIdentifier, EntryNode> nodes = new HashMap<>(pending.size());
/* 113 */     for (Map.Entry<PluginIdentifier, PendingLoadPlugin> entry : pending.entrySet()) {
/* 114 */       nodes.put(entry.getKey(), new EntryNode(entry.getValue()));
/*     */     }
/*     */ 
/*     */     
/* 118 */     HashSet<PluginIdentifier> classpathPlugins = new HashSet<>();
/* 119 */     for (Map.Entry<PluginIdentifier, PendingLoadPlugin> entry : pending.entrySet()) {
/* 120 */       if (((PendingLoadPlugin)entry.getValue()).isInServerClassPath()) {
/* 121 */         classpathPlugins.add(entry.getKey());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 126 */     HashMap<PluginIdentifier, Set<PluginIdentifier>> missingDependencies = new HashMap<>();
/* 127 */     for (EntryNode node : nodes.values()) {
/* 128 */       PluginManifest manifest = node.plugin.manifest;
/* 129 */       for (PluginIdentifier depId : manifest.getDependencies().keySet()) {
/* 130 */         if (nodes.containsKey(depId)) {
/* 131 */           node.edge.add(depId); continue;
/*     */         } 
/* 133 */         ((Set<PluginIdentifier>)missingDependencies.computeIfAbsent(node.plugin.identifier, k -> new HashSet())).add(depId);
/*     */       } 
/*     */       
/* 136 */       for (PluginIdentifier identifier : manifest.getOptionalDependencies().keySet()) {
/* 137 */         EntryNode dep = nodes.get(identifier);
/* 138 */         if (dep != null) node.edge.add(identifier);
/*     */       
/*     */       } 
/*     */       
/* 142 */       if (!node.plugin.isInServerClassPath()) {
/* 143 */         node.edge.addAll(classpathPlugins);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 148 */     HashMap<PluginIdentifier, Set<PluginIdentifier>> missingLoadBefore = new HashMap<>();
/* 149 */     for (Map.Entry<PluginIdentifier, PendingLoadPlugin> entry : pending.entrySet()) {
/* 150 */       PluginManifest manifest = ((PendingLoadPlugin)entry.getValue()).manifest;
/* 151 */       for (PluginIdentifier targetId : manifest.getLoadBefore().keySet()) {
/* 152 */         EntryNode targetNode = nodes.get(targetId);
/* 153 */         if (targetNode != null) {
/* 154 */           targetNode.edge.add(entry.getKey()); continue;
/*     */         } 
/* 156 */         ((Set<PluginIdentifier>)missingLoadBefore.computeIfAbsent(entry.getKey(), k -> new HashSet())).add(targetId);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 161 */     if (!missingDependencies.isEmpty() || !missingLoadBefore.isEmpty()) {
/* 162 */       StringBuilder sb = new StringBuilder();
/* 163 */       if (!missingDependencies.isEmpty()) {
/* 164 */         sb.append("Missing required dependencies:\n");
/* 165 */         for (Map.Entry<PluginIdentifier, Set<PluginIdentifier>> entry : missingDependencies.entrySet()) {
/* 166 */           sb.append("  ").append(entry.getKey()).append(" requires: ").append(entry.getValue()).append("\n");
/*     */         }
/*     */       } 
/* 169 */       if (!missingLoadBefore.isEmpty()) {
/* 170 */         sb.append("Missing loadBefore targets:\n");
/* 171 */         for (Map.Entry<PluginIdentifier, Set<PluginIdentifier>> entry : missingLoadBefore.entrySet()) {
/* 172 */           sb.append("  ").append(entry.getKey()).append(" loadBefore: ").append(entry.getValue()).append("\n");
/*     */         }
/*     */       } 
/* 175 */       throw new IllegalArgumentException(sb.toString());
/*     */     } 
/*     */     
/* 178 */     ObjectArrayList<PendingLoadPlugin> loadOrder = new ObjectArrayList(nodes.size());
/*     */     
/* 180 */     while (!nodes.isEmpty()) {
/* 181 */       boolean didWork = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 187 */       for (Iterator<Map.Entry<PluginIdentifier, EntryNode>> iterator = nodes.entrySet().iterator(); iterator.hasNext(); ) {
/* 188 */         Map.Entry<PluginIdentifier, EntryNode> entry = iterator.next();
/*     */         
/* 190 */         EntryNode node = entry.getValue();
/* 191 */         if (!node.edge.isEmpty())
/*     */           continue; 
/* 193 */         didWork = true;
/* 194 */         iterator.remove();
/* 195 */         loadOrder.add(node.plugin);
/*     */ 
/*     */         
/* 198 */         PluginIdentifier identifier = entry.getKey();
/* 199 */         for (EntryNode otherNode : nodes.values()) {
/* 200 */           otherNode.edge.remove(identifier);
/*     */         }
/*     */       } 
/*     */       
/* 204 */       if (!didWork) {
/* 205 */         StringBuilder sb = new StringBuilder("Found cyclic dependency between plugins:\n");
/* 206 */         for (Map.Entry<PluginIdentifier, EntryNode> entry : nodes.entrySet()) {
/* 207 */           sb.append("  ").append(entry.getKey()).append(" waiting on: ").append(((EntryNode)entry.getValue()).edge).append("\n");
/*     */         }
/* 209 */         throw new IllegalArgumentException(sb.toString());
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     return (List<PendingLoadPlugin>)loadOrder;
/*     */   }
/*     */   
/*     */   private static final class EntryNode {
/* 217 */     private final Set<PluginIdentifier> edge = new HashSet<>();
/*     */     
/*     */     private final PendingLoadPlugin plugin;
/*     */     
/*     */     private EntryNode(PendingLoadPlugin plugin) {
/* 222 */       this.plugin = plugin;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 228 */       return "EntryNode{plugin=" + String.valueOf(this.plugin) + ", dependencies=" + String.valueOf(this.edge) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\pending\PendingLoadPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */