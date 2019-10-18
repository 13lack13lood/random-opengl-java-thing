package com.shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.entities.Camera;
import com.entities.Light;
import com.tool.box.Maths;

public class StaticShader extends ShaderProgram{

	private static final int MAX_LIGHTS = 29;
	
	private static final String VERTEX_FILE = "src/com/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/com/shaders/fragmentShader.txt";
	
	private int location_transformationmatrix;
	private int location_projectionmatrix;
	private int location_viewmatrix;
	
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	
	private int location_shineDamper;
	private int location_reflectivity;	
	private int location_useFakeLighting;
	
	private int location_skyColour;
	
	private int location_numberOfRows;
	private int location_offset;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
		
	public void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");

	}

	protected void getAllUniformLocations() {
		location_transformationmatrix = super.getUniformLocation("transformationMatrix");
		location_projectionmatrix = super.getUniformLocation("projectionMatrix");
		location_viewmatrix = super.getUniformLocation("viewMatrix");
		
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_useFakeLighting = super.getUniformLocation("useFakeLighting");
		
		location_skyColour = super.getUniformLocation("skyColour");
		
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		
		for(int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	public void loadOfset(float x, float y) {
		super.load2DVector(location_offset, new Vector2f(x, y));
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationmatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionmatrix, projectionMatrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewmatrix, viewMatrix);
	}
	
	public void loadLights(List<Light> lights) {
		for(int i = 0; i < MAX_LIGHTS; i++) {
			if(i < lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColour[i], lights.get(i).getColour());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());

			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);

	}
	
	public void loadFakeLightingVariable(boolean useFake) {
		super.loadBoolean(location_useFakeLighting, useFake);
	}
	
	public void loadSkyColour(float r, float g, float b) {
		super.loadVector(location_skyColour, new Vector3f(r, g, b));
	}

}
