package thb.si;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Mandelbrot {

    private byte[] contentBytes;
    private Integer w, h, it;

    public Mandelbrot(){}


    public void create(Integer w, Integer h, Integer it){

        int width=w,height=h,max=it;
        BufferedImage image=new BufferedImage(width,height,
                BufferedImage.TYPE_INT_RGB);
        int black=0;
        int[]colors=new int[max];
        for(int i=0;i<max;i++){
            colors[i]= Color.HSBtoRGB(i/256f,1,i/(i+8f));
        }
        for(int row=0;row<height;row++){
            for(int col=0;col<width;col++){
                double c_re=(col-width/2)*4.0/width;
                double c_im=(row-height/2)*4.0/width;
                double x=0,y=0;
                double r2;
                int iteration=0;
                while(x*x+y*y<4&&iteration<max){
                    double x_new=x*x-y*y+c_re;
                    y=2*x*y+c_im;
                    x=x_new;
                    iteration++;
                }
                if(iteration<max)image.setRGB(col,row,colors[iteration]);
                else image.setRGB(col,row,black);
            }
        }
        write(image, "png", new ByteArrayOutputStream());
        // setContentBytes(baos.toByteArray());
        // ImageIO.write(image,"png",new File("./mandelbrot.png"));
    }
    private void write(BufferedImage image, String format, ByteArrayOutputStream baos){
        try {
            ImageIO.write(image, format, baos);
            setContentBytes(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getIt() {
        return it;
    }

    public void setIt(Integer it) {
        this.it = it;
    }

    public byte[] getContentBytes() {
        return contentBytes;
    }

    public void setContentBytes(byte[] contentBytes) {
        this.contentBytes = contentBytes;
    }
}
