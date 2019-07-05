
import java.io.File;

import javax.swing.JFileChooser;

import ij.*;
import ij.io.FileSaver;
import ij.io.Opener;
import ij.plugin.ContrastEnhancer;
import ij.plugin.ZProjector;
import ij.plugin.filter.Convolver;
import ij.plugin.filter.ImageMath;
import ij.process.*;
//import java.awt.image.BufferedImage;
public class GaborFilter
{



		
//permet de dimensionner l'image
//BufferedImage getImage; //ImagePlus
static ImagePlus getImage; //ImagePlus

public GaborFilter(String path)
{
// Constructeur
ImageStack kernels;
double sigma = 8.0;
double gamma = 0.25;
// Phase
double psi = Math.PI / 4.0 * 0;
// Fréquence de la composante sinusoïdale
double Fx = 3.0;
// Nombre de l'orientation des différents angles utilisés
int nAngles = 5;
// Ouvrir l'image et le copier
Opener open = new Opener();
ImagePlus originalImage = open.openImage(path);
originalImage = new ImagePlus(originalImage.getTitle(), originalImage.getProcessor().convertToFloat());
int width = originalImage.getWidth();
int height = originalImage.getHeight();
// Appliquer la composante pour les gausiennes
double sigma_x = sigma;
double sigma_y = sigma / gamma;
// Décider la taille of du filtre basé sur sigma
int largerSigma = (sigma_x > sigma_y) ? (int) sigma_x : (int) sigma_y;
if(largerSigma < 1)
largerSigma = 1;
ImageProcessor ip = originalImage.getProcessor().duplicate(); //permet de prendre la marice des pixels
double sigma_x2 = sigma_x * sigma_x;
double sigma_y2 = sigma_y * sigma_y;
// Dimensions de filtre
int filterSizeX = 19;
int filterSizeY = 19;
int middleX = (int) Math.round(filterSizeX / 2);
int middleY = (int) Math.round(filterSizeY / 2);
ImageStack is = new ImageStack(width, height);
kernels = new ImageStack(filterSizeX, filterSizeY);
double rotationAngle = Math.PI/(double)nAngles;
// Faire une rotation de 0 à 180 degrés
for (int i=0; i<nAngles; i++)
{
double theta = rotationAngle * i;
FloatProcessor filter = new FloatProcessor(filterSizeX, filterSizeY);
for (int x=-middleX; x<=middleX; x++)
{
for (int y=-middleY; y<=middleY; y++)
{
double xPrime = (double)x * Math.cos(theta) + (double)y * Math.sin(theta);
double yPrime = (double)y * Math.cos(theta) - (double)x * Math.sin(theta);
double a = 1.0 / ( 2.0 * Math.PI * sigma_x * sigma_y ) *
Math.exp(-0.5 * (xPrime*xPrime / sigma_x2 + yPrime*yPrime / sigma_y2) );
double c = Math.cos( 2.0 * Math.PI * (Fx * xPrime) / filterSizeX + psi);
filter.setf(x+middleX, y+middleY, (float)(a*c) );
}
}
kernels.addSlice("kernel angle = " + theta, filter);
}
// Appliquer les kernels c-a-d les noyaux de convolution
for (int i=0; i<nAngles; i++)
{
Convolver c = new Convolver();
float[] kernel = (float[]) kernels.getProcessor(i+1).getPixels();
ip = originalImage.getProcessor().duplicate();
c.convolveFloat(ip, kernel, filterSizeX, filterSizeY);
is.addSlice("gabor angle = " + i, ip);
}
// Normaliser le filtre
ContrastEnhancer c = new ContrastEnhancer();
for(int i=1 ; i <= is.getSize(); i++)
{
c.stretchHistogram(is.getProcessor(i), 0.4);
}
ImagePlus projectStack = new ImagePlus("filtered stack",is);
IJ.run(projectStack, "Enhance Contrast", "saturated=0.4 normalize normalize_all");
ImageStack resultStack = new ImageStack(width, height);
ZProjector zp = new ZProjector(projectStack);
zp.setStopSlice(is.getSize());
for (int i=0; i<=5; i++)
{
zp.setMethod(i);
zp.doProjection();
resultStack.addSlice("Gabor_" + i
+"_"+sigma+"_" + gamma + "_"+ (int) (psi / (Math.PI/4) ) +"_"+Fx,
zp.getProjection().getChannelProcessor());
}
// Récupérer l'image résultante
ImagePlus ModifiedPicture = new ImagePlus ("Gabor stack projections", resultStack) ;
IJ.run(ModifiedPicture, "Enhance Contrast", "saturated=0.4 normalize normalize_all");
//getImage = ModifiedPicture.getBufferedImage();
getImage = ModifiedPicture;


}
public ImagePlus getpic()
{
	return getImage;
	}



}