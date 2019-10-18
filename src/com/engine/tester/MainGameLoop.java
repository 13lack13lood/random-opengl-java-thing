package com.engine.tester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.entities.Camera;
import com.entities.Entity;
import com.entities.Light;
import com.entities.Player;
import com.models.RawModel;
import com.models.TexturedModel;
import com.render.engine.DisplayManager;
import com.render.engine.Loader;
import com.render.engine.MasterRenderer;
import com.render.engine.OBJLoader;
import com.render.guis.GuiRenderer;
import com.render.guis.GuiTexture;
import com.scene.terrain.Terrain;
import com.textures.ModelTexture;
import com.textures.TerrainTexture;
import com.textures.TerrainTexturePack;
import com.tool.box.Maths;
import com.tool.box.MousePicker;

public class MainGameLoop {

	public static void main(String[] args) {
		
		 	DisplayManager.createDisplay();
	        Loader loader = new Loader();
	        
	        //terrain stuff 
	        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassTerrain"));
	        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
	        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
	        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
	        
	        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
	        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
	        
	        Terrain terrain1 = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
	        
	        List<Terrain> terrains = new ArrayList<Terrain>();
	        terrains.add(terrain1);
 
	        //end of terrain stuff 
	        
	        RawModel modelTreePine = OBJLoader.loadObjModel("Poplar_Tree", loader);
	        RawModel modelTreePoplar = OBJLoader.loadObjModel("Fir_Tree", loader);
	        RawModel modelTreeOak = OBJLoader.loadObjModel("Oak_Tree", loader);
	        RawModel modelCrystal = OBJLoader.loadObjModel("Crystal", loader);
	        RawModel modelFern = OBJLoader.loadObjModel("fern", loader);
	        RawModel modelGrass = OBJLoader.loadObjModel("grass", loader);
	        
	        RawModel modelPerson = OBJLoader.loadObjModel("person", loader);
	        RawModel modelLamp = OBJLoader.loadObjModel("lamp", loader);
//	        RawModel modelAwp = OBJLoader.loadObjModel("AWP", loader);
	        
	        TexturedModel texturedModelTreePine = new TexturedModel(modelTreePine, new ModelTexture(loader.loadTexture("lowPolyTree")));
	        texturedModelTreePine.getTexture().setReflectivity(0.1f);
	        texturedModelTreePine.getTexture().setShineDamper(100);
	        
	        TexturedModel texturedModelTreePoplar = new TexturedModel(modelTreePoplar, new ModelTexture(loader.loadTexture("pineTree")));
	        texturedModelTreePoplar.getTexture().setReflectivity(0.1f);
	        texturedModelTreePoplar.getTexture().setShineDamper(10);
	        
	        TexturedModel texturedModelTreeOak = new TexturedModel(modelTreeOak, new ModelTexture(loader.loadTexture("lowPolyTree")));
	        texturedModelTreeOak.getTexture().setReflectivity(0.1f);
	        texturedModelTreeOak.getTexture().setShineDamper(10);
	        
	        TexturedModel texturedModelBlueCrystal = new TexturedModel(modelCrystal, new ModelTexture(loader.loadTexture("blue crystal")));
	        texturedModelBlueCrystal.getTexture().setReflectivity(1);
	        texturedModelBlueCrystal.getTexture().setShineDamper(10);
	        
	        TexturedModel texturedModelRedCrystal = new TexturedModel(modelCrystal, new ModelTexture(loader.loadTexture("red crystal")));
	        texturedModelRedCrystal.getTexture().setReflectivity(1);
	        texturedModelRedCrystal.getTexture().setShineDamper(10);
	        
	        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
	        fernTextureAtlas.setNumberOfRows(2);
	        TexturedModel texturedModelFern = new TexturedModel(modelFern, fernTextureAtlas);
	        texturedModelFern.getTexture().setHasTransparency(true);
	        texturedModelFern.getTexture().setUseFakeLighting(true);
	        
	        ModelTexture grassTextureAtlas = new ModelTexture(loader.loadTexture("diffuse"));
	        grassTextureAtlas.setNumberOfRows(4);
	        TexturedModel texturedModelGrass = new TexturedModel(modelGrass, grassTextureAtlas);
	        texturedModelGrass.getTexture().setHasTransparency(true);
	        texturedModelGrass.getTexture().setUseFakeLighting(true);
	        
	        TexturedModel texturedModelPerson = new TexturedModel(modelPerson, new ModelTexture(loader.loadTexture("playerTexture")));
	        TexturedModel texturedModelLamp = new TexturedModel(modelLamp, new ModelTexture(loader.loadTexture("lamp")));
	      	texturedModelLamp.getTexture().setUseFakeLighting(true);
//	        TexturedModel texturedModelAwp = new TexturedModel(modelAwp, new ModelTexture(loader.loadTexture("color")));

	        List<Light> lights = new ArrayList<Light>();
//	        lights.add(new Light(new Vector3f(-200, 10, -200), new Vector3f(10, 0, 0)));
//	        lights.add(new Light(new Vector3f(200, 10, 200), new Vector3f(0, 0, 10)));
	        lights.add(new Light(new Vector3f(0, 1000, -7000),new Vector3f(0.4f, 0.4f, 0.4f)));
	        lights.add(new Light(new Vector3f(185, terrain1.getHeightOfTerrain(185, -293) + 13, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
	        lights.add(new Light(new Vector3f(370, terrain1.getHeightOfTerrain(370, -300) + 13, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
	        lights.add(new Light(new Vector3f(293, terrain1.getHeightOfTerrain(293, -305) + 13, -305), new Vector3f(2, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
	        lights.add(new Light(new Vector3f(700, terrain1.getHeightOfTerrain(293, -305) + 13, -305), new Vector3f(2, 2, 2), new Vector3f(1, 0.01f, 0.002f)));

	        Light light = new Light(new Vector3f(293, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f));
	        lights.add(light);
	        
	        Random random = new Random(676452);
	        
	        MasterRenderer renderer = new MasterRenderer(loader);
	        
	        Player player = new Player(texturedModelPerson, new Vector3f(100, 0, -50), 0, 0, 0, 1);
	         
	        Camera camera = new Camera(player);  
	        
	        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain1);
	        
	        List<Entity> entities = new ArrayList<Entity>();
	        entities.add(new Entity(texturedModelLamp, new Vector3f(185, terrain1.getHeightOfTerrain(185, -293), -293), 0, 0, 0, 1));
	        entities.add(new Entity(texturedModelLamp, new Vector3f(370, terrain1.getHeightOfTerrain(370, -300), -300), 0, 0, 0, 1));
	        entities.add(new Entity(texturedModelLamp, new Vector3f(293, terrain1.getHeightOfTerrain(293, -305), -305), 0, 0, 0, 1));
	        
	        Entity lampEntity = new Entity(texturedModelLamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1);
	        entities.add(lampEntity);
	        
//	        Entity awpEntity = new Entity(texturedModelAwp, new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z), 0, 0, 0, 1);
//	        entities.add(awpEntity);
	        
	        List<GuiTexture> guis = new ArrayList<GuiTexture>();
	        GuiTexture gui = new GuiTexture(loader.loadTexture("owl"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
	        guis.add(gui);
	        
	        GuiRenderer guiRenderer = new GuiRenderer(loader);	        
	        
	        for(int i = 0; i < 1600; i++) {
	        	int position = 3200;
	        	
	        	if(i % 1 == 0) {
	        		float x = random.nextFloat() * position;
		        	float z = random.nextFloat() * -position;
		        	float y = terrain1.getHeightOfTerrain(x, z);
		        	
		            entities.add(new Entity(texturedModelGrass, random.nextInt(9), new Vector3f(x, y, z), 0, 0, 0, 2));
	        	}
	        	
	        	if(i % 2 == 0) {
	        		float x = random.nextFloat() * position;
		        	float z = random.nextFloat() * -position;
		        	float y = terrain1.getHeightOfTerrain(x, z);
		        	
		            entities.add(new Entity(texturedModelFern, random.nextInt(4), new Vector3f(x, y, z), 0, 0, 0, 1.5f));
	        	}
	        	
	        	if(i % 10 == 0) {
	        		float x = random.nextFloat() * position;
		        	float z = random.nextFloat() * -position;
		        	float y = terrain1.getHeightOfTerrain(x, z);
		        	
		        	entities.add(new Entity(texturedModelTreePine, new Vector3f(x, y, z), 0, 0, 0, 10));
	        	}
	        	
	        	if(i % 10 == 0) {
	        		float x = random.nextFloat() * position;
		        	float z = random.nextFloat() * -position;
		        	float y = terrain1.getHeightOfTerrain(x, z);
		        	
		            entities.add(new Entity(texturedModelTreePoplar, new Vector3f(x, y, z), 0, 0, 0, 10));		            
	        	}
	        	
	        	if(i % 10 == 0) {
	        		float x = random.nextFloat() * position;
		        	float z = random.nextFloat() * -position;
		        	float y = terrain1.getHeightOfTerrain(x, z);
		        	
		            entities.add(new Entity(texturedModelTreeOak, new Vector3f(x, y, z), 0, 0, 0, 10));		            
	        	}
	        	
	        	if(i % 50 == 0) {
	        		float x = random.nextFloat() * position;
		        	float z = random.nextFloat() * -position;
		        	float y = terrain1.getHeightOfTerrain(x, z);
		        	
		        	float[] array = Maths.generateRandomPosition(y - 0.2f, y - 3, 0, 0);
		        	
		            entities.add(new Entity(texturedModelBlueCrystal, new Vector3f(x, array[0], z), 0, 0, 0, 10));		            
	        	}
	        	
	        	if(i % 50 == 0) {
	        		float x = random.nextFloat() * position;
		        	float z = random.nextFloat() * -position;
		        	float y = terrain1.getHeightOfTerrain(x, z);
		        	
		        	float[] array = Maths.generateRandomPosition(y - 0.2f, y - 3, 0, 0);
		        	
		            entities.add(new Entity(texturedModelRedCrystal, new Vector3f(x, array[0], z), 0, 0, 0, 10));		            
	        	}
	        }
	        
	        while(!Display.isCloseRequested()){
	            camera.move();
	            renderer.processEntity(player);
    
	            player.move(terrain1);

	            renderer.processTerrain(terrain1);

	            picker.update();
	            Vector3f terrainPoint = picker.getCurrentTerrainPoint();
	            
	            if(terrainPoint != null) {
	            	lampEntity.setPosition(terrainPoint);
	            	light.setPosition(new Vector3f(terrainPoint.x, terrainPoint.y + 15, terrainPoint.z));
	            	if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
	        	        entities.add(new Entity(texturedModelLamp, terrainPoint, 0, 0, 0, 1));        	                	      	        	        
	        	        lights.add(new Light(new Vector3f(terrainPoint.x, terrainPoint.y + 15, terrainPoint.z), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
	            		        	       
	            	}
	            }
	            
	            for(Entity entity : entities){
	                renderer.processEntity(entity);
	            }
	            
	            guiRenderer.render(guis);
	            renderer.renderScene(entities, terrains, lights, camera);;
	            DisplayManager.updateDisplay();
	        }
	        
	        guiRenderer.cleanUp();
	        renderer.cleanUp();
	        loader.cleanUp();
	        DisplayManager.closeDisplay();
	}

}
