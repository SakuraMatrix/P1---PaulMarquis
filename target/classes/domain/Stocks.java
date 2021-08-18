package domain;

public class Stocks {
    private String ticker; 
    private double price;  
   
    public Stocks(String tickerInput, double priceInput){ 
        this.ticker = tickerInput; 
        this.price = priceInput;
    }  

    public void test(){ 
        System.out.println("Stock Function");
    }


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

} 
