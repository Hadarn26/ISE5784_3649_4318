package primitives;

public class Material {
    public Double3 kD=Double3.ZERO;
    public Double3 kS=Double3.ZERO;
    public int nShininess=0;
    public Double3 kT=Double3.ZERO;//שקיפות
    public Double3 kR=Double3.ZERO;//השתקפות

    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }



}
