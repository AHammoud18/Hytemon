package com.hypixel.hytale.server.npc.asset.builder.validators;

public abstract class StringArrayValidator extends Validator {
  public abstract boolean test(String[] paramArrayOfString);
  
  public abstract String errorMessage(String paramString, String[] paramArrayOfString);
  
  public abstract String errorMessage(String[] paramArrayOfString);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StringArrayValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */