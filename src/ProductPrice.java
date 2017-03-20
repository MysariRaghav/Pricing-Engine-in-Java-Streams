import java.math.BigDecimal;


public class ProductPrice {

	public static class Builder
	{
		private String productCode;
		private BigDecimal price;
		
		public Builder(String productCode) {
			
			this.productCode=productCode;
		}
		
		public ProductPrice build()
		{
			return new ProductPrice(this);
		}
		/*public Builder setProductCode(String productCode) {			
			this.productCode= productCode;
			return this;			
		}*/
		public Builder setPrice(BigDecimal price) {			
			this.price= price;
			return this;			
		}
		
	}
	
	private final String productCode;
	private final BigDecimal price;
	
	private ProductPrice(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.productCode= builder.productCode;
		this.price= builder.price;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "ProductPrice [productCode=" + productCode + ", price=" + price + "]";
	}

	
	
	
}
