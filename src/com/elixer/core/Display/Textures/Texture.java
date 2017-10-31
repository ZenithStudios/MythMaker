package com.elixer.core.Display.Textures;

import com.elixer.core.Util.Logger;
import com.elixer.core.Util.ResourceType;
import com.elixer.core.Util.Util;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4i;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by aweso on 10/20/2017.
 */
public class Texture {

    private BufferedImage rawImage;
    private Path file;
    private int width, height;
    private int rows, columns, spacing;

    private int[] pixelData;
    private boolean needsUpdate = true;
    private int textureID;

    public Texture(String filename) {
        loadRawImage(filename);
        pixelData = rawImage.getRGB(0, 0, width, height, null, 0, width);

        rows = 1;
        columns = 1;
        spacing = 0;

        updateTexture();
    }

    public Texture(String filename, int rows, int columns, int spacing) {
        loadRawImage(filename);
        pixelData = rawImage.getRGB(0, 0, width, height, null, 0, width);

        this.rows = rows;
        this.columns = columns;
        this.spacing = spacing;

        updateTexture();
    }

    private void loadRawImage(String filename) {
        this.file = Util.getResource(filename, ResourceType.TEXTURE);

        try {
            rawImage = ImageIO.read(file.toFile());

            width = rawImage.getWidth();
            height = rawImage.getHeight();

        } catch (IOException e) {
            Logger.println(Logger.Levels.ERROR, "File not found: " + e.getMessage());
        }
    }

    private void updateTexture() {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width*height*4);

        for(int i = 0; i < pixelData.length; i++) {
            buffer.put((byte)((pixelData[i] >> 16) & 0xFF));
            buffer.put((byte)((pixelData[i] >> 8) & 0xFF));
            buffer.put((byte)((pixelData[i] >> 0) & 0xFF));
            buffer.put((byte)((pixelData[i] >> 24) & 0xFF));
        }

        buffer.flip();
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private Vector2i getGlobalTexturePos(int x, int y, int i) {
        int rowHeight = height/rows;
        int colWidth = width/columns;

        int column = i % columns;
        int row = Math.floorDiv(i, rows);

        return new Vector2i(Util.clampi(x+(column*colWidth)+(column*spacing), 0, colWidth),
                Util.clampi(y+(row*rowHeight)+(row*spacing), 0, rowHeight));
    }

    public Vector2i getIndexRowCol(int i) {

        int column = i % columns;
        int row = Math.floorDiv(i, rows);

        return new Vector2i(column, row);
    }

    public int getColWidth() {
        return getWidth()/columns;
    }

    public int getRowHeight() {
        return getHeight()/rows;
    }

    public int getTexturesCount() {
        return (columns*rows);
    }

    private Vector4i getUV(int index) {
        return null;
    }

    public int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSpacing() {
        return spacing;
    }
}
