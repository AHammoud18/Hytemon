/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.hypixel.hytale.codec.schema.NamedSchema;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.SchemaConvertable;
/*     */ import com.hypixel.hytale.codec.schema.config.ArraySchema;
/*     */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*     */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*     */ import java.util.LinkedHashMap;
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
/*     */ class SchemaGenerator
/*     */   implements SchemaConvertable<Void>, NamedSchema
/*     */ {
/*     */   @Nonnull
/* 255 */   public static SchemaGenerator INSTANCE = new SchemaGenerator();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getSchemaName() {
/* 260 */     return "NPC:Type:BuilderModifier";
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 266 */     ObjectSchema s = new ObjectSchema();
/* 267 */     s.setTitle("BuilderModifier");
/* 268 */     LinkedHashMap<String, Schema> props = new LinkedHashMap<>();
/* 269 */     s.setProperties(props);
/*     */     
/* 271 */     props.put("_ExportStates", new ArraySchema((Schema)new StringSchema()));
/* 272 */     props.put("_InterfaceParameters", new ObjectSchema());
/* 273 */     StringSchema combatConfig = new StringSchema();
/* 274 */     combatConfig.setHytaleAssetRef(BalanceAsset.class.getSimpleName());
/* 275 */     props.put("_CombatConfig", combatConfig);
/* 276 */     ObjectSchema interactionVars = new ObjectSchema();
/* 277 */     interactionVars.setTitle("Map");
/* 278 */     Schema childSchema = context.refDefinition((SchemaConvertable)RootInteraction.CHILD_ASSET_CODEC);
/* 279 */     interactionVars.setAdditionalProperties(childSchema);
/* 280 */     props.put("_InteractionVars", interactionVars);
/*     */ 
/*     */     
/* 283 */     s.setAdditionalProperties(BuilderExpression.toSchema(context));
/* 284 */     return (Schema)s;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderModifier$SchemaGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */