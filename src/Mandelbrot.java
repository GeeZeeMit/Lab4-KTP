import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator
{
    private Complex z =
            new Complex(0, 0);
    private Complex c =
            new Complex(0, 0);
    public static final int MAX_ITERATIONS
            = 2000;
    private class Complex
    {
        private double real, ipic;
        private Complex(double real, double imag) { this.real = real; this.ipic = imag; }
        private double abs() { return real * real + ipic * ipic; }
        private Complex sum(Complex c)
        {
            return new Complex(this.real + c.real, this.ipic + c.ipic);
        }
        private Complex times(Complex c)
        {
            double real = this.real * c.real - this.ipic * c.ipic;
            double imag = this.real * c.ipic + this.ipic * c.real;
            return new Complex(real,imag);
        }
    }
    public void getInitialRange(Rectangle2D.Double rect)
    {
        rect.setRect(-2, -1.5, 3,3);
    }
    public int numIterations(double x, double y)
    {
        z.real = 0; z.ipic = 0; c.real = x; c.ipic = y;
        for (int IterNum = 0; IterNum < MAX_ITERATIONS; IterNum++)
        {
            z = z.times(z).sum(c);
            if (z.abs() > 4) return IterNum;
        }
        return -1;
    }
}
