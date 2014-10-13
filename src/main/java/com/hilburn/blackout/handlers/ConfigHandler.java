package com.hilburn.blackout.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.hilburn.blackout.tileentity.stellarfabricator.Recipe;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


public class ConfigHandler {
	public static ArrayList<String> recipes;

	public static void init(File file)
	{
		Configuration config = new Configuration(file);
		recipes=new ArrayList<String>();
		
		Property recipe = config.get("Stellar Fabricator", "recipes", new String[] {});
		recipe.comment = 	 "ItemStack format is 'modname:itemname:stacksize(:metadata optional)'. Can be left empty\n"
							+"Recipe format is 'Stack1,Stack2,..,Stack9,Output Stack, Speed, Torque, Power(, Base time optional)\n"
							+"The base time is in ticks - default is 15 seconds and time reduces by 1s every time the speed doubles";	
		recipes.addAll(Arrays.asList(recipe.getStringList()));
		for (String inputString: recipes)
		{
			Recipe.addRecipe(inputString);
		}
		config.save();
	}
}