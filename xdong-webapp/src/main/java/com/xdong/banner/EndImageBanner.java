package com.xdong.banner;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiBackground;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiColors;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiPropertySource;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

/**
 * 类EndBanner.java的实现描述：TODO 类实现描述
 * 
 * @author wanglei 2018年4月29日 下午10:30:24
 */
public class EndImageBanner {

    private static final String   location            = "classpath:bootimage/2.jpg";

    private Resource              resource;

    private static final double[] RGB_WEIGHT          = { 0.2126d, 0.7152d, 0.0722d };

    private static final char[]   PIXEL               = { ' ', '6', '6', '6', '6', '6', '6', '6', '@' };

    private static final int      LUMINANCE_INCREMENT = 10;

    private static final int      LUMINANCE_START     = LUMINANCE_INCREMENT * PIXEL.length;

    public EndImageBanner(){
        ResourceLoader resourceLoader = new DefaultResourceLoader(ClassUtils.getDefaultClassLoader());
        this.resource = resourceLoader.getResource(location);
    }

    public void printBanner(PrintStream out) throws IOException {
        int width = 76;
        int height = 0;
        int margin = 2;
        boolean invert = true;
        BufferedImage image = readImage(width, height);
        printBanner(image, margin, invert, out);
    }

    private BufferedImage readImage(int width, int height) throws IOException {
        InputStream inputStream = this.resource.getInputStream();
        try {
            BufferedImage image = ImageIO.read(inputStream);
            return resizeImage(image, width, height);
        } finally {
            inputStream.close();
        }
    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        if (width < 1) {
            width = 1;
        }
        if (height <= 0) {
            double aspectRatio = (double) width / image.getWidth() * 0.5;
            height = (int) Math.ceil(image.getHeight() * aspectRatio);
        }
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Image scaled = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        resized.getGraphics().drawImage(scaled, 0, 0, null);
        return resized;
    }

    private void printBanner(BufferedImage image, int margin, boolean invert, PrintStream out) {
        AnsiElement background = (invert ? AnsiBackground.BLACK : AnsiBackground.DEFAULT);
        out.print(AnsiOutput.encode(AnsiColor.DEFAULT));
        out.print(AnsiOutput.encode(background));
        out.println();
        out.println();
        AnsiColor lastColor = AnsiColor.DEFAULT;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int i = 0; i < margin; i++) {
                out.print(" ");
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y), false);
                AnsiColor ansiColor = AnsiColors.getClosest(color);
                if (ansiColor != lastColor) {
                    out.print(AnsiOutput.encode(ansiColor));
                    lastColor = ansiColor;
                }
                out.print(getAsciiPixel(color, invert));
            }
            out.println();
        }
        out.print(AnsiOutput.encode(AnsiColor.DEFAULT));
        out.print(AnsiOutput.encode(AnsiBackground.DEFAULT));
        out.println();
    }

    private char getAsciiPixel(Color color, boolean dark) {
        double luminance = getLuminance(color, dark);
        for (int i = 0; i < PIXEL.length; i++) {
            if (luminance >= (LUMINANCE_START - (i * LUMINANCE_INCREMENT))) {
                return PIXEL[i];
            }
        }
        return PIXEL[PIXEL.length - 1];
    }

    private int getLuminance(Color color, boolean inverse) {
        double luminance = 0.0;
        luminance += getLuminance(color.getRed(), inverse, RGB_WEIGHT[0]);
        luminance += getLuminance(color.getGreen(), inverse, RGB_WEIGHT[1]);
        luminance += getLuminance(color.getBlue(), inverse, RGB_WEIGHT[2]);
        return (int) Math.ceil((luminance / 0xFF) * 100);
    }

    private double getLuminance(int component, boolean inverse, double weight) {
        return (inverse ? 0xFF - component : component) * weight;
    }
}
