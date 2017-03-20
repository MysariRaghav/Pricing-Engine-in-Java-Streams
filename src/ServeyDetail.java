import java.math.BigDecimal;
import java.util.Map.Entry;

public class ServeyDetail {

	
	public static class Builder
	{
		private String productCode;
		private String company;
		private BigDecimal price;
		
		public Builder(String productCode) {
			
			this.productCode=productCode;
		}
		
		public ServeyDetail build()
		{
			return new ServeyDetail(this);
		}
		/*public Builder setProductCode(String productCode) {			
			this.productCode= productCode;
			return this;			
		}*/
		public Builder setCompany(String company) {			
			this.company= company;
			return this;			
		}
		public Builder setPrice(BigDecimal price) {			
			this.price= price;
			return this;			
		}
		
	}
	
	private final String productCode;
	private final String company;
	private final BigDecimal price;
	
	private ServeyDetail(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.productCode= builder.productCode;
		this.company= builder.company;
		this.price= builder.price;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public String getCompany() {
		return company;
	}
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "ServeyDetail [productCode=" + productCode + ", company=" + company + ", price=" + price + "]";
	}

	public Object priceLT0() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static int comparePriceFreq(Entry<String, Long> o1, Entry<String, Long> o2) {
		// TODO Auto-generated method stub
		return o1.getValue().compareTo(o2.getValue()) == 0 
				? o2.getKey().split("-->")[1].compareTo(o1.getKey().split("-->")[1]) 
						: o1.getValue().compareTo(o2.getValue());
	}
	
	
}
