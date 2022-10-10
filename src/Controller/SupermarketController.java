package Controller;

import Model.Good;
import Model.Order;
import Model.OrderItem;
import Model.OrderStatus;

import java.util.*;

public class SupermarketController {

    private Map<String,Good> goods;
    private List<Order> orders;

    public Map<String, Good> getGoods() {
        return goods;
    }

    public List<Order> getOrders() {
        return orders;
    }
    private static SupermarketController instance = new SupermarketController();
    private SupermarketController() {
        goods = new HashMap<>();
        orders = new ArrayList<>();
    }

    public Good addGood(String goodName, boolean isCountable, int count, double amount, int buyPrice, int salePrice) {
        Good good = goods.get(goodName);
        if (good == null) {
            good = new Good(goodName, salePrice, buyPrice, isCountable, count, amount);
            goods.put(goodName,good);
        } else {
            good.setCount(good.getCount() + count);
            good.setAmount(good.getAmount() + amount);
            good.setBuyPrice(buyPrice);
            good.setSellPrice(salePrice);
        }

        return good;
    }

    public Order newOrder(String consumerName){
        Order order = new Order(consumerName);
        orders.add(order);
        return order;
    }

    public Good addItemToOrder(Order order,String goodName, int count, double amount) throws ItemUnavailableException, ItemNotEnoughException {
        Good requestedGood = goods.get(goodName);
        if (requestedGood == null)
            throw new ItemUnavailableException("Item Is Not Available");
        if(requestedGood.isCountable()){
            if(count <= requestedGood.getCount()){
                OrderItem orderItem = new OrderItem(requestedGood, count, 0);
                order.addOrderItemToOrderItemsList(orderItem);
            } else {
                throw new ItemNotEnoughException("Item Not Enough", requestedGood);
            }
        } else {
            if(amount <= requestedGood.getAmount()){
                OrderItem orderItem = new OrderItem(requestedGood,0, amount);
                order.addOrderItemToOrderItemsList(orderItem);
            } else {
                throw new ItemNotEnoughException("Item Not Enough", requestedGood);
            }
        }
        return requestedGood;
    }

    public void chrckoutOrder(Order order, boolean isCash){
        order.setCash(isCash);
        for (OrderItem item : order.getOrderItems()) {
            if (item.getCount() != 0){
                item.getGood().setCount(item.getGood().getCount() - item.getCount());
            } else if (item.getAmount() != 0) {
                item.getGood().setAmount(item.getGood().getAmount() - item.getAmount());
            }
        }
        order.setOrderStatus(OrderStatus.DONE);
    }

    public int getTotalSales(String option){
        return orders.stream().filter(order -> {
            if (option == null)
                return true;
            if (option.equalsIgnoreCase("cash")){
                return order.isCash();
            } else if (option.equalsIgnoreCase("credit")){
                return !order.isCash();
            }
            return true;
        })
                .map(order -> order.getTotalProfit())
                .reduce((a,b) -> a+b)
                .orElse(0);
    }

    public int getTotalProfit(){
        Optional<Integer> sum = orders.stream().map(order -> order.getTotalProfit()).reduce((a,b) -> a + b);
        return sum.orElse(0);
    }

    public static class ItemUnavailableException extends Exception{
        public ItemUnavailableException(String message){super(message);}
    }
    public static class ItemNotEnoughException extends Exception{
        Good good;
        public Good getGood() {
            return good;
        }//
        public ItemNotEnoughException(String messege, Good good) {
            super(messege);
            this.good = good;
        }
    }
}
