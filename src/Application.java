import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.soap.Detail;

import java.util.List;

public class Application {

	public static void main(String[] args) {
		
		Map<String, List<ServeyDetail>> serveyDetails = null;
		Map<String, ProductClassification> productClassifications= null;
		List<ProductPrice> productPrices= null;
		
		String productClassificationsText=  "mp3player H H\r\nssd L L";
		String serveyDetailsText= "ssd W 11.0\r\nmp3player Z 50.0\r\nssd X 12.2\r\nssd X 12.0\r\nmp3player X 60.0\r\nmp3player Y 20.0\r\nssd V 10.0\r\nssd Y 11.0\r\nssd Z 12.0";
		//\r\nssd X 12.0\r\nmp3player X 60.0\r\nmp3player Y 20.0\r\nssd V 10.0\r\nssd Y 11.0\r\nssd Z 12.0
		
		
		
		serveyDetails = Pattern.compile(System.lineSeparator(), Pattern.LITERAL)
		               .splitAsStream(serveyDetailsText)
		               .map(s -> s.split(" "))
		               .map(a -> new ServeyDetail.Builder(a[0]).setCompany(a[1]).setPrice(new BigDecimal(a[2])).build())
		               .collect(Collectors.groupingBy(ServeyDetail::getProductCode, Collectors.mapping(Function.identity(),  Collectors.toList())));
		               //.toArray(ServeyDetail[]::new);
		
		productClassifications = Pattern.compile(System.lineSeparator(), Pattern.LITERAL)
	               .splitAsStream(productClassificationsText)
	               .map(s -> s.split(" "))
	               .map(a -> new ProductClassification.Builder(a[0]).setSupply(a[1].charAt(0)).setDemand(a[2].charAt(0)).build())
	               .collect(Collectors.toMap(ProductClassification::getProductCode, Function.identity()));
	               //.toArray(ServeyDetail[]::new);
		
		for(Entry<String, ProductClassification> ser: productClassifications.entrySet())
		{
			System.out.println("\n"+ ser.getKey() +"  " + ser.getValue() +"\n");
		}
		
		List<ProductPrice> productPriceList = computePrice(serveyDetails, productClassifications, productPrices);
		
		System.out.println(productPriceList);
		
		//System.out.println(details);
		
		
		
		
	}

	private static List<ProductPrice> computePrice(Map<String, List<ServeyDetail>> serveyDetails,
			Map<String, ProductClassification> productClassifications, 
			List<ProductPrice> productPrices) {
		// TODO Auto-generated method stub
		
		Map<String, BigDecimal> productPriceMap = serveyDetails.values().stream()
		.map(x -> {x.removeIf(detail -> (!(detail.getPrice().doubleValue() < 0.5*avgPrices(x) || detail.getPrice().doubleValue() > 0.5*avgPrices(x)))); return x;})
		.flatMap(x -> x.stream())
		.collect(Collectors.groupingBy(
	            ServeyDetail::getProductCode,
	            Collectors.collectingAndThen(
	              Collectors.groupingBy(serveyDetail -> serveyDetail.getProductCode() + "-->" +serveyDetail.getPrice(), Collectors.counting()),
	              map -> {
	            	  
	            	  Stream<Entry<String, Long>> priceFreqStream = map.entrySet().stream();
	            	  Entry<String, Long> choosenPriceFreq = priceFreqStream.max(ServeyDetail::comparePriceFreq).get();
	            	  
	            	  priceFreqStream = map.entrySet().stream();
	            	  long multiFreq = priceFreqStream.filter(priceFreq -> priceFreq.getValue().equals(choosenPriceFreq.getValue()))
	            	  				 .count();
	            	  
	            	  BigDecimal choosenPrice =new BigDecimal(choosenPriceFreq.getKey().split("-->")[1]);
	            	  
	            	  if(multiFreq>1)
	            	  	return choosenPrice.add(choosenPrice.multiply(supplyDemandFactor(choosenPriceFreq.getKey().split("-->")[0], productClassifications)));
	            	  	
		            return choosenPrice;
	              }
	            )
	         ));
		
		List<ProductPrice> productPriceList = productPriceMap.entrySet().stream().map(x-> new ProductPrice.Builder(x.getKey()).setPrice(x.getValue()).build()).collect(Collectors.toList());
		
		return productPriceList;
		/*Iterator<BigDecimal> i = akjldfs.values().iterator();
		System.out.println(i.next() +"-->"+ i.next());*/
		/*new Comparator<Entry<BigDecimal, Long>>() {

			@Override
			public int compare(Entry<BigDecimal, Long> o1, Entry<BigDecimal, Long> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().compareTo(o2.getValue()) == 0? o2.getKey().compareTo(o1.getKey()) :o1.getValue().compareTo(o2.getValue());
			}
  		}*/
		
	/*	new Comparator<Entry<>>() {
		}*/

//		.collect(Collectors.groupingBy(detail -> detail , Collectors.counting()))
		//Map.Entry.comparingByValue()
/*		.flatMap(x -> x.stream())
		.collect(Collectors.groupingBy(ServeyDetail::getPrice, Collectors.mapping(Function.identity(),  Collectors.toList())));*/
		
	}

	private static BigDecimal supplyDemandFactor(String productCode,
			Map<String, ProductClassification> productClassifications) {
		// TODO Auto-generated method stub
		
		ProductClassification productClassification= productClassifications.get(productCode);
		char supply= productClassification.getSupply(), demand= productClassification.getDemand();
		
		if(supply == 'L' && demand== 'H') return BigDecimal.valueOf(0.05d);
		else if(supply == 'H' && demand== 'L') return BigDecimal.valueOf(-0.05d);
		else if(supply == 'L' && demand== 'L') return BigDecimal.valueOf(0.1d);
		return BigDecimal.ZERO;
	}

	private static double avgPrices(List<ServeyDetail> x) {
		// TODO Auto-generated method stub
		
		return x.stream()
		.filter(detail -> detail.getPrice()
		.compareTo(BigDecimal.ZERO)>0)
		.mapToDouble(detail -> detail.getPrice().doubleValue())
		.average().getAsDouble();
		
		
	}
}
