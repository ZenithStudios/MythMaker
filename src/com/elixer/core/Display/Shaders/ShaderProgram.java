package com.elixer.core.Display.Shaders;

import com.elixer.core.Util.Logger;
import com.elixer.core.Util.ResourceType;
import com.elixer.core.Util.Util;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.glUniform1d;

public class ShaderProgram {

    private int programID;
    private ArrayList<Integer> shaders = new ArrayList<>();

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public static enum ShaderType {
        FRAGMENT(GL_FRAGMENT_SHADER),
        VERTEX(GL_VERTEX_SHADER),
        GEOMETERY(GL_GEOMETRY_SHADER);

        private int type;

        ShaderType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
        programID = glCreateProgram();
        getShader(vertexShaderFile, ShaderType.VERTEX);
        getShader(fragmentShaderFile, ShaderType.FRAGMENT);
        glLinkProgram(programID);

        if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            Logger.println("Shader Program Link Error: ", Logger.Levels.ERROR);
            Logger.println(glGetProgramInfoLog(programID), Logger.Levels.ERROREND);
        }
    }

    private void getShader(String file, ShaderType type) {
        int shader = glCreateShader(type.getType());
        StringBuilder source = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(Util.getResource(file, ResourceType.SHADER).toFile()))) {
            String line;

            while((line = br.readLine()) != null) {
                source.append(line + "\n");
            }
        } catch (IOException e) {
            Logger.println("Unable to reader Shader file.", Logger.Levels.ERROR);
        }

        glShaderSource(shader, source);
        glCompileShader(shader);

        if(glGetShaderi(shader, GL_COMPILE_STATUS)==GL_FALSE) {
            Logger.println("Shader Compile Error:", Logger.Levels.ERROR);
            Logger.println(glGetShaderInfoLog(shader, 500), Logger.Levels.ERROREND);
        }

        glAttachShader(programID, shader);
        shaders.add(shader);
    }

    public void use() {
        glUseProgram(programID);
    }

    public void setUniform(String name, float value) {
        glUniform1f(glGetUniformLocation(programID, name), value);
    }

    public void setUniform(String name, double value) {
        glUniform1d(glGetUniformLocation(programID, name), value);
    }

    public void setUniform(String name, Matrix4f mat) {
        glUniformMatrix4fv(glGetUniformLocation(programID, name), false, mat.get(matrixBuffer));
    }

    public void destroy() {
        for(int i: shaders) {
            glDeleteShader(i);
        }

        glDeleteProgram(programID);
    }
}
