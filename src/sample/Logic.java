package sample;

public class Logic {
    static final double PI = Math.PI;
    final double dX = PI/20;
    double[] x = new double[41];
    double[] f = new double[41];
    double[] v = new double[41];
    double[] a = new double[41];
    double dt = 0.05;
    public Logic(){
        x[0] = 0;
        for(int i = 0; i<f.length; ++i){
            f[i] = Math.sin(dX*i);
            v[i] = 0;
            if(i!=0)x[i] = x[i-1] + dX;
        }
        for(int i = 0; i<a.length; ++i){
            if(i==0||i==40){
                a[i] = 0;
                continue;
            }
            a[i] = (f[i+1]-2*f[i]+f[i-1])/Math.pow(dX,2);
        }
    }

    public void move(){
        for(int i = 0; i<v.length; ++i){
            v[i]+=a[i]*dt;
            f[i]+=v[i]*dt;
        }
        for(int i = 0; i<a.length; ++i){
            if(i==0||i==40){
                a[i] = 0;
                continue;
            }
            a[i] = (f[i+1]-2*f[i]+f[i-1])/Math.pow(dX,2);
        }
    }
}
