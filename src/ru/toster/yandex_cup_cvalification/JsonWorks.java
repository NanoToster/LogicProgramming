package ru.toster.yandex_cup_cvalification;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ivan Rovenskiy
 * 25 October 2020
 */
public class JsonWorks {
    private static final List<Offer> offerList = new ArrayList<>();

    public static void main(String[] args) {
        int outputArrayLength;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            final String[] firstLine = bufferedReader.readLine().split(" ");
            outputArrayLength = Integer.parseInt(firstLine[1]);

            final JSONParser jsonParser = new JSONParser();
            for (int i = 0; i < Integer.parseInt(firstLine[0]); i++) {
                final JSONObject jsonObject = (JSONObject) jsonParser.parse(bufferedReader.readLine());
                final JSONArray offersJsonArray = (JSONArray) jsonObject.get("offers");

                Iterator<JSONObject> iterator = offersJsonArray.iterator();
                while (iterator.hasNext()) {
                    final JSONObject jsonOfferObject = iterator.next();

                    offerList.add(new Offer(
                            jsonOfferObject.get("offer_id").toString(),
                            Integer.parseInt(jsonOfferObject.get("market_sku").toString()),
                            Integer.parseInt(jsonOfferObject.get("price").toString())
                    ));
                }
            }
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }

        final Comparator<Offer> offerComparator =
                Comparator.comparingInt(Offer::getPrice)
                        .thenComparing(Offer::getOfferId);
        offerList.sort(offerComparator);

        final JSONArray resultJsonArray = new JSONArray();
        if (offerList.size() <= outputArrayLength) {
            resultJsonArray.addAll(
                    offerList.stream()
                            .map(offer -> {
                                final JSONObject jsonObject = new JSONObject();
                                jsonObject.put("offer_id", offer.getOfferId());
                                jsonObject.put("market_sku", String.valueOf(offer.getMarketSku()));
                                jsonObject.put("price", String.valueOf(offer.getPrice()));
                                return jsonObject;
                            })
                            .collect(Collectors.toList()));
        } else {
            resultJsonArray.addAll(
                    offerList.subList(0, outputArrayLength).stream()
                            .map(offer -> {
                                final JSONObject jsonObject = new JSONObject();
                                jsonObject.put("offer_id", offer.getOfferId());
                                jsonObject.put("market_sku", String.valueOf(offer.getMarketSku()));
                                jsonObject.put("price", String.valueOf(offer.getPrice()));
                                return jsonObject;
                            })
                            .collect(Collectors.toList()));
        }

        final JSONObject outputJson = new JSONObject();
        outputJson.put("offers", resultJsonArray);

        System.out.println(outputJson.toString());
    }

    private static class Offer {
        private final String offerId;
        private final int marketSku;
        private final int price;

        public Offer(String offerId, int marketSku, int price) {
            this.offerId = offerId;
            this.marketSku = marketSku;
            this.price = price;
        }

        public String getOfferId() {
            return offerId;
        }

        public int getMarketSku() {
            return marketSku;
        }

        public int getPrice() {
            return price;
        }
    }
}
