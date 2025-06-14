package darkbum.mdrailsnails.util;

import org.lwjgl.opengl.GL11;

public class OpenGLHelper {
    public OpenGLHelper() {}

    public static void enableTexture2D() {
        enable(3553);
    }

    public static void disableTexture2D() {
        disable(3553);
    }

    public static void pushAttrib() {
        GL11.glPushAttrib(8256);
    }

    public static void popAttrib() {
        GL11.glPopAttrib();
    }

    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    private static void disable(int cap) {
        GL11.glDisable(cap);
    }

    private static void enable(int cap) {
        GL11.glEnable(cap);
    }

    public static void disableAlpha() {
        disable(3008);
    }

    public static void enableAlpha() {
        enable(3008);
    }

    public static void alphaFunc(int func, float ref) {
        GL11.glAlphaFunc(func, ref);
    }

    public static void enableLighting() {
        enable(2896);
    }

    public static void disableLighting() {
        disable(2896);
    }

    public static void disableDepth() {
        disable(6145);
    }

    public static void enableDepth() {
        enable(6145);
    }

    public static void depthFunc(int func) {
        GL11.glDepthFunc(func);
    }

    public static void depthMask(boolean flag) {
        GL11.glDepthMask(flag);
    }

    public static void disableBlend() {
        disable(3042);
    }

    public static void enableBlend() {
        enable(3042);
    }

    public static void blendFunc(int sfactor, int dfactor) {
        GL11.glBlendFunc(sfactor, dfactor);
    }

    public static void enableCull() {
        enable(2884);
    }

    public static void disableCull() {
        disable(2884);
    }

    public static void enableRescaleNormal() {
        enable(32826);
    }

    public static void disableRescaleNormal() {
        disable(32826);
    }

    public static void matrixMode(int mode) {
        GL11.glMatrixMode(mode);
    }

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void rotate(double angle, double x, double y, double z) {
        GL11.glRotated(angle, x, y, z);
    }

    public static void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    public static void scale(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    public static void scale(double x, double y, double z) {
        GL11.glScaled(x, y, z);
    }

    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    public static void translate(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
    }

    public static void colorMask(boolean r, boolean g, boolean b, boolean a) {
        GL11.glColorMask(r, g, b, a);
    }

    public static void colour(float r, float g, float b) {
        GL11.glColor3f(r, g, b);
    }

    public static void colour(int colour) {
        float r = (float)(colour >> 16 & 255) / 255.0F;
        float g = (float)(colour >> 8 & 255) / 255.0F;
        float b = (float)(colour & 255) / 255.0F;
        colour(r, g, b);
    }

    public static void viewport(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }

    public static void normal(float x, float y, float z) {
        GL11.glNormal3f(x, y, z);
    }
}
