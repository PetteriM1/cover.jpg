package me.petterim1.coverjpg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("cover.jpg version 1.0");
        System.out.println("Made by PetteriM1");
        System.out.println("---------------------");
        String path = null;
        if (args.length > 0) {
            path = args[0];
            System.out.println("Path set to " + path);
        }
        if (path == null) {
            path = new File("").getAbsolutePath();
            System.out.println("Using default path");
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        boolean needsInput = true;
        String input = null;
        while (running) {
            if (needsInput) {
                System.out.println("Paste a link to the image you want to save");
                input = scanner.nextLine();
                while (!input.toLowerCase().startsWith("http") || !input.contains("://") || !input.contains(".")) {
                    System.out.println("Invalid link! Please paste a direct link to the image");
                    input = scanner.nextLine();
                }
            }
            System.out.println("Downloading image...");
            try {
                BufferedImage image = ImageIO.read(new URL(input));
                if (image == null) throw new NullPointerException("image == null");
                System.out.println("Resizing image...");
                BufferedImage resized = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = resized.createGraphics();
                graphics.drawImage(image.getScaledInstance(256, 256, Image.SCALE_SMOOTH), 0, 0, null);
                graphics.dispose();
                System.out.println("Saving image...");
                File out = new File(path + File.separatorChar + "cover.jpg");
                ImageIO.write(resized, "jpg", out);
                System.out.println("Done! Image saved to " + path);
                System.out.println("Remember to move it before saving a new one");
            } catch (Exception ex) {
                System.out.println("Failed!");
                ex.printStackTrace();
            }
            System.out.println("Do you want to save another image? (y/n)");
            try {
                input = scanner.nextLine();
                if (input.toLowerCase().startsWith("y")) {
                    needsInput = true;
                    continue;
                } else if (input.toLowerCase().startsWith("http") && input.contains("://") && input.contains(".")) {
                    needsInput = false;
                    continue;
                }
            } catch (Exception ignore) {
            }
            running = false;
        }
    }
}
