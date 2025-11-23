package xyz.wagyourtail.wagyourgui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class TextInput extends Button {
    public Consumer<String> onChange;
    public String mask = ".*";
    public String content;
    protected int selColor;
    protected int selStart;
    public int selStartIndex;
    protected int selEnd;
    public int selEndIndex;
    protected int arrowCursor;

    public TextInput(int x, int y, int width, int height, Font textRenderer, int color, int borderColor, int highlightColor, int textColor, String message, Consumer<Button> onClick, Consumer<String> onChange) {
        super(x, y, width, height, textRenderer, color, borderColor, color, textColor, Component.literal(""), onClick);
        this.selColor = highlightColor;
        this.content = message;
        this.onChange = onChange;
        this.updateSelStart(content.length());
        this.updateSelEnd(content.length());
        this.arrowCursor = content.length();
    }

    public void setMessage(String message) {
        content = message;
    }

    public void updateSelStart(int startIndex) {
        selStartIndex = startIndex;
        if (startIndex == 0) {
            selStart = getX() + 1;
        } else {
            selStart = getX() + 2 + textRenderer.width(content.substring(0, startIndex));
        }
    }

    public void updateSelEnd(int endIndex) {
        selEndIndex = endIndex;
        if (endIndex == 0) {
            selEnd = getX() + 2;
        } else {
            selEnd = getX() + 3 + textRenderer.width(content.substring(0, endIndex));
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent buttonEvent, boolean debounce) {
        if (this.isFocused()) {
            int pos = textRenderer.plainSubstrByWidth(content, (int) (buttonEvent.x() - getX() - 2)).length();
            updateSelStart(pos);
            updateSelEnd(pos);
            arrowCursor = pos;
        }
        return super.mouseClicked(buttonEvent, debounce);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent buttonEvent, double deltaX, double deltaY) {
        if (this.isFocused()) {
            int pos = textRenderer.plainSubstrByWidth(content, (int) (buttonEvent.x() - getX() - 2)).length();
            updateSelEnd(pos);
            arrowCursor = pos;
        }
        return super.mouseDragged(buttonEvent, deltaX, deltaY);
    }

    public void swapStartEnd() {
        int temp1 = selStartIndex;
        updateSelStart(selEndIndex);
        updateSelEnd(temp1);

    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        boolean ctrl;
        if (this.isFocused()) {
            if (selEndIndex < selStartIndex) {
                swapStartEnd();
            }
            Minecraft mc = Minecraft.getInstance();
            if (keyEvent.isSelectAll()) {
                this.updateSelStart(0);
                this.updateSelEnd(content.length());
            } else if (keyEvent.isCopy()) {
                mc.keyboardHandler.setClipboard(this.content.substring(selStartIndex, selEndIndex));
            } else if (keyEvent.isPaste()) {
                content = content.substring(0, selStartIndex) + mc.keyboardHandler.getClipboard() + content.substring(selEndIndex);
                if (onChange != null) {
                    onChange.accept(content);
                }
                updateSelEnd(selStartIndex + mc.keyboardHandler.getClipboard().length());
                arrowCursor = selStartIndex + mc.keyboardHandler.getClipboard().length();
            } else if (keyEvent.isCut()) {
                mc.keyboardHandler.setClipboard(this.content.substring(selStartIndex, selEndIndex));
                content = content.substring(0, selStartIndex) + content.substring(selEndIndex);
                if (onChange != null) {
                    onChange.accept(content);
                }
                updateSelEnd(selStartIndex);
                arrowCursor = selStartIndex;
            }
            switch (keyEvent.key()) {
                case GLFW.GLFW_KEY_BACKSPACE:
                    if (selStartIndex == selEndIndex && selStartIndex > 0) {
                        updateSelStart(selStartIndex - 1);
                    }
                    content = content.substring(0, selStartIndex) + content.substring(selEndIndex);
                    if (onChange != null) {
                        onChange.accept(content);
                    }
                    updateSelEnd(selStartIndex);
                    arrowCursor = selStartIndex;
                    break;
                case GLFW.GLFW_KEY_DELETE:
                    if (selStartIndex == selEndIndex && selStartIndex < content.length()) {
                        updateSelEnd(selEndIndex + 1);
                    }
                    content = content.substring(0, selStartIndex) + content.substring(selEndIndex);
                    if (onChange != null) {
                        onChange.accept(content);
                    }
                    updateSelEnd(selStartIndex);
                    arrowCursor = selStartIndex;
                    break;
                case GLFW.GLFW_KEY_HOME:
                    updateSelStart(0);
                    updateSelEnd(0);
                    break;
                case GLFW.GLFW_KEY_END:
                    this.updateSelStart(content.length());
                    this.updateSelEnd(content.length());
                    break;
                case GLFW.GLFW_KEY_LEFT:
                    ctrl = !keyEvent.hasControlDown();
                    if (arrowCursor > 0) {
                        if (arrowCursor < selEndIndex) {
                            updateSelStart(--arrowCursor);
                            if (ctrl) {
                                updateSelEnd(selStartIndex);
                            }
                        } else if (arrowCursor >= selEndIndex) {
                            updateSelEnd(--arrowCursor);
                            if (ctrl) {
                                updateSelStart(selEndIndex);
                            }
                        }
                    }
                    break;
                case GLFW.GLFW_KEY_RIGHT:
                    ctrl = !keyEvent.hasControlDown();
                    if (arrowCursor < content.length()) {
                        if (arrowCursor < selEndIndex) {
                            updateSelStart(++arrowCursor);
                            if (ctrl) {
                                updateSelEnd(selStartIndex);
                            }
                        } else {
                            updateSelEnd(++arrowCursor);
                            if (ctrl) {
                                updateSelStart(selEndIndex);
                            }
                        }
                    }
                    break;
                default:
            }
        }
        return super.keyPressed(keyEvent);
    }

    @Override
    public boolean charTyped(CharacterEvent characterEvent) {
        if (selEndIndex < selStartIndex) {
            swapStartEnd();
        }
        String newContent = content.substring(0, selStartIndex) + characterEvent.codepoint() + content.substring(selEndIndex);
        if (newContent.matches(mask)) {
            content = newContent;
            if (onChange != null) {
                onChange.accept(content);
            }
            updateSelStart(selStartIndex + 1);
            arrowCursor = selStartIndex;
            updateSelEnd(arrowCursor);
        }
        return false;
    }

    public void setSelected(boolean sel) {
        this.setFocused(sel);
    }

    @Override
    protected void renderMessage(GuiGraphics drawContext) {
        drawContext.fill(selStart, height > 9 ? getY() + 2 : getY(), Math.min(selEnd, getX() + width - 2), (height > 9 ? getY() + 2 : getY()) + textRenderer.lineHeight, selColor);
        drawContext.drawString(textRenderer, textRenderer.plainSubstrByWidth(content, width - 4), getX() + 2, height > 9 ? getY() + 2 :
                getY(), textColor);
    }

}
