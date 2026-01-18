package com.hypixel.hytale.server.npc.asset.builder.validators;

public abstract class StringValidator extends Validator {
  public abstract boolean test(String paramString);
  
  public abstract String errorMessage(String paramString);
  
  public abstract String errorMessage(String paramString1, String paramString2);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */