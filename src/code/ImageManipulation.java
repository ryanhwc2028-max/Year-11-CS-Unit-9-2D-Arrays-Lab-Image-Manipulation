package code;

import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage image = new APImage("cyberpunk2077.jpg");
        image.draw();
        grayScale("cyberpunk2077.jpg"); // i need to call it here since vscode only has the run button for main methods
        blackAndWhite("cyberpunk2077.jpg");
        edgeDetection("cyberpunk2077.jpg", 20);
        reflectImage("cyberpunk2077.jpg");
        rotateImage("cyberpunk2077.jpg");
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (Pixel pixel : image){
            int x = getAverageColour(pixel);
            pixel.setBlue(x);
            pixel.setGreen(x);
            pixel.setRed(x);
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        return (r+g+b)/3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (Pixel pixel : image){
            int x = getAverageColour(pixel);
            if (x < 128){
                pixel.setBlue(0);
                pixel.setGreen(0);
                pixel.setRed(0);
            } else {
                pixel.setBlue(255);
                pixel.setGreen(255);
                pixel.setRed(255);
            }
        }
        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        int width  = image.getWidth();
        int height = image.getHeight(); //since we can't use a enhanced for loop as you need to check the pixels on the side
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Pixel here = image.getPixel(x, y);//we need to use getPixel() from 138 of APImage.java
                if (x == 0 || y == 0){ //check if its one of the edge ones
                    here.setRed(255);
                    here.setGreen(255);
                    here.setBlue(255);
                } else {
                    Pixel left = image.getPixel(x-1,y);
                    Pixel down = image.getPixel(x,y-1); 

                    if (Math.abs(getAverageColour(here) - getAverageColour(left)) > threshold || Math.abs(getAverageColour(here) - getAverageColour(down)) > threshold){ //check if the difference is above the threshold of 20. Math.abs gets the absolute value so you always have the difference
                        here.setRed(0);
                        here.setBlue(0);
                        here.setGreen(0);
                    } else{
                        here.setRed(255);
                        here.setGreen(255);
                        here.setBlue(255);
                    }

                }



            }
        }
        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        int width  = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width/2; x++){ //since you only need to swap half of the pixels with each other
                Pixel here = image.getPixel(x, y);
                Pixel there = image.getPixel(width-1-x, y);//width -1-x returns the flipped version since width starts at 0 u need to minus 1
                int r = here.getRed(); //u have to set one of them as variables first, or else it will flip over the same thing (cuz here changes to there and then there changes to here, which is the same value)
                int g = here.getGreen();
                int b = here.getBlue();
                here.setRed(there.getRed()); //switch all the RGB values around
                here.setGreen(there.getGreen());
                here.setBlue(there.getBlue());
                there.setRed(r);
                there.setGreen(g);
                there.setBlue(b);
            }
        }
        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        int width  = image.getWidth();
        int height = image.getHeight();
        int newWidth = height;
        int newHeight = width;
        APImage rotated = new APImage(newWidth, newHeight);
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Pixel before = image.getPixel(x, y);
                int newX = height - 1 - y; //starts at index 0 so -1
                int newY = x;
                Pixel newPixel = rotated.getPixel(newX, newY);
                newPixel.setRed(before.getRed()); //set the pixels on rotated
                newPixel.setGreen(before.getGreen());
                newPixel.setBlue(before.getBlue());
            }
        }
        rotated.draw();
    }

}
