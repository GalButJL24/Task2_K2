public class FractionTest implements  Fractionable{
    private  int num;
    private  int denum;
    int cmpCnt = 0;
    int cmpCntMutator = 0;
    public FractionTest(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Cache
    @Override
    public double doubleValue() {
        cmpCnt++;
        return (double) num/denum;
    }

    @Mutator
    @Override
    public void setNum(int num) {
        this.num = num;
        cmpCntMutator++;
    }

    @Mutator
    @Override
    public void setDenum(int denum) {
        this.denum = denum;
        cmpCntMutator++;
    }

    @Cache
    @Override
    public double multiplyValue() {
        return (double) num*denum;
    }


    @Override
    public void setNumForTest(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        cmpCnt++;
        return "FractionTest{" +
                "num=" + num +
                ", denum=" + denum +
                ", cmpCnt=" + cmpCnt +
                '}';
    }


}
