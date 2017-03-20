public class ProductClassification {

	public static class Builder
	{
		private String productCode;
		private char supply;
		private char demand;
		
		public Builder(String productCode) {
			
			this.productCode=productCode;
		}
		
		public ProductClassification build()
		{
			return new ProductClassification(this);
		}
		/*public Builder setProductCode(String productCode) {			
			this.productCode= productCode;
			return this;			
		}*/
		public Builder setSupply(char supply) {			
			this.supply= supply;
			return this;			
		}
		public Builder setDemand(char demand) {			
			this.demand= demand;
			return this;			
		}
		
	}
	
	private final String productCode;
	private final char demand;
	private final char supply;
	
	private ProductClassification(Builder builder) {
		// TODO Auto-generated constructor stub
		
		this.productCode= builder.productCode;
		this.demand= builder.demand;
		this.supply= builder.supply;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public char getDemand() {
		return demand;
	}
	public char getSupply() {
		return supply;
	}

	@Override
	public String toString() {
		return "ProductClassification [productCode=" + productCode + ", demand=" + demand + ", supply=" + supply + "]";
	}
	
	
	
}
